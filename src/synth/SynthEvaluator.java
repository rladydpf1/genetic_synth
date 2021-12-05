package synth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import logdata.LogData;
import util.FunctionDriver;

public class SynthEvaluator {
    private String definition;
    private String fileName;
    HashMap<Boolean, HashSet<LogData>> eval = new HashMap<>();

    public SynthEvaluator(String fileName, String definition) {
        this.definition = definition;
        this.fileName = fileName;
    }

    public Float getAccuracy(String cCode, HashSet<LogData> testSet, HashSet<LogData> selected) {
        ArrayList<LogData> tests = new ArrayList<>(testSet);
        
        if (selected != null) tests.removeAll(selected);
        if (tests.isEmpty()) return -1F;

        FunctionDriver driver = new FunctionDriver(fileName);
        String function = "\n" + definition + "{\n" + cCode + "\n}\n";
        String main = "int main() {\n";
        for (int i = 0; i < tests.size(); i++) {
            main += "\t" + "printf(\"%d\\n\"," + tests.get(i).getCallFormat() + ");\n"; 
        }
        main += "}\n";
        driver.addCode(function);
        driver.addCode(main);
        driver.writeCode();
        String[] res = driver.run().split("\n");
        int hit = 0;
        if (res.length != tests.size()) return -1F;
            for (int i = 0; i < tests.size(); i++) {
                if (tests.get(i).getOutput().toString().equals(res[i])) {
                    hit++;
                }
                else {
                }
            }
        return (float) hit * 100F / tests.size();
    }
}
