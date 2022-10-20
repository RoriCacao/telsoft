package ru.sultanov.telsoft.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sultanov.telsoft.entity.Person;
import ru.sultanov.telsoft.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Person person = userRepository.findPersonByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found "));


        return build(person);
    }

    public Person loadUserById(Long id) {
        return userRepository.findPersonById(id).orElse(null);
    }

    public static Person build(Person person) {
        List<GrantedAuthority> authorities = person.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new Person(person.getId(), person.getUsername(), person.getEmail()
                , person.getPassword()
                , authorities);
    }
}
