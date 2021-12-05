package synth;
import java.util.HashSet;

import logdata.LogData;

public class SynthNode {
    
    private Integer id;
    private SynthCode code;
    private String cCode;
    private HashSet<LogData> usedData;
    private HashSet<String> usedGraamrs;
    private boolean isSynthesized = false;

    public SynthNode() {
        usedData = null;
        code = null;
        usedGraamrs = null;
        cCode = null;
    }

    public Integer getId() {
        return id;
    }

    public SynthCode getCode() {
        return code;
    }

    public String getCCode() {
        return cCode;
    }

    public HashSet<LogData> getUsedData() {
        return usedData;
    }

    public HashSet<String> getUsedGraamrs() {
        return usedGraamrs;
    }

    public boolean isSynthesized() {
        return isSynthesized;
    }

    public void setSynthesized() {
        isSynthesized = true;
    }

    public void setUsedData(HashSet<LogData> usedData) {
        this.usedData = usedData;
    }

    public void setUsedGraamrs(HashSet<String> usedGraamrs) {
        this.usedGraamrs = usedGraamrs;
    }

    public void setCode(SynthCode code) {
        this.code = code;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCCode(String cCode) {
        this.cCode = cCode;
    }
}
