package org.weatherApp.config;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.weatherApp.service", "org.weatherApp.repository"})
@PropertySource("classpath:h2database.properties")
public class TestConfig {

    private final Environment environment;

    public TestConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getRequiredProperty("h2.connection.driver_class"));
        dataSource.setUrl(environment.getRequiredProperty("h2.connection.url"));
        dataSource.setUsername(environment.getRequiredProperty("h2.connection.username"));
        dataSource.setPassword(environment.getRequiredProperty("h2.connection.password"));
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("db/changelog/db.test-master.yaml");
        liquibase.setDefaultSchema("PUBLIC");
        liquibase.setDropFirst(true);
        liquibase.setShouldRun(true);
        return liquibase;
    }

    @Bean(name="h2data")
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("org.weatherApp.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("h2.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("h2.hibernate.ddl_auto"));
        properties.put("hibernate.show_sql", environment.getProperty("h2.hibernate.show_sql"));
        properties.put("javax.persistence.validation.mode", "none");
        properties.put("hibernate.format_sql", environment.getProperty("h2.hibernate.format_sql"));
        return properties;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager(
            SessionFactory sessionFactory) {

        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
