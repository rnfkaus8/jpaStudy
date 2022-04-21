package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TeamJpaRepositoryTest {

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Test
    void saveAndFindById() throws Exception{
        //given
        Team teamA = new Team("teamA");
        teamJpaRepository.save(teamA);
        //when
        Team findTeam = teamJpaRepository.findById(teamA.getId()).orElseThrow();
        //then
        assertThat(findTeam).isEqualTo(teamA);
    }

    @Test
    void delete() throws Exception{
        //given
        Team teamA = new Team("teamA");
        teamJpaRepository.save(teamA);
        //when
        assertThat(teamJpaRepository.count()).isEqualTo(1);
        //then
        teamJpaRepository.delete(teamA);
        assertThat(teamJpaRepository.count()).isEqualTo(0);
    }
    
    @Test
    void findAllAndCount() throws Exception{
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamJpaRepository.save(teamA);
        teamJpaRepository.save(teamB);
        //when
        List<Team> findTeams = teamJpaRepository.findAll();
        long count = teamJpaRepository.count();
        //then
        assertThat(findTeams.size()).isEqualTo(2);
        assertThat(findTeams).hasSameElementsAs(Arrays.asList(teamA, teamB));
        assertThat(count).isEqualTo(2);

    }



}