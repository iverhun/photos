package test.photos.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.Paths;

import static sun.plugin.javascript.navig.JSType.Image;
//
///**
// * @author Ivan Verhun (ivanver@jfrog.com)
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//public class ImageRepoTest {
//
//    @Autowired
//    public ImageRepo imageRepo;
//
//    @Test
//    public void testGet() throws Exception {
//        assertEquals(0, imageRepo.count());
//
//        Image savedImage = imageRepo.save(new Image(Paths.get("/tmp/img.jpg")));
//        assertEquals(1, imageRepo.count());
//
//        Iterable<Image> images = imageRepo.findAll();
//
//        //        assertEquals(1, imageRepo.count());
//        assertEquals(savedImage.getId(), images.iterator().next().getId());
//
//    }
//
//}