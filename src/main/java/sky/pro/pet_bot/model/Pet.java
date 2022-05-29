package sky.pro.pet_bot.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс, описывающий животных
 * приюта для вывода пользователю
 */
@Entity
@Table (name = "pets")
public class Pet {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String type;
    private Integer age;

    public Pet(Integer id, String name, String type, Integer age) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public Pet() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
        return Objects.equals(id, pet.id) && Objects.equals(name, pet.name) && Objects.equals(type, pet.type) && Objects.equals(age, pet.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, age);
    }
}
