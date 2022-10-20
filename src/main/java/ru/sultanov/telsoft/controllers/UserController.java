package ru.sultanov.telsoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sultanov.telsoft.dto.PersonDTO;
import ru.sultanov.telsoft.entity.Person;
import ru.sultanov.telsoft.facade.UserFacade;
import ru.sultanov.telsoft.services.UserService;
import ru.sultanov.telsoft.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin //что это? чтобы небыло ошибки когда будем вызывать с локальных серверов
public class UserController {


    private final UserService userService;
    private final UserFacade userFacade;
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public UserController(UserService userService, UserFacade userFacade, ResponseErrorValidation responseErrorValidation) {
        this.userService = userService;
        this.userFacade = userFacade;
        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/")
    public ResponseEntity<PersonDTO> getCurrentUser(Principal principal) {
        Person person = userService.getCurrentPerson(principal);
        PersonDTO personDTO = userFacade.personToPersonDTO(person);

        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PersonDTO> getUserProfile(@PathVariable("userId") String userId) {
        Person person = userService.getPersonById(Long.parseLong(userId));
        PersonDTO personDTO = userFacade.personToPersonDTO(person);

        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Person person = userService.updatePerson(personDTO, principal);
        PersonDTO personUpdated = userFacade.personToPersonDTO(person);

        return new ResponseEntity<>(personUpdated, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonDTO>> getAllUsers() {
        List<PersonDTO> allUsers = userService.allPerson().stream().map(userFacade::personToPersonDTO).toList();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
