package pro.sky.telegramcatdog.constants;

import com.vdurmont.emoji.EmojiParser;

public class Constants {
    // Emojis
    public final static String EMOJI_SMILEY = EmojiParser.parseToUnicode(":smiley:");
    public final static String EMOJI_WAVE = EmojiParser.parseToUnicode(":wave:");

    // Information messages
    public final static String WELCOME_MSG_TEXT = "Привет " + EMOJI_WAVE + " Это бот приюта для кошек о собак. Чем я могу Вам помочь? " + EMOJI_SMILEY;

    // Buttons text
    public final static String BUTTON_STAGE1_TEXT = "Узнать информацию о приюте (этап 1)";
    public final static String BUTTON_STAGE2_TEXT = "Как взять собаку из приюта (этап 2)";
    public final static String BUTTON_STAGE3_TEXT = "Прислать отчет о питомце (этап 3)";
    public final static String BUTTON_CALL_VOLUNTEER_TEXT = "Позвать волонтера";

    // Buttons callback text
    public final static String BUTTON_STAGE1_CALLBACK_TEXT = "button Stage1 clicked";
    public final static String BUTTON_STAGE2_CALLBACK_TEXT = "button Stage2 clicked";
    public final static String BUTTON_STAGE3_CALLBACK_TEXT = "button Stage3 clicked";
    public final static String BUTTON_CALL_VOLUNTEER_CALLBACK_TEXT = "button CallVolunteer clicked";
}
