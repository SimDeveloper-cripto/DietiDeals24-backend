package com.exam.ingsw.dietideals24.repository;

import java.util.Optional;
import com.exam.ingsw.dietideals24.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    Optional<User> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.userId = :userId")
    Optional<User> findByIdAndEmail(@Param("userId") Integer userId, @Param("email") String email);
}