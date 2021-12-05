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
                EuSolverExecutor synthesizer = specifications.get(i);
                threadPool.submit(() -> {
                    SynthCode function = synthesizer.synthesize(timeout, task.getId().toString());
                    task.setCode(function);
                    task.setUsedData(synthesizer.getData());
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