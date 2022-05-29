package sky.pro.pet_bot.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс, описывающий общие ответы,
 * хранящиеся в БД для вывода пользователю
 */
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Answer(Long id, String textMessage) {
        this.id = id;
        this.textMessage = textMessage;
    }

    private String textMessage;

    public Answer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
