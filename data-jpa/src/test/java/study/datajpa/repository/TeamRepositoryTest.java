package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void saveAndFindById() throws Exception{
        //given
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);
        //when
        Team findTeam = teamRepository.findById(teamA.getId()).orElseThrow();
        //then
        assertThat(findTeam).isEqualTo(teamA);
    }

    @Test
    void delete() throws Exception{
        //given
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);
        //when
        assertThat(teamRepository.count()).isEqualTo(1);
        //then
        teamRepository.delete(teamA);
        assertThat(teamRepository.count()).isEqualTo(0);
    }

    @Test
    void findAllAndCount() throws Exception{
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        //when
        List<Team> findTeams = teamRepository.findAll();
        long count = teamRepository.count();
        //then
        assertThat(findTeams.size()).isEqualTo(2);
        assertThat(findTeams).hasSameElementsAs(Arrays.asList(teamA, teamB));
        assertThat(count).isEqualTo(2);

    }
}