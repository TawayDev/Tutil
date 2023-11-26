package dev.taway.crypto;

/**
 * Very ancient type of symmetric encryption.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Wikipedia - Caesar cipher</a>
 * @since 0.1.2
 */
public class CaesarCipher {
    public static String encrypt(String text, int shift) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ((int) chars[i] + shift);
        }
        return String.valueOf(chars);
    }

    public static String decrypt(String text, int shift) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ((int) chars[i] - shift);
        }
        return String.valueOf(chars);
    }
}
