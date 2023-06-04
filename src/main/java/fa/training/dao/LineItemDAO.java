package fa.training.dao;

import fa.training.entities.LineItem;

import java.sql.*;
import java.util.List;

interface LineItemDAO {
    public int create(int order_id, int product_id, int quantity, double price) throws SQLException;
    public List<LineItem> retrieve();
    public int update(int order_id, int product_id, int quantity, double price) throws SQLException;
    public int delete(int order_id, int product_id) throws SQLException;
}
