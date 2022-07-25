package sky.pro.pet_bot.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "volunteers")
public class Volunteer {
    public enum VolunteersStatus{
        FREE,
        BUSY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer chatId;
    private String name;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private VolunteersStatus status;

    @OneToMany(mappedBy = "volunteer")
    private Collection<User> users;

    public Volunteer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public VolunteersStatus getStatus() {
        return status;
    }

    public void setStatus(VolunteersStatus status) {
        this.status = status;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return getId().equals(volunteer.getId()) && getChatId().equals(volunteer.getChatId())
                && getName().equals(volunteer.getName()) && Objects.equals(getPhoneNumber(), volunteer.getPhoneNumber())
                && getStatus() == volunteer.getStatus() && Objects.equals(getUsers(), volunteer.getUsers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getName(), getPhoneNumber(), getStatus(), getUsers());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Volunteer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("chatId=" + chatId)
                .add("name='" + name + "'")
                .add("phoneNumber='" + phoneNumber + "'")
                .add("status= '" + status + "'")
                .add("users=" + users)
                .toString();
    }
}
