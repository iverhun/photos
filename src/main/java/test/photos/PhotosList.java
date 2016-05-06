package test.photos;

import java.nio.file.Path;

/**
 * TODO: Document me
 *
 * @author Ivan Verhun (ivanver@jfrog.com)
 */
public class PhotosList {
    public String src;
    public String desc;

    public PhotosList(Path path) {
        this.src = "res/" + path.getFileName().toString();
        this.desc = path.getFileName().toString();
    }
}
