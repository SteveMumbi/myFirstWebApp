package com.mycompany;

import com.mycompany.user.User;
import com.mycompany.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

//Test class
@DataJpaTest //Enables use of the JPA Test
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Tests against the actual DB
@Rollback(false) //Persists records in the DB
public class UserRepositoryTests {
    //autowiring a reference to the UserRepository
    @Autowired private UserRepository repo;

    //Test method - CREATE
    @Test
    public void testAddNew() {
        //Creating a user object and assigning data
        User user = new User();
        user.setEmail("samuelkaru@gmail.com");
        user.setFirstName("Samuel");
        user.setLastName("Karu");
        user.setPassword("SomberoPicasso");

        //persisting the object to DB and assigning the returned instance of the persisted object
        User savedUser = repo.save(user);

        //Asserting that the returned instance of the persisted object is not null, and it's id is greater than zero
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    //Test method - RETRIEVE
    @Test
    public void testListAll() {
        //Query user entries in the DB and assigning the iterable result of type User
        Iterable<User> users = repo.findAll();
        //Asserting the result has at-least one entry/iterations
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        //Listing all entries
        for(User user : users) {
            System.out.println(user);
        }
    }

    //Test method - UPDATE
    @Test
    public void testUpdate() {
        Integer userId = 1;
        //Fetching record to update from DB and assigning the container object of type User
        Optional<User> optionalUser = repo.findById(userId);
        //Assigning value of the container object to a User object
        User user = optionalUser.get();
        //Updating pwd
        user.setPassword("SierraPapa*1");
        //Committing to DB
        repo.save(user);

        //Fetching updated record from DB
        User updatedUser = repo.findById(userId).get();
        //Asserting that the pwd was changed
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("SierraPapa*1");
    }

    //Test method - RECEIVING USER BY ID
    @Test
    public void testGet() {
        Integer userId = 2;
        //Fetching record to update from DB and assigning the container object of type User
        Optional<User> optionalUser = repo.findById(userId);
        //Asserting that the container object has a value
        Assertions.assertThat(optionalUser).isPresent();
        //Printing the value
        System.out.println(optionalUser.get());
    }

    //Test method - DELETE
    @Test
    public void testDelete() {
        Integer userId = 2;
        //deleting record from DB
        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);
        //Asserting that the record is deleted
        Assertions.assertThat(optionalUser).isNotPresent();
    }


}
