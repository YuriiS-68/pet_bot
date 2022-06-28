package sky.pro.pet_bot.service.impl;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.service.CreateKeyboardMenuInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Service
public class CreateKeyboardMenuImpl implements CreateKeyboardMenuInterface {
    private final Properties messagesProperties;
    private final Logger logger = LoggerFactory.getLogger(CreateKeyboardMenuImpl.class);

    public CreateKeyboardMenuImpl() throws IOException {
        this.messagesProperties = PropertiesLoaderUtils
                .loadProperties(new EncodedResource(new ClassPathResource("/messages.properties"),
                StandardCharsets.UTF_8));
    }

    @Override
    public InlineKeyboardMarkup startKeyboard() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[][]{
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(messagesProperties.getProperty("button_shelter_cat"))
                                        .callbackData("CAT SHELTER")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(messagesProperties.getProperty("button_shelter_dog"))
                                        .callbackData("DOG SHELTER")
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
                                getButtonSecurityByKindPet(kindShelter)
                        },
                        new InlineKeyboardButton[]{
                                getButtonSafetyAdviceByKindPet(kindShelter)
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(messagesProperties.getProperty("button_accept_data"))
                                        .callbackData("LEAVE YOUR CONTACT DETAILS")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton(messagesProperties.getProperty("button_call_volunteer"))
                                        .callbackData("CALL VOLUNTEER")
                        }
                });
    }

    private InlineKeyboardButton getButtonSafetyAdviceByKindPet(String kindShelter){
        logger.info("Run method getButtonShelterByKindPet: {}", kindShelter);

        if (kindShelter.equals("INFO ABOUT DOG SHELTER")){
            return new InlineKeyboardButton(messagesProperties.getProperty("button_safety_advice_dog_shelter"))
                    .callbackData("SAFETY ADVICE OF DOG SHELTER");
        }
        return new InlineKeyboardButton(messagesProperties.getProperty("button_safety_advice_cat_shelter"))
                .callbackData("SAFETY ADVICE OF CAT SHELTER");
    }
    private InlineKeyboardButton getButtonSecurityByKindPet(String kindShelter){
        logger.info("Run method getButtonShelterByKindPet: {}", kindShelter);

        if (kindShelter.equals("INFO ABOUT DOG SHELTER")){
            return new InlineKeyboardButton(messagesProperties.getProperty("button_security_dog_shelter"))
                    .callbackData("SECURITY OF DOG SHELTER");
        }
        return new InlineKeyboardButton(messagesProperties.getProperty("button_security_cat_shelter"))
                .callbackData("SECURITY OF CAT SHELTER");
    }
    private InlineKeyboardButton getButtonShelterByKindPet(String kindShelter){
        logger.info("Run method getButtonShelterByKindPet: {}", kindShelter);

        if (kindShelter.equals("INFO ABOUT DOG SHELTER")){
            return new InlineKeyboardButton(messagesProperties.getProperty("button_about_dog_shelter"))
                    .callbackData("ABOUT OUR DOG SHELTER");
        }
        return new InlineKeyboardButton(messagesProperties.getProperty("button_about_cat_shelter"))
                .callbackData("ABOUT OUR CAT SHELTER");
    }

    private InlineKeyboardButton getButtonShedulleByKindPet(String kindShedulleShelter){
        logger.info("Run method getButtonShedulleByKindPet: {}", kindShedulleShelter);

        if (kindShedulleShelter.equals("INFO ABOUT DOG SHELTER")){
            return new InlineKeyboardButton(messagesProperties.getProperty("button_shedulle_dog_shelter"))
                    .callbackData("SHELTER DOG OPENING HOURS");
        }
        return new InlineKeyboardButton(messagesProperties.getProperty("button_shedulle_cat_shelter"))
                .callbackData("SHELTER CAT OPENING HOURS");
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
                                new InlineKeyboardButton(messagesProperties.getProperty("button_call_volunteer")).callbackData("CALL VOLUNTEER")
                        }
                });
    }

    private InlineKeyboardButton getButtonByKindPet(String kindPet){
        if (kindPet.equals("DOG SHELTER")){
            return new InlineKeyboardButton(messagesProperties.getProperty("button_take_dog")).callbackData("DOG");
        }
        return new InlineKeyboardButton(messagesProperties.getProperty("button_take_cat")).callbackData("CAT");
    }

    private InlineKeyboardButton getButtonMenuInfoByKindPet(String kindPet){
        if (kindPet.equals("DOG SHELTER")){
            return new InlineKeyboardButton(messagesProperties.getProperty("button_menu_info_dog"))
                    .callbackData("INFO ABOUT DOG SHELTER");
        }
        return new InlineKeyboardButton(messagesProperties.getProperty("button_menu_info_cat"))
                .callbackData("INFO ABOUT CAT SHELTER");
    }

    private InlineKeyboardButton getButtonMenuReportByKindPet(String kindPet){
        if (kindPet.equals("DOG SHELTER")){
            return new InlineKeyboardButton(messagesProperties.getProperty("button_menu_report_dog"))
                    .callbackData("DOG REPORTS");
        }
        return new InlineKeyboardButton(messagesProperties.getProperty("button_menu_report_cat"))
                .callbackData("CAT REPORTS");
    }
}
