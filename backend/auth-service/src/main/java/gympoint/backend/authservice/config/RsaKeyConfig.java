package gympoint.backend.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class RsaKeyConfig {

    @Bean
    public RsaKeyProperties rsaKeys() throws NoSuchAlgorithmException {
        // Generate key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RsaKeyProperties rsaKeyProperties = new RsaKeyProperties();
        rsaKeyProperties.setPublicKey(publicKey);
        rsaKeyProperties.setPrivateKey(privateKey);
        return rsaKeyProperties;
    }
} 