package com.adityayadav.usermanager;

import com.adityayadav.usermanager.exception.UserException;
import com.adityayadav.usermanager.model.User;
import com.adityayadav.usermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        try {
            User user = userService.findUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserException ue) {
            ue = new UserException("User by Id " + id + " was not found!");
            System.out.println(ue.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            User userById = userService.findUserById(id);
            userById.setFirstName(user.getFirstName());
            userById.setLastName(user.getLastName());
            userById.setEmailId(user.getEmailId());
            userById.setPincode(user.getPincode());
            userById.setDob(user.getDob());
            userById.setDoj(user.getDoj());
            User updateUser = userService.updateUser(userById);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/findallandsortby/{type}")
    public ResponseEntity<List<User>> getAllUsersAndSortByType(@PathVariable("type") String type) {
        if (type.equals("doj")) {
            List<User> users = userService.findAllOrderByDoj();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else if (type.equals("dob")) {
            List<User> users = userService.findAllOrderByDob();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }
}
