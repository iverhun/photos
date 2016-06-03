package test.photos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.photos.model.Image;
import test.photos.repository.ImageRepo;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ivan Verhun (ivanver@jfrog.com)
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;

    public void saveImages(String albumPath) throws IOException {
        List<Image> images = Files.list(Paths.get(albumPath))
                                  .filter(p -> !p.getFileName().toString().endsWith("MOV"))
                                  .map(Image::new)
                                  .collect(Collectors.toList());
        imageRepo.save(images);
    }

    public List<Image> listNotRemovedImages() {
        return imageRepo.findNotRemoved();
    }

    public List<Image> listImages() {
        return imageRepo.findAll();
    }

    public Image getImage(String desc) {
        return imageRepo.findByDescription(desc).stream().findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

    public Image markRemoved(String description) {
        Image image = getBySrc(description);
        image.setRemoved(true);
        return imageRepo.save(image);
    }

    public Image markNotRemoved(String description) {
        Image image = getBySrc(description);
        image.setRemoved(false);
        return imageRepo.save(image);
    }

    private Image getBySrc(String description) {
        return imageRepo.findByDescription(description).stream().findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }
}
