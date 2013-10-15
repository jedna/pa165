package eu.ibacz.swsc.spring.di.testdependencyinjection.dao;

import eu.ibacz.swsc.spring.di.testdependencyinjection.dto.Customer;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xhrubes
 */
public interface CustomerDao {
    public List<Customer> findAll();
    public void save(Customer customer);
}
