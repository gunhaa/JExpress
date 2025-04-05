package simple.tempEntity;

import lombok.Getter;

@Getter
public class MemberDTO1 {
    private String name;
    private String engName;
    private Team team;
    private int age;

    public MemberDTO1(String name, String engName, Team team, int age) {
        this.name = name;
        this.engName = engName;
        this.team = team;
        this.age = age;
    }
}
