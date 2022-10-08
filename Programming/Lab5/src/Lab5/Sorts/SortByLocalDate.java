package Lab5.Sorts;

import Lab5.DataBases.Person;
import java.util.Comparator;

public class SortByLocalDate implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o1.getCreationDate().compareTo(o2.getCreationDate());
    }
}
