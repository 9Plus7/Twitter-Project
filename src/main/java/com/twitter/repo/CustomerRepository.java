package com.twitter.repo;

import com.twitter.model.Customer;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFirstName(String FirstName);
    List<Customer> findByLastName(String LastName);
    List<Customer> findAll();
    List<Customer> findByFirstNameLike(String name);


    @Query("SELECT a FROM Customer a WHERE a.firstName like %:name% or a.lastName like %:name%")
    List<Customer> fetchUsers(@Param("name") String name);
}
