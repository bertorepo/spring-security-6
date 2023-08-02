package com.hubert.customer;

import com.hubert.exception.ResourceDuplicateException;
import com.hubert.exception.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDTOMapper customerDTOMapper;


    public CustomerService(CustomerDao customerDao, PasswordEncoder passwordEncoder, CustomerDTOMapper customerDTOMapper) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
        this.customerDTOMapper = customerDTOMapper;
    }

    public List<CustomerDTO> fetchAllCustomers() {
        return customerDao.getAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.email();

        if (customerDao.checkIfCustomerEmailExist(email)) {
            throw new ResourceDuplicateException("Email already taken!");
        }

        Customer customer = new Customer(
                customerRegistrationRequest.username(),
                customerRegistrationRequest.email(),
                passwordEncoder.encode(customerRegistrationRequest.password())
        );

        customerDao.addCustomer(customer);
    }

    public CustomerDTO findCustomerById(Integer customerId) {
        return customerDao.getCustomer(customerId)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id %s not found!".formatted(customerId)));
    }
}
