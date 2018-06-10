package com.enterprisedb.salesengineering.demoapprestapi.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages = {"com.enterprisedb.salesengineering.demoapprestapi.data.repositories"})
@EnableTransactionManagement
public class DataConfiguration {

    private static final String[] ENTITY_PACKAGES = {
            "com.enterprisedb.salesengineering.demoapprestapi.data.domain"
    };

    public void setUrl(String url) {
        this.url = url;
    }

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.initialSize:1}")
    private int initialSize;

    @Value("${datasource.maxActive:3}")
    private int maxActive;

    @Value("${datasource.validationQuery:SELECT 1}")
    private String validationQuery;

    @Value("${datasource.testWhileIdle:true}")
    private boolean testWhileIdle;

    @Value("${datasource.testOnBorrow:true}")
    private boolean testOnBorrow;

    @Value("${datasource.timeBetweenEvictionRunsMillis:10000}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${datasource.defaultTransactionIsolation:2}")
    private int defaultTransactionIsolation;

    @Value("${datasource.minEvictableIdleTimeMillis:600000}")
    private int minEvictableIdleTimeMillis;

    @Value("${datasource.defaultReadOnly:false}")
    private boolean defaultReadOnly;

    @Value("${datasource.maxIdle:1}")
    private int maxIdle;

    @Value("${jpa.hbm2ddl.auto:none}")
    private String hbm2ddlAuto;

    @Value("${jpa.show_sql:true}")
    private boolean showSql;

    @Value("${jpa.format_sql:true}")
    private boolean formatSql;

    @Value("${jpa.properties.org.hibernate.flushMode:COMMIT}")
    private String flushMode;

    @Bean
    public DataSource dataSource() {
        BasicDataSource datasource = new BasicDataSource();
        datasource.setDriverClassName(org.postgresql.Driver.class.getName());
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setInitialSize(initialSize);
        datasource.setMaxActive(maxActive);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTimeBetweenEvictionRunsMillis(
                timeBetweenEvictionRunsMillis);
        datasource.setDefaultTransactionIsolation(defaultTransactionIsolation);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setDefaultReadOnly(defaultReadOnly);
        datasource.setMaxIdle(maxIdle);

        return datasource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);

        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true);
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(ENTITY_PACKAGES);

        Properties jpaProperties = new Properties();
        //Configures the used database dialect. This allows Hibernate to create SQL
        //that is optimized for the used database.
        jpaProperties.put("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQLDialect");

        //Specifies the action that is invoked to the database when the Hibernate
        //SessionFactory is created or closed.
        jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);

        //Configures the naming strategy that is used when Hibernate creates
        //new database objects and schema elements
        jpaProperties.put("hibernate.ejb.naming_strategy",
                "org.hibernate.cfg.ImprovedNamingStrategy");

        //If the value of this property is true, Hibernate writes all SQL
        //statements to the console.
        jpaProperties.put("hibernate.show_sql", showSql);

        //If the value of this property is true, Hibernate will use prettyprint
        //when it writes SQL to the console.
        jpaProperties.put("hibernate.format_sql", formatSql);

        jpaProperties.put(
                "spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults",
                "false");

        jpaProperties.put("org.hibernate.flushMode", flushMode);

        factory.setJpaProperties(jpaProperties);

        return factory;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Bean
    public JpaTransactionManager transactionManager(
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager txnManager = new JpaTransactionManager();
        txnManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return txnManager;
    }
}
