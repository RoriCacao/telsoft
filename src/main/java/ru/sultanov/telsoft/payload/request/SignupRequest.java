package ru.sultanov.telsoft.payload.request;

import lombok.Data;
import ru.sultanov.telsoft.annotations.PasswordMatches;
import ru.sultanov.telsoft.annotations.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "It should have email format ")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "please enter name")
    private String firstname;
    @NotEmpty(message = "please enter lastname")
    private String lastname;
    @NotEmpty(message = "please enter username")
    private String username;
    @NotEmpty(message = "please enter password")
    @Size(min = 6)
    private String password;
    private String confirmPassword;

}
