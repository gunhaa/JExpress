package simple.userEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private int age;

    private String engName;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Member(){}

    public Member(String name, int age, String engName) {
        this.name = name;
        this.age = age;
        this.engName = engName;
    }

}
