package TP1.storeApplication.service;

import TP1.storeApplication.entity.Customer;
import TP1.storeApplication.entity.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService( CustomerRepository customerRepository ){
        this.customerRepository = customerRepository;
    }

    public void createCustomer(String email, String password, String firstName, String lastName){
        var customer = new Customer(email, password, firstName, lastName);
        customerRepository.save(customer);
    }

    public Customer connect(String email, String password) {
        Iterable<Customer> customers = readAllCustomers();
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    public Iterable<Customer> readAllCustomers(){
        return customerRepository.findAll();
    }
}
