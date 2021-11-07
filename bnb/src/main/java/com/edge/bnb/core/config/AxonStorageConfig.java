package com.edge.bnb.core.config;

import com.google.common.base.Preconditions;
import org.axonframework.common.jdbc.PersistenceExceptionResolver;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class AxonStorageConfig {
    @Autowired
    private Environment environment;

    @Primary
    @Bean
    public DataSource eventStoreDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(
                environment.getProperty("spring.datasource.driverClassname")));
        dataSource.setUsername(Preconditions.checkNotNull(
                environment.getProperty("spring.datasource.username")));
        dataSource.setPassword(Preconditions.checkNotNull(
                environment.getProperty("spring.datasource.password")));
        dataSource.setUrl(Preconditions.checkNotNull(
                environment.getProperty("spring.datasource.url")));
        return dataSource;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        final LocalContainerEntityManagerFactoryBean container = new LocalContainerEntityManagerFactoryBean();
        container.setDataSource(eventStoreDataSource());
        container.setPersistenceUnitName("eventStore");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        container.setJpaVendorAdapter(adapter);

        container.setJpaProperties(getJpaProperties());
        return container;
    }

    private Properties getJpaProperties(){
        final Properties properties = new Properties();

        properties.setProperty("hibernate.show_sql",
                Preconditions.checkNotNull(environment.getProperty("spring.jpa.show-sql")));
        properties.setProperty("hibernate.generate_statistics", "false");
        properties.setProperty("hibernate.hbm2ddl",
                Preconditions.checkNotNull(environment.getProperty("spring.jpa.hibernate.ddl-auto")));
        properties.setProperty("hibernate.dialect",
                Preconditions.checkNotNull(environment.getProperty("spring.jpa.hibernate.dialect")));
        return properties;
    }

    @Primary
    @Bean
    public PlatformTransactionManager jpaTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Primary
    @Bean
    public EventStorageEngine storageEngine(Serializer defaultSerializer,
                                            PersistenceExceptionResolver persistenceExceptionResolver,
                                            @Qualifier("eventSerializer") Serializer eventSerializer,
                                            AxonConfiguration configuration,
                                            EntityManagerProvider entityManagerProvider,
                                            TransactionManager transactionManager) {
        return JpaEventStorageEngine.builder()
                .snapshotSerializer(defaultSerializer)
                .upcasterChain(configuration.upcasterChain())
                .persistenceExceptionResolver(persistenceExceptionResolver)
                .eventSerializer(eventSerializer)
                .entityManagerProvider(entityManagerProvider)
                .transactionManager(transactionManager)
                .build();
    }

    @Primary
    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
                .build();
    }
}
