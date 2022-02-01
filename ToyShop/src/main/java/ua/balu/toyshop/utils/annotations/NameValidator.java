package ua.balu.toyshop.utils.annotations;

import ua.balu.toyshop.exception.IncorrectInputException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {
    private static final String NAME_REGEX = "[a-zA-ZА-Яа-я]+";
    private static final String INCORRECT_NAME_INPUT = "Incorrect name/last name: %s";

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {

        if (!name.matches(NAME_REGEX)) {
            throw new IncorrectInputException(String.format(INCORRECT_NAME_INPUT, name));
        }
        return true;
    }
}
