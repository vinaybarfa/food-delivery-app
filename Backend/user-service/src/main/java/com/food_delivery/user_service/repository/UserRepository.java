package com.food_delivery.user_service.repository;

import com.food_delivery.user_service.entity.Role;
import com.food_delivery.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByIsAvailableAndFullName(boolean isAvailable, String fullName);

    List<User> findByFullName(String name);

    List<User> findByIdGreaterThanEqualAndRole(Integer id, Role role);

    @Query(value = "SELECT * FROM user WHERE id % 2= 0", nativeQuery = true)
    List<User> findUsersWithEvenId();

    User findById(Integer id);
}