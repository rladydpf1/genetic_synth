package util;
import java.util.Arrays;

public class Input {
    private Integer[] values;

    public Input(Integer[] values) {
        this.values = values;
    }

    public Integer[] getValues() {
        return values;
    }

    public Input deepCopy() {
        Integer[] copy = new Integer[values.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = values[i];
        }
        return new Input(copy);
    }

    @Override
    public String toString() {
        String res = "(";
        for (int i = 0; i < values.length - 1; i++) {
            res += values[i] + " ";
        }
        res += values[values.length - 1] + ")";
        return res;
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        for (Integer value : values) {
            hashcode += value.hashCode() * 31;
        }
        return hashcode;
    }

    @Override
    public boolean equals(Object obj){
        Input si = (Input) obj;
        if (obj instanceof Input) {
            return Arrays.deepEquals(this.values, si.values);
        }
        return false;
    }

}
