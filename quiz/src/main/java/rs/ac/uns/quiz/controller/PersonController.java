package rs.ac.uns.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.quiz.dto.LoginDto;
import rs.ac.uns.quiz.dto.TokenDto;
import rs.ac.uns.quiz.dto.UserDto;
import rs.ac.uns.quiz.exception.BadRequestException;
import rs.ac.uns.quiz.security.TokenUtils;
import rs.ac.uns.quiz.service.PersonService;
import rs.ac.uns.quiz.time.Time;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static rs.ac.uns.quiz.model.Globals.*;

@RestController
@RequestMapping(path = "/user")
public class PersonController {


    @Autowired
    PersonService personService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenUtils tokenUtils;

    public PersonController() {
    }

    @GetMapping(value="/loggedUser")
    public ResponseEntity<UserDto> getUser(HttpServletRequest http) {
        String authToken = http.getHeader("X-Auth-Token");
        UserDto user =personService.getLoggedInUser(authToken);

        return ResponseEntity.ok().body(user);
    }


    @GetMapping(value="/getAll")
    public ResponseEntity<List<UserDto>> getUsers() {

        List<UserDto> userDtos=personService.getAllUsers();

        return ResponseEntity.ok().body(userDtos);
    }


    @PutMapping(value="/editData")
    public ResponseEntity<UserDto> editData(@RequestBody UserDto userDto,HttpServletRequest http) {

        String authToken = http.getHeader("X-Auth-Token");

        UserDto u=personService.getLoggedInUser(authToken);

        userDto.setUsername(u.getUsername());
        userDto.setPassword(u.getPassword());
        UserDto user =personService.editData(userDto);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(value="/editPassword")
    public ResponseEntity editPassword(@RequestBody LoginDto loginDto,HttpServletRequest http) {

        String authToken = http.getHeader("X-Auth-Token");
        UserDto u=personService.getLoggedInUser(authToken);
        loginDto.setUsername(u.getUsername());
        boolean bool =personService.editPassword(loginDto);
        return ResponseEntity.ok().body(bool);
    }

    @PostMapping(value="/register")
    public ResponseEntity register(@RequestBody UserDto userDto) {

        boolean bool =personService.register(userDto);
        return ResponseEntity.ok().body(bool);
    }

    @PostMapping(value="/block")
    public ResponseEntity block(@RequestBody String username) {

       username=username.replaceAll("^.|.$", "");

        boolean bool =personService.blockUser(username);
        return ResponseEntity.ok().body(bool);
    }
    @PostMapping(value="/unblock")
    public ResponseEntity unblock(@RequestBody String username) {

        username=username.replaceAll("^.|.$", "");

        boolean bool =personService.unblockUser(username);
        return ResponseEntity.ok().body(bool);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto, @RequestParam(name = "g-recaptcha-response") String capResponse) throws IOException {
        Date date = Time.getTime();

        boolean verified = personService.verifyRecaptcha(capResponse);
        if (verified == false) {

            return new ResponseEntity<>(new TokenDto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);

        }


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpHeaders headers = new HttpHeaders();
        UserDetails details = userDetailsService.loadUserByUsername(loginDto.getUsername());
        if (details.getAuthorities().toString().contains("USER")) {
            if (date.before(Time.getQuizDate(HOURS_LOGIN_START, MINUTES_LOGIN_START)) || date.after(Time.getQuizDate(HOURS_LOGIN_END, MINUTES_LOGIN_END))) {
                System.out.println("Time is problem");

                throw new BadRequestException("Time for login  expired. See you next time. :)");
            }

        }

        if (details.getAuthorities().toString().contains("BLOCKED")) {
            throw new RuntimeException("This user is blocked.");
        }


        String authToken = tokenUtils.generateToken(details);
        headers.add("X-Auth-Token", authToken);

        TokenDto tokenDto = new TokenDto();
        String role = details.getAuthorities().toString().replaceAll("^.|.$", "");
        tokenDto.setRole(role);
        tokenDto.setToken(authToken);

        return new ResponseEntity<>(tokenDto, headers, HttpStatus.OK);

    }


}
