package com.ZestAssignment.EmployeeManagment.Controller;

import com.ZestAssignment.EmployeeManagment.DTO.RoleDTO;
import com.ZestAssignment.EmployeeManagment.DTO.UserDTO;
import com.ZestAssignment.EmployeeManagment.Domain.Role;
import com.ZestAssignment.EmployeeManagment.Entity.UserEntity;
import com.ZestAssignment.EmployeeManagment.Repository.UserRepository;
import com.ZestAssignment.EmployeeManagment.Services.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/api/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserDTO currUserDetails, HttpServletRequest request, HttpServletResponse response){
        // check if user is present
        UserEntity currUser = userRepository.findByEmail(currUserDetails.getEmail()).orElse(null);

        if(currUser == null){
            return new ResponseEntity<>("Please signup first", HttpStatus.NOT_FOUND);
        }

        String token = JwtHelper.generateJWTToken(currUserDetails);
        currUser.setResetToken(token);
        currUser.setVerified(true);
        // save user with updated token
        userRepository.save(currUser);


        boolean passwordMatches = bCryptPasswordEncoder.matches(
                currUserDetails.getPassword(), currUser.getPassword()
        );

        if (!passwordMatches) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        currUserDetails.setRole(currUser.getRole());

        // set the updated token in header
        response.setHeader("Authorization", "Bearer " + token);

        return new ResponseEntity<>("LOGIN_SUCCESS", HttpStatus.OK);

    }


    @PostMapping("/api/signup")
    public ResponseEntity<?> signUpUser(@Valid @RequestBody UserDTO currUserDetails, HttpServletResponse response){
        // check if user is present
        try{
            UserEntity currUser = userRepository.findByEmail(currUserDetails.getEmail()).orElse(null);
            if(currUser != null){
                if(currUser.isVerified()){
                    return new ResponseEntity<>("User is Verified", HttpStatus.OK);
                }
                else{
                    // token generation and setting token value in db against that user
                    String token = JwtHelper.generateJWTToken(currUserDetails);
                    currUser.setResetToken(token);
                    return new ResponseEntity<>("VERIFICATION_RESENT", HttpStatus.OK);
                }
            }
            String hashedPassword = bCryptPasswordEncoder.encode(currUserDetails.getPassword());


            // token generation and setting token value in db against that user
            String token = JwtHelper.generateJWTToken(currUserDetails);

            // user not present create the user
            UserEntity newUser = UserEntity.builder()
                    .email(currUserDetails.getEmail())
                    .password(hashedPassword)
                    .resetToken(token)
                    .role(currUserDetails.getRole())
                    .build();

            // then save the user with role entry as null
            userRepository.save(newUser);

            response.setHeader("Authorization", token);

            return new ResponseEntity<>(token, HttpStatus.OK);

        }catch (Exception e){
            throw new RuntimeException("Failed to signup");
        }
    }


}
