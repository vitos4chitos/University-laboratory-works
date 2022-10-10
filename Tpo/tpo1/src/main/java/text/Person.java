package text;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person implements Feel, Relationship {

    private String name;
    private String surname;
    private int age;
    private int personCode;
    private List<Long> randomThoughts;

    @Override
    public Boolean getRelationShip(Person another) {
        return another.howAreYouFeeling(another.getRandomThoughts()).equals(this.howAreYouFeeling(this.randomThoughts));
    }
}
