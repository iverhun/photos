package test.photos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.orientechnologies.orient.core.annotation.OVersion;

import javax.persistence.Id;
import java.io.Serializable;
import java.nio.file.Path;

@JsonIgnoreProperties({ "handler" })
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    private String id;

    private String src;
    private String description;
    private boolean removed;

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    @OVersion
    private Object version;

    public Image() {
    }

    public Image(String src, String description) {
        this.src = src;
        this.description = description;
    }

    public Image(Path path) {
        this.src = "res/" + path.getFileName().toString();
        this.description = path.getFileName().toString();
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}