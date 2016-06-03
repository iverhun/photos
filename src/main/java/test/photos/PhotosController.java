package test.photos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import test.photos.model.Image;
import test.photos.service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

@RestController
public class PhotosController {

    public static final String PATH = "/Users/ivanverhun/Pictures/Photos Library.photoslibrary/Masters/2016/05/03/20160503-195853/";

    //    private final Set<String> removedPhotos = new HashSet<>();
    private final Stack<UpdateCommand> updateCommands = new Stack<>();

    @Autowired
    private ImageService imageService;

    //    @RequestMapping("/photos")
    //    public List<PhotosList> photos() throws IOException {
    //        return list(Paths.get(PATH))
    //                    .filter(p -> !p.getFileName().toString().endsWith("MOV"))
    //                    .limit(10)
    //                    .map(PhotosList::new)
    //                    .filter(pl -> !removedPhotos.contains(pl.desc))
    //                    .collect(Collectors.toList());
    //    }

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

    @RequestMapping(value = "/photos", method = RequestMethod.GET)
    public List<Image> list() throws IOException {
        return imageService.listNotRemovedImages();
    }

    @RequestMapping(value = "/photos/{desc}", method = RequestMethod.GET)
    public Image getByDescription(@PathVariable("desc") String desc) throws IOException {
        return imageService.getImage(desc);
    }

    @RequestMapping(value = "/library", method = RequestMethod.POST)
    public void init() throws IOException {
        imageService.saveImages(PATH);
    }

    private void markPhoto(String imgPath, Action action) {
        if (Action.DELETE.equals(action)) {
            //removedPhotos.add(imgPath);
            imageService.markRemoved(imgPath);
        } else {
            //removedPhotos.remove(imgPath);
            imageService.markNotRemoved(imgPath);
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