package genetic;

import java.util.ArrayList;
import java.util.HashSet;

import eusolver.EuSolverExecutor;
import eusolver.EuSolverSyntax;
import logdata.LogData;
import logdata.LogDataSelector;
import synth.SynthNode;
import util.MultiTasks;

public class InitPopulation {

    final static int pop_size = 30;
    final static int init_example_num = 10;
    HashSet<LogData> synthSet;
    ArrayList<String> paNames;
    String funName;
    int timeout;
    
    public InitPopulation(HashSet<LogData> synthSet, ArrayList<String> paNames, String funName, int timeout) {
        this.synthSet = synthSet;
        this.paNames = paNames;
        this.funName = funName;
        this.timeout = timeout;
    }

    public ArrayList<SynthNode> genPopulations() {
        ArrayList<SynthNode> initPop = new ArrayList<>();
        ArrayList<EuSolverExecutor> specifications = new ArrayList<>();

        LogDataSelector selector = new LogDataSelector(synthSet);
        int id = 0;
        for (int i = 0; i < pop_size; i++) {
            EuSolverSyntax syntax = new EuSolverSyntax(funName, paNames, "Int");
            syntax.setRandomGrammars();
            HashSet<LogData> constraints = selector.selectRandomly(init_example_num);
            EuSolverExecutor spec = new EuSolverExecutor(funName + "_" + id + ".sl", syntax, constraints);
            specifications.add(spec);
            SynthNode node = new SynthNode();
            node.setId(id++);
            initPop.add(node);
        }
        MultiTasks.synthesizeConcurrently(initPop, funName, timeout, specifications);
        try {
            Process process = Runtime.getRuntime().exec("killall python3");
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return initPop;
    }
}
