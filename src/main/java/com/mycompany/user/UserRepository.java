package com.mycompany.user;

import org.springframework.data.repository.CrudRepository;

//Persists data from the entity class to DB
public interface UserRepository extends CrudRepository<User, Integer> {
}
