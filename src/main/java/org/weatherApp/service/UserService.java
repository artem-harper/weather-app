package org.weatherApp.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.RegisterUserDto;
import org.weatherApp.entity.User;
import org.weatherApp.exceptions.InvalidPasswordException;
import org.weatherApp.exceptions.UserAlreadyExistException;
import org.weatherApp.exceptions.UserNotFoundException;
import org.weatherApp.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginUserDto authUser(LoginUserDto userDto){

        String requestPassword = userDto.getPassword();

        LoginUserDto loginUserDto =  userRepository.findByLogin(modelMapper.map(userDto, User.class))
                .map(user1 -> modelMapper.map(user1, LoginUserDto.class))
                .orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(requestPassword, loginUserDto.getPassword())){
            return loginUserDto;
        }else{
            throw new InvalidPasswordException();
        }
    }

    public void registerUser(RegisterUserDto registerUserDto){

        User user = modelMapper.map(registerUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.findByLogin(user).isPresent()){
            throw new UserAlreadyExistException();
        }
        userRepository.save(user);
    }
}
