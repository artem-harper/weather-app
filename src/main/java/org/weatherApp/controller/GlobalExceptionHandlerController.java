package org.weatherApp.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.RegisterUserDto;
import org.weatherApp.exceptions.*;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(exception = {UserNotFoundException.class, InvalidPasswordException.class})
    public String authHandleException(Model model) {
        model.addAttribute("error", "user_notFound_or_invalid_password");
        model.addAttribute(new LoginUserDto());
        return "sign-in";
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public String registerHandleException(Model model){
        model.addAttribute("error", "user_already_exist");
        model.addAttribute("registerUserDto", new RegisterUserDto());
        return "sign-up";
    }

    @ExceptionHandler(exception = {SessionNotFoundException.class, SessionExpiredException.class})
    public String sessionHandlerException(){
        return "redirect:/sign-in";
    }

}
