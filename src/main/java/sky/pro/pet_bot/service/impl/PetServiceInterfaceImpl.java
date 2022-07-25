package sky.pro.pet_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.dao.PetRepository;
import sky.pro.pet_bot.model.Pet;
import sky.pro.pet_bot.service.PetServiceInterface;

import java.util.Collection;

@Service
public class PetServiceInterfaceImpl implements PetServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceInterfaceImpl.class);

    private final PetRepository petRepository;

    public PetServiceInterfaceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    @Override
    public Pet addUserPet(Pet pet) {
        logger.info("Pet successfully saved");
        return petRepository.save(pet);
    }

    @Override
    public Pet getPetById(Long id) {
        logger.info("Method getPetById is started");
        return petRepository.findById(id).get();
    }

    public boolean isExistPetByUserId(Long userId){
        logger.info("Run method isExistPetByUserId: {}", userId);
        boolean isExistPet = petRepository.existsByUserId(userId);
        logger.info("isExistPet result: {}", isExistPet);
        return isExistPet;
    }



    @Override
    public Collection<Pet> getPetsByUserId (Long userId){
        return petRepository.getPetsByUserId(userId);
    }

    @Override
    public Collection<Pet> getAllPets() {
        logger.info("Method getAllPets is start");
        return petRepository.findAll();
    }

    public Pet updatePet(Pet pet) {
        logger.info("Pet was successfully updated");
        return petRepository.save(pet);
    }

    public void deletePetById(long id) {
        logger.info("Pet was successfully updated");
        petRepository.deleteById(id);
    }
}
