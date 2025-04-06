package simple.userEntity;

import lombok.Getter;

@Getter
public class MemberDto3 {

    private int age;
    private String engName;

    public MemberDto3(int age, String engName) {
        this.age = age;
        this.engName = engName;
    }

}
