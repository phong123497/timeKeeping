package com.timekeeping.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author minhtq2 on 20/10/2023
 * @project TimeKeeping
 */
@Configuration
@PropertySource({ "classpath:timekeeping.properties" })
@EnableJpaRepositories(entityManagerFactoryRef = "timekeepingEntityManagerFactory",
        transactionManagerRef = "timekeepingTransactionManager",
        basePackages = {"com.timekeeping.common" })
//@EnableJpaAuditing
public class PersistenceConfig {
    @Autowired
    private Environment env;

    @Bean(name = "timekeepingDataSource")
    @ConfigurationProperties(prefix = "timekeeping.auth.spring.datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("timekeeping.auth.spring.datasource.url"));
        dataSource.setUsername(env.getProperty("timekeeping.auth.spring.datasource.username"));
        dataSource.setPassword(env.getProperty("timekeeping.auth.spring.datasource.password"));

        return dataSource;
    }

    @Bean(name = "timekeepingEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean timekeepingEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                                  @Qualifier("timekeepingDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.timekeeping.common").build();
    }

    @Primary
    @Bean(name = "timekeepingTransactionManager")
    public PlatformTransactionManager timekeepingTransactionManager(
            @Qualifier("timekeepingEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
