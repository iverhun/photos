package test.photos;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    public static final String PATH = "/Users/ivanverhun/Pictures/Photos Library.photoslibrary/Masters/2016/05/03/20160503-195853/";

    private final Set<String> removedPhotos = new HashSet<>();
    private final Stack<UpdateCommand> updateCommands = new Stack<>();

    @RequestMapping("/photos")
    public List<PhotosList> photos() throws IOException {
        return Files.list(Paths.get(PATH))
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