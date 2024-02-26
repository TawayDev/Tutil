package dev.taway.tutil.format;

import dev.taway.tutil.logging.Logger;

import java.util.HashMap;
import java.util.Map;

public class StringFormatter {
    static Logger logger = new Logger();

    /**
     * Formats a string by replacing placeholders with corresponding values from a HashMap.
     *
     * @param formatString The string to be formatted, containing placeholders to be replaced.
     * @param values       A HashMap containing key-value pairs, where each key corresponds to a placeholder in the formatString
     *                     and each value represents the value to be used for replacement.
     * @return The formatted string with placeholders replaced by values from the HashMap.
     */
    public static String formatString(String formatString, HashMap<String, String> values) {
        try {
            if (formatString != null && !values.isEmpty()) {
                for (Map.Entry<String, String> entry : values.entrySet()) {
                    formatString = formatString.replace(entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception exception) {
            System.out.println("Exception in StringFormatter.formatString. Could not be logged because this method is used in logger class. Exception: " + exception);
        }
        return formatString;
    }

    /**
     * Aligns a string to the specified size and text alignment.
     *
     * @param string    The string to be aligned.
     * @param newSize   The new size of the aligned string.
     * @param textAlign The text alignment to be applied.
     * @return The aligned string according to the specified size and text alignment.
     */
    public static String alignString(String string, int newSize, TextAlign textAlign) {
        if (string == null || textAlign == null) {
            throw new IllegalArgumentException("String and textAlign cannot be null.");
        }
        if (newSize < 0) {
            throw new IllegalArgumentException("newSize cannot be negative.");
        }
        switch (textAlign) {
            case LEFT:
                return String.format("%-" + newSize + "s", string);
            case MIDDLE:
                int spaces = newSize - string.length();
                int leftSpaces = spaces / 2;
                int rightSpaces = spaces - leftSpaces;
                return String.format("%" + leftSpaces + "s%s%" + rightSpaces + "s", "", string, "");
            case RIGHT:
                return String.format("%" + newSize + "s", string);
            default:
                return string;
        }
    }
}
