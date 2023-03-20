package pro.sky.telegramcatdog.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pro.sky.telegramcatdog.constants.PetType;
import pro.sky.telegramcatdog.model.Adopter;
import pro.sky.telegramcatdog.model.AdoptionReport;
import pro.sky.telegramcatdog.model.Guest;
import pro.sky.telegramcatdog.model.Volunteer;
import pro.sky.telegramcatdog.repository.AdopterRepository;
import pro.sky.telegramcatdog.repository.AdoptionReportRepository;
import pro.sky.telegramcatdog.repository.GuestRepository;
import pro.sky.telegramcatdog.repository.VolunteerRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static pro.sky.telegramcatdog.constants.Constants.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private TelegramBot telegramBot;
    private PetType shelterType;
    private final VolunteerRepository volunteerRepository;
    private final GuestRepository guestRepository;
    private final AdopterRepository adopterRepository;
    private final AdoptionReportRepository adoptionReportRepository;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, VolunteerRepository volunteerRepository, GuestRepository guestRepository, AdopterRepository adopterRepository, AdoptionReportRepository adoptionReportRepository) {
        this.telegramBot = telegramBot;
        this.volunteerRepository = volunteerRepository;
        this.guestRepository = guestRepository;
        this.adopterRepository = adopterRepository;
        this.adoptionReportRepository = adoptionReportRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {

            logger.info("Processing update: {}", update);

            if (update.message() != null) {
                processMessage(update);
            }
            // Process button clicks
            else {
                processButtonClick(update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMessage(SendMessage message) {
        SendResponse response = telegramBot.execute(message);
        if (response != null && !response.isOk()) {
            logger.warn("Message was not sent: {}, error code: {}", message, response.errorCode());
        }
    }

    /**
     * Creates buttons for the shelter type selection message (reply to the /start command)
     * @return {@code InlineKeyboardMarkup}
     */
    private InlineKeyboardMarkup createButtonsShelterTypeSelect() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_CAT_SHELTER_TEXT).callbackData(BUTTON_CAT_SHELTER_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_DOG_SHELTER_TEXT).callbackData(BUTTON_DOG_SHELTER_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    /**
     * Creates buttons for the reply message to the shelter type selection (Stage 0)
     * @return {@code InlineKeyboardMarkup}
     */
    private InlineKeyboardMarkup createButtonsStage0() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_STAGE1_TEXT).callbackData(BUTTON_STAGE1_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_STAGE2_TEXT).callbackData(BUTTON_STAGE2_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_STAGE3_TEXT).callbackData(BUTTON_STAGE3_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_CALL_VOLUNTEER_TEXT).callbackData(BUTTON_CALL_VOLUNTEER_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createButtonsStage1() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        // todo (Olga): Add more buttons here

        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_SHARE_CONTACT_DETAILS_TEXT).callbackData(BUTTON_SHARE_CONTACT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createButtonsStage2() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        // todo (Denis): Add more buttons here

        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_SHARE_CONTACT_DETAILS_TEXT).callbackData(BUTTON_SHARE_CONTACT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createButtonsStage3() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_REPORT_TEMPLATE_TEXT).callbackData(BUTTON_REPORT_TEMPLATE_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_SEND_REPORT_TEXT).callbackData(BUTTON_SEND_REPORT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    private ReplyKeyboardMarkup createRequestContactKeyboardButton() {
        KeyboardButton keyboardButton = new KeyboardButton(BUTTON_SHARE_CONTACT_TEXT);
        keyboardButton.requestContact(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardButton);
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }


    private ReplyKeyboardMarkup createMainMenuKeyboardButtons() {
        KeyboardButton keyboardButton1 = new KeyboardButton(BUTTON_MAIN_MENU_TEXT);
        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_CALL_VOLUNTEER_TEXT);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    /**
     * Process messages from user.
     *
     * @param update user input (can be text, button click, emoji, sticker, etc.)
     *               but process only messages with {@code message()} defined.
     */
    private void processMessage(Update update) {
        if (update.message().contact() != null) {
            // Save new adopter contacts in our db
            saveAdopter(update);
            return;
        }

        if (update.message().photo() != null) {
            saveAdoptionReport(update);
            return;
        }

        if (update.message().text() == null) {
            // For stickers incomeMsgText is null
            return;
        }
        switch (update.message().text()) {
            case "/start", BUTTON_MAIN_MENU_TEXT:
                processStartCommand(update);
                break;
            case BUTTON_CALL_VOLUNTEER_TEXT:
                // Call a volunteer
                callVolunteer(update);
                break;
        }
    }

    /**
     * Process button clicks from user.
     *
     * @param update user input (can be text, button click, emoji, sticker, etc.)
     *               but process only button clicks with {@code callbackData()} defined.
     * @see InlineKeyboardButton#callbackData()
     */
    private void processButtonClick(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            switch (callbackQuery.data()) {
                case BUTTON_CAT_SHELTER_CALLBACK_TEXT:
                    // Cat shelter selected
                    sendButtonClickMessage(chatId, BUTTON_CAT_SHELTER_CALLBACK_TEXT);
                    processCatShelterClick(chatId);
                    break;
                case BUTTON_DOG_SHELTER_CALLBACK_TEXT:
                    // Dog shelter selected
                    sendButtonClickMessage(chatId, BUTTON_DOG_SHELTER_CALLBACK_TEXT);
                    processDogShelterClick(chatId);
                    break;
                case BUTTON_STAGE1_CALLBACK_TEXT:
                    // General info about the shelter (stage 1)
                    sendButtonClickMessage(chatId, BUTTON_STAGE1_CALLBACK_TEXT);
                    processStage1Click(chatId);
                    break;
                case BUTTON_STAGE2_CALLBACK_TEXT:
                    // How to adopt a dog/cat (stage 2)
                    sendButtonClickMessage(chatId, BUTTON_STAGE2_CALLBACK_TEXT);
                    processStage2Click(chatId);
                    break;
                case BUTTON_STAGE3_CALLBACK_TEXT:
                    // Send a follow-up report (stage 3)
                    sendButtonClickMessage(chatId, BUTTON_STAGE3_CALLBACK_TEXT);
                    processStage3Click(chatId);
                case BUTTON_REPORT_TEMPLATE_CALLBACK_TEXT:
                    sendButtonClickMessage(chatId, "инструкция");
                    break;
                case BUTTON_SEND_REPORT_CALLBACK_TEXT:
                    sendButtonClickMessage(chatId, "отправьте фото");
                    break;
                case BUTTON_SHARE_CONTACT_CALLBACK_TEXT:
                    // Share your contact details
                    sendButtonClickMessage(chatId, BUTTON_SHARE_CONTACT_CALLBACK_TEXT);
                    shareContact(update);
                    break;

                // todo (Olga, Denis, Tamerlan): process more button clicks here
            }
        }
    }

    private void shareContact(Update update) {
        long chatId = update.callbackQuery().from().id();
        SendMessage message = new SendMessage(chatId, SHARE_CONTACT_MSG_TEXT);
        // Adding buttons
        message.replyMarkup(createRequestContactKeyboardButton());
        sendMessage(message);
    }

    private void processStartCommand(Update update) {
        long chatId = update.message().chat().id();
        Guest guest = guestRepository.findByChatId(chatId);
        if (guest == null) {
            sendShelterTypeSelectMessage(chatId);
        } else {
            shelterType = guest.getLastMenu();
            switch (guest.getLastMenu()) {
                case DOG:
                    sendStage0Message(chatId, DOG_SHELTER_WELCOME_MSG_TEXT);
                    break;
                case CAT:
                    sendStage0Message(chatId, CAT_SHELTER_WELCOME_MSG_TEXT);
                    break;
                default:
                    sendShelterTypeSelectMessage(chatId);
            }
        }
    }

    private void processCatShelterClick(long chatId) {
        shelterType = PetType.CAT;
        saveGuest(chatId, shelterType);
        sendStage0Message(chatId, CAT_SHELTER_WELCOME_MSG_TEXT);
    }

    private void processDogShelterClick(long chatId) {
        shelterType = PetType.DOG;
        saveGuest(chatId, shelterType);
        sendStage0Message(chatId, DOG_SHELTER_WELCOME_MSG_TEXT);
    }

    private void sendShelterTypeSelectMessage(long chatId) {
        SendMessage message = new SendMessage(chatId, SHELTER_TYPE_SELECT_MSG_TEXT);
        // Adding buttons
        message.replyMarkup(createButtonsShelterTypeSelect());
        sendMessage(message);
    }

    private void sendStage0Message(long chatId, String messageText) {
        SendMessage message = new SendMessage(chatId, messageText);
        // Remove all buttons
        //message.replyMarkup(new ReplyKeyboardRemove());
        // Adding buttons
        message.replyMarkup(createButtonsStage0());
        sendMessage(message);
    }

    /**
     * Processing request: General info about the shelter (stage 1)
     * @param chatId
     */
    private void processStage1Click(long chatId) {
        if (shelterType == null) {
            return;
        }
        String messageText = null;
        switch (shelterType) {
            case DOG:
                messageText = DOG_SHELTER_STAGE1_WELCOME_MSG_TEXT;
                break;
            case CAT:
                messageText = CAT_SHELTER_STAGE1_WELCOME_MSG_TEXT;
                break;
        }
        SendMessage message = new SendMessage(chatId, messageText);
        // Adding buttons
        message.replyMarkup(createButtonsStage1());
        sendMessage(message);
    }

    /**
     * Processing request: How to adopt a dog/cat (stage 2)
     * @param chatId
     */
    private void processStage2Click(long chatId) {
        if (shelterType == null) {
            return;
        }
        String messageText = null;
        switch (shelterType) {
            case DOG:
                messageText = DOG_SHELTER_STAGE2_WELCOME_MSG_TEXT;
                break;
            case CAT:
                messageText = CAT_SHELTER_STAGE2_WELCOME_MSG_TEXT;
                break;
        }
        SendMessage message = new SendMessage(chatId, messageText);
        // Adding buttons
        message.replyMarkup(createButtonsStage2());
        sendMessage(message);
    }

    /**
     * Processing request: Send a follow-up report (stage 3)
     * @param chatId
     */
    private void processStage3Click(long chatId) {
        if (shelterType == null) {
            return;
        }
        String messageText = null;
        switch (shelterType) {
            case DOG:
                messageText = DOG_SHELTER_STAGE3_WELCOME_MSG_TEXT;
                break;
            case CAT:
                messageText = CAT_SHELTER_STAGE3_WELCOME_MSG_TEXT;
                break;
        }
        SendMessage message = new SendMessage(chatId, messageText);
        // Adding buttons
        message.replyMarkup(createButtonsStage3());
        sendMessage(message);




    }

    /**
     * Sends technical message that the button has been clicked.
     * Can be disabled if it is not needed.
     * @param chatId sends message to this chat
     * @param message the message itself
     */
    private void sendButtonClickMessage(long chatId, String message) {
        sendMessage(new SendMessage(chatId, message));
    }

    /**
     * Generates and sends message to volunteer from volunteers table.
     * If {@code @username} of the guest is defined it mentions him by {@code @username}.
     * Otherwise, it mentions him by {@code chat_id}.
     * If volunteers table is empty - sends {@code NO_VOLUNTEERS_TEXT} message.
     *
     * @param update 'Call a volunteer' button click.
     */
    private void callVolunteer(Update update) {
        String userId = ""; // guest's chat_id or username
        long chatId = 0; // volunteer's chat_id
        userId += update.message().from().id();
        logger.info("UserId = {}", userId);
        // todo: select random volunteer. Now it always selects the 1st one.
        Volunteer volunteer = volunteerRepository.findById(1L).orElse(null);
        if (volunteer == null) {
            // Guest chat_id. Send message to the guest.
            chatId = Long.parseLong(userId);
            SendMessage message = new SendMessage(chatId, NO_VOLUNTEERS_TEXT);
            sendMessage(message);
        } else {
            // Volunteer chat_id. Send message to volunteer.
            chatId = volunteer.getChatId();
            if (update.message().from().username() != null) {
                userId = "@" + update.message().from().username();
                SendMessage message = new SendMessage(chatId, String.format(CONTACT_TELEGRAM_USERNAME_TEXT, userId));
                sendMessage(message);
            } else {
                SendMessage message = new SendMessage(chatId, String.format(CONTACT_TELEGRAM_ID_TEXT, userId));
                sendMessage(message);
            }
        }
    }

    private void saveGuest(long chatId, PetType lastMenu) {
        Guest guest = guestRepository.findByChatId(chatId);
        if (guest == null) {
            guest = new Guest(chatId, new Timestamp(System.currentTimeMillis()), lastMenu);
            guestRepository.save(guest);
        }
    }

    private void saveAdopter(Update update) {
        if (update.message().contact() != null) {
            String firstName = update.message().contact().firstName();
            String lastName = update.message().contact().lastName();
            String phone1 = update.message().contact().phoneNumber();
            String username = update.message().chat().username();
            long chatId = update.message().chat().id();
            Adopter adopter = adopterRepository.findByChatId(chatId);
            if (adopter == null) {
                adopter = new Adopter(firstName, lastName, phone1, chatId, username);
                adopterRepository.save(adopter);
                SendMessage message = new SendMessage(chatId, SAVE_ADOPTER_SUCCESS_TEXT + ' ' + WE_WILL_CALL_YOU_TEXT);
                sendMessage(message.replyMarkup(createMainMenuKeyboardButtons()));
            } else {
                SendMessage message = new SendMessage(chatId, ADOPTER_ALREADY_EXISTS_TEXT + ' ' + WE_WILL_CALL_YOU_TEXT);
                sendMessage(message.replyMarkup(createMainMenuKeyboardButtons()));
            }
        }
    }

    private void saveAdoptionReport(Update update) {
        long chatId = update.message().chat().id();
        String diet = "diet";
        String wellBeing = "wellBeing";
        String behaviorChange = "behaviorChange";
        Adopter adopterId =  adopterRepository.findByChatId(chatId);
        if (update.message().photo() != null) {
            byte[] image = getPhoto(update);
            LocalDateTime photoSendingTime = LocalDateTime.now();
            AdoptionReport adoptionReport = new AdoptionReport(image, diet, wellBeing, behaviorChange, photoSendingTime );
            adoptionReport.setAdopterId(adopterId);
            adoptionReportRepository.save(adoptionReport);
            long id = adoptionReport.getId();
            SendMessage message = new SendMessage(chatId, "фото сохранено, переходите на следующий шаг");
            sendMessage(message);
        }
        else {
            String firstMessageText = update.message().text();
            LocalDateTime firstMessageTextTime = LocalDateTime.now();
            AdoptionReport adoptionReport = adoptionReportRepository.findByReportDate_Date(firstMessageTextTime);
            if (adoptionReport.getReportDate().getDayOfYear() == firstMessageTextTime.getDayOfYear()) {

            }
        }

    }

    public byte[] getPhoto(Update update) {
        if (update.message().photo() != null) {
            PhotoSize[] photoSizes = update.message().photo();
            for (PhotoSize photoSize: photoSizes) {
                GetFile getFile = new GetFile(photoSize.fileId());
                GetFileResponse getFileResponse = telegramBot.execute(getFile);
                if (getFileResponse.isOk()) {
                    File file = getFileResponse.file();
                    String extension = StringUtils.getFilenameExtension(file.filePath());
                    try {
                        byte[] image = telegramBot.getFileContent(file);
                        return image;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }
}
