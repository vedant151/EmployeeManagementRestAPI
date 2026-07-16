package com.ZestAssignment.EmployeeManagment.DTO;

import com.ZestAssignment.EmployeeManagment.Domain.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "role")
    private Role role;


}
