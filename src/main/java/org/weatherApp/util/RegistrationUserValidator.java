package org.weatherApp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.weatherApp.dto.RegisterUserDto;

@Component
public class RegistrationUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterUserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterUserDto registerUserDto = (RegisterUserDto) target;

        if (!registerUserDto.getPassword().equals(registerUserDto.getRepeatPassword())){
            errors.rejectValue("repeatPassword", "500", "Passwords must be the same");
        }
    }
}
