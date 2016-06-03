package test.photos.repository;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.photos.model.Image;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Ivan Verhun (ivanver@jfrog.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestConfig.class })
public class ImageRepoTest {

    @Autowired
    private OObjectDatabaseTx objectDb;

    @Autowired
    public ImageRepo imageRepo;

    @Before
    public void cleanupDb() {
        ODatabaseRecordThreadLocal.INSTANCE.set(objectDb.getUnderlying());
        objectDb.command(new OCommandSQL("TRUNCATE CLASS Image")).execute();
    }

    @Test
    public void testSaveAndGet() throws Exception {
        assertEquals(0, imageRepo.count());

        Image savedImage = imageRepo.save(new Image(Paths.get("/tmp/img.jpg")));
        assertEquals(1, imageRepo.count());

        List<Image> images = imageRepo.findAll();

        assertEquals(1, images.size());
        assertEquals(savedImage.getId(), images.iterator().next().getId());

    }

    @Test
    public void testFindByDesc() throws Exception {
        // G
        assertEquals(0, imageRepo.count());

        Image savedImage = imageRepo.save(new Image(Paths.get("/tmp/img.jpg")));
        assertEquals(1, imageRepo.count());

        // W
        List<Image> images = imageRepo.findByDescription("img.jpg");

        // T
        assertEquals(1, images.size());
        assertEquals("img.jpg", images.iterator().next().getDescription());
        assertEquals(savedImage.getId(), images.iterator().next().getId());

        assertTrue(imageRepo.findByDescription("wrong_img.jpg").isEmpty());
    }


    @Test
    public void testFindBySrc() throws Exception {
        // G
        assertEquals(0, imageRepo.count());

        Image savedImage = imageRepo.save(new Image(Paths.get("/tmp/img.jpg")));
        assertEquals(1, imageRepo.count());

        // W
        List<Image> images = imageRepo.findBySrc("res/img.jpg");

        // T
        assertEquals(1, images.size());
        assertEquals("res/img.jpg", images.iterator().next().getSrc());
        assertEquals(savedImage.getId(), images.iterator().next().getId());

        assertTrue(imageRepo.findBySrc("res/wrong_img.jpg").isEmpty());
    }


    @Test
    public void testFindNotRemoved() throws Exception {
        // G
        assertEquals(0, imageRepo.count());

        Image notRemovedImage = imageRepo.save(new Image(Paths.get("/tmp/img.jpg")));

        Image ri = new Image(Paths.get("/tmp/img_removed.jpg"));
        ri.setRemoved(true);
        Image removedImage = imageRepo.save(ri);

        assertEquals(2, imageRepo.count());

        // W
        List<Image> images = imageRepo.findNotRemoved();

        // T
        assertEquals(1, images.size());
        assertEquals(notRemovedImage.getDescription(), images.iterator().next().getDescription());
    }


    @Test
    public void testUpdate() {
        // G
        assertEquals(0, imageRepo.count());

        Image savedImage = imageRepo.save(new Image(Paths.get("/tmp/img.jpg")));
        assertEquals(1, imageRepo.count());
        assertFalse(imageRepo.findAll().get(0).isRemoved());

        // W
        savedImage.setRemoved(true);
        imageRepo.save(savedImage);

        // T
        List<Image> all = imageRepo.findAll();
        assertEquals(1, all.size());

        assertTrue(all.get(0).isRemoved());

    }

    @Test
    public void testUpdate2() {
        // G
        assertEquals(0, imageRepo.count());

        Image savedImage = imageRepo.save(new Image(Paths.get("/tmp/img.jpg")));
        assertEquals(1, imageRepo.count());
        assertFalse(imageRepo.findAll().get(0).isRemoved());

        // W
        Image detachedImage = new Image();
        detachedImage.setId(savedImage.getId());
        detachedImage.setSrc(savedImage.getSrc());
        detachedImage.setDescription(savedImage.getDescription());
        detachedImage.setVersion(savedImage.getVersion());

        detachedImage.setRemoved(true);

        imageRepo.save(detachedImage);

        // T
        List<Image> all = imageRepo.findAll();
        assertEquals(1, all.size());

        assertTrue(all.get(0).isRemoved());
    }
}