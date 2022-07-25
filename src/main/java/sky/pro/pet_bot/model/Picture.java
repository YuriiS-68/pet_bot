package sky.pro.pet_bot.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @Column(name = "report_id")
    private Long id;
    private String filePath;
    private Integer fileSize;
    private String mediaType;
    @Lob
    private byte[] preview;
    @OneToOne
    @MapsId
    @JoinColumn(name = "report_id")
    private Report report;

    public Picture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Report getReports() {
        return report;
    }

    public void setReports(Report report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picture picture = (Picture) o;
        return id.equals(picture.id) && getFilePath().equals(picture.getFilePath()) && getFileSize().equals(picture.getFileSize())
                && getMediaType().equals(picture.getMediaType()) && Objects.equals(getReports(), picture.getReports());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getFilePath(), getFileSize(), getMediaType(), getReports());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Picture.class.getSimpleName() + "[", "]")
                .add("pictureId=" + id)
                .add("filePath='" + filePath + "'")
                .add("fileSize=" + fileSize)
                .add("mediaType='" + mediaType + "'")
                .add("report=" + report)
                .toString();
    }
}
