package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            em.flush();
            em.clear();


            // 상태필드
//            String query = "select m.username from Member m";

            // 단일 값 연관 경로
//            String query = "select m.team from Member m";

            // 컬렉션 값 연관 경로
            String query = "select t.members from Team t";
            Collection resultList = em.createQuery(query, Collection.class).getResultList();

            for (Object o : resultList) {
                System.out.println("o = " + o);
            }
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
