package util;
import java.util.ArrayList;
import java.util.concurrent.*;

import synth.SynthCode;
import synth.SynthNode;
import eusolver.EuSolverExecutor;

public class MultiTasks {
    
    public static void synthesizeConcurrently(ArrayList<SynthNode> tasks, String funName, int timeout, ArrayList<EuSolverExecutor> specifications) {
        try {
            ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (int i = 0; i < tasks.size(); i++) {
                SynthNode task = tasks.get(i);
                if (task.isSynthesized()) continue;
                EuSolverExecutor synthesizer = specifications.get(i);
                threadPool.submit(() -> {
                    SynthCode function = synthesizer.synthesize(timeout, task.getId().toString());
                    if (function != null) {
                        task.setCCode(function.getCCode());
                        task.setCode(function);
                        task.setUsedData(synthesizer.getData());
                        task.setUsedGraamrs(function.getUsedGrammars());
                        task.setSynthesized();
                    }
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