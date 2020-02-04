package rs.ac.uns.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.quiz.dto.LoginDto;
import rs.ac.uns.quiz.dto.RecaptchaResponse;
import rs.ac.uns.quiz.dto.UserDto;
import rs.ac.uns.quiz.exception.AlreadyExistsException;
import rs.ac.uns.quiz.exception.NotFoundException;
import rs.ac.uns.quiz.model.Person;
import rs.ac.uns.quiz.model.Role;
import rs.ac.uns.quiz.repository.PersonRepository;
import rs.ac.uns.quiz.security.TokenUtils;
import rs.ac.uns.quiz.service.PersonService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {


    @Value("${google.recaptcha.secret.key}")
    private String secretKey;

    private String url = "https://www.google.com/recaptcha/api/siteverify";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto getLoggedInUser(String authToken) {

        String username = this.tokenUtils.getUsernameFromToken(authToken);
        return personRepository.findPersonByUsername(username)
                .map(person -> personToUserDto(person))
                .orElseThrow(() -> new NotFoundException(String.format("User with username %s not found.", username)));

    }

    @Override
    public boolean register(UserDto userDto) {
        if (personRepository.findPersonByUsername(userDto.getUsername()).isPresent()) {

            throw new AlreadyExistsException(String.format("User with username %s already exists.", userDto.getUsername()));
        } else {
            Person p = new Person();
            p.setRole(Role.USER);
            personRepository.save(userDtoToPerson(p, userDto));
            return true;
        }

    }

    @Override
    public List<UserDto> getAllUsers() {

        List<UserDto> userDtos = personRepository.findAllByRoleNot(Role.ADMIN).get().stream().map(person -> personToUserDto(person)).collect(Collectors.toList());
        System.out.println(userDtos.size());
        return userDtos;
    }

    @Override
    public boolean verifyRecaptcha(String recaptcha) {
        System.out.println("key is " + secretKey);
        MultiValueMap param = new LinkedMultiValueMap<>();
        param.add("secret", secretKey);
        param.add("response", recaptcha);
        RecaptchaResponse recaptchaResponse = this.restTemplate.postForObject(url, param, RecaptchaResponse.class);
        System.out.println(recaptchaResponse.isSuccess());

        if (recaptchaResponse.isSuccess()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean editPassword(LoginDto editDto) {
        Person person = personRepository.findPersonByUsername(editDto.getUsername())
                .orElseThrow(() -> new NotFoundException(String.format("User with username %s not found.", editDto.getUsername())));

        person.setPassword(passwordEncoder.encode(editDto.getPassword()));
        personRepository.save(person);
        return true;
    }

    @Override
    public UserDto editData(UserDto userDto) {
        Person person = personRepository.findPersonByUsername(userDto.getUsername())
                .orElseThrow(() -> new NotFoundException(String.format("User with username %s not found.", userDto.getUsername())));
        personRepository.save(userDtoToPerson(person, userDto));
        return userDto;


    }

    @Override
    public UserDto userByUsername(String username) {
        Person person = personRepository.findPersonByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username %s not found.", username)));
        return personToUserDto(person);
    }

    @Override
    public boolean blockUser(String username) {
        Person p = personRepository.findPersonByUsername(username).orElseThrow(() -> new NotFoundException(String.format("User with username %s not found.", username)));
        p.setRole(Role.BLOCKEDUSER);
        personRepository.save(p);

        return true;
    }

    @Override
    public boolean unblockUser(String username) {
        Person p = personRepository.findPersonByUsername(username).orElseThrow(() -> new NotFoundException(String.format("User with username %s not found.", username)));
        p.setRole(Role.USER);
        personRepository.save(p);
        return true;
    }


    private UserDto personToUserDto(Person p) {
        UserDto userDto = new UserDto();
        userDto.setName(p.getName());
        userDto.setRole(p.getRole().toString());
        System.out.println(userDto.getRole());
        userDto.setCountry(p.getCountry());
        userDto.setEmail(p.getEmail());
        userDto.setUsername(p.getUsername());

        return userDto;

    }

    private Person userDtoToPerson(Person p, UserDto userDto) {

        p.setCountry(userDto.getCountry());
        p.setEmail(userDto.getEmail());
        p.setName(userDto.getName());
        if (userDto.getPassword() != null) {
            p.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if (userDto.getUsername() != null) {
            p.setUsername(userDto.getUsername());
        }

        return p;

    }
}
