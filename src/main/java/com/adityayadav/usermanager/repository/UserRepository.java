package com.adityayadav.usermanager.repository;

import com.adityayadav.usermanager.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<List<User>> findAllByFirstName(String firstName);

    Optional<List<User>> findAllByLastName(String lastName);

    Optional<List<User>> findAllByPincode(int pincode);

    List<User> findByOrderByDob(Sort sort);

    List<User> findByOrderByDoj(Sort sort);
}
