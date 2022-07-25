package sky.pro.pet_bot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long pictureId;
    private String filePath;
    private Integer fileSize;
    private String mediaType;

    @ManyToOne()
    @JoinColumn(name = "pet_id")
    @JsonBackReference
    private Pet pet;

    @OneToMany(mappedBy = "picture")
    @JsonManagedReference
    private Collection<Report> reports;

    public Picture() {
    }

    public Picture(Long pictureId, String filePath, Integer fileSize, String mediaType) {
        this.pictureId = pictureId;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
    }

    public Long getId() {
        return pictureId;
    }

    public void setId(Long id) {
        this.pictureId = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Collection<Report> getReports() {
        return reports;
    }

    public void setReports(Collection<Report> reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picture picture = (Picture) o;
        return pictureId.equals(picture.pictureId) && getFilePath().equals(picture.getFilePath()) && getFileSize().equals(picture.getFileSize()) && getMediaType().equals(picture.getMediaType()) && Objects.equals(getPet(), picture.getPet()) && Objects.equals(getReports(), picture.getReports());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pictureId, getFilePath(), getFileSize(), getMediaType(), getPet(), getReports());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Picture.class.getSimpleName() + "[", "]")
                .add("pictureId=" + pictureId)
                .add("filePath='" + filePath + "'")
                .add("fileSize=" + fileSize)
                .add("mediaType='" + mediaType + "'")
                .add("pet=" + pet)
                .add("reports=" + reports)
                .toString();
    }
}
