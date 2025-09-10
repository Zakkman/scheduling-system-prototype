package application.backend.email;

import java.security.SecureRandom;

public class CodeGenerator {

    private final static String NUMBERS = "0123456789";
    private final static int CODE_LENGTH = 6;

    public static String getCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeBuilder.append(NUMBERS.charAt(random.nextInt(CODE_LENGTH)));
        }
        return codeBuilder.toString();
    }
}