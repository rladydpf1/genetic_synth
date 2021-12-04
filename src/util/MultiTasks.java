package util;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.*;

import synth.SynthCode;
import synth.SynthNode;
import logdata.LogDataAnalyzer;
import logdata.LogData;
import eusolver.EuSolverExecutor;

public class MultiTasks {
    
    public static void synthesizeConcurrently(ArrayList<SynthNode> tasks, LogDataAnalyzer analyzer, int timeout, ArrayList<HashSet<LogData>> constraintsList) {
        try {
            String funName = analyzer.getFunctionName();
            Syntax syntax = analyzer.getEuSolverSyntax();
            ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (int i = 0; i < tasks.size(); i++) {
                SynthNode task = tasks.get(i);
                HashSet<LogData> constraints = constraintsList.get(i);
                threadPool.submit(() -> {
                    EuSolverExecutor synthesizer = new EuSolverExecutor(funName + "_" + task.getId() + ".sl");
                    synthesizer.generate(syntax, constraints);
                    SynthCode function = synthesizer.synthesize(timeout, task.getId().toString());
                    task.setCode(function);
                    task.setUsedData(constraints);
                    task.setSatisfied(true);
                });
                Thread.sleep(10);
            }
            threadPool.shutdown(); 
            try { 
                if (!threadPool.awaitTermination(timeout, TimeUnit.SECONDS)) { 
                    threadPool.shutdownNow(); 
                } 
            } catch (InterruptedException e) { 
                threadPool.shutdownNow(); 
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}