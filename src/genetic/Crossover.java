package genetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import eusolver.EuSolverExecutor;
import eusolver.EuSolverSyntax;
import logdata.LogData;
import logdata.LogDataSelector;
import synth.SynthNode;

public class Crossover {
    String funName;
    ArrayList<String> paNames;

    public Crossover(String funName, ArrayList<String> paNames) {
        this.funName = funName;
        this.paNames = paNames;
    }

    public ArrayList<EuSolverExecutor> crossover(ArrayList<SynthNode> parents, int id) {
        ArrayList<EuSolverExecutor> spec = new ArrayList<>();
        HashSet<String> grammars = new HashSet<>();
        SynthNode parent0 = parents.get(0);
        SynthNode parent1 = parents.get(1);
        HashSet<LogData> logdata0 = new HashSet<>(parent0.getUsedData());
        HashSet<LogData> logdata1 = new HashSet<>(parent1.getUsedData());
        LogDataSelector selector0 = new LogDataSelector(logdata0);
        LogDataSelector selector1 = new LogDataSelector(logdata1);
        Random random = new Random();

        grammars.addAll(parent0.getUsedGraamrs());
        grammars.addAll(parent1.getUsedGraamrs());
        
        EuSolverSyntax syntax = new EuSolverSyntax(funName, paNames, "Int");
        syntax.setGrammars(grammars);

        logdata0.addAll(selector1.selectRandomly(random.nextInt(logdata1.size())));
        logdata1.addAll(selector0.selectRandomly(random.nextInt(logdata0.size())));
        
        spec.add(new EuSolverExecutor(funName + "_" + (id + 0) + ".sl", syntax, logdata0));
        spec.add(new EuSolverExecutor(funName + "_" + (id + 1) + ".sl", syntax, logdata1));

        return spec;
    }
}
