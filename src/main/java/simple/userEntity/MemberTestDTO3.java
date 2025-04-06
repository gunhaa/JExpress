package simple.userEntity;

import lombok.Getter;

@Getter
public class MemberTestDTO3 {

    private int age;
    private String engName;

    public MemberTestDTO3(int age, String engName) {
        this.age = age;
        this.engName = engName;
    }

}
