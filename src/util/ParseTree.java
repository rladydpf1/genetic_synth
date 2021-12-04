package util;
public class ParseTree {

    private String operator;
    private Integer value = null;
    private ParseTree first = null;
    private ParseTree second = null;
    private ParseTree third = null;

    public ParseTree(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public Integer getValue() {
        return value;
    }

    public ParseTree getFirst() {
        return first;
    }

    public ParseTree getSecond() {
        return second;
    }

    public ParseTree getThird() {
        return third;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setFirst(ParseTree first) {
        this.first = first;
    }

    public void setSecond(ParseTree second) {
        this.second = second;
    }

    public void setThird(ParseTree third) {
        this.third = third;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
