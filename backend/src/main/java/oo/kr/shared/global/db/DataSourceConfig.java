package oo.kr.shared.global.db;

import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Profile("!test & local")
@EnableTransactionManagement
public class DataSourceConfig {

  private final String MASTER_DATASOURCE = "masterDataSource";
  private final String SLAVE_DATASOURCE = "slaveDataSource";
  private final String ROUTING_DATASOURCE = "routingDatasource";
  private final String LAZY_ROUTING_DATASOURCE = "lazyRoutingDataSource";
  private final HibernateProperties hibernateProperties;
  private final JpaProperties jpaProperties;

  public DataSourceConfig(JpaProperties jpaProperties) {
    this.jpaProperties = jpaProperties;
    this.hibernateProperties = new HibernateProperties();
    hibernateProperties.setDdlAuto("create");
  }

  @Bean(MASTER_DATASOURCE)
  @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
  public DataSource masterDataSource() {
    return DataSourceBuilder.create()
                            .build();
  }

  @Bean(SLAVE_DATASOURCE)
  @ConfigurationProperties(prefix = "spring.datasource.hikari.slave")
  public DataSource slaveDataSource() {
    return DataSourceBuilder.create()
                            .build();
  }

  @DependsOn({MASTER_DATASOURCE, SLAVE_DATASOURCE})
  @Bean(ROUTING_DATASOURCE)
  public AbstractRoutingDataSource routingDataSource(
      @Qualifier(MASTER_DATASOURCE) DataSource masterDataSource,
      @Qualifier(SLAVE_DATASOURCE) DataSource slaveDataSource
  ) {
    AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
      @Override
      protected Object determineCurrentLookupKey() {
        return RoutingDataSourceManager.getCurrentDataSourceName();
      }
    };

    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DataSourceType.MASTER, masterDataSource);
    targetDataSources.put(DataSourceType.SLAVE, slaveDataSource);

    routingDataSource.setTargetDataSources(targetDataSources);
    routingDataSource.setDefaultTargetDataSource(masterDataSource);

    return routingDataSource;
  }

  @DependsOn(ROUTING_DATASOURCE)
  @Bean(LAZY_ROUTING_DATASOURCE)
  public LazyConnectionDataSourceProxy lazyRoutingDatasource(
      @Qualifier(ROUTING_DATASOURCE) DataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }

  @Bean
  public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
    return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
  }

  @DependsOn(LAZY_ROUTING_DATASOURCE)
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier(LAZY_ROUTING_DATASOURCE) DataSource lazyRoutingDataSource
  ) {
    var props = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(),
        new HibernateSettings());

    return builder.dataSource(lazyRoutingDataSource)
                  .packages("oo.kr.shared.domain.**.domain")
                  .properties(props)
                  .persistenceUnit("common")
                  .build();
  }

  @DependsOn(LAZY_ROUTING_DATASOURCE)
  @Bean
  public JdbcTemplate jdbcTemplate(@Qualifier(LAZY_ROUTING_DATASOURCE) DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }


}
