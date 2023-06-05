package fa.training.main;


import fa.training.dao.CustomerDAOImpl;
import fa.training.dao.LineItemDAOImpl;
import fa.training.dao.OrderDAOImpl;
import fa.training.entities.Customer;
import fa.training.entities.LineItem;
import fa.training.entities.Order;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SaleManagement {
    Scanner scanner = new Scanner(System.in);
    public List<Customer> getAllCustomer(){
        OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
        List<Order> listOrder = orderDAOImpl.retrieve();
        CustomerDAOImpl customerDAOImp = new CustomerDAOImpl();
        List<Customer> listCustomer = customerDAOImp.retrieve();
        List<Integer> listInt = new ArrayList<>();
        for (Order o : listOrder) {
            listInt.add(o.getCustomerId());
        }
        List<Customer> listInOrder = new ArrayList<>();
        for (Customer c: listCustomer) {
            for (int i : listInt) {
                if(i == c.getCustomerId()){
                    listInOrder.add(c);
                }
            }
        }
        return listInOrder;
    }

    public List<Order> getAllOrderByCustomerId(int customerId){
        OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
        List<Order> list = orderDAOImpl.retrieve();
        List<Order> listByCustomerId = new ArrayList<>();
        for (Order o: list) {
            if(o.getCustomerId() == customerId){
                listByCustomerId.add(o);
            }
        }
        return listByCustomerId;
    }

    public List<LineItem> getAllItemsByOrderId(int orderId){
        LineItemDAOImpl lineItemDAOImpl = new LineItemDAOImpl();
        List<LineItem> list = lineItemDAOImpl.retrieve();
        List<LineItem> listByOrderId = new ArrayList<>();
        for (LineItem li : list) {
            if(li.getOrderId() == orderId){
                listByOrderId.add(li);
            }
        }
        return listByOrderId;
    }
    public Double computeOrderTotal(int orderId){
        SaleManagement saleManagement = new SaleManagement();
        OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
        List<LineItem> list = saleManagement.getAllItemsByOrderId(orderId);
        double total_price = 0;
        for (LineItem li: list) {
            total_price += li.getPrice();
        }
        return total_price;
    }

    public boolean addCustomer(Customer customer) throws SQLException {
        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
        Scanner scanner = new Scanner(System.in);
        boolean check = true;
        int row = 0;
        while (check) {
            System.out.print("Enter name: ");
            customer.setCustomerName(scanner.next());

            System.out.print("Do you want to continue(Y/N): ");
            String next = scanner.next();
            if (next.equals("N") || next.equals("n")) {
                row = customerDAOImpl.create(customer.getCustomerName());
                check = false;
            } else if (next.equals("Y") || next.equals("y")) {
                row = customerDAOImpl.create(customer.getCustomerName());
                continue;
            }
        }
        if(row > 0){
            System.out.println("Insert success!");
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteCustomer(int customerId) throws SQLException {
        SaleManagement saleManagement = new SaleManagement();
        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
        OrderDAOImpl orderDAOImpl = new OrderDAOImpl();

        List<Order> listOrderByCustomerId = saleManagement.getAllOrderByCustomerId(customerId);
        for (Order o: listOrderByCustomerId) {
            orderDAOImpl.delete(o.getOrderId());
        }
        int row = customerDAOImpl.delete(customerId);
        if(row > 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();

        int row = customerDAOImpl.update(customer.getCustomerId(), customer.getCustomerName());
        if(row > 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addOrder(Order order) throws SQLException {
        OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
        Scanner scanner = new Scanner(System.in);
        int row = 0;
        try {
            System.out.print("Enter date: ");
            Date date = null;
            date = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
            order.setOrderDate(date);

            System.out.print("Enter customer_id: ");
            order.setCustomerId(Integer.parseInt(scanner.nextLine()));

            System.out.print("Enter employee_id: ");
            order.setEmployeeId(Integer.parseInt(scanner.nextLine()));

            System.out.print("Enter total: ");
            order.setTotal(Double.parseDouble(scanner.nextLine()));

            row = orderDAOImpl.create(order);

        } catch (ParseException e) {
            System.err.println("Error format!!!");
        }
        if (row > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addLineItem(LineItem item) throws SQLException {
        LineItemDAOImpl lineItemDAOImpl = new LineItemDAOImpl();

        int row = lineItemDAOImpl.create(item.getOrderId(), item.getProductId(), item.getQuantity(), item.getPrice());
        if(row > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean updateOrderTotal(int orderId) throws SQLException {
        OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
        System.out.print("Enter new order total: ");
        double newTotal = Double.parseDouble(scanner.nextLine());
        int row = orderDAOImpl.updateDate(orderId,-1,-1, newTotal);
        if(row > 0){
            return true;
        }
        else{
            return false;
        }
    }
    public void menu(){
        System.out.println("1. List all customer in order");
        System.out.println("2. List all orders by customer id");
        System.out.println("3. List all item by order id");
        System.out.println("4. Computer total order by order id");
        System.out.println("5. Add a customer");
        System.out.println("6. Delete a customer");
        System.out.println("7. Update a customer");
        System.out.println("8. Create an order");
        System.out.println("9. Create a line item");
        System.out.println("10. Update total order by order id");
        System.out.print("Function: ");
    }
    public static void main(String[] args) throws SQLException {
        SaleManagement saleManagement = new SaleManagement();
//        saleManagement.addOrder(new Order());
        saleManagement.addCustomer(new Customer());
    }
}
