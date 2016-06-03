package test.photos.repository;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import test.photos.model.Image;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringApplicationConfiguration
@ComponentScan({"test.photos.repository"})
public class TestConfig {

    @Bean
    public OObjectDatabaseTx objectDb() {
        OObjectDatabaseTx db = new OObjectDatabaseTx("memory:test_photos");

        if (!db.exists()) {
            db.create();
        } else {
            db.open("admin", "admin");
        }

        ODatabaseRecordThreadLocal.INSTANCE.set(db.getUnderlying());
        return db;
    }

    @PostConstruct
    public void registerEntities() {
        objectDb().getEntityManager().registerEntityClass(Image.class);
    }

    @PreDestroy
    public void close() {
        objectDb().close();
    }

}
