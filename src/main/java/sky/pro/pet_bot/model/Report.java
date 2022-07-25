package sky.pro.pet_bot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String reportText;
    private LocalDateTime timeSendingReport;
    private boolean isViewed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "report", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Picture picture;

    public Report() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }


    public LocalDateTime getTimeSendingReport() {
        return timeSendingReport;
    }

    public void setTimeSendingReport(LocalDateTime timeSendingReport) {
        this.timeSendingReport = timeSendingReport;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return isViewed() == report.isViewed() && getId().equals(report.getId())
                && Objects.equals(getReportText(), report.getReportText())
                && Objects.equals(getTimeSendingReport(), report.getTimeSendingReport())
                && getUser().equals(report.getUser()) && getPicture().equals(report.getPicture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReportText(), getTimeSendingReport(), isViewed(), getUser(), getPicture());
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", Report.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("reportText='" + reportText + "'")
                .add("timeSendingReport=" + timeSendingReport)
                .add("isViewed=" + isViewed)
                .add("user=" + user);

        if (picture != null){
            stringJoiner.add("picture=" + picture.getId());
        }
        return stringJoiner.toString();
    }
}
