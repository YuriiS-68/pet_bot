package sky.pro.pet_bot.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "shelters")
public class Shelter {
    public enum KindShelter{
        FOR_DOG,
        FOR_CAT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String accessRules;
    @Enumerated(EnumType.STRING)
    private KindShelter type;

    @OneToMany(mappedBy = "shelter")
    private Collection<Pet> pets;

    public Shelter() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccessRules() {
        return accessRules;
    }

    public void setAccessRules(String accessRules) {
        this.accessRules = accessRules;
    }

    public KindShelter getType() {
        return type;
    }

    public void setType(KindShelter type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return getId().equals(shelter.getId()) && getName().equals(shelter.getName()) && getAddress().equals(shelter.getAddress()) && getAccessRules().equals(shelter.getAccessRules()) && getType() == shelter.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddress(), getAccessRules(), getType());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Shelter.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("address='" + address + "'")
                .add("accessRules='" + accessRules + "'")
                .add("type=" + type)
                .toString();
    }
}
