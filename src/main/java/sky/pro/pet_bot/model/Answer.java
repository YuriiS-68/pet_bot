package sky.pro.pet_bot.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private Long idMessage;

    public Answer() {
    }

    public Answer(Long id, Long chatId, Long idMessage) {
        this.id = id;
        this.chatId = chatId;
        this.idMessage = idMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return getId().equals(answer.getId()) && getChatId().equals(answer.getChatId()) && Objects.equals(getIdMessage(), answer.getIdMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getIdMessage());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Answer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("chatId=" + chatId)
                .add("idMessage=" + idMessage)
                .toString();
    }
}
