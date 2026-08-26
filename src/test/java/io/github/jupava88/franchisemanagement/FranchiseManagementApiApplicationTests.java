package io.github.jupava88.franchisemanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.autoconfigure.exclude="
		+ "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
		+ "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,"
		+ "org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration")
class FranchiseManagementApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
