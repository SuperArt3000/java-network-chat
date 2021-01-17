package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zurbaevi Nika
 */
public class Validator {

    private static final String NICKNAME_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,10}[a-zA-Z0-9]$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-5])(?=.*[a-z])(?=.*[A-Z]).{4,20}$";

    private static final Pattern nicknamePattern = Pattern.compile(NICKNAME_PATTERN);
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValidNickname(final String nickname) {
        Matcher matcher = nicknamePattern.matcher(nickname);
        return matcher.matches();
    }

    public static boolean isValidPassword(final String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidChangeNickname(final String nickname) {
        Matcher matcher = nicknamePattern.matcher(nickname);
        return matcher.matches();
    }
}
