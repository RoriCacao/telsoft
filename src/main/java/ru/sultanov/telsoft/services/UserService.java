package ru.sultanov.telsoft.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sultanov.telsoft.dto.PersonDTO;
import ru.sultanov.telsoft.entity.Person;
import ru.sultanov.telsoft.entity.enums.ERole;
import ru.sultanov.telsoft.exceptions.UserExistException;
import ru.sultanov.telsoft.payload.request.SignupRequest;
import ru.sultanov.telsoft.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person createPerson(SignupRequest userIn){
        Person person = new Person();
        person.setEmail(userIn.getEmail());
        person.setFirstname(userIn.getFirstname());
        person.setLastname(userIn.getLastname());
        person.setUsername(userIn.getUsername());
        person.setPassword(passwordEncoder.encode(userIn.getPassword()));
        person.getRole().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving User{}",userIn.getEmail());
            return userRepository.save(person);
        } catch (Exception e){
            LOG.error("Error during registration. {}",e.getMessage());
            throw  new UserExistException("The user "+person.getUsername()
                    + "already exist. Please check credentials");
        }
    }

    public Person updatePerson (PersonDTO personDTO, Principal principal){
        Person person = getPersonByPrincipal(principal);
        person.setFirstname(personDTO.getFirstname());
        person.setLastname(personDTO.getLastname());

        return userRepository.save(person);
    }

    public Person getCurrentPerson(Principal principal){
        return getPersonByPrincipal(principal);
    }

    private Person getPersonByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findPersonByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username "+username+" not found"));
    }

    public Person getPersonById(long parseLong) {
        return userRepository.findPersonById(parseLong)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    public List<Person> allPerson(){
        return userRepository.findAll();
    }
}
