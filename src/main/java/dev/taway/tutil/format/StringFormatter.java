package dev.taway.tutil.format;

import java.util.HashMap;
import java.util.Map;

public class StringFormatter {
    /**
     * Formats a string by replacing placeholders with corresponding values from a HashMap.
     *
     * @param formatString The string to be formatted, containing placeholders to be replaced.
     * @param values A HashMap containing key-value pairs, where each key corresponds to a placeholder in the formatString
     *               and each value represents the value to be used for replacement.
     * @return The formatted string with placeholders replaced by values from the HashMap.
     */
    public static String formatString(String formatString, HashMap<String, String> values) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            formatString = formatString.replace(key, value);
        }
        return formatString;
    }
}
