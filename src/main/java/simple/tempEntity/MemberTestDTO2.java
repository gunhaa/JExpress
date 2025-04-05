package simple.tempEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberTestDTO2 {
    private String name;
    private String engName;
    private Team Team;

    public MemberTestDTO2(String name, String engName, Team team) {
        this.name = name;
        this.engName = engName;
        this.Team = team;
    }
}
