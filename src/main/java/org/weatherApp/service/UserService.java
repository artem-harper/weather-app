package org.weatherApp.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.dto.LocationDto;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.RegisterUserDto;
import org.weatherApp.dto.UserDto;
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

    @Transactional
    public UserDto authUser(LoginUserDto userDto) {

        String requestPassword = userDto.getPassword();

        User user = userRepository.findByLogin(modelMapper.map(userDto, User.class))
                .orElseThrow(UserNotFoundException::new);


        if (passwordEncoder.matches(requestPassword, user.getPassword())) {
            return modelMapper.map(user, UserDto.class);
        } else {
            throw new InvalidPasswordException();
        }
    }

    @Transactional
    public void registerUser(RegisterUserDto registerUserDto) {

        User user = modelMapper.map(registerUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.findByLogin(user).isPresent()) {
            throw new UserAlreadyExistException();
        }
        userRepository.save(user);
    }
}
