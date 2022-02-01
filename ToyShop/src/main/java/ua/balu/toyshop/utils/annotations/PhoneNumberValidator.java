package ua.balu.toyshop.utils.annotations;

import ua.balu.toyshop.exception.IncorrectInputException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final String PHONE_NUMBER_REGEX = "^[0-9]{9}$";
    private static final String INCORRECT_NUMBER_MESSAGE = "Incorrect number";
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (!phoneNumber.matches(PHONE_NUMBER_REGEX)){
            throw  new IncorrectInputException(INCORRECT_NUMBER_MESSAGE);
        }
        return true;
    }
}