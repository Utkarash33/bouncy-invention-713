package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.Exception.CustomerException;
import com.masai.model.Customer;
import com.masai.service.CustomerService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
    @Autowired
	private PasswordEncoder  passwordEncoder;
    
	
	
	@PostMapping("/registerCustomer")
	private ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer)throws CustomerException{
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		Customer c = customerService.registerCustomer(customer);
		return new ResponseEntity<Customer>(c, HttpStatus.CREATED);	
	}
	
	@PutMapping("/update/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable Integer customerId)throws CustomerException{
		return new ResponseEntity<Customer>(customerService.updateCustomer(customer, customerId),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/delete/{customerId}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable Integer customerId)throws CustomerException{
		return new ResponseEntity<Customer>(customerService.deleteCustomer(customerId),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerId)throws CustomerException{
		return new ResponseEntity<Customer>(customerService.viewCustomerById(customerId),HttpStatus.FOUND);
	}
}
