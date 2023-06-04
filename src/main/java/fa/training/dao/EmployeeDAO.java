package fa.training.dao;

import fa.training.common.Database;
import fa.training.entities.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

interface EmployeeDAO {
    public int create(Employee employee) throws SQLException;
    public List<Employee> retrieve();
    public int update(int employee_id, String employee_name, double salary, int spvrId) throws SQLException;
    public int delete(int employee_id) throws SQLException;
}
