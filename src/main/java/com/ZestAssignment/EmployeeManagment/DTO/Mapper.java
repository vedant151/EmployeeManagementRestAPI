package com.ZestAssignment.EmployeeManagment.DTO;

import com.ZestAssignment.EmployeeManagment.Entity.EmployeeEntity;
import com.ZestAssignment.EmployeeManagment.Entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public UserResponseDTO convertUserEntityToUserDTO(UserEntity userEntity){
        return UserResponseDTO.builder()
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }

    public EmployeeDTO convertEmployeeEntityToEmployeeDTO(EmployeeEntity employeeEntity){
        return EmployeeDTO.builder()
                .employeeId(employeeEntity.getEmployeeId())
                .email(employeeEntity.getEmail())
                .name(employeeEntity.getName())
                .department(employeeEntity.getDepartment())
                .position(employeeEntity.getPosition())
                .salary(employeeEntity.getSalary())
                .dateOfJoining(employeeEntity.getDateOfJoining())
                .build();
    }
}
