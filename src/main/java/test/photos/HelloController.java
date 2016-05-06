package test.photos;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.sun.tools.javac.code.Lint.LintCategory.PATH;

/**
 * TODO: Document me
 *
 * @author Ivan Verhun (ivanver@jfrog.com)
 */
@RestController
public class HelloController {

    public static final String PATH = "/Users/ivanverhun/Pictures/Photos Library.photoslibrary/Masters/2016/05/03/20160503-195853/";

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/photos")
    public List<PhotosList> photos() throws IOException {
        return Files.list(Paths.get(PATH))
                    .filter(p -> !p.getFileName().toString().endsWith("MOV"))
                    //.limit(10)
                    .map(PhotosList::new).collect(Collectors.toList());
    }

}