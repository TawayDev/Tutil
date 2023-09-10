package dev.taway.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaesarCipherTest {
    @Test
    void CaesarTest() {
        String original =
                "Never gonna give you up,"+
                "Never gonna let you down,"+
                "Never gonna run around and desert you,"+
                "Never gonna make you cry,"+
                "Never gonna say goodbye,"+
                "Never gonna tell a lie and hurt you";
        String encrypted = CaesarCipher.encrypt(original, 15);
        String decrypted = CaesarCipher.decrypt(encrypted, 15);
        assertEquals(original, decrypted);
    }
}