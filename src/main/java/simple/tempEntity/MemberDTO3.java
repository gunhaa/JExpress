package simple.tempEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO3 {
    private String name;
    private String engName;
    private String teamName;

    public MemberDTO3(String name, String engName, String teamName) {
        this.name = name;
        this.engName = engName;
        this.teamName = teamName;
    }
}
