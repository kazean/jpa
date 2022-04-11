package pratice.jpa.test.jpql;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pratice.jpa.test.entity.item.Item;
import pratice.jpa.test.jpql.dto.PracUserDTO;
import pratice.jpa.test.jpql.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Rollback(value = false)
@Transactional
public class JpqlTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void initCheck() {
        PracMember pracMember = em.find(PracMember.class, 1L);
        List<PracOrder> pracOrders = pracMember.getPracOrders();
        PracOrder pracOrder1 = em.find(PracOrder.class, 3L);
        PracOrder pracOrder2 = em.find(PracOrder.class, 5L);
        assertThat(pracOrders).containsExactly(pracOrder1, pracOrder2);
    }

    @Test
    void select() {
        String jpql = "select m from PracMember m";
        List<PracMember> resultList = em.createQuery(jpql, PracMember.class)
                .getResultList();
        for (PracMember pracMember : resultList) {
            log.info("#pracMember={}", pracMember);
        }
    }

    @Test
    void typedQueryAndQuery() {
        String jpql = "select m.username, m.age from PracMember m";
        List resultList = em.createQuery(jpql)
                .getResultList();

        for (Object o : resultList) {
            Object[] oArr = (Object[]) o;
            String name = String.valueOf(oArr[0]);
            int age = (int) oArr[1];
            log.info("name={}, age={}", name, age);
        }
    }

    @Test
    void parameterBinding() {
        String jpql = "select m from PracMember m where m.username =: username";
        List<PracMember> resultList = em.createQuery(jpql, PracMember.class)
                .setParameter("username", "user1")
                .getResultList();

        for (PracMember pracMember : resultList) {
            log.info("pracMember={}", pracMember);
        }
    }

    @Test
    void projection() {
        String entityJpql = "select m.pracTeam from PracMember m";
        List<PracTeam> resultList = em.createQuery(entityJpql, PracTeam.class)
                .getResultList();

        for (PracTeam pracTeam : resultList) {
            log.info("pracTeam={}", pracTeam);
        }
    }

    @Test
    void newOperation() {
        String jpql = "select new pratice.jpa.test.jpql.dto.PracUserDTO(m.username, m.age)" +
                " from PracMember m";
        List<PracUserDTO> resultList = em.createQuery(jpql, PracUserDTO.class)
                .getResultList();

        for (PracUserDTO pracUserDTO : resultList) {
            log.info("pracUserDTO={}", pracUserDTO);
        }
    }

    @Test
    void paging() {
        String jpql = "select m from PracMember m";
        List<PracMember> resultList1 = em.createQuery(jpql, PracMember.class)
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();

        for (PracMember pracMember : resultList1) {
            log.info("pracMember={}", pracMember);
        }

        List<PracMember> resultList2 = em.createQuery(jpql, PracMember.class)
                .setFirstResult(1)
                .setMaxResults(1)
                .getResultList();

        for (PracMember pracMember : resultList2) {
            log.info("pracMember={}", pracMember);

        }

    }

    @Test
    void innerJoin() {
        String teamName = "teamA";
        String jpql = "select m from PracMember m join m.pracTeam t where t.name =:teamName";
        List<PracMember> pracMembers = em.createQuery(jpql, PracMember.class)
                .setParameter("teamName", teamName)
                .getResultList();

        for (PracMember pracMember : pracMembers) {
            log.info("pracMember={}", pracMember);
        }
    }

    /*
    member 쿼리 별개로 나간다.
     */
    @Test
    void leftCollectionJoin() {
        String jpql = "select t from PracTeam t LEFT JOIN t.pracMembers m";
        List<PracTeam> pracTeams = em.createQuery(jpql, PracTeam.class)
                .getResultList();

        for (PracTeam pracTeam : pracTeams) {
            log.info("pracTeam={}", pracTeam);
            List<PracMember> pracMembers = pracTeam.getPracMembers();
            for (PracMember pracMember : pracMembers) {
                log.info(">pracMember={}", pracMember);
            }
        }
    }

    /*
    조인쿼리 한번 나간다.
    일대다 관계에서는 건수 늘어날 수 있음 distinct
     */
    @Test
    void collectionFetchJoin() {
        String jpql = "select distinct t from PracTeam t LEFT JOIN FETCH t.pracMembers m";
        List<PracTeam> pracTeams = em.createQuery(jpql, PracTeam.class)
                .getResultList();

        for (PracTeam pracTeam : pracTeams) {
            log.info("pracTeam={}", pracTeam);
            List<PracMember> pracMembers = pracTeam.getPracMembers();
            for (PracMember pracMember : pracMembers) {
                log.info(">pracMember={}", pracMember);
            }
        }
    }

    /*
    where having 절에서만
    select from X
     */
    @Test
    void subQuery() {
        String jpql = "SELECT m FROM PracMember m" +
                " WHERE m IN " +
                "( SELECT m2 FROM PracMember m2 WHERE m2.age>=20 )";
        List<PracMember> pracMembers = em.createQuery(jpql, PracMember.class)
                .getResultList();

        for (PracMember pracMember : pracMembers) {
            log.info("pracMember={}", pracMember);
        }
    }

    @Test
    void collectionParse() {
        String jpql = "SELECT m FROM PracMember m WHERE m.pracOrders IS NOT EMPTY";
        List<PracMember> pracMembers = em.createQuery(jpql, PracMember.class)
                .getResultList();

        for (PracMember pracMember : pracMembers) {
            log.info("pracMember={}", pracMember);
        }

        String jpqlSize = "SELECT m FROM PracMember m WHERE m.pracOrders.size > 0";
        List<PracMember> pracMembersSize = em.createQuery(jpqlSize, PracMember.class)
                .getResultList();

        for (PracMember pracMember : pracMembersSize) {
            log.info("Size > pracMember={}", pracMember);
        }

    }

    /*@Test
    void initCheck2(){
        PracMember pracMember = em.find(PracMember.class, 1L);
        PracOrder pracOrder1 = em.find(PracOrder.class, 3L);
        PracOrder pracOrder2 = em.find(PracOrder.class, 5L);
        assertThat(pracMember.getPracOrders()).containsExactly(pracOrder1, pracOrder2);
    }*/

    @Test
    void polymorphismType() {
        String jpql = "SELECT i FROM Item i WHERE type(i) IN (Book, Movie)";
        List<Item> resultList = em.createQuery(jpql, Item.class)
                .getResultList();
    }

    @Test
    void polymorphismTreat() {
        String jqpl = "SELECT i FROM Item i WHERE treat(i as Book).author = 'kim'";
        List<Item> resultList = em.createQuery(jqpl, Item.class)
                .getResultList();
    }


}
