package dev.taway.tutil.crypto;

import dev.taway.tutil.io.file.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;

public class RSATest {

    private RSA rsa;
    private final int keySize = 2048;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    KeyPair keyPair;

    @BeforeEach
    void setUp() {
        rsa = new RSA(keySize);
        publicKey = rsa.getPublicKey();
        privateKey = rsa.getPrivateKey();
        keyPair = new KeyPair(publicKey, privateKey);
    }

    @Test
    void constructorWithKeySize_ShouldGenerateKeys() {
        RSA rsaWithKeySize = new RSA(keySize);
        assertNotNull(rsaWithKeySize.getPrivateKey());
        assertNotNull(rsaWithKeySize.getPublicKey());
    }

    @Test
    void encrypt_ShouldReturnEncryptedData() {
        String message = "test message";
        byte[] encryptedData = rsa.encrypt(message);
        assertNotNull(encryptedData);
        assertNotEquals(0, encryptedData.length);
    }

    @Test
    void decrypt_ShouldReturnOriginalMessage() {
        String message = "test message";
        byte[] encryptedData = rsa.encrypt(message);
        String decryptedMessage = rsa.decrypt(encryptedData);
        assertEquals(message, decryptedMessage);
    }

    @Test
    void saveAndLoadPublicKey_ShouldBeConsistent() {
        String path = "./public.key";
        rsa.savePublicKeyToFile(path);
        rsa.loadPublicKeyFromFile(path);
        assertEquals(publicKey, rsa.getPublicKey());
        File file = new File(path);
        file.deleteOnExit();
    }

    @Test
    void saveAndLoadPrivateKey_ShouldBeConsistent() {
        String path = "./private.key";
        rsa.savePrivateKeyToFile(path);
        rsa.loadPrivateKeyFromFile(path);
        assertEquals(privateKey, rsa.getPrivateKey());
        File file = new File(path);
        file.deleteOnExit();
    }

    @Test
    void constructorWithUnsafeKeySize_ShouldLogWarning() {
//        LMAO figure out how to test this automatically
        int unsafeKeySize = 1024;
        RSA unsafeRsa = new RSA(unsafeKeySize);
    }
}
