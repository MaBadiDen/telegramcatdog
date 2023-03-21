package pro.sky.telegramcatdog.constants;

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
    public final static String CAT_SHELTER_STAGE1_WELCOME_MSG_TEXT = "Кошачий приют " + EMOJI_SMILEY_CAT + " Этап 1: Узнать информацию о приюте.";
    public final static String CAT_SHELTER_STAGE2_WELCOME_MSG_TEXT = "Кошачий приют " + EMOJI_SMILEY_CAT + " Этап 2: Как взять кошку из приюта.";
    public final static String CAT_SHELTER_STAGE3_WELCOME_MSG_TEXT = "Кошачий приют " + EMOJI_SMILEY_CAT + " Этап 3: Прислать отчет о питомце.";
    public final static String DOG_SHELTER_STAGE1_WELCOME_MSG_TEXT = "Собачий приют " + EMOJI_DOG + " Этап 1: Узнать информацию о приюте.";
    public final static String DOG_SHELTER_STAGE2_WELCOME_MSG_TEXT = "Собачий приют " + EMOJI_DOG + " Этап 2: Как взять собаку из приюта.";
    public final static String DOG_SHELTER_STAGE3_WELCOME_MSG_TEXT = "Собачий приют " + EMOJI_DOG + " Этап 3: Прислать отчет о питомце.";
    public final static String SHARE_CONTACT_MSG_TEXT = "Нажмите на кнопку " + EMOJI_POINT_DOWN + ", чтобы послать свои контактные данные.";
    public final static String DOG_SHELTER_WELCOME_MSG_TEXT = "Вас приветствует приют для собак. Чем я могу Вам помочь? " + EMOJI_DOG;
    public final static String CONTACT_TELEGRAM_USERNAME_TEXT = "Пожалуйста свяжитесь с пользователем %s. Ему нужна помощь.";
    public final static String CONTACT_TELEGRAM_ID_TEXT = "Пожалуйста свяжитесь с пользователем id %s. Ему нужна помощь.";
    public final static String NO_VOLUNTEERS_TEXT = "На данный момент нет свободных волонтеров.";
    public final static String SAVE_ADOPTER_SUCCESS_TEXT = "Мы записали ваши контактные данные.";
    public final static String ADOPTER_ALREADY_EXISTS_TEXT = "У нас уже есть ваши контактные данные.";
    public final static String WE_WILL_CALL_YOU_TEXT = "Скоро с вами свяжется наш волонтер.";

    // Buttons text
    public final static String BUTTON_CAT_SHELTER_TEXT = "Приют для кошек";
    public final static String BUTTON_DOG_SHELTER_TEXT = "Приют для собак";
    public final static String BUTTON_STAGE1_TEXT = "Узнать информацию о приюте (этап 1)";
    public final static String BUTTON_STAGE2_TEXT = "Как взять питомца из приюта (этап 2)";
    public final static String BUTTON_STAGE3_TEXT = "Прислать отчет о питомце (этап 3)";
    public final static String BUTTON_CALL_VOLUNTEER_TEXT = "Позвать волонтера";
    public final static String BUTTON_MAIN_MENU_TEXT = "Главное меню";
    public final static String BUTTON_SHARE_CONTACT_TEXT = "Отправить контакт";
    public final static String BUTTON_SHARE_CONTACT_DETAILS_TEXT = "Оставить свои контактные данные";
    public final static String BUTTON_REPORT_TEMPLATE_TEXT = "Форма ежедневного отчета";
    public final static String BUTTON_SEND_REPORT_TEXT = "Послать отчет";

    // Buttons callback text
    public final static String BUTTON_CAT_SHELTER_CALLBACK_TEXT = "button_Cat_Shelter_clicked";
    public final static String BUTTON_DOG_SHELTER_CALLBACK_TEXT = "button_Dog_Shelter_clicked";
    public final static String BUTTON_STAGE1_CALLBACK_TEXT = "button_Stage1_clicked";
    public final static String BUTTON_STAGE2_CALLBACK_TEXT = "button_Stage2_clicked";
    public final static String BUTTON_STAGE3_CALLBACK_TEXT = "button_Stage3_clicked";
    public final static String BUTTON_CALL_VOLUNTEER_CALLBACK_TEXT = "button_CallVolunteer_clicked";
    public final static String BUTTON_SHARE_CONTACT_CALLBACK_TEXT = "button_ShareContact_clicked";
    public final static String BUTTON_REPORT_TEMPLATE_CALLBACK_TEXT = "button_ReportTemplate_clicked";
    public final static String BUTTON_SEND_REPORT_CALLBACK_TEXT = "button_SendReport_clicked";

    // REST endpoint testing urls
    public final static String LOCALHOST_URL = "http://localhost:";
    public final static String BRANCHPARAMS_URL = "/pet-shelter/params";
    public final static String PET_URL = "/pet-shelter/pet";
    public final static String VOLUNTEER_URL = "/pet-shelter/volunteer";
    public final static String BREED_URL = "/pet-shelter/breed";
    public final static String ADOPTION_REPORT_URL = "/pet-shelter/adoptionReport";

    public final static String ADOPTIONDOC_URL = "/pet-shelter/docs";

}
