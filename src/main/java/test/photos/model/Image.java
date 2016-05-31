package test.photos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.orientechnologies.orient.core.annotation.OVersion;

import java.io.Serializable;
import java.nio.file.Path;

@JsonIgnoreProperties({ "handler" })
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    private String src;
    private String desc;
    private boolean removed;

    @OVersion
    private Long version;

    public Image() {
    }

    public Image(String src, String desc) {
        this.src = src;
        this.desc = desc;
    }

    public Image(Path path) {
        this.src = "res/" + path.getFileName().toString();
        this.desc = path.getFileName().toString();
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}