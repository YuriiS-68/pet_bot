package sky.pro.pet_bot.service;

import sky.pro.pet_bot.model.Volunteer;

import java.util.Collection;

public interface VolunteerServiceInterface {
    Volunteer getVolunteerById(Long id);

    boolean addVolunteer(String name, String phoneNumber);

    Volunteer updateVolunteer (Volunteer volunteer);

    void deleteVolunteer(Long id);

    Volunteer getFreeVolunteerByStatusAndUserId(Long userId, Volunteer.VolunteersStatus status);

    Volunteer getFreeVolunteerByStatus(String status);

    void updateVolunteerStatus(Long volunteerId, Volunteer.VolunteersStatus status);

    Collection<Volunteer> findFreeVolunteers(Long idUser, Volunteer.VolunteersStatus status);

    Collection<Volunteer> getAllVolunteers();
}
