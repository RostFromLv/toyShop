package ua.balu.toyshop.utils.annotations;

import ua.balu.toyshop.exception.IncorrectInputException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password,String> {

    private static final String INCORRECT_PASSWORD_UPPER_LETERS = "Password dont contain UPPER letters";
    private static final String INCORRECT_PASSWORD_DOWN_LETERS = "Password dont contain DOWN letters";
    private static final String INCORRECT_PASSWORD_DIGIT = "Password dont contain DIGIT";
    private static final String INCORRECT_PASSWORD_SPECIAL_SYMBOLS = "Password dont contain SPECIAL SUMBOLS";

    private static final String CHECK_PASSWORD_UPPER_LETTERS=".*[A-Z].*";
    private static final String CHECK_PASSWORD_DOWN_LETTER = ".*[a-z].*";
    private static final String CHECK_PASSWORD_DIGIT = ".*\\d.*";
    private static final String CHECK_PASSWORD_SPECIAL_SYMBOLS = ".*[!@#$%^&*()_<>\\-+=`;:'\"\\,./].*";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (!password.matches(CHECK_PASSWORD_DIGIT) ){
            throw new IncorrectInputException(INCORRECT_PASSWORD_DIGIT);
        }
        if (!password.matches(CHECK_PASSWORD_DOWN_LETTER)){
            throw new IncorrectInputException(INCORRECT_PASSWORD_DOWN_LETERS);
        }
        if (!password.matches(CHECK_PASSWORD_UPPER_LETTERS)){
            throw new IncorrectInputException(INCORRECT_PASSWORD_UPPER_LETERS);
        }
        if (!password.matches(CHECK_PASSWORD_SPECIAL_SYMBOLS)){
            throw new IncorrectInputException(INCORRECT_PASSWORD_SPECIAL_SYMBOLS);
        }
        return true;
    }
}
