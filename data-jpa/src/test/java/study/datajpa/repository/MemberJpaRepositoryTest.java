package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @AfterEach
    void clear(){
        memberJpaRepository.deleteAll();
    }

    @Test
    void testMember() throws Exception{
        //given
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);
        //when
        Member findMember = memberJpaRepository.find(savedMember.getId());
        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
        assertThat(findMember).isEqualTo(savedMember);

    }
    
    @Test
    void saveMember() throws Exception{
        //given
        Member member = new Member("member1");
        memberJpaRepository.save(member);
        //when
        Member findMember = memberJpaRepository.find(member.getId());
        //then
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    void findById() throws Exception{
        //given
        Member member = new Member("member1");
        memberJpaRepository.save(member);
        //when
        Optional<Member> findMemberOptional = memberJpaRepository.findById(member.getId());
        Member findMember = findMemberOptional.orElseThrow(()->new IllegalArgumentException("회원 조회가 실패하였습니다."));
        //then
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    void findAll() throws Exception{
        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        //when
        List<Member> findMembers = memberJpaRepository.findAll();
        //then
        assertThat(findMembers.size()).isEqualTo(2);
        assertThat(findMembers).hasSameElementsAs(Arrays.asList(member1, member2));
    }

    @Test
    void count() throws Exception{
        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        //when
        long resultCount = memberJpaRepository.count();
        //then
        assertThat(resultCount).isEqualTo(2);
    }
}