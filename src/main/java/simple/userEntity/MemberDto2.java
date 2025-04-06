package simple.userEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto2 {
    private String name;
    private String engName;
    private Team Team;

    public MemberDto2(String name, String engName, Team team) {
        this.name = name;
        this.engName = engName;
        this.Team = team;
    }
}
