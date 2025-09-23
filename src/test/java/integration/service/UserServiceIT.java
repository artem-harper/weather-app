package integration.service;

import org.junit.jupiter.api.Nested;
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
import org.weatherApp.exceptions.UserNotFoundException;
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

    @Nested
    class RegistrationTest{
        @Test
        void shouldIncrementUsersCountWithNewRegisteredUser() {

            int dbUsersCountBeforeReg = userRepository.findAll().size();

            RegisterUserDto registerUserDto = RegisterUserDto.builder()
                    .login("test")
                    .password("testPassword")
                    .repeatPassword("testPassword")
                    .build();

            userService.registerUser(registerUserDto);

            int dbUsersCountAfterReg = userRepository.findAll().size();

            assertThat(dbUsersCountAfterReg-dbUsersCountBeforeReg).isEqualTo(1);
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

    }

    @Nested
    class AuthTest{
        @Test
        void notRegisteredUserShouldThrowExceptionOnAuth(){

            LoginUserDto loginUserDto = LoginUserDto.builder()
                    .login("testNotRegisteredUser")
                    .password("1234")
                    .build();

            assertThrows(UserNotFoundException.class, ()->userService.authUser(loginUserDto));

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

}
