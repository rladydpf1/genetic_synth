package logdata;
import java.util.ArrayList;
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
}