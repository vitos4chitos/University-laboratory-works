package Lab5.Sorts;

import Lab5.DataBases.Person;

import java.util.Comparator;

public class SortById implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return Integer.parseInt(o1.getId().toString()) - Integer.parseInt(o2.getId().toString());
    }
}
