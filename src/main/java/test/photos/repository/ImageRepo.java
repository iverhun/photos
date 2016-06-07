package test.photos.repository;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import javafx.collections.transformation.SortedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import test.photos.model.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.description;

/**
 * @author Ivan Verhun (ivanver@jfrog.com)
 */
@Repository
public class ImageRepo {

    @Autowired
    private OObjectDatabaseTx objectDb;

    public List<Image> findByDescription(String description) {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());

        List<Image> result = objectDb.query(
                new OSQLSynchQuery<Image>("SELECT * FROM Image WHERE description = '" + description + "'"));
        return result;
    }

    public List<Image> findBySrc(String src) {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());

        List<Image> result = objectDb.query(
                new OSQLSynchQuery<Image>("SELECT * FROM Image WHERE src = '" + src + "'"));
        return result;
    }

    public List<Image> findNotRemoved() {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());

        List<Image> result = objectDb.query(
                new OSQLSynchQuery<Image>("SELECT * FROM Image WHERE removed = false ORDER BY src ASC"));
        return result;
    }

    public List<Image> findAll() {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());

        List<Image> images = new ArrayList<>();
        objectDb.browseClass(Image.class).forEach(image -> images.add(image));
        images.stream().forEach(i -> objectDb.detach(i));

        return images.stream().sorted((a, b) -> a.getSrc().compareTo(b.getSrc())).collect(Collectors.toList());
    }

    public Image save(Image image) {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());
        return objectDb.save(image);
    }

    public void save(List<Image> images) {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());
        images.stream().forEach(i -> objectDb.save(i));
    }

    public long count() {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());
        return objectDb.countClass(Image.class);
    }
}
