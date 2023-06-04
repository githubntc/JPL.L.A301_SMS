package fa.training.dao;

import fa.training.common.Database;
import fa.training.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class ProductDAO {
    Database database;
    public ProductDAO(){
        database = new Database();
    }

    public int create(String product_name, double list_price) throws SQLException {
        Connection conn = database.getConn();
        int count;
        try{
            conn.setAutoCommit(false);
            String sql = "INSERT INTO `product`(`product_name`, `list_price`) VALUES ('?', ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, product_name);
            preparedStatement.setDouble(2, list_price);
            count = preparedStatement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            return count;
        }catch (SQLException e){
            conn.rollback();
            return 0;
        }
    }

    public List<Product> retrieve(){
        List<Product> list = new ArrayList<>();
        Product product;
        Connection conn = database.getConn();
        try{
            String sql = "SELECT * FROM `product`";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                product = new Product(resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getDouble("list_price"));
                list.add(product);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public int update(int product_id, String product_name, double list_price) throws SQLException{
        Connection conn = database.getConn();
        int count = 0;
        try{
            conn.setAutoCommit(false);
            if(list_price<0){
               String sql = "UPDATE `product` SET `product_name`='?' WHERE `product_id`= ?";
               PreparedStatement preparedStatement = conn.prepareStatement(sql);
               preparedStatement.setString(1, product_name);
               preparedStatement.setInt(2, product_id);
               count = preparedStatement.executeUpdate();
            }
            if(product_name.equals("")){
                String sql = "UPDATE `product` SET `list_price` = ? WHERE `product_id`= ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setDouble(1, list_price);
                preparedStatement.setInt(2, product_id);
                count = preparedStatement.executeUpdate();
            }
            conn.commit();
            conn.setAutoCommit(true);
            return count;
        }catch (SQLException e){
            conn.rollback();
            return 0;
        }
    }

    public int delete(int product_id) throws SQLException{
        Connection conn = database.getConn();
        int count;
        try {
            conn.setAutoCommit(false);
            String sql = "DELETE FROM `product` WHERE `product_id`= ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, product_id);
            count = preparedStatement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            return count;
        }catch (SQLException e){
            conn.rollback();
            return 0;
        }
    }
}
