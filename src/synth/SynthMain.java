package synth;
import java.io.File;
import java.util.*;

import logdata.LogData;
import logdata.LogDataAnalyzer;
import logdata.LogDataSelector;
import util.MultiTasks;

public class SynthMain {
    public static void main(String[] args) {
        args = new String[3];
        // containsDuplicate // 1500, 800
        // containsNearbyDuplicate // 3000, 400
        // jumpGame // 9000, 400
        // busyStudent  // 2500, 300
        // numFriendRequests // 100 > 1h
        // removeElement // 8500, 800
        // majorityElement //x, 400 > 1h
        // missingNumber // x, 300 > 1h
        
        args[0] = "test/getbiggestrect.log";
        args[1] = "test/getbiggestrect.test";
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

        int current = 0;
        SynthNode root = new SynthNode(synthSet);
        root.setId(current);

        // 1. syntheisze programs with every 10/100/1000 constriants.
        ArrayList<SynthNode> initSynths = new ArrayList<>();
        ArrayList<HashSet<LogData>> selecteds = new ArrayList<>();
        LogDataSelector selector = new LogDataSelector(synthSet);
        int init = 1000, max = synthSet.size(), factor = 100, id = 0;
        if (max > 100) max = 10;
        for (int j = 0; j < 10; j++) {
            init = 10;
            selecteds.add(selector.selectRandomly(init));
            SynthNode node = new SynthNode(synthSet);
            node.setId(id++);
            initSynths.add(node);
        }
        MultiTasks.synthesizeConcurrently(initSynths, logDataAnalyzer, timeout, selecteds);
        try {
            Process process = Runtime.getRuntime().exec("killall python3");
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. evaluate all synthesized programs, and get the max size of constriants and the program which got the best accuarcy.
        SynthCode bestCode = null;
        int max_const_size = 0, best_const_size = 0;
        float bestAccuracy = -2F;
        System.out.println("function name: " + funName);
        for (int i = 0; i < initSynths.size(); i++) {
            try {
                SynthNode node = initSynths.get(i);
                System.out.print("constraints: " + node.getUsedConstraints().size() + ", ");
                SynthCode code = node.getCode();
                if (code == null) {
                    System.out.println("synthesis timeout");
                    continue;
                }
                max_const_size = node.getUsedConstraints().size();
                SynthEvaluator evaluator = new SynthEvaluator("evaluator_" + funName + "_" + node.getId() + ".c", definition);
                float accuracy = evaluator.getAccuracy(code.getCCode(), testSet, null, 1);
                System.out.println("accuracy: " + accuracy + "%, size: " + node.getCode().getSize() + ", time: " + node.getCode().getTime());
                // System.out.println(code.getCCode() + "\n");
                if (accuracy > bestAccuracy) {
                    bestAccuracy = accuracy;
                    bestCode = code;
                    best_const_size = node.getUsedConstraints().size();
                }
                else if (accuracy == bestAccuracy) {
                    if (bestCode.getSize() > code.getSize()) {
                        bestCode = code;
                    }
                }
            } catch (Exception e) {
                continue;
            }
            
        }
        System.out.println(" best code: " + bestCode.getCCode());
        System.out.println(" best accuracy: " + bestAccuracy + "%");
        System.out.println(" best size: " + bestCode.getSize());
        System.out.println(" best time: " + bestCode.getTime());
    }
}