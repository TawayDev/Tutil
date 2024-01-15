package dev.taway.tutil.crypto;

/**
 * Very ancient type of symmetric encryption.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Wikipedia - Caesar cipher</a>
 * @since 0.1.2
 */
public class CaesarCipher {
    /**
     * Encrypts the given text using the Caesar cipher algorithm and the specified shift.
     *
     * @param text  the text to encrypt
     * @param shift the number of positions to shift each character
     * @return the encrypted text
     */
    public static String encrypt(String text, int shift) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ((int) chars[i] + shift);
        }
        return String.valueOf(chars);
    }

    /**
     * Decrypts the given text using the Caesar cipher algorithm and the specified shift.
     *
     * @param text  the text to decrypt
     * @param shift the number of positions to shift each character in the opposite direction (negative shift)
     * @return the decrypted text
     */
    public static String decrypt(String text, int shift) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ((int) chars[i] - shift);
        }
        return String.valueOf(chars);
    }
}
