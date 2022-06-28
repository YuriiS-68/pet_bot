package sky.pro.pet_bot.service;


import sky.pro.pet_bot.model.Volunteer;

import java.util.Collection;

public interface VolunteerServiceInterface {

    Volunteer addVolunteer(Volunteer volunteer);

    Collection<Volunteer> getVolunteerById(Long id);

    Collection<Volunteer> getAllVolunteers();

    Volunteer update(Volunteer volunteer);

    void delete(Long id);
}
