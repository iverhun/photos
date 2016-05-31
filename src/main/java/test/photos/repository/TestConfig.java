package test.photos.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
//@EnableJpaRepositories(basePackages = "test.photos.repository")
public class TestConfig {
    //    @Bean
    //    JpaVendorAdapter jpaVendorAdapter() {
    //        return new AbstractJpaVendorAdapter() {
    //            @Override
    //            public PersistenceProvider getPersistenceProvider() {
    //                return new com.objectdb.jpa.Provider();
    //            }
    //
    //            @Override
    //            public Map<String, ?> getJpaPropertyMap() {
    //                return Collections.singletonMap(
    //                        "javax.persistence.jdbc.url", "test.odb");
    //            }
    //        };
    //    }

    //    @Bean
    //    public DataSource dataSource() {
    ////        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    ////        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    //        DriverManagerDataSource dataSource = new DriverManagerDataSource("test.odb");
    //        dataSource.setDriverClassName();
    //
    //    }
    //
    //    @Bean
    //    public EntityManagerFactory entityManagerFactory() {
    //        //        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    //        //        vendorAdapter.setGenerateDdl( true );
    //
    //        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    //        factory.setJpaVendorAdapter(jpaVendorAdapter());
    //        factory.setPackagesToScan(Image.class.getPackage().getName());
    //        //factory.setDataSource(dataSource());
    //        //factory.setDataSource(jpaVendorAdapter().);
    //        factory.afterPropertiesSet();
    //
    //        return factory.getObject();
    //        EntityManagerFactory emf = Persistence
    //                .createEntityManagerFactory("test.odb");
    //        return emf;
    //    }
    //
    //    @Bean
    //    public PlatformTransactionManager transactionManager() {
    //        JpaTransactionManager txManager = new JpaTransactionManager();
    //        txManager.setEntityManagerFactory(entityManagerFactory());
    //        return txManager;
    //    }
}
