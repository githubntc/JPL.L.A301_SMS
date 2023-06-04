package fa.training.common;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/sms";
    static final String USER = "root";
    static final String PASSWORD = "";

    private Connection connection;

    public Database(){
        this.init();
    }

    private void init(){
        try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public Connection getConn() {
        return connection;
    }

    public void setConnection(Connection conn) {
        this.connection = conn;
    }
}
