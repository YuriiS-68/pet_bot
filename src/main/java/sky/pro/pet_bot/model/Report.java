package sky.pro.pet_bot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reportText;
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Report(Long id, String reportText, Long chatId) {
        this.id = id;
        this.reportText = reportText;
        this.chatId = chatId;
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

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report that = (Report) o;
        return Objects.equals(id, that.id) && Objects.equals(reportText, that.reportText) && Objects.equals(chatId, that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportText, chatId);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reportText='" + reportText + '\'' +
                ", chatId=" + chatId +
                '}';
    }
}
