package util;
public class Condition {
    String condition;
    Integer size;

    public Condition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return condition;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        Condition c = (Condition) obj;
        return condition.equals(c.condition);
    }
}
