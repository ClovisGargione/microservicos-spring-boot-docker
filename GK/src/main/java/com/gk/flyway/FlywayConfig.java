/**
 * 
 */
package com.gk.flyway;

import java.util.Arrays;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author clovis
 *
 */
@Configuration
public class FlywayConfig {
	
	
	List<String> schemas = Arrays.asList("master");

	@Bean
	public FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
		flyway.setDataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
		flyway.setCallbacks(seedDataFlywayCallback());
		for (String string : schemas) {
			flyway.setBaselineOnMigrate(true);
 			flyway.setSchemas(string);
 			flyway.migrate();
		}
		return new FlywayMigrationInitializer(flyway);
	}

	@Bean
	public FlywayCallback seedDataFlywayCallback() {
		return new FlywayCallbackMigration();
	}

}
