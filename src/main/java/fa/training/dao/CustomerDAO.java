package fa.training.dao;

import fa.training.common.Database;
import fa.training.entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

interface CustomerDAO {
    public int create(String customer_name) throws SQLException;
    public List<Customer> retrieve();
    public int update(int customer_id, String customer_name) throws SQLException;
    public int delete(int customer_id) throws SQLException;
}
