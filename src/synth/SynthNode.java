package synth;
import java.util.HashSet;

import logdata.LogData;

public class SynthNode {
    
    private Integer id;
    private SynthCode code;
    private HashSet<LogData> usedData;

    public SynthNode() {
        usedData = null;
        code = null;
    }

    public Integer getId() {
        return id;
    }

    public SynthCode getCode() {
        return code;
    }

    public HashSet<LogData> getUsedConstraints() {
        return usedData;
    }

    public void setUsedData(HashSet<LogData> usedData) {
        this.usedData = usedData;
    }

    public void setCode(SynthCode code) {
        this.code = code;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
