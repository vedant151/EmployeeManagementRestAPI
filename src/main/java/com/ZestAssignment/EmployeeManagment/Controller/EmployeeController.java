package com.ZestAssignment.EmployeeManagment.Controller;

import com.ZestAssignment.EmployeeManagment.DTO.EmployeeDTO;
import com.ZestAssignment.EmployeeManagment.DTO.Mapper;
import com.ZestAssignment.EmployeeManagment.Entity.EmployeeEntity;
import com.ZestAssignment.EmployeeManagment.Exception.EmployeeException;
import com.ZestAssignment.EmployeeManagment.Repository.EmployeeRepository;
import com.ZestAssignment.EmployeeManagment.Repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Mapper mapper;

    @GetMapping("/api/employee")
    public ResponseEntity<?> getEmployee(){

        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();

        // convert userEntity to userDTO
        List<EmployeeDTO> employeeResponseDTOList = employeeEntityList.stream().map(entity -> {
            return mapper.convertEmployeeEntityToEmployeeDTO(entity);
        }).toList();

        return new ResponseEntity<>(employeeResponseDTOList, HttpStatus.OK);
    }

    @GetMapping("/api/employee/{employeeEmail}")
    public ResponseEntity<?> getEmployeeByEmail(@PathVariable String employeeEmail){

        EmployeeEntity currEmployeeEntity = employeeRepository.findByEmail(employeeEmail).get();

        if(currEmployeeEntity != null){
            EmployeeDTO employeeResponseDTO = mapper.convertEmployeeEntityToEmployeeDTO(currEmployeeEntity);
            return new ResponseEntity<>(employeeResponseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>("Employee Not found", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/api/employee")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO){

        // save the hashed password
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                                                        .name(employeeDTO.getName())
                                                        .email(employeeDTO.getEmail())
                                                        .department(employeeDTO.getDepartment())
                                                        .position(employeeDTO.getPosition())
                                                        .salary(employeeDTO.getSalary())
                                                        .dateOfJoining(employeeDTO.getDateOfJoining())
                                                        .build();

        try {
            employeeRepository.save(employeeEntity);
        }catch (Exception ex){
            throw new EmployeeException("Something went wrong");
        }

        return new ResponseEntity<>("Employee Added Successfully !!", HttpStatus.OK);

    }

    @DeleteMapping("/api/employee/{employeeName}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String employeeName){
        EmployeeEntity employeeEntity = employeeRepository.findByEmail(employeeName).get();

        try{
            employeeRepository.delete(employeeEntity);
        }catch (Exception ex){
            throw new RuntimeException("User not found !!");
        }

        return new ResponseEntity<>("Employee Deleted Successfully !!", HttpStatus.OK);
    }
}
