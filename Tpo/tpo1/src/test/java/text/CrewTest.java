package text;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrewTest {

    @Test
    void relationshipsTest() {
        List<Person> personList = new ArrayList<>();
        Long[] sourceArray = { 1L, 1L, 2L, 3L, 5L, 8L, 13L };
        Long[] sourceArrayTwo = { 2L, 3L, 5L, 8L, 13L, 21L, 34L };
        Long[] sourceArrayThree = { 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L };
        Long[] sourceArrayFour = { 1L, 3L, 1L, 3L, 13L, 15L, 34L, 55L };
        Person personOne = new Person("Nikita", "Mishanin", 20, 1, Arrays.asList(sourceArray));
        Person personTwo = new Person("Viktor", "Kozlov", 21, 2, Arrays.asList(sourceArrayTwo));
        Person personThree = new Person("Bogdan", "Lytikov", 20, 3, Arrays.asList(sourceArrayThree));
        personList.add(personOne);
        personList.add(personTwo);
        personList.add(personThree);

        Crew crew = new Crew(1, personList, "Space X Crew");
        assertEquals(Reason.VOLITION, crew.getReason());

        Person personFour = new Person("Kirill", "Tankovskiy", 21, 4, Arrays.asList(sourceArrayFour));
        personList.add(personFour);
        crew.setCrewMembers(personList);
        assertEquals(Reason.PHYSICAL, crew.getReason());
    }
}