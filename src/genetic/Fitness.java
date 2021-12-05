package genetic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import logdata.LogData;
import synth.SynthCode;
import synth.SynthEvaluator;
import synth.SynthNode;

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

    public Float evaluate(SynthNode individual) {
        float accuracy = -1F;
        try {
            SynthCode code = individual.getCode();
            if (code == null) {
                return -1F;
            }
            SynthEvaluator evaluator = new SynthEvaluator("evaluator_" + funName + "_" + individual.getId() + ".c", definition);
            accuracy = evaluator.getAccuracy(code.getCCode(), testSet, null);
        }
        catch(Exception e) { return -1F; }

        insert(individual.getId(), accuracy);
        return accuracy;
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

    public ArrayList<Integer> getRanking() {
        return ranking;
    }

    public HashMap<Integer, Float> getFitnessValues() {
        return fitnessValues;
    }
}
