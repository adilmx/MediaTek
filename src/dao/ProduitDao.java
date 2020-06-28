package dao;
import beans.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDao {
    
    public int saveProduit(Produit produit) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            String query = "insert into Produit(id_pro,lib_pro,prix_unit_pro,qte_dispo,type_pro,desc_pro,pic_src_pro) values(pro_seq.nextval,?,?,?,?,?,?)";
            statement = connection.prepareStatement(query);
            int count = 1;
            statement.setString(count++, produit.getLib_pro());
            statement.setDouble(count++, produit.getPrix_unit_pro());
            statement.setDouble(count++, produit.getQte_dispo());
            statement.setInt(count++, produit.getType_pro());
            statement.setString(count++, produit.getDesc_pro());
            statement.setString(count++, produit.getPic_src_pro());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return 1;
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

    public int updateProduit(Produit produit) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            String query = "update Produit set lib_pro = ?,prix_unit_pro = ?,qte_dispo = ?,type_pro = ?,desc_pro = ?,pic_src_pro = ? where id_pro = ?";
            statement = connection.prepareStatement(query);
            int count = 1;
            statement.setString(count++, produit.getLib_pro());
            statement.setDouble(count++, produit.getPrix_unit_pro());
            statement.setDouble(count++, produit.getQte_dispo());
            statement.setInt(count++, produit.getType_pro());
            statement.setString(count++, produit.getDesc_pro());
            statement.setString(count++, produit.getPic_src_pro());
            statement.setInt(count++, produit.getId_pro());
            statement.executeUpdate();
                return 1;
            

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

    
    
    
    
    public Produit getProduitById(int id_pro) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Produit produit ;

        try {
            connection = Database.getDBConnection();
            String query = "select * from produit where id_pro = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setInt(counter++,id_pro);
            ResultSet rs = statement.executeQuery();
           
            
            if(rs.next()){
            produit = new Produit();
            produit.setId_pro(rs.getInt(1));
            produit.setLib_pro(rs.getString(2));
            produit.setPrix_unit_pro(rs.getDouble(3));
            produit.setQte_dispo(rs.getInt(4));
            produit.setType_pro(rs.getInt(5));
            produit.setDesc_pro(rs.getString(6));
            produit.setPic_src_pro(rs.getString(7));
            return produit;
            }
            
            
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
    


    public List<Produit> getAllProduit() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Produit produit ;
        List<Produit> produits = new ArrayList<>() ;

        try {
            connection = Database.getDBConnection();
            String query = "select * from produit ";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            
            while(rs.next()){
            produit = new Produit();
            produit.setId_pro(rs.getInt(1));
            produit.setLib_pro(rs.getString(2));
            produit.setPrix_unit_pro(rs.getDouble(3));
            produit.setQte_dispo(rs.getInt(4));
            produit.setType_pro(rs.getInt(5));
            produit.setDesc_pro(rs.getString(6));
            produit.setPic_src_pro(rs.getString(7));
            produits.add(produit);
            }
            return produits;
            
        } catch (SQLException exception) {
            System.out.println("error de sql produit find by id:" + exception.getMessage());
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
    
  public int DeleteProduitById(int id_pro) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Produit produit ;

        try {
            connection = Database.getDBConnection();
            String query = "delete from produit where id_pro = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setInt(counter++,id_pro);
            ResultSet rs = statement.executeQuery();
            
            
            return 1;
            
        } catch (SQLException exception) {
            System.out.println("error de sql produit find by id:" + exception.getMessage());
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
  
   public List<Produit> getProduitByType(int type_pro) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Produit produit ;
        List<Produit> produits = new ArrayList<>() ;

        try {
            connection = Database.getDBConnection();
            String query = "select * from produit where type_pro = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setInt(counter++,type_pro);
            ResultSet rs = statement.executeQuery();
            
            
            while(rs.next()){
            produit = new Produit();
            produit.setId_pro(rs.getInt(1));
            produit.setLib_pro(rs.getString(2));
            produit.setPrix_unit_pro(rs.getDouble(3));
            produit.setQte_dispo(rs.getInt(4));
            produit.setType_pro(rs.getInt(5));
            produit.setDesc_pro(rs.getString(6));
            produit.setPic_src_pro(rs.getString(7));
            produits.add(produit);
            }
            
            return produits;
            
        } catch (SQLException exception) {
            System.out.println("error de sql produit find by id:" + exception.getMessage());
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
    
   public List<Produit> getProduitByLib(String lib_pro) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Produit produit ;
        List<Produit> produits = new ArrayList<>() ;

        try {
            connection = Database.getDBConnection();
            String query = "select * from produit where lib_pro like ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setString(counter++,"%"+lib_pro+"%");
            ResultSet rs = statement.executeQuery();
           
            
            while(rs.next()){
            produit = new Produit();
            produit.setId_pro(rs.getInt(1));
            produit.setLib_pro(rs.getString(2));
            produit.setPrix_unit_pro(rs.getDouble(3));
            produit.setQte_dispo(rs.getInt(4));
            produit.setType_pro(rs.getInt(5));
            produit.setDesc_pro(rs.getString(6));
            produit.setPic_src_pro(rs.getString(7));
            produits.add(produit);
            
            }
            return produits;
            
        } catch (SQLException exception) {
            System.out.println("error de sql produit find by lib aham:" + exception.getMessage());
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
