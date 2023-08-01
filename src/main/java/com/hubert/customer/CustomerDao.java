package com.hubert.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    void addCustomer(Customer customer);
    boolean checkIfCustomerEmailExist(String email);
    Optional<Customer> selectCustomerByUsername(String username);
}
