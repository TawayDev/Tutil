package dev.taway.crypto;

import dev.taway.logging.LogLevel;
import dev.taway.logging.Logger;

/**
 * @since 0.1.2
 * @see <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Wikipedia - Caesar cipher</a>
 */
public class CaesarCipher {
    static Logger logger = new Logger("CaesarCipher");
    public static String encrypt(String text, int shift) {
        try {
            char[] chars = text.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                chars[i] = (char)((int)chars[i] + shift);
            }
            return String.valueOf(chars);
        } catch (Exception exception) {
            logger.log(LogLevel.ERROR, "encrypt", exception.getMessage());
        }
        return "";
    }

    public static String decrypt(String text, int shift) {
        try {
            char[] chars = text.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                chars[i] = (char)((int)chars[i] - shift);
            }
            return String.valueOf(chars);
        } catch (Exception exception) {
            logger.log(LogLevel.ERROR, "decrypt", exception.getMessage());
        }
        return "";
    }
}
