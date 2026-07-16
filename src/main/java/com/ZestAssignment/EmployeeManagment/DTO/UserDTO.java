package com.ZestAssignment.EmployeeManagment.DTO;

import com.ZestAssignment.EmployeeManagment.Domain.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @Id
    private UUID id;

    @NotBlank(message = "Email should not be valid")
    @Email(message = "Enter valid email")
    @JsonProperty(value = "email")
    private String email;


    @JsonProperty(value = "password")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String password;

    @JsonProperty(value = "role")
    private Role role;

}
