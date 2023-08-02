package com.hubert.customer;

import com.hubert.jwt.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final  CustomerService customerService;
    private final JWTUtil jwtUtil;

    public CustomerController(CustomerService customerService, JWTUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.fetchAllCustomers();
    }

    @GetMapping("{customerId}")
    public CustomerDTO getCustomer(@PathVariable("customerId") Integer customerId) {
        return customerService.findCustomerById(customerId);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        customerService.addCustomer(customerRegistrationRequest);

        //attach the token to the headers
        String token = jwtUtil.issueToken(customerRegistrationRequest.username(), "ROLE_USER");
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
    }
}
