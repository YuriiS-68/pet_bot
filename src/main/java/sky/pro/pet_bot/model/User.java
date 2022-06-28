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

    public enum TypeShelters{
        DOG_SHELTER,
        CAT_SHELTER;

    }
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private Integer messageId;
    private String phoneNumber;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String location;

    @Enumerated(EnumType.STRING)
    private User.TypeShelters typeShelter;

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

    public User(Long id, Long chatId, Integer messageId, String phoneNumber, String name, String firstName, String lastName,
                String email, String location, TypeShelters typeShelter) {
        this.id = id;
        this.chatId = chatId;
        this.messageId = messageId;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.location = location;
        this.typeShelter = typeShelter;
    }

    public TypeShelters getTypeShelter() {
        return typeShelter;
    }

    public void setTypeShelter(TypeShelters typeShelter) {
        this.typeShelter = typeShelter;
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

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return getId().equals(user.getId()) && getChatId().equals(user.getChatId()) && getMessageId().equals(user.getMessageId())
                && Objects.equals(getPhoneNumber(), user.getPhoneNumber()) && getName().equals(user.getName())
                && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName())
                && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getLocation(), user.getLocation())
                && Objects.equals(getTypeShelter(), user.getTypeShelter())
                && Objects.equals(getPets(), user.getPets()) && Objects.equals(getReports(), user.getReports())
                && Objects.equals(getVolunteer(), user.getVolunteer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getMessageId(), getPhoneNumber(), getName(), getFirstName(), getLastName(),
                getEmail(), getLocation(), getTypeShelter(), getPets(), getReports(), getVolunteer());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("chatId=" + chatId)
                .add("messageId=" + messageId)
                .add("phoneNumber='" + phoneNumber + "'")
                .add("name='" + name + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .add("location='" + location + "'")
                .add("typeShelter='" + typeShelter + "'")
                .add("pets=" + pets)
                .add("reports=" + reports)
                .add("volunteer=" + volunteer.getName())
                .toString();
    }
}
