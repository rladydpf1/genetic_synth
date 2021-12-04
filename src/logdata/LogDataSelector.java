package logdata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class LogDataSelector {
    private HashSet<LogData> data;
    
    public LogDataSelector(HashSet<LogData> data){
        this.data = data;
    }

    public HashSet<LogData> selectRandomly(int num) {
        if (data.size() < num) {
            return data;
        }
        HashSet<LogData> selected = new HashSet<>();
        Random random = new Random();
        ArrayList<LogData> list = new ArrayList<>(data);
        int size = list.size();
        while(selected.size() < num) {
            selected.add(list.get(random.nextInt(size)));
        }
        return selected;
    }

    public HashSet<LogData> selectWidely(int num) {
        if (data.size() < num) {
            return data;
        }
        HashSet<LogData> selected = new HashSet<>();
        HashSet<Integer> outputRange = new HashSet<>();
        
        for (LogData constraint : data) {
            if (!outputRange.contains(constraint.getOutput())) {
                selected.add(constraint);
            }
            if (num == selected.size()) return selected;
        }
        for (LogData constraint : data) {
            if (!selected.contains(constraint)) {
                selected.add(constraint);
            }
            if (num == selected.size()) break;
        }
        return selected;
    }

    public HashSet<LogData> selectByScore(int num) {
        if (data.size() < num) {
            return data;
        }
        HashSet<LogData> selected = new HashSet<>();
        ArrayList<LogData> sorted = new ArrayList<>(data);
        Collections.sort(sorted);
        for (int i = 0; i < num; i++) {
            selected.add(sorted.get(i));
        }
        return selected;
    }
}