package com.ZestAssignment.EmployeeManagment.Repository;

import com.ZestAssignment.EmployeeManagment.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(UUID id);

}
