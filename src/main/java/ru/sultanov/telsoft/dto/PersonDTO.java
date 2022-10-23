package ru.sultanov.telsoft.dto;

import lombok.Data;
import ru.sultanov.telsoft.entity.enums.ERole;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

//Data transfer object сущности Person
@Data
public class PersonDTO {

    private Long id;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    private String username;

}
