package sky.pro.pet_bot.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String textMessage;

    public Answer(Long id, String textMessage) {
        this.id = id;
        this.textMessage = textMessage;
    }

    public Answer() {
    }

    public boolean isById(Long id) {
        if (this.id != id) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id) && Objects.equals(textMessage, answer.textMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, textMessage);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", textMessage='" + textMessage + '\'' +
                '}';
    }
}
