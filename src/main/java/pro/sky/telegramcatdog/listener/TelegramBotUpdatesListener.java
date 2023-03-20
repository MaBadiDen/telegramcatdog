package pro.sky.telegramcatdog.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegramcatdog.constants.PetType;
import pro.sky.telegramcatdog.model.Adopter;
import pro.sky.telegramcatdog.model.AdoptionDoc;
import pro.sky.telegramcatdog.model.Guest;
import pro.sky.telegramcatdog.model.Volunteer;
import pro.sky.telegramcatdog.repository.AdopterRepository;
import pro.sky.telegramcatdog.repository.AdoptionDocRepository;
import pro.sky.telegramcatdog.repository.GuestRepository;
import pro.sky.telegramcatdog.repository.VolunteerRepository;

import java.sql.Timestamp;
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
    private final AdoptionDocRepository adoptionDocRepository;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, VolunteerRepository volunteerRepository, GuestRepository guestRepository, AdopterRepository adopterRepository, AdoptionDocRepository adoptionDocRepository) {
        this.telegramBot = telegramBot;
        this.volunteerRepository = volunteerRepository;
        this.guestRepository = guestRepository;
        this.adopterRepository = adopterRepository;
        this.adoptionDocRepository = adoptionDocRepository;
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
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_RULES_MEETING_ANIMAL_TEXT).callbackData(BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(adoptionDocRepository.findById(3).orElse(null).getShortDesc()).callbackData(BUTTON_DOCS_FOR_ADOPTION_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(adoptionDocRepository.findById(4).orElse(null).getShortDesc()).callbackData(BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(adoptionDocRepository.findById(5).orElse(null).getShortDesc()).callbackData(BUTTON_ARRANGEMENAT_FOR_LITTLE_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(adoptionDocRepository.findById(6).orElse(null).getShortDesc()).callbackData(BUTTON_ARRANGEMENAT_FOR_ADULT_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(adoptionDocRepository.findById(7).orElse(null).getShortDesc()).callbackData(BUTTON_ADVICES_FOR_DISABLE_ANIMAL_CALLBACK_TEXT));
        if(shelterType.equals(PetType.DOG)) {
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(adoptionDocRepository.findById(8).orElse(null).getShortDesc()).callbackData(BUTTON_ADVICES_FROM_KINOLOG_CALLBACK_TEXT));
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(adoptionDocRepository.findById(9).orElse(null).getShortDesc()).callbackData(BUTTON_RECOMMENDED_KINOLOGS_CALLBACK_TEXT));
        }
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(adoptionDocRepository.findById(10).orElse(null).getShortDesc()).callbackData(BUTTON_REASONS_FOR_REFUSAL_CALLBACK_TEXT));
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
                    break;
                case BUTTON_SHARE_CONTACT_CALLBACK_TEXT:
                    // Share your contact details
                    sendButtonClickMessage(chatId, BUTTON_SHARE_CONTACT_CALLBACK_TEXT);
                    shareContact(update);
                    break;
                case BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT:
                    // Instruction how to meet animal first time
                    sendButtonClickMessage(chatId, BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT);
                    processInfoMeetingClick(chatId);
                    break;
                case BUTTON_DOCS_FOR_ADOPTION_CALLBACK_TEXT:
                    // List of required docs
                    sendButtonClickMessage(chatId, BUTTON_DOCS_FOR_ADOPTION_CALLBACK_TEXT);
                    processListOfDocsClick(chatId);
                    break;
                case BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_CALLBACK_TEXT:
                    // Recommendation how to transport animals
                    sendButtonClickMessage(chatId, BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_CALLBACK_TEXT);
                    processTransportAnimal(chatId);
                    break;
                case BUTTON_ARRANGEMENAT_FOR_LITTLE_CALLBACK_TEXT:
                    //  Arrangement for little animal in house
                    sendButtonClickMessage(chatId, BUTTON_ARRANGEMENAT_FOR_LITTLE_CALLBACK_TEXT);
                    processRecForLittle(chatId);
                    break;
                case BUTTON_ARRANGEMENAT_FOR_ADULT_CALLBACK_TEXT:
                    // Arrangement for adult animal in house
                    sendButtonClickMessage(chatId, BUTTON_ARRANGEMENAT_FOR_ADULT_CALLBACK_TEXT);
                    processRecForAdult(chatId);
                    break;
                case BUTTON_ADVICES_FOR_DISABLE_ANIMAL_CALLBACK_TEXT:
                    // Advices how to be with disable animals
                    sendButtonClickMessage(chatId, BUTTON_ADVICES_FOR_DISABLE_ANIMAL_CALLBACK_TEXT);
                    processRecForDisable(chatId);
                    break;
                case BUTTON_ADVICES_FROM_KINOLOG_CALLBACK_TEXT:
                    // Advices from kinolog
                    sendButtonClickMessage(chatId, BUTTON_ADVICES_FROM_KINOLOG_CALLBACK_TEXT);
                    processKinologAdvices(chatId);
                    break;
                case BUTTON_RECOMMENDED_KINOLOGS_CALLBACK_TEXT:
                    // List of recommended kinologs
                    sendButtonClickMessage(chatId, BUTTON_RECOMMENDED_KINOLOGS_CALLBACK_TEXT);
                    processRecKinologs(chatId);
                    break;
                case BUTTON_REASONS_FOR_REFUSAL_CALLBACK_TEXT:
                    // Reasons why we can refuse you
                    sendButtonClickMessage(chatId, BUTTON_REASONS_FOR_REFUSAL_CALLBACK_TEXT);
                    processReasonsRefusal(chatId);
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
        Message message1 = new Message();

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
        String messageText = switch (shelterType) {
            case DOG -> DOG_SHELTER_STAGE2_WELCOME_MSG_TEXT;
            case CAT -> CAT_SHELTER_STAGE2_WELCOME_MSG_TEXT;
        };
        SendMessage message = new SendMessage(chatId, messageText);
        // Adding buttons
        message.replyMarkup(createButtonsStage2());

        sendMessage(message);
    }

    private void processInfoMeetingClick(long chatId) {
        if(shelterType == null) {
            return;
        }
        String messageText = switch (shelterType) {
            case DOG -> adoptionDocRepository.findById(1).orElse(null).getDescription();
            case CAT -> adoptionDocRepository.findById(2).orElse(null).getDescription();
        };
        SendMessage message = new SendMessage(chatId, messageText);
        // Adding buttons
        message.replyMarkup(createButtonsStage2());
        sendMessage(message);
    }

    private void processListOfDocsClick(long chatId) {
        if(shelterType == null) {
            return;
        }
        String messageText = adoptionDocRepository.findById(3).orElse(null).getDescription();
        SendMessage message = new SendMessage(chatId, messageText);
        message.replyMarkup(createButtonsStage2());
        sendMessage(message);
    }

    private void processTransportAnimal(long chatId) {
        if(shelterType == null) {
            return;
        }
        String messageText = adoptionDocRepository.findById(4).orElse(null).getDescription();
        SendMessage message = new SendMessage(chatId, messageText);
        message.replyMarkup(createButtonsStage2());
        sendMessage(message);
    }

    private void processRecForLittle(long chatId) {
        if(shelterType == null) {
            return;
        }
        String messageText = adoptionDocRepository.findById(5).orElse(null).getDescription();
        SendMessage message = new SendMessage(chatId, messageText);
        message.replyMarkup(createButtonsStage2());
        sendMessage(message);
    }

    private void processRecForAdult(long chatId) {
        if(shelterType == null) {
            return;
        }
        String messageText = adoptionDocRepository.findById(6).orElse(null).getDescription();
        SendMessage message = new SendMessage(chatId, messageText);
        message.replyMarkup(createButtonsStage2());
        sendMessage(message);
    }

    private void processRecForDisable(long chatId) {
        if(shelterType == null) {
            return;
        }
        String messageText = adoptionDocRepository.findById(7).orElse(null).getDescription();
        SendMessage message = new SendMessage(chatId, messageText);
        message.replyMarkup(createButtonsStage2());
        sendMessage(message);
    }

    private void processKinologAdvices(long chatId) {
        if(shelterType == null) {
            return;
        }
        String messageText = adoptionDocRepository.findById(8).orElse(null).getDescription();
        SendMessage message = new SendMessage(chatId, messageText);
        message.replyMarkup(createButtonsStage2());
        sendMessage(message);
    }

    private void processRecKinologs(long chatId) {
        if(shelterType == null) {
            return;
        }
        String messageText = adoptionDocRepository.findById(9).orElse(null).getDescription();
        SendMessage message = new SendMessage(chatId, messageText);
        message.replyMarkup(createButtonsStage2());
        sendMessage(message);
    }

    private void processReasonsRefusal(long chatId) {
        if(shelterType == null) {
            return;
        }
        String messageText = adoptionDocRepository.findById(10).orElse(null).getDescription();
        SendMessage message = new SendMessage(chatId, messageText);
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
                adopter = new Adopter(1, firstName, lastName, phone1, chatId, username);
                adopterRepository.save(adopter);
                SendMessage message = new SendMessage(chatId, SAVE_ADOPTER_SUCCESS_TEXT + ' ' + WE_WILL_CALL_YOU_TEXT);
                sendMessage(message.replyMarkup(createMainMenuKeyboardButtons()));
            } else {
                SendMessage message = new SendMessage(chatId, ADOPTER_ALREADY_EXISTS_TEXT + ' ' + WE_WILL_CALL_YOU_TEXT);
                sendMessage(message.replyMarkup(createMainMenuKeyboardButtons()));
            }
        }
    }


}
