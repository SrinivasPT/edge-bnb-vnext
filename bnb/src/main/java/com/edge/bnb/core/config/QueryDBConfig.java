package com.edge.bnb.core.config;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.edge.bnb.query.repository"}, // Note: This is the query repositories. No repository on the command side
        entityManagerFactoryRef = "cqrsEntityManager",
        transactionManagerRef = "cqrsTransactionManager",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ,
                pattern = "org.axonframework.eventsourcing.eventstore.jpa.*")
)
public class QueryDBConfig {
    @Autowired
    private Environment environment;

    public QueryDBConfig(){
        super();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean cqrsEntityManager(){
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(cqrsDataSource());
        em.setPersistenceUnitName("appEntities"); // Note: This links the persistence XML Persistence Unit
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("queryDb.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", environment.getProperty("queryDb.jpa.hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public DataSource cqrsDataSource(){
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                Preconditions.checkNotNull(environment.getProperty("queryDb.datasource.driver-class-name")));
        dataSource.setUrl(
                Preconditions.checkNotNull(environment.getProperty("queryDb.datasource.url")));
        dataSource.setUsername(
                Preconditions.checkNotNull(environment.getProperty("queryDb.datasource.username")));
        dataSource.setPassword(
                Preconditions.checkNotNull(environment.getProperty("queryDb.datasource.password")));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager cqrsTransactionManager(){
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(cqrsEntityManager().getObject());
        return transactionManager;
    }
}
