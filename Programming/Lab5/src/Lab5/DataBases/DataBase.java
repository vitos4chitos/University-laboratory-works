package Lab5.DataBases;

import java.io.*;
import java.util.*;

public class DataBase {
    private static File file;
    public HashSet<Person> base = new HashSet<Person>();
    public static ArrayList<File> scriptsNames = new ArrayList<File>();
    public static Stack<Scanner> scanners = new Stack<Scanner>();
    public ArrayList<Integer> id = new ArrayList<Integer>();
    public static Queue<String> history = new LinkedList<String>();
    public static ArrayList<String> passportId = new ArrayList<String>();
    private static File idFile;
    public static File getFile(){
        return file;
    }
    public static File getIdFile(){return idFile;}
    public static void addToHistory(String command){
        if(history.size() == 10){
            history.poll();
            history.add(command);
        }
        else{
            history.add(command);
        }
    }
    public void changeFileName(String s){
        file = new File(s);
        idFile = new File(file.getParent() + "/id.csv");
        try {
            FileReader fr = new FileReader(idFile);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            String[] str = line.split(";");
            for(String string: str){
                id.add(Integer.valueOf(string));
            }
        }
        catch (IOException e){
            System.out.println("За что ты так меня?");
        }

    }
}

