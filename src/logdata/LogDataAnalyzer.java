package logdata;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class LogDataAnalyzer {
    private HashSet<LogData> testSet;
    private HashSet<LogData> synthSet;
    private String funName;
    private ArrayList<String> paNames;
    private String definition;
    
    public LogDataAnalyzer(File logFile, File testFile) {
        testSet = new HashSet<>();
        synthSet = new HashSet<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(logFile));
            String log_s = null;
            while((log_s = reader.readLine()) != null) {
                if (log_s.length() == 0) continue;
                LogData log = new LogData(log_s);
                synthSet.add(log);
            }
            reader.close();
            reader = new BufferedReader(new FileReader(testFile));
            log_s = null;
            while((log_s = reader.readLine()) != null) {
                if (log_s.length() == 0) continue;
                LogData log = new LogData(log_s);
                testSet.add(log);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashSet<LogData> dup = new HashSet<>(testSet);
        dup.retainAll(synthSet);
        if (dup.size() > 0) {
            System.out.println("duplicated log data exists\n");
            for (LogData log : dup) {
                System.out.println(log.getConstraintFormat());
            }
            System.exit(0);
        }
        funName = testSet.iterator().next().getFunName();
        int paramSize = testSet.iterator().next().getInput().getValues().length;
        paNames = new ArrayList<>();
        paNames.add("arg_0_");
        definition = "int " + funName + "(int arg_0_";
        for (int i = 1; i < paramSize; i++) {
            String name = "arg_" + i + "_";
            definition += ", int " + name;
            paNames.add(name);
        }
        definition += ")";
    }

    public HashSet<LogData> getSynthesisData() {
        return synthSet;
    }

    public HashSet<LogData> getTestData() {
        return testSet;
    }

    public String getDefinition() {
        return definition;
    }

    public String getFunctionName() {
        return funName;
    }

    public ArrayList<String> getPaNames() {
        return paNames;
    }

    // public Syntax getEuSolverSyntax() {
    //     if (eSyntax != null) return eSyntax;
    //     eSyntax = new EuSolverSyntax(funName, paNames, "Int");
    //     eSyntax.setSyntacticBasic();
    //     return eSyntax;
    // }
}
