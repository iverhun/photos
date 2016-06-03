package test.photos;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import test.photos.model.Image;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class OrientDbConfig {

    @Value("${photos.home}")
    private String photosHome;

    @Bean
    @Profile("!test")
    public OObjectDatabaseTx objectDb() {
        OObjectDatabaseTx db = new OObjectDatabaseTx("plocal:" + photosHome);

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
