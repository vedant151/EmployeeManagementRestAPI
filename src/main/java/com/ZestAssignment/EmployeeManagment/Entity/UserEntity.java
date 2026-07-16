package com.ZestAssignment.EmployeeManagment.Entity;

import com.ZestAssignment.EmployeeManagment.Domain.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "UserEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "username")
    @NotBlank(message = "Email should not be null")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "isVerified")
    private boolean isVerified = false;

    @Column(name = "verification_token")
    private String verification_token;

    @Enumerated(EnumType.STRING)
    private Role role;

}
