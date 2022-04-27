package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void memberTest() throws Exception{
        //given
        Member member = new Member("userA");
        Member savedMember = memberRepository.save(member);
        //when
        Member findMember = memberRepository.findById(savedMember.getId()).get();
        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findByUsernameAndAgeGreaterThen() throws Exception{
        //given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member1", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> findMembers = memberRepository.findByUsernameAndAgeGreaterThan("member1", 15);
        //then
        assertThat(findMembers.size()).isEqualTo((1));
        assertThat(findMembers.get(0)).isEqualTo(member2);
        assertThat(findMembers.get(0).getUsername()).isEqualTo("member1");
        assertThat(findMembers.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void findByUsername() throws Exception{
        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> findMembers = memberRepository.findByUsername("member1");
        //then
        assertThat(findMembers).hasSameElementsAs(Arrays.asList(member1));
    }

    @Test
    void findUser() throws Exception{
        //given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> findMembers = memberRepository.findUser("member1", 10);
        //then
        assertThat(findMembers).hasSameElementsAs(Arrays.asList(member1));
    }
    
    @Test
    void findUsernameList() throws Exception{
        //given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<String> findMembers = memberRepository.findUsernameList();
        //then
        assertThat(findMembers).hasSameElementsAs(Arrays.asList(member1.getUsername(), member2.getUsername()));
    }
    
    @Test
    void findMemberDtoList() throws Exception{
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);


        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 10);
        memberRepository.save(member1);
        memberRepository.save(member2);

        member1.setTeam(teamA);
        member2.setTeam(teamB);
        //when
        List<MemberDto> findMemberDtoList = memberRepository.findMemberDtoList();

        //then
        assertThat(findMemberDtoList).hasSameElementsAs(Arrays.asList(new MemberDto(member1.getId(), member1.getUsername(), member1.getTeam().getName()), new MemberDto(member2.getId(), member2.getUsername(), member2.getTeam().getName())));

    }

    @Test
    void findByNames() throws Exception{
        //given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        Member member3 = new Member("member3", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        //when
        List<Member> findMembers = memberRepository.findByNames(Arrays.asList("member1", "member2"));
        findMembers.forEach(member -> {
            System.out.println("member = " + member.getUsername());
        });
        //then
        assertThat(findMembers).hasSameElementsAs(Arrays.asList(member1, member2));
    }

    @Test
    void returnTypeTest() throws Exception{
        //given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> findMembers = memberRepository.findListByUsername("member1");
        Member findMember = memberRepository.findMemberByUsername("member1");
        Optional<Member> findMemberOptional = memberRepository.findOptionalByUsername("member1");
        //then
        assertThat(findMembers).hasSameElementsAs(Arrays.asList(member1));
        assertThat(findMember).isEqualTo(member1);
        assertThat(findMemberOptional.orElseThrow()).isEqualTo(member1);
    }

    @Test
    public void pagingTest() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));


        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        List<Member> members = page.getContent();

        //then
        assertThat(members.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void sliceTest() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));


        //when
        Slice<Member> page = memberRepository.findSliceByAge(age, pageRequest);
        List<Member> members = page.getContent();

        //then
        assertThat(members.size()).isEqualTo(3);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }
    @Test
    public void queryPageTest() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findQueryByAge(age, pageRequest);
        List<Member> members = page.getContent();

        //then
        assertThat(members.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    void bulkAgePlus() throws Exception{
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        //when
        int resultCount = memberRepository.bulkAgePlus(20);
        List<Member> findMembers = memberRepository.findByUsername("member5");
        Member member = findMembers.get(0);

        //then
        assertThat(resultCount).isEqualTo(3);
        assertThat(member.getAge()).isEqualTo(41);
    }

    @Test
    void findMemberLazy(){
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        //when
        List<Member> findMembers = memberRepository.findAll();

        for (Member member : findMembers) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }

        //then

    }

}