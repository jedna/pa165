package eu.ibacz.swsc.spring.di.testdependencyinjection;

import eu.ibacz.swsc.spring.di.testdependencyinjection.dto.Customer;
import eu.ibacz.swsc.spring.di.testdependencyinjection.service.BankService;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;


public class BankServiceTest extends AbstractIntegrationTest {
    
    @Autowired
    private BankService bankService;
    
    @Test
    public void testCreateNewCustomer() {
        Customer customer = bankService.createNewCustomer("Firstname", "Lastname");
        assertNotNull(customer.getId());
    }
    
    @Test
    public void testGetAllCustomers() {
        bankService.createNewCustomer("Firstname1", "Lastname1");
        bankService.createNewCustomer("Firstname2", "Lastname2");
        
        List<Customer> customersList = bankService.getAllCustomers();
        assertEquals(2, customersList.size());
    }
    
}
