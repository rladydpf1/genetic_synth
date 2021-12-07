package genetic;

import java.util.ArrayList;
import java.util.HashSet;

import eusolver.EuSolverExecutor;
import eusolver.EuSolverSyntax;
import logdata.LogData;
import synth.SynthNode;

public class Crossover {
    String funName;
    ArrayList<String> paNames;

    public Crossover(String funName, ArrayList<String> paNames) {
        this.funName = funName;
        this.paNames = paNames;
    }

    public EuSolverExecutor crossover(ArrayList<SynthNode> parents, int id) {
        HashSet<String> grammars = new HashSet<>();
        SynthNode parent0 = parents.get(0);
        SynthNode parent1 = parents.get(1);
        HashSet<LogData> logdata0 = new HashSet<>(parent0.getUsedData());
        HashSet<LogData> logdata1 = new HashSet<>(parent1.getUsedData());

        grammars.addAll(parent0.getUsedGraamrs());
        grammars.addAll(parent1.getUsedGraamrs());
        
        EuSolverSyntax syntax = new EuSolverSyntax(funName, paNames, "Int");
        syntax.setGrammars(grammars);
        logdata0.addAll(logdata1);

        return new EuSolverExecutor(funName + "_" + (id + 0) + ".sl", syntax, logdata0);
    }
}
