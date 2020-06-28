
package controllers;

import beans.Produit;
import beans.Type;
import dao.ProduitDao;
import dao.TypeDao;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;


public class AboutProduitController implements Initializable {
    private final ProduitDao produitDao = new ProduitDao() ;
    private TypeDao typeDao = new TypeDao();
    private Type type ;
    private Produit produit = new Produit() ;
    
    @FXML
    private Button back;
    @FXML
    private Button del_btn;
    @FXML
    private Button update_btn;
    @FXML
    private ImageView pic_pro;
    @FXML
    private Text desc_txt;
    @FXML
    private Text nom_txt;
    @FXML
    private Text type_txt;
    @FXML
    private Text prix_txt;
    @FXML
    private Text title_txt;
    @FXML
    private Text qte_txt;
    

    public void initdata(Produit p) throws SQLException{
         File imageFile = new File(p.getPic_src_pro());
         String fileLocation = imageFile.toURI().toString();
         Image fxImage = new Image(fileLocation);  
         pic_pro.setImage(fxImage);
        
       
        type = typeDao.getTypeById(p.getType_pro());
        desc_txt.setText("  "+p.getDesc_pro());
        title_txt.setText("INFOS SUR LE PRODUIT : "+p.getLib_pro());
        prix_txt.setText("PRIX DE PRODUIT : "+p.getPrix_unit_pro()+"DH");
        nom_txt.setText("NOM DE PRODUIT : "+p.getLib_pro());
        type_txt.setText("TYPE DE PRODUIT : "+type.getLib_type());
        qte_txt.setText("QUANTITE STOCK : "+p.getQte_dispo());
        produit = p;
    }
    public void initdata_back(Produit p) throws SQLException {
         File imageFile = new File(p.getPic_src_pro());
         String fileLocation = imageFile.toURI().toString();
         Image fxImage = new Image(fileLocation);  
         pic_pro.setImage(fxImage);
        
         type = typeDao.getTypeById(p.getType_pro());
        desc_txt.setText("  "+p.getDesc_pro());
        title_txt.setText("INFOS SUR LE PRODUIT : "+p.getLib_pro());
        prix_txt.setText("PRIX DE PRODUIT : "+p.getPrix_unit_pro()+"DH");
        nom_txt.setText("NOM DE PRODUIT : "+p.getLib_pro());
        type_txt.setText("TYPE DE PRODUIT : "+type.getLib_type());
        qte_txt.setText("QUANTITE STOCK : "+p.getQte_dispo());
        produit = p ;
    }
    
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void GoToUpdate(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/ModificationProduit.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                ModificationProduitController controller = loader.getController();
                controller.initdata(produit);
                System.out.println("kkk"+produit.getId_pro());
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Update infos");

                window.setScene(scene);
               window.sizeToScene();
                window.show();
                //strat
        double y = window.getHeight();
        double x = window.getWidth();
        System.out.println("x:"+x+"y"+y);
        window.widthProperty().addListener((obs , oldval ,newval) -> {
        double y_ = window.getHeight();
        double x_ = window.getWidth();
        if(x_ <= x && y_ <= y){
            window.setHeight(y);
            window.setWidth(x);
            System.out.println("x:"+x_+"y"+y_);
        }else{
            System.out.println("boom");
        }
        });
       window.heightProperty().addListener((obs , oldval ,newval) -> {
        double y_ = window.getHeight();
        double x_ = window.getWidth();
        if(x_ <= x && y_ <= y){
        window.setHeight(y);
        window.setWidth(x);
        System.out.println("x:"+x_+"y"+y_);
        }else{
            System.out.println("boom");
        }
        }); 
       //end
                
    }

    @FXML
    private void back_fnc(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/Produit.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("product");
                window.setScene(scene);
                 window.sizeToScene();
                window.show();
                //strat
        double y = window.getHeight();
        double x = window.getWidth();
        System.out.println("x:"+x+"y"+y);
        window.widthProperty().addListener((obs , oldval ,newval) -> {
        double y_ = window.getHeight();
        double x_ = window.getWidth();
        if(x_ <= x && y_ <= y){
            window.setHeight(y);
            window.setWidth(x);
            System.out.println("x:"+x_+"y"+y_);
        }else{
            System.out.println("boom");
        }
        });
       window.heightProperty().addListener((obs , oldval ,newval) -> {
        double y_ = window.getHeight();
        double x_ = window.getWidth();
        if(x_ <= x && y_ <= y){
        window.setHeight(y);
        window.setWidth(x);
        System.out.println("x:"+x_+"y"+y_);
        }else{
            System.out.println("boom");
        }
        }); 
       //end
    }

    @FXML
    private void del_fnc(ActionEvent event) throws SQLException, IOException {
           Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
           int id_pro_del = produit.getId_pro();
             
           
            ButtonType okbtn = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
            ButtonType nobtn = new ButtonType("NO",ButtonBar.ButtonData.CANCEL_CLOSE);
          
            Alert alert = new Alert(Alert.AlertType.WARNING,"VOULEZ VOUS VRAIMENT SUPPRIMER LE PRODUIT ["+produit.getLib_pro()+"] DE FAÇON DÉFINITIVE",okbtn,nobtn);
            alert.setTitle("ALERT");
            alert.setHeaderText("confirmer pour continuer");
            alert.initOwner(owner);
            
            Optional<ButtonType> res = alert.showAndWait();
           
            if(res.orElse(nobtn) == okbtn){
                 int id_pro = produitDao.DeleteProduitById(id_pro_del);
            if(id_pro != 0){
                
            showAlert(Alert.AlertType.ERROR, owner, "success", "le produit a bien ete supprimer");
             FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/Produit.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("product");
                window.setScene(scene);
                window.sizeToScene();
                window.show();
                //strat
        double y = window.getHeight();
        double x = window.getWidth();
        System.out.println("x:"+x+"y"+y);
        window.widthProperty().addListener((obs , oldval ,newval) -> {
        double y_ = window.getHeight();
        double x_ = window.getWidth();
        if(x_ <= x && y_ <= y){
            window.setHeight(y);
            window.setWidth(x);
            System.out.println("x:"+x_+"y"+y_);
        }else{
            System.out.println("boom");
        }
        });
       window.heightProperty().addListener((obs , oldval ,newval) -> {
        double y_ = window.getHeight();
        double x_ = window.getWidth();
        if(x_ <= x && y_ <= y){
        window.setHeight(y);
        window.setWidth(x);
        System.out.println("x:"+x_+"y"+y_);
        }else{
            System.out.println("boom");
        }
        }); 
       //end
            }else{
            showAlert(Alert.AlertType.ERROR, owner, "Erreur", "problem lors de suppression !");
            
         } 
            }
            
            

         
    }

   
    
}
