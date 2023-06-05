package fa.training.dao;

import fa.training.common.Database;
import fa.training.entities.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO{
    Database database;
    public OrderDAOImpl(){
        database = new Database();
    }
    @Override
    public int create(Order order) throws SQLException {
        Connection conn = database.getConn();
        int count;

        try{
            conn.setAutoCommit(false);
            String sql = "INSERT INTO `orders`(`order_date`, `customer_id`, `employee_id`, `total`) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
            java.sql.Date date = new Date(order.getOrderDate().getTime());
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setInt(3, order.getEmployeeId());
            preparedStatement.setDouble(4, order.getTotal());
            count = preparedStatement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            return count;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Don't exist customer or employee!!!");
            return 0;
        }catch (SQLException e){
            conn.rollback();
            return 0;
        }
    }
    @Override
    public List<Order> retrieve(){
        List<Order> list = new ArrayList<>();
        Order order;

        try{
            String sql = "SELECT * FROM `Orders`";
            Statement statement = database.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                order = new Order(resultSet.getInt("order_id"),
                        (resultSet.getDate("order_date")),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("employee_id"),
                        resultSet.getDouble("total"));
                list.add(order);
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }
    @Override
    public int updateDate(int order_id, int customer_id, int employee_id, double total) throws SQLException{
        Connection conn = database.getConn();
        int count = 0;

        try{
            conn.setAutoCommit(false);

            if((employee_id < 0) && (total < 0)){
                String sql = "UPDATE `Orders` SET `customer_id` = ? WHERE `order_id` = ?";
                PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
                preparedStatement.setInt(1, customer_id);
                preparedStatement.setInt(2,order_id);
                count = preparedStatement.executeUpdate();
            }

            if((customer_id < 0) && (total < 0)){
                String sql = "UPDATE `Orders` SET `employee` = ? WHERE `rrder_id` = ? ";
                PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
                preparedStatement.setInt(1,employee_id);
                preparedStatement.setInt(2,order_id);
                count = preparedStatement.executeUpdate();
            }

            if((customer_id < 0) && (employee_id < 0)){
                String sql = "UPDATE `Orders` SET `total` = ? WHERE `order_id` = ? ";
                PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
                preparedStatement.setDouble(1, total);
                preparedStatement.setInt(2,order_id);
                count = preparedStatement.executeUpdate();
            }

            conn.commit();
            conn.setAutoCommit(true);

            return count;
        }catch(SQLException e){
            conn.rollback();
            return count;
        }
    }
    @Override
    public int delete(int order_id) throws SQLException{
        Connection conn = database.getConn();
        int count;

        try{
            conn.setAutoCommit(false);
            String sql = "DELETE FROM `Orders` WHERE `order_id` = ?";
            PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
            preparedStatement.setInt(1,order_id);
            count = preparedStatement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);

            return count;
        }catch(SQLException e){
            conn.rollback();

            return 0;
        }
    }
}
