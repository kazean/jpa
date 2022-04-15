package pratice.jpa.test.criteria;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pratice.jpa.test.jpql.dto.PracUserDTO;
import pratice.jpa.test.jpql.entity.PracMember;
import pratice.jpa.test.jpql.entity.PracTeam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest
public class CriteriaTest {

    @Autowired
    EntityManager em;

    @Test
    void basic() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PracMember> cq = cb.createQuery(PracMember.class);
        Root<PracMember> m = cq.from(PracMember.class);

        cq.select(m);
        TypedQuery<PracMember> query = em.createQuery(cq);
        List<PracMember> resultList = query.getResultList();
    }

    /*
    Member
    -name:회원1
    -name desc
     */
    @Test
    void searchCondition1() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PracMember> cq = cb.createQuery(PracMember.class);
        Root<PracMember> m = cq.from(PracMember.class);

        cq.select(m)
                .where(cb.equal(m.get("username"), "회원1"))
                .orderBy(cb.desc(m.get("age")));

        List<PracMember> resultList = em.createQuery(cq)
                .getResultList();
    }

    /*
    Member
    -age > 10
    m.<T>get(String column)
     */
    @Test
    void searchCondition2() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PracMember> cq = cb.createQuery(PracMember.class);
        Root<PracMember> m = cq.from(PracMember.class);

        cq.select(m)
                .where(cb.gt(m.<Integer>get("age"), 10));
        List<PracMember> resultList = em.createQuery(cq)
                .getResultList();
    }

    @Test
    void select() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<PracMember> m = cq.from(PracMember.class);

        cq.multiselect(m.get("username"), m.<Integer>get("age"));
        List<Object[]> resultList = em.createQuery(cq).getResultList();
    }

    @Test
    void new_construct() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PracUserDTO> cq = cb.createQuery(PracUserDTO.class);
        Root<PracMember> m = cq.from(PracMember.class);
        cq.select(cb.construct(PracUserDTO.class, m.get("username"), m.get("age")));

        List<PracUserDTO> resultList = em.createQuery(cq)
                .getResultList();
    }
    
    @Test
    void select_tuple() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<PracMember> m = cq.from(PracMember.class);

        cq.multiselect(
                m.get("username").alias("username"),
                m.get("age").alias("age")
        );
        List<Tuple> resultList = em.createQuery(cq)
                .getResultList();

        for (Tuple tuple : resultList) {
            tuple.get("username", String.class);
            tuple.get("age", Integer.class);
        }
    }

    @Test
    void join() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<PracMember> m = cq.from(PracMember.class);

        Join<PracMember, PracTeam> t = m.join("pracTeam");
        cq.multiselect(m, t)
                .where(cb.equal(t.get("name"), "팀A"));
        List<Object[]> resultList = em.createQuery(cq).getResultList();
    }

    /*
    select m from PracMember m
    where m.age >= (select AVG(m2.age) from PracMember m2;
     */
    @Test
    void subquery() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PracMember> mainQuery = cb.createQuery(PracMember.class);
        Root<PracMember> m1 = mainQuery.from(PracMember.class);

        Subquery<Double> subQuery = mainQuery.subquery(Double.class);
        Root<PracMember> m2 = subQuery.from(PracMember.class);
        subQuery.select(cb.avg(m2.<Integer>get("age")));

        mainQuery.select(m1)
                .where(cb.ge(m1.<Integer>get("age"), subQuery));
        List<PracMember> resultList = em.createQuery(mainQuery).getResultList();
    }

    /*
    상호 관련 서브쿼리
    select m from PracMember m
    where exists ( select t From m.team t where t.name = '팀A' )
     */
    @Test
    void subquery2() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PracMember> mainQuery = cb.createQuery(PracMember.class);
        Root<PracMember> m = mainQuery.from(PracMember.class);

        Subquery<PracTeam> subQuery = mainQuery.subquery(PracTeam.class);
        Root<PracMember> subM = subQuery.correlate(m);
        Join<PracMember, PracTeam> t = subM.join("pracTeam");
        subQuery.select(t)
                .where(cb.equal(t.get("name"), "팀A"));

        mainQuery.select(m)
                .where(cb.exists(subQuery));

        List<PracMember> resultList = em.createQuery(mainQuery).getResultList();

    }

    @Test
    void search_case() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<PracMember> m = cq.from(PracMember.class);

        cq.multiselect(
                m.get("username"),
                cb.selectCase()
                        .when(cb.gt(m.<Integer>get("age"), 60), 600)
                        .when(cb.lt(m.<Integer>get("age"),10),100)
                        .otherwise(1000)
        );
        List<Object[]> resultList = em.createQuery(cq).getResultList();
    }

    /*
    하이버네이트에선 Predicate에서 바인딩 해줘도 된다
    
    네이티브함수 cb.function("SUM", Long.class, m.get("age"))
    하이버네이트 구현체는 방언에 사용자 정의함수 등록해야 함
     */
    @Test
    void parameterBinding() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PracMember> cq = cb.createQuery(PracMember.class);
        Root<PracMember> m = cq.from(PracMember.class);

        cq.select(m)
                .where(cb.equal(m.get("username"), cb.parameter(String.class, "usernameParam")));

        List<PracMember> resultList = em.createQuery(cq)
                .setParameter("usernameParam", "user1")
                .getResultList();
    }

    @Test
    void dynamicQuery() {
        String userParam = "user1";
        Integer ageParam = 10;
        String teamnameParam = "teamA";

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PracMember> cq = cb.createQuery(PracMember.class);

        Root<PracMember> m = cq.from(PracMember.class);
        Join<PracMember, PracTeam> t = m.join("pracTeam");

        List<Predicate> criteria = new ArrayList<>();
        if (userParam != null) {
            criteria.add(cb.equal(m.get("username"), userParam));
        }
        if (ageParam != null) {
            criteria.add(cb.equal(m.get("age"), ageParam));
        }
        if (teamnameParam != null) {
            criteria.add(cb.equal(t.get("name"), teamnameParam));
        }
        cq.select(m)
                .where(cb.and(criteria.toArray(new Predicate[0])));

        List<PracMember> resultList = em.createQuery(cq).getResultList();
    }

}
