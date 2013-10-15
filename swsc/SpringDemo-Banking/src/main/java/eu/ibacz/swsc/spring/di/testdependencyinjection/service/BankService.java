package eu.ibacz.swsc.spring.di.testdependencyinjection.service;


import eu.ibacz.swsc.spring.di.testdependencyinjection.dto.Customer;
import java.util.List;


public interface BankService {
    List<Customer> getAllCustomers();
    Customer createNewCustomer(String firstname, String lastname);
}
