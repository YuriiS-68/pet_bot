package sky.pro.pet_bot.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private String name;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private VolunteersStatus status;

    @OneToMany(mappedBy = "volunteer")
    @JsonManagedReference
    private Collection<User> users;

    @OneToMany(mappedBy = "volunteer")
    private Collection<Report> reports;

    public Volunteer(Long id, String name, String phoneNumber, VolunteersStatus status) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public Volunteer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Collection<Report> getReports() {
        return reports;
    }

    public void setReports(Collection<Report> reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return getId().equals(volunteer.getId()) && getName().equals(volunteer.getName())
                && Objects.equals(getPhoneNumber(), volunteer.getPhoneNumber())
                && getStatus() == volunteer.getStatus() && Objects.equals(getUsers(), volunteer.getUsers())
                && Objects.equals(getReports(), volunteer.getReports());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPhoneNumber(), getStatus(), getUsers(), getReports());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Volunteer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("phoneNumber='" + phoneNumber + "'")
                .add("status= '" + status + "'")
                .add("users=" + users)
                .add("reports=" + reports)
                .toString();
    }
}
