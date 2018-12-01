package sese.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sese.entities.Customer;
import sese.responses.CustomerResponse;
import sese.services.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/kunde")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAll();
    }

    @PostMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public void addCustomer(@RequestBody Customer customer) {
        System.out.println(customer);
        customerService.addNewCustomer(customer);
    }
}