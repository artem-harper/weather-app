package org.weatherApp.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.RegisterUserDto;
import org.weatherApp.dto.SessionDto;
import org.weatherApp.exceptions.InvalidPasswordException;
import org.weatherApp.exceptions.UserAlreadyExistException;
import org.weatherApp.exceptions.UserNotFoundException;
import org.weatherApp.service.SessionService;
import org.weatherApp.service.UserService;
import org.weatherApp.util.RegistrationUserValidator;
import java.util.UUID;

@Validated
@Controller()
public class AuthorizationController {

    private final UserService userService;
    private final SessionService sessionService;
    private final RegistrationUserValidator registrationUserValidator;

    public AuthorizationController(UserService userService, SessionService sessionService, RegistrationUserValidator registrationUserValidator) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.registrationUserValidator = registrationUserValidator;
    }

    @GetMapping("/sign-in")
    private String signInPage(@ModelAttribute("loginUserDto") LoginUserDto userDto) {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String signUpPage(@ModelAttribute("registerUserDto") RegisterUserDto userDto) {
        return "sign-up";
    }

    @PostMapping("/sign-in")
    public String authUser(@ModelAttribute("loginUserDto") LoginUserDto loginUserDto, Model model,
                           HttpServletResponse httpServletResponse) {

        LoginUserDto loginUserDtoSaved;

        try {
            loginUserDtoSaved = userService.authUser(loginUserDto);
        } catch (UserNotFoundException | InvalidPasswordException e) {
            model.addAttribute("error", "1");
            return "sign-in";
        }

        UUID sessionId = UUID.randomUUID();

        sessionService.saveSession(SessionDto.builder()
                .id(sessionId)
                .userDto(loginUserDtoSaved)
                .build());


        return "redirect:/";
    }

    @PostMapping("/sign-up")
    public String registerUser(@ModelAttribute("registerUserDto") @Valid RegisterUserDto registerUserDto,
                               BindingResult bindingResult) {

        registrationUserValidator.validate(registerUserDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "sign-up";
        }

        try {
            userService.registerUser(registerUserDto);
        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("login", "500", e.getMessage());
            return "sign-up";
        }

        return "redirect:/sign-in";
    }

}
