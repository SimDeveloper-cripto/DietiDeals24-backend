package com.exam.ingsw.dietideals24.repository;

import com.exam.ingsw.dietideals24.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer>{

}