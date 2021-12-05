package genetic;

import java.util.ArrayList;
import java.util.HashSet;

import eusolver.EuSolverExecutor;
import eusolver.EuSolverSyntax;
import logdata.LogData;
import logdata.LogDataSelector;
import synth.SynthNode;

public class Population {
    ArrayList<SynthNode> pop = new ArrayList<>();
    ArrayList<EuSolverExecutor> specifications = new ArrayList<>();
    
    public Population(HashSet<LogData> synthSet, ArrayList<String> paNames, String funName, int pop_size, int init_example_num) {
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
            pop.add(node);
        }
    }

    public ArrayList<EuSolverExecutor> getSpecifications() {
        return specifications;
    }

    public ArrayList<SynthNode> getPopulation() {
        return pop;
    }
}
