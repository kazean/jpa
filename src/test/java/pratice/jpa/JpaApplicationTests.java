package pratice.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Rollback(value = true)
class JpaApplicationTests {

	@Test
	void contextLoads() {
	}

}
