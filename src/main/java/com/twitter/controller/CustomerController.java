package com.twitter.controller;



import com.twitter.exception.ResourceNotFoundException;
import com.twitter.model.Customer;
import com.twitter.model.CustomerUI;
import com.twitter.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerRepository repository;


    @GetMapping("/customer/get/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found" +
                "for this id:: " + id));
        return ResponseEntity.ok().body(customer);
    }


    @PutMapping("/customer/update/{id}")
    public String updateCustomer(@PathVariable(value = "id") Long id, @RequestBody Customer c) throws ResourceNotFoundException {
        Customer customers = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        customers.setFirstName(c.getFirstName());
        customers.setLastName(c.getLastName());


        return this.repository.save(customers).toString();
    }

    @PostMapping("/customer/bulkcreate")
    public String bulkcreate() {
        // save a single Customer
        repository.save(new Customer("Rajesh", "Bhojwani"));

        // save a list of Customer
        repository.saveAll(Arrays.asList(new Customer("Salim", "Khan")
                , new Customer("Rajesh", "Parihar")
                , new Customer("Rahul", "Dravid")
                , new Customer("Dharmendra", "Bhojwani")));

        return "Customer are created";
    }

    @PostMapping("/customer/create")
    public String create(@RequestBody CustomerUI c) {
        // save a single Customer
        repository.save(new Customer(c.getFirstName(), c.getLastName()));

        return "Customer is created";
    }


        @DeleteMapping("/customer/delete/{id}")
        public Map<String, Boolean> delete (@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
            Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));

            this.repository.delete(customer);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);

            return response;
        }


        @GetMapping("/customer/findall")
        public List<CustomerUI> findAll () {

            List<Customer> customers = repository.findAll();
            List<CustomerUI> customerUI = new ArrayList<>();

            for (Customer customer : customers) {
                customerUI.add(new CustomerUI(customer.getFirstName(), customer.getLastName()));
            }

            return customerUI;
        }

        @RequestMapping("/customer/search/{id}")
        public String search ( @PathVariable long id){
            String Customer = repository.findById(id).toString();
            return Customer;
        }

        @GetMapping("/customer/searchbyfirstname/{firstname}")
        public List<CustomerUI> fetchDataByFirstName (@PathVariable String firstname){

            List<Customer> customers = repository.findByFirstName(firstname);
            List<CustomerUI> customerUI = new ArrayList<>();

            for (Customer customer : customers) {
                customerUI.add(new CustomerUI(customer.getFirstName(), customer.getLastName()));
            }

            return customerUI;
        }

        @RequestMapping("/customer/searchbylastname/{lastname}")
        public List<CustomerUI> fetchDataByLastName (@PathVariable String lastname){

            List<Customer> customers = repository.findByLastName(lastname);
            List<CustomerUI> customerUI = new ArrayList<>();

            for (Customer customer : customers) {
                customerUI.add(new CustomerUI(customer.getFirstName(), customer.getLastName()));
            }

            return customerUI;
        }

        @RequestMapping("/customer/searchname/{name}")
        public List<CustomerUI> fetchDataByConstraining (@PathVariable String name){

            List<Customer> customers = repository.fetchUsers(name);
            System.out.println(customers.size());
            List<CustomerUI> customerUI = new ArrayList<>();

            for (Customer customer : customers) {
                customerUI.add(new CustomerUI(customer.getFirstName(), customer.getLastName()));
            }

            return customerUI;
        }

        @RequestMapping("/customer/searchname2/{name}")
        public List<CustomerUI> fetchDataByConstraining2 (@PathVariable String name){

            List<Customer> customers = repository.findByFirstNameLike(name);
            System.out.println(customers.size());
            List<CustomerUI> customerUI = new ArrayList<>();

            for (Customer customer : customers) {
                customerUI.add(new CustomerUI(customer.getFirstName(), customer.getLastName()));
            }

            return customerUI;
        }
    }
