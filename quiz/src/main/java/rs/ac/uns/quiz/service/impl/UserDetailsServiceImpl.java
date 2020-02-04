package rs.ac.uns.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import rs.ac.uns.quiz.exception.NotFoundException;
import rs.ac.uns.quiz.model.Person;
import rs.ac.uns.quiz.repository.PersonRepository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PersonRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Person user=userRepository.findPersonByUsername(username).orElseThrow(() -> new NotFoundException(String.format("User with username %s not found", username)));
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(grantedAuthority);
        User.UserBuilder builder = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(authorities);
        return builder.build();
    }
}
