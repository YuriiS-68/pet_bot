package sky.pro.pet_bot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table (name= "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String phoneNumber;
    private String name;
    private String location;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Collection<Pet> pets;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Collection<Report> reports;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    @JsonBackReference
    private Volunteer volunteer;


    public User() {
    }

    public User(Long id, String phoneNumber, String name, Long chatId, String location) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.chatId = chatId;
        this.location = location;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Collection<Pet> getPets() {
        return pets;
    }

    public void setPets(Collection<Pet> pets) {
        this.pets = pets;
    }

    public Collection<Report> getReports() {
        return reports;
    }

    public void setReports(Collection<Report> reports) {
        this.reports = reports;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId().equals(user.getId()) && getChatId().equals(user.getChatId()) && Objects.equals(getPhoneNumber(), user.getPhoneNumber()) && getName().equals(user.getName()) && Objects.equals(getLocation(), user.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getPhoneNumber(), getName(), getLocation());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("chatId=" + chatId)
                .add("phoneNumber='" + phoneNumber + "'")
                .add("name='" + name + "'")
                .add("location='" + location + "'")
                .add("pets=" + pets)
                .add("reports=" + reports)
                .add("volunteer=" + volunteer)
                .toString();
    }
}
