package simple.userEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Team")
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String teamName;
    private String teamEngName;

    public Team() {
    }

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private List<Member> memberList = new ArrayList<>();
}
