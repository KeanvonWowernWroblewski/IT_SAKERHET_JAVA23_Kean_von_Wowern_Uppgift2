package com.example.keanuppgift2v4.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;


@Component
public class CryptoHelper {

    private static final String AES_SPEC = "AES/CBC/PKCS5Padding";

    private final SecretKeySpec keySpec;
    private final IvParameterSpec ivSpec;

    public CryptoHelper() {
        byte[] rawKey = "thisIsMySecretKey123456789012345".getBytes(StandardCharsets.UTF_8);
        this.keySpec = new SecretKeySpec(rawKey, "AES");
        byte[] ivBytes = Arrays.copyOfRange(rawKey, 0, 16);
        this.ivSpec = new IvParameterSpec(ivBytes);
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_SPEC);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] result = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(result);
    }


    public String decrypt(String encoded) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_SPEC);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] raw = Base64.getDecoder().decode(encoded);
        byte[] finalData = cipher.doFinal(raw);
        return new String(finalData, StandardCharsets.UTF_8);
    }
}
