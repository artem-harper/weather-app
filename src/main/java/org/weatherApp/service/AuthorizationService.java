package org.weatherApp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.weatherApp.dto.RegisterUserDto;
import org.weatherApp.entity.User;
import org.weatherApp.repository.UserRepository;

@Service
public class AuthorizationService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AuthorizationService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public void register(RegisterUserDto registerUserDto){
        userRepository.save(modelMapper.map(registerUserDto, User.class));
    }
}
