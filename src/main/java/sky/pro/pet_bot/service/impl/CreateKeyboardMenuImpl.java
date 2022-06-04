package sky.pro.pet_bot.service.impl;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.exception.NotExistSuchPetException;
import sky.pro.pet_bot.service.CreateKeyboardMenuInterface;

@Service
public class CreateKeyboardMenuImpl implements CreateKeyboardMenuInterface {
    private static final String BUTTON_SHELTER_CAT = "Приют для кошек";
    private static final String BUTTON_SHELTER_DOG = "Приют для собак";

    private static final String BUTTON_MENU_INFO_DOG = "Узнать информацию о приюте для собак";
    private static final String BUTTON_MENU_INFO_CAT = "Узнать информацию о приюте для кошек";

    private static final String BUTTON_ABOUT_CAT_SHELTER = "Информация о приюте для кошек";
    private static final String BUTTON_ABOUT_DOG_SHELTER = "Информация о приюте для собак";

    private static final String BUTTON_SHEDULLE_DOG_SHELTER = "Часы работы приюта для собак и адрес";
    private static final String BUTTON_SHEDULLE_CAT_SHELTER = "Часы работы приюта для кошек и адрес";

    private static final String SECURITY_SHELTER = "Охрана";
    private static final String SAFETY_ADVICE_SHELTER = "Рекомендации по ТБ";
    private static final String ACCEPT_DATA = "Ваши контактные данные";

    private static final String BUTTON_CAT = "Как взять кошку из приюта";
    private static final String BUTTON_DOG = "Как взять собаку из приюта";
    private static final String BUTTON_MENU_REPORT_DOG = "Прислать отчет о собаке";
    private static final String BUTTON_MENU_REPORT_CAT = "Прислать отчет о кошке";
    private static final String CALL_VOLUNTEER = "Обратиться к волонтеру";


    @Override
    public InlineKeyboardMarkup startKeyboard() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[][]{
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(BUTTON_SHELTER_CAT).callbackData("CAT SHELTER")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(BUTTON_SHELTER_DOG).callbackData("DOG SHELTER")
                        }
                });
    }

    public InlineKeyboardMarkup menuShelterKeyboard(String kindShelter) {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[][]{
                        new InlineKeyboardButton[]{
                                getButtonShelterByKindPet(kindShelter)
                        },
                        new InlineKeyboardButton[]{
                                getButtonShedulleByKindPet(kindShelter)
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(SECURITY_SHELTER).callbackData("SECURITY OF SHELTER")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(SAFETY_ADVICE_SHELTER).callbackData("SAFETY ADVICE OF SHELTER")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(ACCEPT_DATA).callbackData("LEAVE YOUR CONTACT DETAILS")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(CALL_VOLUNTEER).callbackData("CALL VOLUNTEER")
                        }
                });
    }
    private InlineKeyboardButton getButtonShelterByKindPet(String kindShelter){
        if (kindShelter.equals("INFO ABOUT DOG SHELTER")){
            return new InlineKeyboardButton(BUTTON_ABOUT_DOG_SHELTER).callbackData("ABOUT OUR DOG SHELTER");
        }
        return new InlineKeyboardButton(BUTTON_ABOUT_CAT_SHELTER).callbackData("ABOUT OUR CAT SHELTER");
    }

    private InlineKeyboardButton getButtonShedulleByKindPet(String kindShedulleShelter){
        if (kindShedulleShelter.equals("SHELTER DOG OPENING HOURS")){
            return new InlineKeyboardButton(BUTTON_SHEDULLE_DOG_SHELTER).callbackData("SHELTER DOG OPENING HOURS");
        }
        return new InlineKeyboardButton(BUTTON_SHEDULLE_CAT_SHELTER).callbackData("SHELTER CAT OPENING HOURS");
    }

    @Override
    public InlineKeyboardMarkup createChooseMenu(String kindPet) {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[][]{
                        new InlineKeyboardButton[]{
                                getButtonMenuInfoByKindPet(kindPet)
                        },
                        new InlineKeyboardButton[]{
                                getButtonByKindPet(kindPet)
                        },
                        new InlineKeyboardButton[]{
                                getButtonMenuReportByKindPet(kindPet)
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(CALL_VOLUNTEER).callbackData("4")
                        }
                });
    }

    private InlineKeyboardButton getButtonByKindPet(String kindPet){
        if (kindPet.equals("DOG SHELTER")){
            return new InlineKeyboardButton(BUTTON_DOG).callbackData("DOG");
        }
        return new InlineKeyboardButton(BUTTON_CAT).callbackData("CAT");
    }

    private InlineKeyboardButton getButtonMenuInfoByKindPet(String kindPet){
        if (kindPet.equals("DOG SHELTER")){
            return new InlineKeyboardButton(BUTTON_MENU_INFO_DOG).callbackData("INFO ABOUT DOG SHELTER");
        }
        return new InlineKeyboardButton(BUTTON_MENU_INFO_CAT).callbackData("INFO ABOUT CAT SHELTER");
    }

    private InlineKeyboardButton getButtonMenuReportByKindPet(String kindPet){
        if (kindPet.equals("DOG SHELTER")){
            return new InlineKeyboardButton(BUTTON_MENU_REPORT_DOG).callbackData("DOG REPORTS");
        }
        return new InlineKeyboardButton(BUTTON_MENU_REPORT_CAT).callbackData("CAT REPORTS");
    }
}
