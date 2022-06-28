package sky.pro.pet_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.service.impl.MessageHandlerServiceInterfaceImpl;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    private final MessageHandlerServiceInterfaceImpl messageHandlerService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, MessageHandlerServiceInterfaceImpl messageHandlerService) {
        this.telegramBot = telegramBot;
        this.messageHandlerService = messageHandlerService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            Message message = update.message();

            messageHandlerService.handleMessage(message, update);

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
