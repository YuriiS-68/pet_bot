package sky.pro.pet_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sky.pro.pet_bot.dao.VolunteerRepository;
import sky.pro.pet_bot.exception.NotFoundVolunteerException;
import sky.pro.pet_bot.model.Volunteer;
import sky.pro.pet_bot.service.VolunteerServiceInterface;

import java.util.Collection;

@Service
public class VolunteerServiceInterfaceImpl implements VolunteerServiceInterface {
    private final Logger logger = LoggerFactory.getLogger(VolunteerServiceInterfaceImpl.class);
    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceInterfaceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Collection<Volunteer> findFreeVolunteers(Long idUser, Volunteer.VolunteersStatus status){
        logger.info("Run method findFreeVolunteers: {} {}", idUser, status);
        Collection<Volunteer> volunteers = volunteerRepository.getVolunteersByStatusAndUserId(idUser, status.toString());
        logger.info("Collection volunteers: {}", volunteers);
        return volunteers;
    }

    @Override
    public Volunteer getFreeVolunteerByStatusAndUserId(Long idUser, Volunteer.VolunteersStatus status) {
        return volunteerRepository.getVolunteerByStatusAndUserId(idUser, status.toString());
    }

    @Override
    public Volunteer getFreeVolunteerByStatus(String status) {
        logger.info("Run method getFreeVolunteerByStatus: {}", status);
        Volunteer volunteer = volunteerRepository.getVolunteerByStatus(status);
        logger.info("getFreeVolunteerByStatus: {}", volunteer );
        return volunteer;
    }

    @Transactional
    @Override
    public void updateVolunteerStatus(Long volunteerId, Volunteer.VolunteersStatus status) {
        volunteerRepository.updateVolunteerStatus(volunteerId, status);
    }

    public Volunteer getVolunteerById(Long id){
        return volunteerRepository.getVolunteerById(id);
    }

    public boolean addVolunteer (String name, String phoneNumber){
        return volunteerRepository.existsVolunteerByNameAndPhoneNumber(name, phoneNumber);
    }

    public Volunteer updateVolunteer (Volunteer volunteer){
        volunteerRepository.save(volunteer);
        return volunteer;
    }

    public void deleteVolunteer(Long id){
        long numOfVolunteerDelete = volunteerRepository.deleteVolunteerById(id);
        if (numOfVolunteerDelete != 1){
            String errorMessage = "No such id " + id + " found in the DB";
            logger.error(errorMessage);
            throw new NotFoundVolunteerException(errorMessage);
        }
    }

    public Collection<Volunteer> findVolunteersByUserId(Long userId){
        return volunteerRepository.getVolunteersByUserId(userId);
    }

    public Collection<Volunteer> getAllVolunteers(){
        return volunteerRepository.findAll();
    }
}
