package synth;
import java.io.File;
import java.util.*;

import genetic.Fitness;
import genetic.InitPopulation;
import logdata.LogData;
import logdata.LogDataAnalyzer;

public class SynthMain {
    public static void main(String[] args) {
        // args = new String[3];
        // args[0] = "example/getbiggestrect.log";
        // args[1] = "example/getbiggestrect.test";
        // args[2] = "60"; 
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
        ArrayList<String> panames = logDataAnalyzer.getPaNames();

        System.out.println("function name: " + funName);
        long beforeTime = System.currentTimeMillis();

        // 1. initial population
        InitPopulation init = new InitPopulation(synthSet, panames, funName, timeout);
        ArrayList<SynthNode> pop = init.genPopulations();
        final int pop_size = pop.size();

        ArrayList<Integer> ranking = new ArrayList<>();
        HashMap<Integer, Float> fitnessValues = new HashMap<>();
        Float bestSoFar = -1F;
        SynthNode bestIndividual = null;
        int chance = 2;
        
        while (bestSoFar < 100F) {
            // 2. evaluate all synthesized programs.
            Fitness fitness = new Fitness(testSet, definition, funName);
            for (int i = 0; i < pop.size(); i++) {
                fitness.evaluate(pop.get(i));
            }
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
            } else chance--;
            if (chance == 0) {
                System.out.println("not improved.");
                break;
            }

            
        }
        

        
        
    }
}