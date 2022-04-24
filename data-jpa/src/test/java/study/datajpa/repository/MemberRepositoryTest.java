package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

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

}