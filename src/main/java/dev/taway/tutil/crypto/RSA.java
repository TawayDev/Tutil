package dev.taway.tutil.crypto;

import dev.taway.tutil.RuntimeConfig;
import dev.taway.tutil.logging.LogLevel;
import dev.taway.tutil.logging.Logger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Getter
@Setter
public class RSA {
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private static final Logger logger = new Logger("RSA");
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private static final String ALGORITHM = "RSA";
    private PrivateKey privateKey;
    private PublicKey publicKey;

    /**
     * Generates a key with the specified key size.
     *
     * @param keySize Size of the key in bits.
     */
    public RSA(int keySize) {
        if ((keySize < 2048) && RuntimeConfig.CRYPTO.warnUnsafeRSAKeySizes)
            logger.log(LogLevel.WARN, "Constructor", "Using keys smaller than 2048-bits is NOT recommended! Please use at least 2048-bits for better security!");
        generateKeyPair(keySize);
    }

    public RSA(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public byte[] encrypt(String message) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(message.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException e) {
            logger.log(LogLevel.ERROR, "encrypt", e.toString());
            return new byte[0];
        }
    }

    public String decrypt(byte[] encryptedMessage) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = new byte[0];
            decryptedBytes = cipher.doFinal(encryptedMessage);
            return new String(decryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException e) {
            logger.log(LogLevel.ERROR, "decrypt", e.toString());
            return "";
        }
    }

    private void generateKeyPair(int keySize) {
        KeyPairGenerator keyGen;
        try {
            keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(keySize);
            KeyPair pair = keyGen.generateKeyPair();
            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            logger.log(LogLevel.ERROR, "generateKeyPair", e.toString());
        }
    }

    public void savePublicKeyToFile(String path) {
        saveKeyToFile(path, publicKey);
    }

    public void savePrivateKeyToFile(String path) {
        saveKeyToFile(path, privateKey);
    }

    public void loadPublicKeyFromFile(String path) {
        this.publicKey = (PublicKey) readKeyFromFile(path, true);
    }

    public void loadPrivateKeyFromFile(String path) {
        this.privateKey = (PrivateKey) readKeyFromFile(path, false);
    }

    private Key readKeyFromFile(String path, boolean isPublic) {
        try {
            byte[] keyBytes;
            keyBytes = Files.readAllBytes(Paths.get(path));

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

            if (isPublic) {
                X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
                return keyFactory.generatePublic(spec);
            } else {
                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
                return keyFactory.generatePrivate(spec);
            }

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.log(LogLevel.ERROR, "readKeyFromFile", e.toString());
//            BAD BAD BAD!!! RETURNING NULL BAD BAD!
            return null;
        }
    }

    private void saveKeyToFile(String path, Key key) {
        try {
            byte[] keyBytes = key.getEncoded();
            Files.write(Paths.get(path), keyBytes);
        } catch (IOException e) {
            logger.log(LogLevel.ERROR, "saveKeyToFile", e.toString());
        }
    }
}
