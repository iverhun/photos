package test.photos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.photos.model.Image;
import test.photos.repository.ImageRepo;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * TODO: Document me
 *
 * @author Ivan Verhun (ivanver@jfrog.com)
 */
@Service
public class ImageService {
    @Autowired
    private ImageRepo imageRepo;

    public void saveImages(List<Image> images) {
        imageRepo.save(images);
    }

    public List<Image> listImages() {
        return imageRepo.findAll();
    }

    public Image getImage(String src) {
        return imageRepo.findBySrc(src).stream().findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

}
