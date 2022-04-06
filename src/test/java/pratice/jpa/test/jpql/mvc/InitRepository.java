package pratice.jpa.test.jpql.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pratice.jpa.test.jpql.entity.*;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class InitRepository {
    @Autowired
    EntityManager em;

    public void init(){
        PracTeam pracTeamA = new PracTeam("teamA");
        PracTeam pracTeamB = new PracTeam("teamB");

        PracMember pracMember1 = new PracMember("user1", 10);
        PracMember pracMember2 = new PracMember("user2", 20);
        PracMember pracMember3 = new PracMember("user3", 30);
        pracMember1.addPracTeam(pracTeamA);
        pracMember2.addPracTeam(pracTeamB);
        pracMember3.addPracTeam(pracTeamB);

        //상품
        PracProduct pracProduct1 = new PracProduct("product1", 100, 10);
        PracProduct pracProduct2 = new PracProduct("product2", 200, 10);
        PracProduct pracProduct3 = new PracProduct("product3", 300, 10);

        //주문
        PracOrder pracOrder1 = new PracOrder(1, new PracAddress("SEOUL", "1", "1-1"));
        PracOrder pracOrder2 = new PracOrder(2, new PracAddress("SEOUL", "1", "1-1"));
        PracOrder pracOrder3 = new PracOrder(3, new PracAddress("JEJU", "1", "1-1"));
        pracOrder1.addPracProduct(pracProduct1);
        pracOrder2.addPracProduct(pracProduct2);
        pracOrder3.addPracProduct(pracProduct3);

        pracMember1.addPracOrder(pracOrder1);
        pracMember1.addPracOrder(pracOrder2);
        pracMember2.addPracOrder(pracOrder3);

        em.persist(pracMember1);
        em.persist(pracMember2);
        em.persist(pracMember3);
    }
}
