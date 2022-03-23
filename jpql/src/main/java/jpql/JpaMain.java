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

            em.flush();
            em.clear();

            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            List<Member> resultList = query1.getResultList();

            for (Member findMember : resultList) {
                System.out.println("findMember.getUsername() = " + findMember.getUsername());
            }

            TypedQuery<Member> query2 = em.createQuery("select m from Member m", Member.class);
            Member singleResult = query2.getSingleResult();
            System.out.println("singleResult.getUsername() = " + singleResult.getUsername());


            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
