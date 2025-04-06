package simple.userEntity;

import lombok.Getter;

@Getter
public class MemberTestDTO1 {
    private String name;
    private String engName;
    private int age;

    public MemberTestDTO1(String name, String engName, int age) {
        this.name = name;
        this.engName = engName;
        this.age = age;
    }
}
