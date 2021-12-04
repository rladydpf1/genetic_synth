package synth;
import java.util.ArrayList;
import java.util.HashSet;

import logdata.LogData;
import util.Condition;

public class SynthNode {
    
    private Integer id;
    private ArrayList<SynthNode> childs;
    private SynthCode code;
    private HashSet<LogData> data, usedData;
    private Condition condition;
    private boolean satisfied, timeout, condTimeout, condSucc;
    private HashSet<Integer> outputRange;

    public SynthNode(HashSet<LogData> data) {
        this.data = data;
        usedData = null;
        code = null;
        childs = new ArrayList<>();
        satisfied = false;
        timeout = false;
        condTimeout = false;
        condSucc = false;
        outputRange = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public HashSet<LogData> getData() {
        return data;
    }

    public ArrayList<SynthNode> getChilds() {
        return childs;
    }

    public SynthCode getCode() {
        return code;
    }

    public Condition getCondition() {
        return condition;
    }

    public HashSet<LogData> getUsedConstraints() {
        return usedData;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public boolean isLeafNode() {
        return childs.isEmpty();
    }

    public boolean isTimeout() {
        return timeout;
    }
    
    public boolean isCondTimeout() {
        return condTimeout;
    }

    public boolean isCondSucc() {
        return condSucc;
    }

    public void setUsedData(HashSet<LogData> usedData) {
        this.usedData = usedData;
    }

    public void setData(HashSet<LogData> data) {
        this.data = data;
    }

    public void setCode(SynthCode code) {
        this.code = code;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void addChild(SynthNode child) {
        this.childs.add(child);
    }

    public void setSatisfied(Boolean satisfied) {
        this.satisfied = satisfied;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTimeout(Boolean timeout) {
        this.timeout = timeout;
    }

    public void setCondTimeout(Boolean condTimeout) {
        this.condTimeout = condTimeout;
    }

    public void setCondSucc(boolean condSucc) {
        this.condSucc = condSucc;
    }

    public void setOutputRange(HashSet<Integer> outputRange) {
        this.outputRange = outputRange;
    }

    public HashSet<Integer> getOutputRange() {
        return outputRange;
    }
}
