package pro.sky.telegramcatdog.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegramcatdog.constants.PetType;
import pro.sky.telegramcatdog.model.BranchParams;
import pro.sky.telegramcatdog.model.Guest;
import pro.sky.telegramcatdog.model.Volunteer;
import pro.sky.telegramcatdog.repository.BranchParamsRepository;
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

    private final BranchParamsRepository branchParamsRepository;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, VolunteerRepository volunteerRepository, GuestRepository guestRepository, BranchParamsRepository branchParamsRepository) {
        this.telegramBot = telegramBot;
        this.volunteerRepository = volunteerRepository;
        this.guestRepository = guestRepository;
        this.branchParamsRepository = branchParamsRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {

            logger.info("Processing update: {}", update);

            // Process shelter type selection message
            if (update.message() != null) {
                String incomeMsgText = update.message().text();
                // For stickers incomeMsgText is null
                if (incomeMsgText == null) {
                    return;
                }
                if (incomeMsgText.equals("/start")) {
                    processStartCommand(update);
                }
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

    /**
     * Creates buttons for the reply message to the shelter type selection (Stage 1)
     * @return {@code InlineKeyboardMarkup}
     */
    private InlineKeyboardMarkup createButtonsStage1() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_INFO_SHELTER_TEXT).callbackData(BUTTON_INFO_SHELTER_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_INFO_SECURITY_TEXT).callbackData(BUTTON_INFO_SECURITY_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_INFO_SAFETY_PRECAUTIONS_TEXT).callbackData(BUTTON_INFO_SAFETY_PRECAUTIONS_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
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

            if (callbackQuery.data().equals(BUTTON_CAT_SHELTER_CALLBACK_TEXT)) {
                // Cat shelter selected
                sendButtonClickMessage(chatId, BUTTON_CAT_SHELTER_CALLBACK_TEXT);
                processCatShelterClick(chatId);

            } else if (callbackQuery.data().equals(BUTTON_DOG_SHELTER_CALLBACK_TEXT)) {
                // Dog shelter selected
                sendButtonClickMessage(chatId, BUTTON_DOG_SHELTER_CALLBACK_TEXT);
                processDogShelterClick(chatId);

            } else if (callbackQuery.data().equals(BUTTON_STAGE1_CALLBACK_TEXT)) {
                // General info about the shelter (stage 1)
                sendButtonClickMessage(chatId, BUTTON_STAGE1_CALLBACK_TEXT);
                processStage1Click(chatId);

            } else if (callbackQuery.data().equals(BUTTON_INFO_SHELTER_CALLBACK_TEXT)) {
                //
                sendButtonClickMessage(chatId,BUTTON_INFO_SHELTER_CALLBACK_TEXT);
                processGettingInfoShelter();


            } else if (callbackQuery.data().equals(BUTTON_INFO_SECURITY_CALLBACK_TEXT)) {
                //Information for providing a car pass
                sendButtonClickMessage(chatId, BUTTON_INFO_SECURITY_CALLBACK_TEXT);
                processGettingSecurityData(chatId);

            } else if (callbackQuery.data().equals(BUTTON_INFO_SAFETY_PRECAUTIONS_CALLBACK_TEXT)) {
                //Safety information
                sendButtonClickMessage(chatId,BUTTON_INFO_SAFETY_PRECAUTIONS_CALLBACK_TEXT);
                processGettingInformationAboutSafetyRules(chatId);

            } else if (callbackQuery.data().equals(BUTTON_STAGE2_CALLBACK_TEXT)) {
                // How to adopt a dog/cat (stage 2)
                sendButtonClickMessage(chatId, BUTTON_STAGE2_CALLBACK_TEXT);
                processStage2Click(chatId);

            } else if (callbackQuery.data().equals(BUTTON_STAGE3_CALLBACK_TEXT)) {
                // Send a follow-up report (stage 3)
                sendButtonClickMessage(chatId, BUTTON_STAGE3_CALLBACK_TEXT);
                processStage3Click(chatId);

            } else if (callbackQuery.data().equals(BUTTON_CALL_VOLUNTEER_CALLBACK_TEXT)) {
                // Call a volunteer
                sendButtonClickMessage(chatId, BUTTON_CALL_VOLUNTEER_CALLBACK_TEXT);
                callVolunteer(update);
            }
        }
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
        // Adding buttons
        message.replyMarkup(createButtonsStage0());
        sendMessage(message);
    }


    /**
     * Processing request: General info about the shelter (stage 1)
     * @param chatId
     */
    private void processStage1Click(long chatId) {
        // to do (Olga): Implement Stage 1 button click functionality (welcome message, buttons)
        SendMessage message = new SendMessage(chatId, STAGE_1_SHELTER_WELCOME_MSG_TEXT);
        // Adding buttons
        message.replyMarkup(createButtonsStage1());
        sendMessage(message);
    }

    /**
     * Processing request: How to adopt a dog/cat (stage 2)
     * @param chatId
     */
    private void processStage2Click(long chatId) {

        // to do (Denis): Implement Stage 2 button click functionality (welcome message, buttons)
    }

    /**
     * Processing request: Send a follow-up report (stage 3)
     * @param chatId
     */
    private void processStage3Click(long chatId) {

        // to do (Tamerlan): Implement Stage 3 button click functionality (welcome message, buttons)
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
        userId += update.callbackQuery().from().id();
        logger.info("UserId = {}", userId);
        // To do: select random volunteer. Now it always selects the 1st one.
        Volunteer volunteer = volunteerRepository.findById(1L).orElse(null);
        if (volunteer == null) {
            // Guest chat_id. Send message to the guest.
            chatId = Long.parseLong(userId);
            SendMessage message = new SendMessage(chatId, NO_VOLUNTEERS_TEXT);
            sendMessage(message);
        } else {
            // Volunteer chat_id. Send message to volunteer.
            chatId = volunteer.getChatId();
            if (update.callbackQuery().from().username() != null) {
                userId = "@" + update.callbackQuery().from().username();
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

    private void processGettingInformationAboutSafetyRules(long chatId) {
        SendMessage message = new SendMessage(chatId, INFO_SAFETY_PRECAUTIONS_TEXT);
        sendMessage(message);
    }

    private void processGettingSecurityData(long chatId) {
        SendMessage message = new SendMessage(chatId, SECURITY_CONTACT_DETAILS);
        sendMessage(message);
    }

    private void processGettingInfoShelter() {

    }

}
