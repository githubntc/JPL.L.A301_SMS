package fa.training.dao;

import fa.training.common.Database;
import fa.training.entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

interface OrderDAO {
    public int create(Order order) throws SQLException;
    public List<Order> retrieve();
    public int updateDate(int order_id, int customer_id, int employee_id, double total) throws SQLException;
    public int delete(int order_id) throws SQLException;
}
