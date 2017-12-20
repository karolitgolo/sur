package pl.itgolo.apps.sur.Modules.Commons.Utils;

/**
 * Created by ITGolo on 27.02.2017.
 */
public class ConsoleUtils {

    /* @var reset  */
    public static final String ANSI_RESET = "\u001B[0m";

    /* @var background color black */
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

    /* @var background color red */
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    /* @var background color green */
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";

    /* @var background color yellow */
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

    /* @var background color blue */
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    /* @var background color purple */
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";

    /* @var background color cyan */
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    /* @var background color cyan */
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    /* @var text color black */
    private static final String ANSI_BLACK = "\u001B[30m";

    /* @var text color red */
    public static final String ANSI_RED = "\u001B[31m";

    /* @var text color green */
    private static final String ANSI_GREEN = "\u001B[32m";

    /* @var text color yellow */
    private static final String ANSI_YELLOW = "\u001B[33m";

    /* @var text color blue */
    private static final String ANSI_BLUE = "\u001B[34m";

    /* @var text color purple */
    private static final String ANSI_PURPLE = "\u001B[35m";

    /* @var text color cyan */
    private static final String ANSI_CYAN = "\u001B[36m";

    /* @var text color white */
    private static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Set color black
     * @param text text
     * @return text with color
     */
    public static String setColorBlack(String text){
        return ConsoleUtils.ANSI_BLACK + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set color red
     * @param text text
     * @return text with color
     */
    public static String setColorRed(String text){
        return ConsoleUtils.ANSI_RED + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set color green
     * @param text text
     * @return text with color
     */
    public static String setColorGreen(String text){
        return ConsoleUtils.ANSI_GREEN + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set color yellow
     * @param text text
     * @return text with color
     */
    public static String setColorYellow(String text){
        return ConsoleUtils.ANSI_YELLOW + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set color blue
     * @param text text
     * @return text with color
     */
    public static String setColorBlue(String text){
        return ConsoleUtils.ANSI_BLUE + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set color purple
     * @param text text
     * @return text with color
     */
    public static String setColorPurple(String text){
        return ConsoleUtils.ANSI_PURPLE + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set color cyan
     * @param text text
     * @return text with color
     */
    public static String setColorCyan(String text){
        return ConsoleUtils.ANSI_CYAN + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set color white
     * @param text text
     * @return text with color
     */
    public static String setColorWhite(String text){
        return ConsoleUtils.ANSI_WHITE + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set background black
     * @param text text
     * @return text with color
     */
    public static String setBackgroundBlack(String text){
        return ConsoleUtils.ANSI_BLACK_BACKGROUND + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set background red
     * @param text text
     * @return text with color
     */
    public static String setBackgroundRed(String text){
        return ConsoleUtils.ANSI_RED_BACKGROUND + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set background green
     * @param text text
     * @return text with color
     */
    public static String setBackgroundGreen(String text){
        return ConsoleUtils.ANSI_GREEN_BACKGROUND + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set background yellow
     * @param text text
     * @return text with color
     */
    public static String setBackgroundYellow(String text){
        return ConsoleUtils.ANSI_YELLOW_BACKGROUND + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set background blue
     * @param text text
     * @return text with color
     */
    public static String setBackgroundBlue(String text){
        return ConsoleUtils.ANSI_BLUE_BACKGROUND + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set background cyan
     * @param text text
     * @return text with color
     */
    public static String setBackgroundCyan(String text){
        return ConsoleUtils.ANSI_CYAN_BACKGROUND + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set background white
     * @param text text
     * @return text with color
     */
    public static String setBackgroundWhite(String text){
        return ConsoleUtils.ANSI_WHITE_BACKGROUND + text + ConsoleUtils.ANSI_RESET;
    }

    /**
     * Set background purple
     * @param text text
     * @return text with color
     */
    public static String setBackgroundPurple(String text){
        return ConsoleUtils.ANSI_PURPLE_BACKGROUND + text + ConsoleUtils.ANSI_RESET;
    }
}
