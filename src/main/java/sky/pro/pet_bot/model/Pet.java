package sky.pro.pet_bot.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name = "pets")
public class Pet {

    public enum PetType{
        DOG,
        CAT,
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;

    @Enumerated (EnumType.STRING)
    private PetType type = PetType.DOG;

    public Pet(Long id, String name, Integer age, PetType type) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public Pet() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && Objects.equals(name, pet.name) && Objects.equals(age, pet.age) && type == pet.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, type);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", type=" + type +
                '}';
    }
}
