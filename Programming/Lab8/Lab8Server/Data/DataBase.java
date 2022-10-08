package Data;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataBase {
    public static Set<Person> base = Collections.synchronizedSet(new HashSet<>());
    public static CopyOnWriteArrayList<String> scriptsNames = new CopyOnWriteArrayList<>();
    public static ConcurrentLinkedQueue<String> history = new ConcurrentLinkedQueue<String>();
    public static void addToHistory(String command){
        if(history.size() == 10){
            history.poll();
            history.add(command);
        }
        else{
            history.add(command);
        }
    }


}