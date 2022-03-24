package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            /**
             * 엔티티 프로젝션
             * 해당 쿼리의 결과는 영속성 컨테스트에서 관리
             */
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();
//
//            Member findMember = result.get(0);
//            findMember.setAge(20);


            /**
             * 엔티티 프로젝션
             * 조인쿼리가 나감  
             * join 쿼리는 성능에 영향을 줄 가능성이 높고 튜닝할 부분이 많기때문에
             * 해당 방법은 권장하지 않음
             */
//            List<Team> resultTeam = em.createQuery("select m.team from Member m", Team.class).getResultList();

            /**
             * 위와 같은 조인 쿼리가 나감
             * 명시적으로 표현이 되어서 권장
             */

//            List<Team> resultTeam2 = em.createQuery("select t from Member m join m.team", Team.class).getResultList();


            /**
             * 임베디드 타입 프로젝션
             */
//            List<Order> orderList = em.createQuery("select o.address from Order o", Order.class).getResultList();

            /**
             * 스칼라 타입 프로젝션
             */
            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m").getResultList();

            Object[] result = resultList.get(0);
            System.out.println(result[0]);
            System.out.println(result[1]);

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
