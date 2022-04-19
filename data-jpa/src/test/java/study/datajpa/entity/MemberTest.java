package study.datajpa.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;
    
    @Test
    void entityTest() throws Exception{
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();
        //when
        List<Member> findMembers = em.createQuery("select m from Member m", Member.class).getResultList();
        //then
//        for (Member member : findMembers) {
//            System.out.println("member = " + member);
//            System.out.println("member.getTeam() = " + member.getTeam());
//        }
        findMembers.stream().forEach(member -> {
            if(member.getAge() <= 20){
                assertThat(member.getTeam().getId()).isEqualTo(teamA.getId());
                assertThat(member.getTeam()).isNotEqualTo(teamA);
            }else{
                assertThat(member.getTeam().getId()).isEqualTo(teamB.getId());
                assertThat(member.getTeam()).isNotEqualTo(teamB);
            }
        });

    }

}