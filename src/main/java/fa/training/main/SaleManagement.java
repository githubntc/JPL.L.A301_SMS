package fa.training.main;


import fa.training.common.Database;
import fa.training.common.Validator;
import fa.training.dao.*;
import fa.training.entities.*;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
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
//                    System.out.println(c.toString());
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
        LineItemDAOImpl lineItemDAOImpl = new LineItemDAOImpl();

        List<Order> listOrderByCustomerId = saleManagement.getAllOrderByCustomerId(customerId);

        List<LineItem> listItemByOrder = new ArrayList<>();
        for (Order o: listOrderByCustomerId) {
            listItemByOrder = saleManagement.getAllItemsByOrderId(o.getOrderId());
            for (LineItem li : listItemByOrder) {
                lineItemDAOImpl.delete(li.getOrderId(),li.getProductId());
            }
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

        int row = orderDAOImpl.create(order);
        if(row > 0){
            return true;
        }
        else{
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
        Validator validator = new Validator();
        boolean checkTotal = true;
        double newTotal = 0;
        String strTotal;
        while ( checkTotal ){
            System.out.print("Enter new order total: ");
            strTotal = scanner.nextLine();
            if(validator.isNumberic(strTotal)){
                newTotal = Double.parseDouble(strTotal);
                checkTotal = false;
            }else {
                System.out.println("Please enter number!");
                checkTotal = true;
            }
        }

        int row = orderDAOImpl.updateDate(orderId,-1,-1, newTotal);
        if(row > 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkCustomerId(int customerId){
        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
        List<Customer> listCustomer = customerDAOImpl.retrieve();
        for (Customer c : listCustomer) {
            if(c.getCustomerId() == customerId){
                return true;
            }
        }
        return false;
    }
    public boolean checkEmployeeId(int employeeId){
        EmployeeDAOImpl employeeDAOImpl = new EmployeeDAOImpl();
        List<Employee> listEmployee = employeeDAOImpl.retrieve();
        for (Employee e : listEmployee) {
            if(e.getEmployeeId() == employeeId){
                return true;
            }
        }
        return false;
    }
    public boolean checkOrderId(int orderId){
        OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
        List<Order> listOrder = orderDAOImpl.retrieve();
        for (Order o : listOrder) {
            if(o.getOrderId() == orderId){
                return true;
            }
        }
        return false;
    }
    public boolean checkProductId(int productId){
        ProductDAOImpl productDAOImpl = new ProductDAOImpl();
        List<Product> listProduct = productDAOImpl.retrieve();
        for (Product p : listProduct) {
            if(p.getProductId() == productId){
                return true;
            }
        }
        return false;
    }
    public Product getProduct(int productId){
        ProductDAOImpl productDAOImpl = new ProductDAOImpl();
        List<Product> listProduct = productDAOImpl.retrieve();
        Product product = null;
        for (Product p : listProduct) {
            if(p.getProductId() == productId){
                product = new Product(p.getProductId(), p.getProductName(), p.getListPice());
            }
        }
        return product;
    }
    public String paddingLeft(String str, int widthFix){
        return String.format("%-" + widthFix + "s", str);
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
        System.out.println("11. Exit");
        System.out.print("Function: ");
    }
    public static void main(String[] args) throws SQLException, ParseException {
        SaleManagement saleManagement = new SaleManagement();
        OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
        Validator validator = new Validator();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<Customer> listCustomer;
        List<Order> listOrderByCusId;
        List<LineItem> listItemByOrderId;
        boolean check = true;
        int chooseFunc = 0;
        String strInt;
        int numberId;
        double total_price;
        while(check){
            saleManagement.menu();
            boolean checkInt = true;
            while(checkInt){
                strInt = saleManagement.scanner.nextLine();
                if(validator.isNumberId(strInt)){
                    chooseFunc = Integer.parseInt(strInt);
                    checkInt = false;
                }
                else {
                    System.out.println("Invalid!");
                    checkInt = true;
                }
            }
            switch (chooseFunc){
                case 1:{
                    listCustomer = saleManagement.getAllCustomer();
                    System.out.println("\nList all customer");
                    System.out.println(saleManagement.paddingLeft("customer id", 15) +
                                saleManagement.paddingLeft("customer name", 30));
                    for (Customer c : listCustomer) {
                        System.out.println(saleManagement.paddingLeft(String.valueOf(c.getCustomerId()), 15) +
                                saleManagement.paddingLeft(c.getCustomerName(), 30));
                    }
                    System.out.println();
                }
                break;
                case 2:{
                    boolean checkOder = true;
                    while (checkOder){
                        System.out.print("\nEnter customer id: ");
                        strInt = saleManagement.scanner.nextLine();
                        if(validator.isNumberId(strInt)){
                            numberId = Integer.parseInt(strInt);
                            checkOder = false;
                            listOrderByCusId = saleManagement.getAllOrderByCustomerId(numberId);
                            System.out.println("\n----------List order by customer id---------");
                            System.out.println(saleManagement.paddingLeft("orderId",10) +
                                    saleManagement.paddingLeft("orderDate",15) +
                                    saleManagement.paddingLeft("customerId",15) +
                                    saleManagement.paddingLeft("employeeId",15) +
                                    saleManagement.paddingLeft("total",10)

                            );
                            for (Order o : listOrderByCusId) {
                                System.out.println(saleManagement.paddingLeft(String.valueOf(o.getOrderId()),10) +
                                        saleManagement.paddingLeft(dateFormat.format(o.getOrderDate()),15) +
                                        saleManagement.paddingLeft(String.valueOf(o.getCustomerId()),15) +
                                        saleManagement.paddingLeft(String.valueOf(o.getEmployeeId()),15) +
                                        saleManagement.paddingLeft(String.valueOf(o.getTotal()),10)
                                        );
                            }
                        }
                        else {
                            checkOder = true;
                            System.out.println("Invalid!");
                        }
                    }
                    System.out.println();
                }
                break;
                case 3:{
                    boolean checkItem = true;
                    while (checkItem){
                        System.out.print("Enter orderid to get list item: ");
                        strInt = saleManagement.scanner.nextLine();
                        if(validator.isNumberId(strInt)){
                            checkItem = false;
                            numberId = Integer.parseInt(strInt);
                            listItemByOrderId = saleManagement.getAllItemsByOrderId(numberId);
                            System.out.println("\n-------------List item by order id---------");
                            System.out.println(saleManagement.paddingLeft("orderId",10) +
                                    saleManagement.paddingLeft("productId",15) +
                                    saleManagement.paddingLeft("quantity",10) +
                                    saleManagement.paddingLeft("price",10));
                            for (LineItem li : listItemByOrderId) {
                                System.out.println(saleManagement.paddingLeft(String.valueOf(li.getOrderId()),10) +
                                        saleManagement.paddingLeft(String.valueOf(li.getProductId()),15) +
                                        saleManagement.paddingLeft(String.valueOf(li.getQuantity()),10) +
                                        saleManagement.paddingLeft(String.valueOf(li.getPrice()),10));
                            }
                        }
                        else {
                            System.out.println("Invalid!");
                            checkItem = true;
                        }
                    }
                }
                break;
                case 4:{
                    System.out.print("Enter order id: ");
                    strInt = saleManagement.scanner.nextLine();
                    if(validator.isNumberId(strInt)){
                        numberId = Integer.parseInt(strInt);
                        total_price = saleManagement.computeOrderTotal(numberId);
                        System.out.println("\n"+saleManagement.paddingLeft("orderid", 10) +
                                saleManagement.paddingLeft("total_price",20));
                        System.out.println(saleManagement.paddingLeft(String.valueOf(numberId), 10) +
                                saleManagement.paddingLeft(String.valueOf(total_price),20));
                    }
                }
                break;
                case 5:{
                    System.out.print("Enter name customer: ");
                    String name = saleManagement.scanner.nextLine();
                    Customer customer = new Customer(-1, name);
                    boolean checkCreate = saleManagement.addCustomer(customer);
                    if(checkCreate){
                        System.out.println("Create success!");
                    }else {
                        System.out.println("Create false!");
                    }
                }
                break;
                case 6:{
                    System.out.print("Enter id customer: ");
                    strInt = saleManagement.scanner.nextLine();
                    if(validator.isNumberId(strInt)){
                        numberId = Integer.parseInt(strInt);
                        boolean checkDelete = saleManagement.deleteCustomer(numberId);
                        if (checkDelete){
                            System.out.println("Delete success!");
                        }else {
                            System.out.println("Delete false!");
                        }
                    }
                }
                break;
                case 7:{
                        System.out.print("Enter customer id: ");
                        strInt = saleManagement.scanner.nextLine();
                        if (validator.isNumberId(strInt)){
                            numberId = Integer.parseInt(strInt);
                            System.out.print("Enter new name: ");
                            String newName = saleManagement.scanner.nextLine();
                            Customer customer = new Customer(numberId, newName);
                            boolean checkUpdate = saleManagement.updateCustomer(customer);
                            if(checkUpdate){
                                System.out.println("Update success!");
                            }
                            else {
                                System.out.println("Update false!");
                            }
                        }
                        else {
                            System.out.println("Invalid!");
                        }
                }
                break;
                case 8:{
                    boolean checkDate = true;
                    String strDate;
                    java.util.Date utilDate = null;
                    Date date = null;
                    while (checkDate){
                        System.out.print("Enter oder date: ");
                        strDate = saleManagement.scanner.nextLine();
                        if(validator.isDate(strDate)){
                            utilDate =  new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
                            date = new Date(utilDate.getTime());
                            checkDate = false;
                        }else {
                            System.out.println("format false!");
                            checkDate = true;
                        }
                    }
                    boolean checkId = true;
                    String strCusId;
                    int customerId = 0;
                    while (checkId){
                        System.out.print("Enter customer id: ");
                        strCusId = saleManagement.scanner.nextLine();
                        if(validator.isNumberId(strCusId)){
                            customerId = Integer.parseInt(strCusId);
                            if(saleManagement.checkCustomerId(customerId)){
                                checkId = false;
                            }
                            else {
                                System.out.println("Customer id is not exitsts!");
                                checkId = false;
                            }
                        }else {
                            System.out.println("Please enter number!");
                            checkId = true;
                        }
                    }
                    checkId = true;
                    int emplpoyeeId = 0;
                    String strEmpId;
                    while (checkId){
                        System.out.print("Enter employee id: ");
                        strEmpId = saleManagement.scanner.nextLine();
                        if(validator.isNumberId(strEmpId)){
                            emplpoyeeId = Integer.parseInt(strEmpId);
                            if(saleManagement.checkEmployeeId(emplpoyeeId)){
                                checkId = false;
                            }
                            else {
                                checkId = true;
                                System.out.println("Employee id is not exists!");
                            }
                        }else {
                            System.out.println("Please enter number!");
                            checkId = true;
                        }
                    }
                    boolean checkTotal = true;
                    double total = 0;
                    String strTotal;
                    while (checkTotal){
                        System.out.print("Enter total: ");
                        strTotal = saleManagement.scanner.nextLine();
                        if(validator.isNumberic(strTotal)){
                            total = Double.parseDouble(strTotal);
                            checkTotal = false;
                        }else {
                            System.out.println("Please enter number!");
                            checkTotal = true;
                        }
                    }
                    Order order = new Order(-1 ,date, customerId, emplpoyeeId, total);
                    boolean checkCreate = saleManagement.addOrder(order);
                    if(checkCreate){
                        System.out.println("Create success!");
                    }else {
                        System.out.println("Create false!");
                    }
                }
                break;
                case 9:{
                    boolean checkId = true;
                    String strOrderId;
                    int orderId = 0;
                    while (checkId){
                        System.out.print("Enter order id: ");
                        strOrderId = saleManagement.scanner.nextLine();
                        if(validator.isNumberId(strOrderId)){
                            orderId = Integer.parseInt(strOrderId);
                            if(saleManagement.checkOrderId(orderId)){
                                checkId = false;
                            }
                            else {
                                System.out.println("Order id is not exitsts!");
                                checkId = false;
                            }
                        }else {
                            System.out.println("Please enter number!");
                            checkId = true;
                        }
                    }
                    checkId = true;
                    int productId = 0;
                    String strProId;
                    while (checkId){
                        System.out.print("Enter product id: ");
                        strProId = saleManagement.scanner.nextLine();
                        if(validator.isNumberId(strProId)){
                            productId = Integer.parseInt(strProId);
                            if(saleManagement.checkProductId(productId)){
                                checkId = false;
                            }
                            else {
                                System.out.println("Product id is not exitsts!");
                                checkId = false;
                            }
                        }else {
                            System.out.println("Please enter number!");
                            checkId = true;
                        }
                    }
                    boolean checkNumber = true;
                    int quantity = 0;
                    String strQuantity;
                    while (checkNumber){
                        System.out.print("Enter quantity: ");
                        strQuantity = saleManagement.scanner.nextLine();
                        if(validator.isNumberic(strQuantity)){
                            quantity = Integer.parseInt(strQuantity);
                            checkNumber = false;
                        }else {
                            System.out.println("Please enter number!");
                            checkNumber = true;
                        }
                    }
                    double price = saleManagement.getProduct(productId).getListPice() * quantity;
                    LineItem lineItem = new LineItem(orderId, productId, quantity, price);
                    boolean checkCreate = saleManagement.addLineItem(lineItem);
                    if(checkCreate){
                        double newTotal = 0;
                        listItemByOrderId = saleManagement.getAllItemsByOrderId(lineItem.getOrderId());
                        for (LineItem li : listItemByOrderId) {
                            newTotal += li.getPrice();
                        }
                        int row = orderDAOImpl.updateDate(orderId,-1,-1, newTotal);
                        System.out.println("Create success!");
                    }else {
                        System.out.println("Create false!");
                    }
                }
                break;
                case 10:{
                    boolean checkId = true;
                    String strOrderId;
                    int orderId = 0;
                    while (checkId){
                        System.out.print("Enter order id: ");
                        strOrderId = saleManagement.scanner.nextLine();
                        if(validator.isNumberId(strOrderId)){
                            orderId = Integer.parseInt(strOrderId);
                            checkId = false;
                        }else {
                            System.out.println("Please enter number!");
                            checkId = true;
                        }
                    }
                    boolean checkUpdate = saleManagement.updateOrderTotal(orderId);
                    if(checkUpdate){
                        System.out.println("Update success!");
                    }else {
                        System.out.println("Update false!");
                    }
                }
                break;
                case 11:{
                    check = false;
                }
                break;
                default:
                {
                    System.out.println("Please enter from 1 to 11 !");
                }
                    break;
            }
        }
    }
}
