package fa.training.dao;

import fa.training.common.Database;
import fa.training.entities.LineItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineItemDAOImpl implements LineItemDAO{
    Database database;
    public LineItemDAOImpl(){
        database = new Database();
    }

    public int create(int order_id, int product_id, int quantity, double price) throws SQLException {
        Connection conn = database.getConn();
        int count;
        try{
            conn.setAutoCommit(false);
            String sql = "INSERT INTO `lineitem`(`order_id`, `product_id`, `quantity`, `price`) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,order_id);
            preparedStatement.setInt(2,product_id);
            preparedStatement.setInt(3,quantity);
            preparedStatement.setDouble(4,price);
            count = preparedStatement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);

            return count;
        }catch (SQLException e){
            conn.rollback();

            return 0;
        }
    }
    public List<LineItem> retrieve(){
        List<LineItem> list = new ArrayList<>();
        LineItem lineItem;
        try{
            String sql = "SELECT * FROM `LineItem";
            Connection conn = database.getConn();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                lineItem = new LineItem(resultSet.getInt("order_id"),resultSet.getInt("product_id"),
                        resultSet.getInt("quantity"), resultSet.getDouble("price"));
                list.add(lineItem);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }
    public int update(int order_id, int product_id, int quantity, double price) throws SQLException{
        Connection conn = database.getConn();
        int count = 0;
        try{
            conn.setAutoCommit(false);
            if(price < 0){
                String sql = "UPDATE `lineitem` SET `quantity` = ? WHERE `order_id` = ?,`product_id` = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, quantity);
                preparedStatement.setInt(2, order_id);
                preparedStatement.setInt(3, product_id);
                count = preparedStatement.executeUpdate();
            }
            if(quantity < 0){
                String sql = "UPDATE `lineitem` SET `price` = ? WHERE `order_id` = ?,`product_id` = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setDouble(1, price);
                preparedStatement.setInt(2, order_id);
                preparedStatement.setInt(3, product_id);
                count = preparedStatement.executeUpdate();
            }
            conn.commit();
            conn.setAutoCommit(true);

            return count;
        }catch (SQLException e){
            conn.rollback();
            return count;
        }
    }
    public int delete(int order_id, int product_id) throws SQLException{
        Connection conn = database.getConn();
        int count;

        try{
            conn.setAutoCommit(false);
            String sql = "DELETE FROM `LineItem` WHERE `order_id` = ? AND `product_id` = ?";
            PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
            preparedStatement.setInt(1,order_id);
            preparedStatement.setInt(1,product_id);
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
