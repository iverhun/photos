package test.photos;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import test.photos.model.Image;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
//@EnableTransactionManagement
//@EnableOrientRepositories(basePackages = "test.photos.repository")
public class OrientDbConfig {

    @Bean
    public OObjectDatabaseTx genericDb() {
        OObjectDatabaseTx db = new OObjectDatabaseTx("plocal:/Users/ivanverhun/.myphotos/db");

        if (!db.exists()) {
            db.create();
        } else {
            db.open("admin", "admin");
        }

        ODatabaseRecordThreadLocal.INSTANCE.set(db.getUnderlying());
        return db;
    }

    @Bean
    @Scope("prototype")
    public OObjectDatabaseTx objectDb() {
        OObjectDatabaseTx db = genericDb();
        ODatabaseRecordThreadLocal.INSTANCE.set(db.getUnderlying());
        return db;
    }

    //    @Bean
    //    public OrientObjectDatabaseFactory factory() {
    //        OrientObjectDatabaseFactory factory =  new OrientObjectDatabaseFactory();
    //
    //        factory.setUrl("plocal:photosdb");
    //        factory.setUsername("admin");
    //        factory.setPassword("admin");
    //
    //        return factory;
    //    }
    //
    //    @Bean
    //    public OrientTransactionManager transactionManager() {
    //        return new OrientTransactionManager(factory());
    //    }
    //
    //    @Bean
    //    public OrientObjectTemplate objectTemplate() {
    //        return new OrientObjectTemplate(factory());
    //    }
    //

    @PostConstruct
    public void registerEntities() {
        genericDb().getEntityManager().registerEntityClass(Image.class);
    }

    @PreDestroy
    public void close() {
        genericDb().close();
    }
}
