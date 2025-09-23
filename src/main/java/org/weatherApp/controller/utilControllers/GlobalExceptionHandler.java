package org.weatherApp.controller.utilControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.RegisterUserDto;
import org.weatherApp.exceptions.*;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = {UserNotFoundException.class, InvalidPasswordException.class})
    public String authHandleException(Model model) {
        log.warn("INVALID USERNAME OR PASSWORD");
        model.addAttribute("error", "user_notFound_or_invalid_password");
        model.addAttribute(new LoginUserDto());
        return "sign-in";
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public String registerHandleException(Model model) {
        log.info("USER ALREADY EXIST");
        model.addAttribute("error", "user_already_exist");
        model.addAttribute("registerUserDto", new RegisterUserDto());
        return "sign-up";
    }

    @ExceptionHandler(exception = {SessionNotFoundException.class, SessionExpiredException.class})
    public String sessionHandlerException() {
        log.info("SESSION EXPIRED");
        return "redirect:/sign-in";
    }

    @ExceptionHandler(LocationAlreadyExistException.class)
    public String locationHandlerException(RedirectAttributes redirectAttributes) {
        log.info("LOCATION ALREADY ADDED");
        redirectAttributes.addAttribute("error", "location_already_added");
        return "redirect:/search";
    }

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions() {
        return "error";
    }
}
