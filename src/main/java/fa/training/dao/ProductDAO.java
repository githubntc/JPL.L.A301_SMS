package fa.training.dao;


import fa.training.entities.Product;

import java.sql.SQLException;
import java.util.List;

interface ProductDAO {
    public int create(String product_name, double list_price) throws SQLException;
    public List<Product> retrieve();
    public int update(int product_id, String product_name, double list_price) throws SQLException;
    public int delete(int product_id) throws SQLException;
}
