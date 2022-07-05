package sky.pro.pet_bot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reportText;
    private LocalDateTime timeSendingReport;
    private boolean isViewed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    @JsonBackReference
    private Volunteer volunteer;
    @ManyToOne
    @JoinColumn(name = "picture_id")
    @JsonBackReference
    private Picture picture;

    public Report(Long id, String reportText) {
        this.id = id;
        this.reportText = reportText;
    }

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

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
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
                && getUser().equals(report.getUser()) && getVolunteer().equals(report.getVolunteer())
                && getPicture().equals(report.getPicture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReportText(), getTimeSendingReport(), isViewed(), getUser(), getVolunteer(),
                getPicture());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Report.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("reportText='" + reportText + "'")
                .add("timeSendingReport=" + timeSendingReport)
                .add("isViewed=" + isViewed)
                .add("user=" + user.getName())
                .add("volunteer=" + volunteer.getName())
                .add("picture=" + picture.getId())
                .toString();
    }
}
