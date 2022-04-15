package pratice.jpa.test.querydsl;

import com.querydsl.core.support.QueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pratice.jpa.test.entity.Member;
import pratice.jpa.test.entity.QMember;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@SpringBootTest
public class QueryDslTest {

    @Autowired
    EntityManager em;

    @Test
    void basic() {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember member = QMember.member;
        List<Member> members = query.selectFrom(member)
                .where(member.name.eq("회원1"))
                .orderBy(member.name.desc())
                .fetch();


    }
}
