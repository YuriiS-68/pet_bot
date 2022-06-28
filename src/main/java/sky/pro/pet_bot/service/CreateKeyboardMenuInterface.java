package sky.pro.pet_bot.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public interface CreateKeyboardMenuInterface {
    InlineKeyboardMarkup startKeyboard();
    InlineKeyboardMarkup createChooseMenu(String kindPet);
}
