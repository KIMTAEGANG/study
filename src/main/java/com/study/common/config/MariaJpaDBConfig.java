package com.study.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.study.jpa.repository"})
@RequiredArgsConstructor
public class MariaJpaDBConfig {

    private final Environment env;

    @Bean
    public DataSource jpaDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("spring.maria.tracking.datasource.driverClassName"));
        config.setJdbcUrl(env.getProperty("spring.maria.tracking.datasource.url"));
        config.setUsername(env.getProperty("spring.maria.tracking.datasource.username"));
        config.setPassword(env.getProperty("spring.maria.tracking.datasource.password"));
        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(1000);
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
}
