package sky.pro.pet_bot.service;

import org.springframework.data.repository.query.Param;
import sky.pro.pet_bot.model.Pet;
import sky.pro.pet_bot.model.User;

import java.util.Collection;

public interface PetServiceInterface {
    boolean isExistPetByUserId(Long userId);

    Pet addUserPet(Pet pet);

    Pet updatePet(Pet pet);

    Pet getPetById(Long id);

    void deletePetById(long id);

    Collection<Pet> getPetsByUserId (Long userId);

    Collection<Pet> getAllPets();

}
