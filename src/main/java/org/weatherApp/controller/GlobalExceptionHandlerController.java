package org.weatherApp.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.RegisterUserDto;
import org.weatherApp.exceptions.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(exception = {UserNotFoundException.class, InvalidPasswordException.class})
    public String authHandleException(Model model) {
        log.warn("INVALID USERNAME OR PASSWORD");
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
        log.info("SESSION EXPIRED");
        return "redirect:/sign-in";
    }

}
