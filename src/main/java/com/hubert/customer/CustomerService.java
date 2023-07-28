package com.hubert.customer;

import com.hubert.exception.ResourceDuplicateException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer>  fetchAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.email();

        if(customerDao.checkIfCustomerEmailExist(email)) {
            throw new ResourceDuplicateException("Email already taken!");
        }

        Customer customer = new Customer(
                customerRegistrationRequest.username(),
                customerRegistrationRequest.email()
        );

        customerDao.addCustomer(customer);
    }
}
