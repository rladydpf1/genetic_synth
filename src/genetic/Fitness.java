package genetic;

import java.util.HashMap;
import java.util.HashSet;

import eusolver.EuSolverExecutor;

import java.util.ArrayList;

import logdata.LogData;
import synth.SynthCode;
import synth.SynthEvaluator;
import synth.SynthNode;
import util.MultiTasks;

public class Fitness {
    HashSet<LogData> testSet;
    HashMap<Integer, Float> fitnessValues;
    ArrayList<Integer> ranking;
    String definition, funName;

    public Fitness(HashSet<LogData> testSet, String definition, String funName) {
        this.testSet = testSet;
        this.definition = definition;
        this.funName = funName;
        fitnessValues = new HashMap<>();
        ranking = new ArrayList<>();
    }

    public void evaluate(ArrayList<SynthNode> pop, ArrayList<EuSolverExecutor> specifications, int timeout) {
        MultiTasks.synthesizeConcurrently(pop, funName, timeout, specifications);
        try {
            Process process = Runtime.getRuntime().exec("killall python3");
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (SynthNode individual : pop) {
            if (fitnessValues.containsKey(individual.getId())) continue;
            float accuracy = -1F;
            try {
                SynthCode code = individual.getCode();
                if (code == null) {
                    continue;
                }
                SynthEvaluator evaluator = new SynthEvaluator("evaluator_" + funName + "_" + individual.getId() + ".c", definition);
                accuracy = evaluator.getAccuracy(code.getCCode(), testSet, null);
            }
            catch(Exception e) { continue; }
    
            insert(individual.getId(), accuracy);
        }
    }

    private void insert(Integer key, Float value) {
        fitnessValues.put(key, value);
        if (ranking.isEmpty()) {
            ranking.add(key);
            return;
        }
        int size = ranking.size();
        for (int i = 0; i < size; i++) {
            Float value_i = fitnessValues.get(ranking.get(i));
            if (value_i < value) {
                ranking.add(i, key);
                return;
            }
        }
        ranking.add(key);
    }

    public void cutBySize(int size) {
        if (ranking.isEmpty()) {
            return;
        }
        for (int i = ranking.size()-1; i > size; i--) {
            fitnessValues.remove(ranking.get(i));
            ranking.remove(i);
        }
    }

    public ArrayList<Integer> getRanking() {
        return ranking;
    }

    public HashMap<Integer, Float> getFitnessValues() {
        return fitnessValues;
    }
}
