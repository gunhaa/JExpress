package simple.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import simple.context.ApplicationContext;
import simple.userEntity.Member;
import simple.userEntity.MemberTeamDto;
import simple.userEntity.Team;

import java.util.Map;

public class CustomRepository {

    public static Member registerMemberWithTeamOrNull(Map<String, String> map) {
        try (EntityManager em = ApplicationContext.getEntityManager()) {

            Member member;
            EntityTransaction tx = em.getTransaction();

            try {

                tx.begin();

                ObjectMapper mapper = new ObjectMapper();
                MemberTeamDto dto = mapper.convertValue(map, MemberTeamDto.class);
                member = new Member(dto.getName(), dto.getAge(), dto.getEngName());

                Team team;

                if (dto.getTeamId() != null) {
                    team = em.find(Team.class, dto.getTeamId());
                    member.setTeam(team);
                } else {
                    String teamName = map.getOrDefault("teamName", "기본 팀");
                    String teamEngName = map.getOrDefault("teamEngName", "Default Team");

                    team = new Team();
                    team.setTeamName(teamName);
                    team.setTeamEngName(teamEngName);
                    em.persist(team);
                }

                member.setTeam(team);

                em.persist(member);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                return null;
            }
            return member;
        }
    }
}
