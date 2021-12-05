package synth;
import java.io.File;
import java.util.*;

import genetic.Crossover;
import genetic.Fitness;
import genetic.Population;
import genetic.Selection;
import logdata.LogData;
import logdata.LogDataAnalyzer;
import eusolver.EuSolverExecutor;

public class SynthMain {
    final static int pop_size = 30;
    final static int init_example_num = 10;

    public static void main(String[] args) {
        args = new String[3];
        args[0] = "example/containsDuplicate.log";
        args[1] = "example/containsDuplicate.test";
        args[2] = "60"; 
        if (args.length != 3) {
            System.out.println("usage: java SynthMain [log_file] [test_file] [timeout_per_synthesis]");
            return;
        }
        try {
            Process process = Runtime.getRuntime().exec("killall python3");
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        File testFile = null, logFile = null;
        int timeout = Integer.parseInt(args[2]);
        try {
            testFile = new File(args[1]).getCanonicalFile();
            logFile = new File(args[0]).getCanonicalFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogDataAnalyzer logDataAnalyzer = new LogDataAnalyzer(logFile, testFile);
        HashSet<LogData> synthSet = logDataAnalyzer.getSynthesisData();
        HashSet<LogData> testSet = logDataAnalyzer.getTestData();
        String funName = logDataAnalyzer.getFunctionName();
        String definition = logDataAnalyzer.getDefinition();   
        ArrayList<String> paNames = logDataAnalyzer.getPaNames();
        Fitness fitness = new Fitness(testSet, definition, funName);

        System.out.println("function name: " + funName);
        long beforeTime = System.currentTimeMillis();
        

        // 1. initial population
        Population init = new Population(synthSet, paNames, funName, pop_size, init_example_num);
        ArrayList<SynthNode> pop = init.getPopulation();
        ArrayList<EuSolverExecutor> specifications = init.getSpecifications();

        ArrayList<Integer> ranking = new ArrayList<>();
        HashMap<Integer, Float> fitnessValues = new HashMap<>();
        Float bestSoFar = -1F;
        SynthNode bestIndividual = null;
        int chance = 2;
        int last_id = pop.size();
        
        do {
            // 2. evaluate all synthesized programs. (fitness)
            fitness.evaluate(pop, specifications, timeout);
            fitness.cutBySize(pop_size);
            ranking = fitness.getRanking();
            fitnessValues = fitness.getFitnessValues();

            // 3. check whether it is updated.
            boolean isUpdated = false;
            Float localBest = -1F;
            if (!ranking.isEmpty()) {
                localBest = fitnessValues.get(ranking.get(0));
            }
            if (bestSoFar < localBest) {
                bestSoFar = localBest;
                bestIndividual = pop.get(ranking.get(0));
                isUpdated = true;
                chance = 2;
            }
            if (isUpdated) {
                System.out.println("==================================");
                System.out.println(String.format("Time: %.2f", (System.currentTimeMillis() - beforeTime) / 1000F));
                System.out.println("Best indivisual: " + bestIndividual.getCode().getCCode());
                System.out.println(String.format("Best-so-far: %.1f", bestSoFar));
                if (bestSoFar == 100F) break;
            } else chance--;
            if (chance == 0) {
                System.out.println("not improved.");
                break;
            }

            // 4. Crossover.
            ArrayList<SynthNode> newPop = new ArrayList<>(pop);
            ArrayList<EuSolverExecutor> newSpec = new ArrayList<>(specifications);
            ArrayList<SynthNode> parents = new ArrayList<>();
            Crossover crossover = new Crossover(funName, paNames);

            for (int i = 0; i < pop_size/4; i++) {
                parents = Selection.tournamentSelect(ranking, pop);
                ArrayList<EuSolverExecutor> specs = crossover.crossover(parents, last_id);
                SynthNode child1 = new SynthNode();
                SynthNode child2 = new SynthNode();
                child1.setId(last_id++);
                child2.setId(last_id++);
                newPop.add(child1); newPop.add(child2);
                newSpec.add(specs.get(0)); newSpec.add(specs.get(1));
            }

            // 5. Mutation.


            pop = newPop;
            specifications = newSpec;

        } while(true);
        

        
        
    }
}