
package dao;

import beans.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TypeDao {
     public List<Type> getAllTypes() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Type type = null;
        List<Type> types = new ArrayList<>();

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "SELECT ID_Type, LIB_TYPE FROM Type ";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
            type = new Type();
            type.setId_type(rs.getInt(1));
            type.setLib_type(rs.getString(2));
            types.add(type);
            }
            
            
            return types;

        } catch (SQLException exception) {
            System.out.println("error de sql :" + exception.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return null;

    }
     public Type getTypeByLib(String lib_type) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Type type = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "SELECT ID_Type, LIB_TYPE FROM Type where LIB_TYPE = ? ";
            int count = 1;
            statement = connection.prepareStatement(query);
            statement.setString(count++,lib_type);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
            type = new Type();
            type.setId_type(rs.getInt(1));
            type.setLib_type(rs.getString(2));
            return type;
            }
            
            
            

        } catch (SQLException exception) {
            System.out.println("error de sql :" + exception.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return null;

    }
     
     public Type getTypeById(int id_type) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Type type = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "SELECT ID_Type, LIB_TYPE FROM Type where id_TYPE = ? ";
            int count = 1;
            statement = connection.prepareStatement(query);
            statement.setInt(count++,id_type);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
            type = new Type();
            type.setId_type(rs.getInt(1));
            type.setLib_type(rs.getString(2));
            return type;
            }
            
            
            

        } catch (SQLException exception) {
            System.out.println("error de sql type :" + exception.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return null;

    }
}
