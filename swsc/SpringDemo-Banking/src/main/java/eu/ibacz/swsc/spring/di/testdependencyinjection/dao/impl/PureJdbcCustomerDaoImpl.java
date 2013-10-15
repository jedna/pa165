package eu.ibacz.swsc.spring.di.testdependencyinjection.dao.impl;


import eu.ibacz.swsc.spring.di.testdependencyinjection.dao.CustomerDao;
import eu.ibacz.swsc.spring.di.testdependencyinjection.dto.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

/**
 *
 * @author xhrubes
 */
@Repository
public class PureJdbcCustomerDaoImpl implements CustomerDao {

    private static final Logger LOG = Logger.getLogger(PureJdbcCustomerDaoImpl.class);
    private DataSource dataSource;

    @Required
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Customer> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Customer> customerList = new ArrayList<Customer>();
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            preparedStatement = connection.prepareStatement("select id, firstname, lastname from customer");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");

                Customer customer = new Customer();
                customer.setId(id);
                customer.setFirstname(firstname);
                customer.setLastname(lastname);

                customerList.add(customer);
            }

        } catch (SQLException ex) {
            throw new IllegalStateException("Error while acccessing DB", ex);
        } finally {
            releaseDbResources(resultSet, preparedStatement, connection);
        }
        return customerList;

    }

    public void save(Customer customer) {
        if (customer.getId() != null) {
            throw new IllegalArgumentException("Customer ID must be null but it's set to "+customer.getId());
        }
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            preparedStatement = connection.prepareStatement("insert into customer (firstname, lastname) values (?,?)", 
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getFirstname());
            preparedStatement.setString(2, customer.getLastname());
            
            preparedStatement.executeUpdate();
            
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();;
            long id = resultSet.getLong(1);
            customer.setId(id);
            
        } catch (SQLException e) {
            throw new IllegalStateException("Error while acccessing DB", e);
        } finally {
            releaseDbResources(resultSet, preparedStatement, connection);
        }
    }

    private void releaseDbResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOG.warn("Cannot close resultset", e);
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                LOG.warn("Cannot close preparedStatement", e);
            }
        }
        DataSourceUtils.releaseConnection(connection, dataSource);
    }
}
