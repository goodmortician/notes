package com.goodmortician.notes.web;

import org.springframework.util.StringUtils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class LocalJwtKeyProvider {
    private final Key privateKey;

    private final Key publicKey;

    public LocalJwtKeyProvider(String privateKey, String publicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        if (StringUtils.hasText(privateKey)) {
            privateKey = privateKey.replaceAll("\\s", "");
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            this.privateKey = keyFactory.generatePrivate(spec);
        } else {
            this.privateKey = null;
        }

        if (StringUtils.hasText(publicKey)) {
            publicKey = publicKey.replaceAll("\\s", "");
            byte[] publicKeyDER = Base64.getDecoder().decode(publicKey);
            this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyDER));
        } else {
            this.publicKey = null;
        }
    }

    public Key getVerifyingKey() {
        return publicKey;
    }

    public Key getSigningKey() {
        return privateKey;
    }
}