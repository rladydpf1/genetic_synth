package genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import eusolver.EuSolverExecutor;
import eusolver.EuSolverSyntax;
import logdata.LogData;
import logdata.LogDataSelector;

public class Mutation {
    String funName;
    ArrayList<String> paNames;
    HashSet<LogData> synthSet;

    private final List<String> operators_Int = Arrays.asList("+", "-", "*", "/", "ite");
    private final List<String> operators_Bool = Arrays.asList("<", "<=", ">", ">=", "or", "and", "=");

    public Mutation(String funName, ArrayList<String> paNames, HashSet<LogData> synthSet) {
        this.funName = funName;
        this.paNames = paNames;
        this.synthSet = synthSet;
    }

    public EuSolverExecutor mutate(EuSolverExecutor spec, int id) {
        Random random = new Random();
        HashSet<String> grammar = new HashSet<>(spec.getGrammar());
        HashSet<LogData> data =  new HashSet<>(spec.getData());
        boolean flag = false;

        // grammar addition
        if (random.nextInt(4) == 0) {
            for (String op : operators_Int) {
                if (random.nextInt(4) == 0) {
                    grammar.add(op);
                    flag = true;
                }
            }
            for (String op : operators_Bool) {
                if (random.nextInt(4) == 0) {
                    grammar.add(op);
                    flag = true;
                }
            }
            if (random.nextInt(8) == 0) {
                Integer constant = random.nextInt(100)-1;
                grammar.add(constant.toString());
                flag = true;
            }
        }

        // grammar deletion
        HashSet<String> removeList = new HashSet<>();
        if (random.nextInt(4) == 0) {
            for (String op : grammar) {
                if (random.nextInt(8) == 0) {
                    removeList.add(op);
                    flag = true;
                }
            }
            grammar.removeAll(removeList);
        }

        // log data addition
        if (random.nextInt(4) == 0) {
            LogDataSelector selector = new LogDataSelector(synthSet);
            data.addAll(selector.selectRandomly(random.nextInt(data.size()/2)));
            flag = true;
        }
        
        // log data deletion
        if (random.nextInt(4) == 0) {
            LogDataSelector selector = new LogDataSelector(data);
            if (data.size() >= 10) {
                data.removeAll(selector.selectRandomly(random.nextInt(data.size()/2)));
                flag = true;
            }
        }
        
        if (!flag) return null;

        EuSolverSyntax syntax = new EuSolverSyntax(funName, paNames, "Int");
        syntax.setGrammars(grammar);
        EuSolverExecutor mutated_spec = new EuSolverExecutor(funName + "_" + (id + 0) + ".sl", syntax, data);

        return mutated_spec;
    }
}
