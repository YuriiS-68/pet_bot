package sky.pro.pet_bot.service.impl;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.service.CreateKeyboardMenuInterface;

@Service
public class CreateKeyboardMenuImpl implements CreateKeyboardMenuInterface {
    @Override
    public InlineKeyboardMarkup startKeyboard() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[][]{
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Приют для кошек").callbackData("cat shelter")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Приют для собак").callbackData("dog shelter")
                        }
                });
    }

    @Override
    public InlineKeyboardMarkup createChooseMenu(String kindPet) {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[][]{
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Узнать информацию о приюте").callbackData("1")
                        },
                        new InlineKeyboardButton[]{
                                getNeededButton(kindPet)
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Прислать отчет о питомце").callbackData("3")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Позвать волонтера").callbackData("4")
                        }
                });
    }

    private InlineKeyboardButton getNeededButton(String kindPet){
        String buttonDog = "Как взять собаку из приюта";
        String buttonCat = "Как взять кошку из приюта";

        if (kindPet.equals("dog shelter")){
            return new InlineKeyboardButton(buttonDog).callbackData("2");
        }
        return new InlineKeyboardButton(buttonCat).callbackData("2");
    }
}
