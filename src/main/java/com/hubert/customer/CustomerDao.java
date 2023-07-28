package com.hubert.customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    void addCustomer(Customer customer);
    boolean checkIfCustomerEmailExist(String email);
}
