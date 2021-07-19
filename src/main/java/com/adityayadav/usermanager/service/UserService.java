package com.adityayadav.usermanager.service;

import com.adityayadav.usermanager.exception.UserException;
import com.adityayadav.usermanager.model.User;
import com.adityayadav.usermanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User by Id " + id + " was not found!"));
    }

    public List<User> findUsersByFirstName(String firstName) {
        return userRepository.findAllByFirstName(firstName).orElseThrow(() -> new UserException("User by firstname: " + firstName + " was not found!"));
    }

    public List<User> findUsersByLastName(String lastName) {
        return userRepository.findAllByLastName(lastName).orElseThrow(() -> new UserException("User by lastname: " + lastName + " was not found!"));
    }

    public List<User> findUsersByPincode(int pincode) {
        return userRepository.findAllByPincode(pincode).orElseThrow(() -> new UserException("User by pincode: " + pincode + " was not found!"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllOrderByDoj() {
        return userRepository.findByOrderByDoj(Sort.by("doj"));
    }

    public List<User> findAllOrderByDob() {
        return userRepository.findByOrderByDob(Sort.by("dob"));
    }
}
