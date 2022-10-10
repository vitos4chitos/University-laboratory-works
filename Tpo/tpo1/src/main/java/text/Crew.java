package text;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Crew {

    private int crewCode;
    private List<Person> crewMembers;
    private String crewName;

    public Reason getReason(){
        for(int i = 0; i < crewMembers.size(); i++){
            for(int j = i + 1; j < crewMembers.size(); j++){
                if(!crewMembers.get(i).getRelationShip(crewMembers.get(j))){
                    return Reason.PHYSICAL;
                }
            }
        }
        return Reason.VOLITION;
    }

}
