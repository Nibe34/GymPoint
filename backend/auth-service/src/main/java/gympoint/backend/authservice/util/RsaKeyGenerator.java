package gympoint.backend.authservice.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaKeyGenerator {
    public static void main(String[] args) throws Exception {
        // Create certs directory if it doesn't exist
        Path certsDir = Paths.get("src", "main", "resources", "certs");
        Files.createDirectories(certsDir);

        // Generate key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Save private key
        try (FileOutputStream fos = new FileOutputStream(certsDir.resolve("private.pem").toFile())) {
            fos.write("-----BEGIN PRIVATE KEY-----\n".getBytes());
            fos.write(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
            fos.write("\n-----END PRIVATE KEY-----\n".getBytes());
        }

        // Save public key
        try (FileOutputStream fos = new FileOutputStream(certsDir.resolve("public.pem").toFile())) {
            fos.write("-----BEGIN PUBLIC KEY-----\n".getBytes());
            fos.write(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
            fos.write("\n-----END PUBLIC KEY-----\n".getBytes());
        }

        System.out.println("RSA key pair generated successfully!");
    }
} 