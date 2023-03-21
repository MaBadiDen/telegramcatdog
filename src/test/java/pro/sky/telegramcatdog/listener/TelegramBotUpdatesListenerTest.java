package pro.sky.telegramcatdog.listener;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegramcatdog.constants.PetType;
import pro.sky.telegramcatdog.model.Guest;
import pro.sky.telegramcatdog.model.Volunteer;
import pro.sky.telegramcatdog.repository.GuestRepository;
import pro.sky.telegramcatdog.repository.VolunteerRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pro.sky.telegramcatdog.constants.Constants.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Mock
    private VolunteerRepository volunteerRepository;

    @Mock
    private GuestRepository guestRepository;

    /* Testing '/start' command when it is a new guest (unknown guest). */
    @Test
    public void handleStartCommandNewGuestTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, "/start");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(SHELTER_TYPE_SELECT_MSG_TEXT);
    }

    /* Testing '/start' command with the guest who has previously selected CAT shelter.
    *  (and he is already saved in the [guests] table) */
    @Test
    public void handleStartCommandCatShelterGuestTest() throws URISyntaxException, IOException {
        long chatId = 1234567809L;
        Guest guest = new Guest(chatId, Timestamp.valueOf("2023-03-01 14:45:06"), PetType.CAT);

        when(guestRepository.findByChatId(any(Long.class))).thenReturn(guest);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, "/start");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CAT_SHELTER_WELCOME_MSG_TEXT);
    }

    /* Testing '/start' command with the guest who has previously selected DOG shelter.
     *  (and he is already saved in the [guests] table) */
    @Test
    public void handleStartCommandDogShelterGuestTest() throws URISyntaxException, IOException {
        long chatId = 1234567809L;
        Guest guest = new Guest(chatId, Timestamp.valueOf("2023-03-01 14:45:06"), PetType.DOG);

        when(guestRepository.findByChatId(any(Long.class))).thenReturn(guest);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, "/start");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(DOG_SHELTER_WELCOME_MSG_TEXT);
    }

    @Test
    public void handleCatShelterSelectTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_CAT_SHELTER_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CAT_SHELTER_WELCOME_MSG_TEXT);
    }

    @Test
    public void handleDogShelterSelectTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_DOG_SHELTER_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(DOG_SHELTER_WELCOME_MSG_TEXT);
    }

    /* Testing Call Volunteer method when guest has no @username defined (his chatId is used in this case). */
    @Test
    public void handleCallVolunteerChatIdTest() throws URISyntaxException, IOException {
        Long volunteerId = 1234567809L;
        String userId = "1234567809";
        Volunteer volunteer = new Volunteer(1, "Vasya", volunteerId, "https://t.me/vasyapupkin", null);

        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(volunteer));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_CALL_VOLUNTEER_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(volunteerId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(String.format(CONTACT_TELEGRAM_ID_TEXT, userId));
    }

    /* Testing Call Volunteer method when guest's @username is defined. */
    @Test
    public void handleCallVolunteerUsernameTest() throws URISyntaxException, IOException {
        Long volunteerId = 1234567809L;
        String userId = "@vasyapupkin";
        Volunteer volunteer = new Volunteer(1, "Volunteer 1", volunteerId, "https://t.me/volunteer1", null);

        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(volunteer));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update_with_username.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_CALL_VOLUNTEER_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(volunteerId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(String.format(CONTACT_TELEGRAM_USERNAME_TEXT, userId));
    }

    /* Testing Call Volunteer method when no volunteers in the table (no volunteers defined). */
    @Test
    public void handleCallVolunteerWhenNoVolunteersTest() throws URISyntaxException, IOException {
        Long userId = 1234567809L;

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_CALL_VOLUNTEER_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(userId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(NO_VOLUNTEERS_TEXT);
    }

    private Update getUpdateMessage(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%message_text%", replaced), Update.class);
    }
}