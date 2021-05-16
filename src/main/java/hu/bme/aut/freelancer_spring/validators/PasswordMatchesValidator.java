package hu.bme.aut.freelancer_spring.validators;


import hu.bme.aut.freelancer_spring.dto.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        /*empty, passing PasswordMatches to ConstraintValidator*/
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserRegistrationDto user = (UserRegistrationDto) obj;
        return user.getPassword().equals(user.getPasswordConfirm());
    }
}