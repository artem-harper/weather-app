package org.weatherApp.controller;

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
import org.weatherApp.exceptions.UserNotFoundException;

import org.weatherApp.service.AuthorizationService;
import org.weatherApp.service.UserService;
import org.weatherApp.util.RegistrationUserValidator;

@Validated
@Controller()
public class AuthorizationController {

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final RegistrationUserValidator registrationUserValidator;

    public AuthorizationController(UserService userService, AuthorizationService authorizationService, RegistrationUserValidator registrationUserValidator) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.registrationUserValidator = registrationUserValidator;
    }

    @GetMapping("/sign-in")
    private String signInPage(@ModelAttribute("loginUserDto") LoginUserDto userDto){
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String signUpPage(@ModelAttribute("registerUserDto") RegisterUserDto userDto){
        return "sign-up";
    }

    @GetMapping()
    public String mainPage(){
        return "index";
    }

    @PostMapping("/sign-in")
    public String signInUser(@ModelAttribute("loginUserDto") LoginUserDto loginUserDto, Model model){

        try {
            LoginUserDto dto = userService.findUserByLogin(loginUserDto);
        }catch (UserNotFoundException e){
            model.addAttribute("error", "1");
            return "sign-in";
        }
        return "redirect:/";
    }

    @PostMapping("/sign-up")
    public String singUpUser(@ModelAttribute("registerUserDto") @Valid RegisterUserDto registerUserDto,
                             BindingResult bindingResult){

        registrationUserValidator.validate(registerUserDto, bindingResult);

        if (bindingResult.hasErrors()){
            return "sign-up";
        }
        authorizationService.register(registerUserDto);
        return "sign-up";
    }

}
