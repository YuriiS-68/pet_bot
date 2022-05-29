package sky.pro.pet_bot.model;

import javax.persistence.*;
import java.util.Objects;

/**Класс, описывающий пользователя, взаимодействующего с ботом
 *
 */

@Entity
@Table (name= "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long phoneNumber;
    private String name;
    private Long chatId;
    private String location;


    public User() {
    }

    public User(Integer id, Long phoneNumber, String name, Long chatId, String location) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.chatId = chatId;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(name, user.name) && Objects.equals(chatId, user.chatId) && Objects.equals(location, user.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, name, chatId, location);
    }
}
