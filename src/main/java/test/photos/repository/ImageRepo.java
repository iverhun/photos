package test.photos.repository;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import test.photos.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Verhun (ivanver@jfrog.com)
 */
@Repository
//public interface ImageRepo extends CrudRepository<Image, Long> {
public class ImageRepo {

    @Autowired
    private OObjectDatabaseTx objectDb;

    public List<Image> findBySrc(String src) {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());

        List<Image> result = objectDb.query(
                new OSQLSynchQuery<Image>("select * from Image where src = " + src));
        return result;
    }

    public List<Image> findAll() {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());

        List<Image> images = new ArrayList<>();
        objectDb.browseClass(Image.class).forEach(image -> images.add(image));
        images.stream().forEach(i -> objectDb.detach(i));

        return images;
    }

    public void save(List<Image> images) {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());
        images.stream().forEach(i -> objectDb.save(i));
    }
}
