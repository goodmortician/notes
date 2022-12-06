package com.goodmortician.notes.auth;

import com.goodmortician.notes.web.LocalJwtKeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Configuration
public class KeyProviderConfig {

    @Bean
    public LocalJwtKeyProvider localJwtKeyProvider(@Value(value = "${jwt.key.public}") String publicKey,
                                                   @Value(value = "${jwt.key.private}") String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new LocalJwtKeyProvider(privateKey, publicKey);
    }
}