package rs.ac.uns.quiz.service;

import rs.ac.uns.quiz.dto.LoginDto;
import rs.ac.uns.quiz.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface PersonService {
    UserDto getLoggedInUser(String authToken);

    boolean register(UserDto userDto);

    List<UserDto> getAllUsers();

    boolean verifyRecaptcha(String recaptcha);

    boolean editPassword(LoginDto editDto);

    UserDto editData(UserDto userDto);

    UserDto userByUsername(String username);

    boolean blockUser(String username);

    boolean unblockUser(String username);

}
