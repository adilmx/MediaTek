package dao;
import beans.Facture;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FactureDao {
    //importante**************************
    public int saveFacture(Facture facture) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            
            String query = "insert into Facture(id_fact,id_clt,date_fact) values(pro_seq.nextval,?,to_date(sysdate,'dd-mm-yyyy'))";
            statement = connection.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
            int count = 1;
            statement.setInt(count++, facture.getId_clt());
            statement.execute();
            
            String getseq = "select pro_seq.currval from dual";
            PreparedStatement statement2 = connection.prepareStatement(getseq );
            ResultSet rs = statement2.executeQuery();
            
             if(rs.next()){
                 System.out.println("getGeneratedKey 2 "+rs.getLong(1));
                 return (int) rs.getLong(1);
             }
            
            

        } catch (SQLException e) {
            System.out.println("error:" + e.getMessage());
        } finally {
           

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

   
    
    
    
    
    public Facture getFactureByIdFact(int id_fact) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Facture facture ;

        try {
            connection = Database.getDBConnection();
            String query = "select * from facture where id_fact = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setInt(counter++,id_fact);
            ResultSet rs = statement.executeQuery();
            
            
            if(rs.next()){
                System.out.println("getfact_ id_fact1 = "+rs.getInt(1));
            facture = new Facture();
            facture.setId_fact(rs.getInt(1));
            facture.setId_clt(rs.getInt(2));
            facture.setDate_fact(rs.getDate(3));
                System.out.println("getfact_ id_fact2 = "+rs.getInt(1));
            return facture;
            }
        } catch (SQLException exception) {
            System.out.println("error de sql getFactureByIdFact :" + exception.getMessage());
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
    
     public Facture getFactureByIdClt(int id_clt) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Facture facture ;

        try {
            connection = Database.getDBConnection();
            String query = "select * from facture where id_clt = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setInt(counter++,id_clt);
            ResultSet rs = statement.executeQuery();
            
            
            if(rs.next()){
            facture = new Facture();
            facture.setId_fact(rs.getInt(1));
            facture.setId_clt(rs.getInt(2));
            facture.setDate_fact(rs.getDate(3));
            
            return facture;
            }
        } catch (SQLException exception) {
            System.out.println("error de sql getFactureByIdClt:" + exception.getMessage());
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