package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
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
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "20000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "20000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("==================");
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("==================");

            List<AddressEntity> addressHistory = findMember.getAddressHistory();

            addressHistory.stream().forEach((addressEntity) -> {
                System.out.println(addressEntity.getAddress().getCity());
            });


            findMember.getFavoriteFoods().stream().forEach((favoriteFood) -> {
                System.out.println(favoriteFood);
            });

            findMember.setHomeAddress(new Address("changeCity", findMember.getHomeAddress().getStreet(), findMember.getHomeAddress().getZipcode()));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "20000"));
            findMember.getAddressHistory().add(new AddressEntity("changeNewCity1", "street", "20000"));


            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
