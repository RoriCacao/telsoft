package ru.sultanov.telsoft.facade;

import org.springframework.stereotype.Component;
import ru.sultanov.telsoft.dto.PersonDTO;
import ru.sultanov.telsoft.entity.Person;

//Конвертация класса PersonDTO в Person
@Component
public class UserFacade {

    public PersonDTO personToPersonDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setFirstname(person.getFirstname());
        personDTO.setLastname(person.getLastname());
        personDTO.setUsername(person.getUsername());
        return personDTO;
    }

}
