package sky.pro.pet_bot.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public interface CreateKeyboardMenuInterface {
    InlineKeyboardMarkup startMenu();

    InlineKeyboardMarkup chooseMenu(String kindPet);

    InlineKeyboardMarkup shelterMenu(String kindShelter);

    InlineKeyboardMarkup getPetMenu(String kindShelter);

    InlineKeyboardMarkup petManageMenu();
}
