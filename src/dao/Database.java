package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Database {
    private static final Logger LOGGER= Logger.getLogger(Database.class.getName());
    private static  final String DB_DRIVER="com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION="jdbc:oracle:thin:@localhost:1521:ORCL";
    private static final String DB_USER="system";
    private static final String DB_PASSWORD="system";
   
    public Database() {
    }
    
    public static Connection getDBConnection() throws SQLException{
        Connection connection=null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
        try {
            connection=DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
        
        return connection;
    }
}
