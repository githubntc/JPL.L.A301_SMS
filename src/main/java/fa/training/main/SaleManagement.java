package fa.training.main;


import fa.training.dao.CustomerDAO;
import fa.training.entities.Customer;

import java.sql.SQLException;
import java.util.List;

public class SaleManagement {
    public static void main(String[] args) throws SQLException {
        CustomerDAO customerDAO = new CustomerDAO();

        int count = customerDAO.delete(1);
        System.out.println(count);
        List<Customer> list = customerDAO.retrieve();
        for (Customer c : list) {
            System.out.println(c);
        }
    }
}
