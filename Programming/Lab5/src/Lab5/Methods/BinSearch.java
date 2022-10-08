package Lab5.Methods;

import Lab5.DataBases.Person;

import java.util.ArrayList;

public class BinSearch {
    static public int binSearchPerson(ArrayList<Person> mass, Integer i){
        int l = -1, m;
        int r = mass.size();
        while (l < r - 1){
            m = (l + r) / 2;
            if (Integer.parseInt(mass.get(m).getId().toString()) < Integer.parseInt(i.toString())){
                l = m;
            }
            else{
                r = m;
            }
        }
        try {
            if (Integer.parseInt(mass.get(r).getId().toString()) == Integer.parseInt(i.toString())) {
                return r;
            }
        }
        catch (Exception e){
        }
        return -1;
    }
    static public int binSearchPassport(ArrayList<String> mass, String s){
        int l = -1, r = mass.size(), m;
        while (l < r - 1){
            m = (l + r) / 2;
            if(mass.get(m).compareTo(s) < 0){
                l = m;
            }
            else {
                r = m;
            }
        }
        try {
            if(mass.get(r).compareTo(s) == 0){
                return r;
            }
        }
        catch (Exception e){}
        return -1;
    }
}
