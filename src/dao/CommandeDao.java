package dao;
import beans.Commande;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeDao {
    
    
    
    public int saveCommande(Commande commande) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            String query = "insert into Commande(id_pro,id_fact,qte_com) values(?,?,?)";
            statement = connection.prepareStatement(query);
            int count = 1;
            statement.setInt(count++, commande.getId_pro());
            statement.setInt(count++, commande.getId_fact());
            statement.setDouble(count++, commande.getQte_com());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return 1;
            }

        } catch (SQLException e) {
            if(e.getErrorCode() == 20001){
            return 2;
            }else{
                System.out.println("error:" + e.getMessage());
            }
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

   
    
    
    
    
    public List<Commande> getCommandeByIdFact(int id_fact) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Commande commande ;
        List<Commande> commandes = new ArrayList<>() ;

        try {
            connection = Database.getDBConnection();
            String query = "select * from commande where id_fact = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setInt(counter++,id_fact);
            ResultSet rs = statement.executeQuery();
            
           
            
            while(rs.next()){
            commande = new Commande();
            commande.setId_pro(rs.getInt(1));
            commande.setId_fact(rs.getInt(2));
            commande.setQte_com(rs.getInt(3));
            commandes.add(commande);
            }
            return commandes;
            
        } catch (SQLException exception) {
            System.out.println("error de sql getCommandeByIdFact:" + exception.getMessage());
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
    
     public List<Commande> getCommandeByIdPro(int id_pro) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Commande commande ;
        List<Commande> commandes = new ArrayList<>() ;

        try {
            connection = Database.getDBConnection();
            String query = "select * from commande where id_pro = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setInt(counter++,id_pro);
            ResultSet rs = statement.executeQuery();
            
           
            
            while(rs.next()){
            commande = new Commande();
            commande.setId_pro(rs.getInt(1));
            commande.setId_fact(rs.getInt(2));
            commande.setQte_com(rs.getInt(3));
            commandes.add(commande);
            }
            return commandes;
            
        } catch (SQLException exception) {
            System.out.println("error de sql commande find by id:" + exception.getMessage());
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
    
   