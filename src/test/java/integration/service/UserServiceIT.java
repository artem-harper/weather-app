package integration.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.config.TestConfig;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.RegisterUserDto;
import org.weatherApp.exceptions.InvalidPasswordException;
import org.weatherApp.exceptions.UserAlreadyExistException;
import org.weatherApp.repository.UserRepository;
import org.weatherApp.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldIncrementUsersCountWithNewRegisteredUser() {

        int dbUsersBeforeReg = userRepository.findAll().size();

        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .login("test")
                .password("testPassword")
                .repeatPassword("testPassword")
                .build();

        userService.registerUser(registerUserDto);

        int dbUsersAfterReg = userRepository.findAll().size();

        assertThat(dbUsersAfterReg-dbUsersBeforeReg).isEqualTo(1);
    }

    @Test
    void userWithNonUniqueNameShouldThrowException(){
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .login("liquibaseUser")
                .password("testPassword")
                .repeatPassword("testPassword")
                .build();


        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(registerUserDto));
    }

    @Test
    void userWithWrongPasswordsShouldThrowException(){
        LoginUserDto loginUserDto = LoginUserDto.builder()
                .login("liquibaseUser")
                .password("wrongPassword")
                .build();

        assertThrows(InvalidPasswordException.class, () -> userService.authUser(loginUserDto));
    }
}
