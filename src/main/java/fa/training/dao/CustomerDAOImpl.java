package fa.training.dao;

import fa.training.common.Database;
import fa.training.entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO{
    Database database;
    public CustomerDAOImpl(){
        this.database = new Database();
    }

    @Override
    public int create(String customer_name) throws SQLException {
        Connection conn = database.getConn();
        int count;
        try{

            String sql = "INSERT INTO `customer`(`customer_name`) VALUES (?)";
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, customer_name);
            count = preparedStatement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            return count;
        }
        catch(SQLException e){
            conn.rollback();
            return 0;
        }
    }
    public List<Customer> retrieve(){
        List<Customer> list = new ArrayList<>();
        Customer customer;
        try{
            String sql = "SELECT * FROM `customer`";
            Statement statement = database.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                customer = new Customer(Integer.parseInt(resultSet.getString("customer_id"))
                        ,resultSet.getString("customer_name"));
                list.add(customer);
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public int update(int customer_id, String customer_name) throws SQLException {
        Connection conn = database.getConn();
        int count;
        try{
            conn.setAutoCommit(false);
            String sql = "UPDATE `customer` SET `customer_name` = ? WHERE `customer_id` = ?";
            PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
            preparedStatement.setString(1, customer_name);
            preparedStatement.setInt(2,customer_id);
            count = preparedStatement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            return count;
        }catch(SQLException e){
            conn.rollback();
            return 0;
        }

    }
    public int delete(int customer_id) throws SQLException {
        Connection conn = database.getConn();
        int count;
        try{
            conn.setAutoCommit(false);
            String sql = "DELETE FROM `customer` WHERE `customer_id` = ?";
            PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
            preparedStatement.setInt(1,customer_id);
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
