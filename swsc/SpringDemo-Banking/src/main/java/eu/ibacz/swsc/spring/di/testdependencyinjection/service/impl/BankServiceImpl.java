package eu.ibacz.swsc.spring.di.testdependencyinjection.service.impl;

import eu.ibacz.swsc.spring.di.testdependencyinjection.dao.CustomerDao;
import eu.ibacz.swsc.spring.di.testdependencyinjection.dao.impl.PureJdbcCustomerDaoImpl;
import eu.ibacz.swsc.spring.di.testdependencyinjection.dto.Customer;
import eu.ibacz.swsc.spring.di.testdependencyinjection.service.BankService;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BankServiceImpl implements BankService {
    
    private CustomerDao customerDao;
    
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    public Customer createNewCustomer(String firstname, String lastname) {
        Customer customer = new Customer();
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customerDao.save(customer);
        return customer;
    }
  
    
}
