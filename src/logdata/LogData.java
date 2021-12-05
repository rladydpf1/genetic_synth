package logdata;

import util.Input;

public class LogData {
    private String funName = "function";
    private Input input;
    private Object output = null;
    private String constraint = null;
    private String call = null;

    public LogData(String log) {
        String[] in_outputs = log.split("=");
        try {
            output = Integer.parseInt(in_outputs[1]);
        } catch (Exception e) {}
        
        String[] fun_inputs = in_outputs[0].split("\\(");
        funName = fun_inputs[0];
        String[] values = fun_inputs[1].replaceFirst("\\)", "").split(" ");
        Integer[] intValues = new Integer[values.length];
        int i = 0;
        for (String value : values) {
            intValues[i++] = Integer.parseInt(value);
        }
        input = new Input(intValues);
    }

    public LogData(Input input, Object output, String funName) {
        this.input = input;
        this.output = output;
        this.funName = funName;
    }

    public String getConstraintFormat() {
        if (constraint == null) {
            String res = "(constraint (= (" + funName + " ";
            for (Object input : input.getValues()) {
                res += input.toString() + " ";
            } res += ") " + output + "))";
            return constraint = res;
        }
        return constraint;
    }

    public String getCallFormat() {
        if (call == null) {
            Object[] values = input.getValues();
            String res = funName + "(" + values[0];
            for (int i = 1; i < input.getValues().length; i++) {
                res += ", " + values[i];
            }
            res += ")";
            return call = res;
        }
        return call;
    }

    public String getFunName() {
        return funName;
    }

    public Input getInput() {
        return input;
    }

    public Object getOutput() {
        return output;
    }

    @Override
    public int hashCode() {
        return input.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        return input.equals(((LogData)obj).getInput());
    }
}
