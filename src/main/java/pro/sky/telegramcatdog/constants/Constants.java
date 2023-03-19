package pro.sky.telegramcatdog.constants;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.vdurmont.emoji.EmojiParser;

public class Constants {

    // Emojis
    public final static String EMOJI_SMILEY = EmojiParser.parseToUnicode(":smiley:");
    public final static String EMOJI_WAVE = EmojiParser.parseToUnicode(":wave:");
    public final static String EMOJI_POINT_DOWN = EmojiParser.parseToUnicode(":point_down:");
    public final static String EMOJI_SMILEY_CAT = EmojiParser.parseToUnicode(":smiley_cat:");
    public final static String EMOJI_DOG = EmojiParser.parseToUnicode(":dog:");

    // Information messages
    public final static String SHELTER_TYPE_SELECT_MSG_TEXT = "Привет " + EMOJI_WAVE + " Выберите тип приюта " + EMOJI_POINT_DOWN;
    public final static String CAT_SHELTER_WELCOME_MSG_TEXT = "Вас приветствует приют для кошек. Чем я могу Вам помочь? " + EMOJI_SMILEY_CAT;
    public final static String DOG_SHELTER_WELCOME_MSG_TEXT = "Вас приветствует приют для собак. Чем я могу Вам помочь? " + EMOJI_DOG;
    public final static String CONTACT_TELEGRAM_USERNAME_TEXT = "Пожалуйста свяжитесь с пользователем %s. Ему нужна помощь.";
    public final static String CONTACT_TELEGRAM_ID_TEXT = "Пожалуйста свяжитесь с пользователем id %s. Ему нужна помощь.";
    public final static String NO_VOLUNTEERS_TEXT = "На данный момент нет свободных волонтеров.";
    public final static String STAGE_1_SHELTER_WELCOME_MSG_TEXT = "Выберете, какая именно информация вас интересует";
    public final static String INFO_SAFETY_PRECAUTIONS_TEXT = "При первичном посещении Приюта, в целях безопасности, гостю запрещается без сопровождения ответственного рабочего по уходу за животными:\n" +
            "\n" +
            "заходить в вольеры с животными;\n" +
            "кормить и гладить животных;\n" +
            "выпускать животных из вольеров;\n" +
            "выходить с животными за территорию приюта.";

    public final static String SECURITY_CONTACT_DETAILS = "Для получения пропуска на машину, свяжитесь с нашей охраной по телефону:\n"+
            "+7998887755";

    // Buttons text
    public final static String BUTTON_CAT_SHELTER_TEXT = "Приют для кошек";
    public final static String BUTTON_DOG_SHELTER_TEXT = "Приют для собак";
    public final static String BUTTON_STAGE1_TEXT = "Узнать информацию о приюте (этап 1)";
    public final static String BUTTON_STAGE2_TEXT = "Как взять собаку из приюта (этап 2)";
    public final static String BUTTON_STAGE3_TEXT = "Прислать отчет о питомце (этап 3)";
    public final static String BUTTON_CALL_VOLUNTEER_TEXT = "Позвать волонтера";

    public final static String BUTTON_INFO_SHELTER_TEXT = "Расписание работы / адрес / схема проезда";

    public final static String BUTTON_INFO_SECURITY_TEXT = "Оформление пропуска на машину";

    public final static String BUTTON_INFO_SAFETY_PRECAUTIONS_TEXT = "Информация о технике безопасности";




    // Buttons callback text
    /**
     * Callback text linked to the STAGE1 button. Use this text when processing button clicks. <br>
     * Example: <br>
     * {@code if (callbackQuery.data().equals(BUTTON_STAGE1_CALLBACK_TEXT)) { Your code to process stage 1 }}
     * @see CallbackQuery
     */
    public final static String BUTTON_CAT_SHELTER_CALLBACK_TEXT = "button_Cat_Shelter_clicked";
    public final static String BUTTON_DOG_SHELTER_CALLBACK_TEXT = "button_Dog_Shelter_clicked";
    public final static String BUTTON_STAGE1_CALLBACK_TEXT = "button_Stage1_clicked";
    public final static String BUTTON_STAGE2_CALLBACK_TEXT = "button_Stage2_clicked";
    public final static String BUTTON_STAGE3_CALLBACK_TEXT = "button_Stage3_clicked";
    public final static String BUTTON_CALL_VOLUNTEER_CALLBACK_TEXT = "button_CallVolunteer_clicked";

    public final static String BUTTON_INFO_SHELTER_CALLBACK_TEXT = "button_Info_Shelter_clicked";

    public final static String BUTTON_INFO_SECURITY_CALLBACK_TEXT = "button_Info_Security_clicked";

    public final static String BUTTON_INFO_SAFETY_PRECAUTIONS_CALLBACK_TEXT = "button_Info_Safety_Precautions_clicked";

    // REST endpoint testing urls
    public final static String LOCALHOST_URL = "http://localhost:";
    public final static String BRANCHPARAMS_URL = "/pet-shelter/params";
    public final static String PET_URL = "/pet-shelter/pet";
    public final static String VOLUNTEER_URL = "/pet-shelter/volunteer";
    public final static String BREED_URL = "/pet-shelter/breed";

}
