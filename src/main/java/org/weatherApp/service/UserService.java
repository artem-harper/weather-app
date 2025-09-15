package org.weatherApp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.entity.User;
import org.weatherApp.exceptions.UserNotFoundException;
import org.weatherApp.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

    }

    public LoginUserDto findUserByLogin(LoginUserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        return userRepository.findByLogin(user)
                .map(user1 -> modelMapper.map(user1, LoginUserDto.class))
                .orElseThrow(UserNotFoundException::new);
    }
}
