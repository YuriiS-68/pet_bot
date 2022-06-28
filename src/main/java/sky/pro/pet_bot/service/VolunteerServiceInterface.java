package sky.pro.pet_bot.service;

import sky.pro.pet_bot.model.Volunteer;

import java.util.Optional;

public interface VolunteerServiceInterface {
    Volunteer getFreeVolunteerByStatusAndUserId(Long userId, Volunteer.VolunteersStatus status);
    Volunteer getFreeVolunteerByStatus(String status);
    void updateVolunteerStatus(Long volunteerId, Volunteer.VolunteersStatus status);
}
