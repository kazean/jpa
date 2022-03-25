package pratice.jpa.model.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class MemberTest {

    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    void memberEntityTest(){
        Member member = new Member();
        em.persist(member);
        log.info("member id={}", member.getId());

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, 1L);
        log.info("findMember={}", findMember);
    }

}