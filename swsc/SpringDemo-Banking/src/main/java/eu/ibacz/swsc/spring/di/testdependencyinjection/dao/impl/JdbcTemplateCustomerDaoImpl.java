/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ibacz.swsc.spring.di.testdependencyinjection.dao.impl;

import eu.ibacz.swsc.spring.di.testdependencyinjection.dao.CustomerDao;
import eu.ibacz.swsc.spring.di.testdependencyinjection.dto.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author xhrubes
 */
public class JdbcTemplateCustomerDaoImpl implements CustomerDao {
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<Customer> findAll() {
        List<Customer> customerList =
                jdbcTemplate.query("select id, firstname, lastname from customer",
                new BeanPropertyRowMapper<Customer>(Customer.class));

        return customerList;
    }
    
    public void save(final Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement stmt = connection.prepareStatement("insert into customer (firstname, lastname) values (?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, customer.getFirstname());
                stmt.setString(2, customer.getLastname());
                return stmt;
            }
        }, keyHolder);
        customer.setId(keyHolder.getKey().longValue());
    }
}
