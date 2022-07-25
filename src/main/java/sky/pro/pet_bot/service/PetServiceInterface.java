package sky.pro.pet_bot.service;

import sky.pro.pet_bot.model.Pet;

import java.util.Collection;

public interface PetServiceInterface {
    boolean isExistPetByUserId(Long userId);

    Pet addUserPet(Pet pet);

    Pet updatePet(Pet pet);

    Pet getPetById(Long id);

    void deletePetById(long id);

    Collection<Pet> getAllPets();

}
