package ru.sultanov.telsoft.validations;

import ru.sultanov.telsoft.annotations.PasswordMatches;
import ru.sultanov.telsoft.payload.request.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        //ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequest userSignupRequest = (SignupRequest) o;
        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
    }
}
