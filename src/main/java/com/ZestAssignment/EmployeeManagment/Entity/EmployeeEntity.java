package com.ZestAssignment.EmployeeManagment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "EmployeeEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID employeeId;

    @Column(name = "name")
    @NotBlank(message = "Name should not be null")
    private String name;

    @Column(name = "email")
    @NotBlank(message = "Email should not be null")
    private String email;

    @Column(name = "department")
    @NotBlank(message = "Department should not be null")
    private String department;

    @Column(name = "position")
    @NotBlank(message = "Position should not be null")
    private String position;

    @Column(name = "salary")
    @NotBlank(message = "Salary should not be null")
    private String salary;

    @Column(name = "dateOfJoining")
    @NotBlank(message = "Date Of Joining should not be null")
    private String dateOfJoining;
}
