package n.samsonov.newsfeed;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

//@Testcontainers
//@TestConfiguration
//@ExtendWith(SpringExtension.class)
//public class TestConfig {
//
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public JdbcDatabaseContainer<?> container() {
//        return new PostgreSQLContainer<>("postgres")
//                .withInitScript("dbScripts/createScript.sql")
//                .withDatabaseName("userTest")
//                .waitingFor(Wait.forListeningPort());
//
//    }
//
//    @Bean
//    public DataSource dataSource(JdbcDatabaseContainer<?> jdbcDatabaseContainer) {
//        var hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl(jdbcDatabaseContainer.getJdbcUrl());
//        hikariConfig.setUsername(jdbcDatabaseContainer.getUsername());
//        hikariConfig.setPassword(jdbcDatabaseContainer.getPassword());
//        return new HikariDataSource(hikariConfig);
//    }
//}
