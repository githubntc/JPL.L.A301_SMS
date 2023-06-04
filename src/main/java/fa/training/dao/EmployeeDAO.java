package fa.training.dao;

import fa.training.common.Database;
import fa.training.entities.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    Database database;
    public EmployeeDAO(){
        this.database = new Database();
    }

    public int create(Employee employee) throws SQLException {
        Connection conn = database.getConn();
        int count;
        try{
            conn.setAutoCommit(false);
            String sql = "INSERT INTO `employee`(`employee_name`, `salary`, `spvrId`) VALUES ('?','?','?')";
            PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.setDouble(2, employee.getSalary());
            preparedStatement.setInt(3, employee.getSpvrId());
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
    public List<Employee> retrieve(){
        List<Employee> list = new ArrayList<>();
        Employee employee;
        try{
            String sql = "SELECT * FROM `employee`";
            Statement statement = database.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                employee = new Employee(resultSet.getInt("employee_id")
                        , resultSet.getString("employee_name")
                        , resultSet.getDouble("salary")
                        , resultSet.getInt("spvrId"));
                list.add(employee);
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    private int update(int employee_id, String employee_name, double salary, int spvrId) throws SQLException{
        Connection conn = database.getConn();
        int count = 0;
        try{
            conn.setAutoCommit(false);
            if((salary < 0) && (spvrId < 0) ){

                String sql = "UPDATE `employee` SET `employee_name` = ? WHERE `employee_id` = ?";
                PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
                preparedStatement.setString(1, employee_name);
                preparedStatement.setInt(2,employee_id);
                count = preparedStatement.executeUpdate();
            }
            if((employee_name.equals("")) && (spvrId < 0)){
                String sql = "UPDATE `employee` SET `salary` = ? WHERE `employee_id` = ? ";
                PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
                preparedStatement.setDouble(1,salary);
                preparedStatement.setInt(2,employee_id);
                count = preparedStatement.executeUpdate();
            }
            if((employee_name.equals("")) && (salary < 0)){
                String sql = "UPDATE `employee` SET `spvrId` = ? WHERE `employee_id` = ? ";
                PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
                preparedStatement.setInt(1, spvrId);
                preparedStatement.setInt(2,employee_id);
                count = preparedStatement.executeUpdate();
            }
            conn.commit();
            conn.setAutoCommit(true);
            return count;
        }catch(SQLException e){
            conn.rollback();
            return 0;
        }
    }
    private int delete(int employee_id) throws SQLException{
        Connection conn = database.getConn();
        int count;
        try{
            conn.setAutoCommit(false);
            String sql = "DELETE FROM `employee` WHERE `employee_id` = ?";
            PreparedStatement preparedStatement = database.getConn().prepareStatement(sql);
            preparedStatement.setInt(1,employee_id);
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
