package dao;

import beans.Journale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JournaleDao {
    
    
     public List<Journale> getJournaleById(int id_pro) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Journale journale ;
        List<Journale> journales = new ArrayList<>();
        
        try {
            connection = Database.getDBConnection();
            String query = "select * from journale where id_pro = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setInt(counter++,id_pro);
            ResultSet rs = statement.executeQuery();
           
            
            while(rs.next()){
            journale = new Journale();
            journale.setId_journale(rs.getInt(1));
            journale.setId_pro(rs.getInt(2));
            journale.setDate_journale(rs.getDate(3));
            journale.setStock(rs.getInt(4));
            journales.add(journale) ;
           
            }
             return journales;
            
        } catch (SQLException exception) {
            System.out.println("error de sql produit find by id aham:" + exception.getMessage());
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
