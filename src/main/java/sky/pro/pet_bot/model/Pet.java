package sky.pro.pet_bot.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table (name = "pets")
public class Pet {
    public enum KindPet{
        DOG,
        CAT
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private KindPet type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Pet() {

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

    public KindPet getType() {
        return type;
    }

    public void setType(KindPet type) {
        this.type = type;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return getId().equals(pet.getId()) && getName().equals(pet.getName()) && getAge().equals(pet.getAge())
                && getType() == pet.getType() && Objects.equals(getUser(), pet.getUser())
                && Objects.equals(getShelter(), pet.getShelter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), getType(), getUser(), getShelter());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Pet.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("age=" + age)
                .add("type=" + type)
                .add("user=" + user.getName())
                .add("shelter=" + shelter.getName())
                .toString();
    }
}
