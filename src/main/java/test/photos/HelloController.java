package test.photos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.photos.model.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import static java.nio.file.Files.list;

@RestController
public class HelloController {

    public static final String PATH = "/Users/ivanverhun/Pictures/Photos Library.photoslibrary/Masters/2016/05/03/20160503-195853/";

    private final Set<String> removedPhotos = new HashSet<>();
    private final Stack<UpdateCommand> updateCommands = new Stack<>();

    @Autowired
    private ImageService imageService;

    @RequestMapping("/photos")
    public List<PhotosList> photos() throws IOException {
        return list(Paths.get(PATH))
                    .filter(p -> !p.getFileName().toString().endsWith("MOV"))
                    .limit(10)
                    .map(PhotosList::new)
                    .filter(pl -> !removedPhotos.contains(pl.desc))
                    .collect(Collectors.toList());
    }

    @RequestMapping(value = "/photos", method = RequestMethod.PUT, consumes = "application/json")
    public void mark(@RequestBody UpdateCommand updateCommand) throws IOException {
        markPhoto(updateCommand.imgPath, updateCommand.action);
        updateCommands.push(updateCommand);
    }

    @RequestMapping(value = "/photos/undo", method = RequestMethod.POST)
    public void undo() throws IOException {
        UpdateCommand updateCommand = updateCommands.pop();
        markPhoto(updateCommand.imgPath, updateCommand.action.getUndoAction());
    }


    @RequestMapping(value = "/photos/testList", method = RequestMethod.GET)
    public List<Image> testList() throws IOException {
        return imageService.listImages();
    }

    @RequestMapping(value = "/photos/testGet", method = RequestMethod.GET)
    public Image testGet(@RequestParam("src") String src) throws IOException {
        return imageService.getImage(src);
    }

    @RequestMapping(value = "/photos/testSave", method = RequestMethod.POST)
    public void testSave() throws IOException {
        List<Image> images = Files.list(Paths.get(PATH))
             .filter(p -> !p.getFileName().toString().endsWith("MOV"))
             .limit(10)
             .map(Image::new)
             .filter(pl -> !removedPhotos.contains(pl.getDesc()))
             .collect(Collectors.toList());

        imageService.saveImages(images);
    }

    private void markPhoto(String imgPath, Action action) {
        if (Action.DELETE.equals(action)) {
            removedPhotos.add(imgPath);
        } else {
            removedPhotos.remove(imgPath);
        }
    }

    public static class UpdateCommand {
        public String imgPath;
        public Action action;
    }

    public enum Action {
        DELETE, RESTORE;

        public Action getUndoAction() {
            if (this.equals(DELETE)) {
                return RESTORE;
            } else {
                return DELETE;
            }
        }
    }

}