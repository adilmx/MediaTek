package controllers;

import beans.Produit;
import beans.Type;
import dao.ProduitDao;
import dao.TypeDao;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CreationProduitController implements Initializable {
    //declarations
    private final ProduitDao produitDao = new ProduitDao() ;
    private final TypeDao typeDao = new TypeDao();
    
    private List<Type> types_arr = new ArrayList<>();
    private List<String> tp = new ArrayList<>();
    
    private Produit produit;
    private Type type;
    
    private File file;
    private final FileChooser fileChooser = new FileChooser();
    private static double rand_id  ;
    
    //fxml declarations
    @FXML
    private Text text;
    @FXML
    private GridPane gp;
    @FXML
    private TextField nom_pro;
    @FXML
    private TextField prix_pro;
    @FXML
    private TextField qte_pro;
    @FXML
    private ComboBox<String> type_pro;
    @FXML
    private TextField desc_pro;
    @FXML
    private Button save_pro;
    @FXML
    private Button back_btn;
    @FXML
    private TextField img_src_btn;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //get items for combobox
         try {
            types_arr = typeDao.getAllTypes();
        } catch (SQLException ex) {
            Logger.getLogger(CreationProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Type type : types_arr) {
            tp.add(type.getLib_type());
        }
       
        ObservableList<String> types = FXCollections.observableArrayList(tp);
        type_pro.setItems(types);
        
        
    }    
    
      private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

      //save products
    @FXML
    private void save_produit_fnc(ActionEvent event) throws SQLException, IOException {
         if (nom_pro.getText().isEmpty() || prix_pro.getText().isEmpty() || type_pro.getSelectionModel().isEmpty() || qte_pro.getText().isEmpty() || desc_pro.getText().isEmpty() ) {
            Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, owner, "Erreur", "Tous les champs doivent être remplis!");
         
       }else{
         if(!Pattern.matches("[0-9]+", prix_pro.getText()) || !Pattern.matches("[0-9]+", qte_pro.getText())){
             Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, owner, "Erreur", "le prix et la quantite doivent etre des nombres seullement!");
        
        } else {
             type = typeDao.getTypeByLib(type_pro.getSelectionModel().getSelectedItem());
             produit = new Produit();
             produit.setLib_pro(nom_pro.getText());
             produit.setPrix_unit_pro(Double.parseDouble(prix_pro.getText()));
             produit.setQte_dispo(Integer.parseInt(qte_pro.getText()));
             produit.setType_pro(type.getId_type());
             produit.setDesc_pro(desc_pro.getText());
             produit.setPic_src_pro(img_src_btn.getText());
             if(file != null ){
            saveFile(file);
        }else{
            System.out.println("no file");
        }
            int id_pro = produitDao.saveProduit(produit);
            if(id_pro != 0){
                Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
            showAlert(Alert.AlertType.INFORMATION, owner, "success", "le produit a bien etet enregestrer");
            
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
                Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, owner, "Erreur", "problem lors de votre enrigestrement !");
            }
         }
        }
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
    private void open_file(MouseEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        file = fileChooser.showOpenDialog(window) ;
        if(file != null ){
            openFile(file);
        }else{
            System.out.println("no file");
        }
    }
  public void openFile(File file) throws IOException{
      rand_id = (int)((Math.random() * ((999 - 100) + 1)) + 100) ;
        String wd = System.getProperty("user.home") + File.separator + "Documents" ;
        wd += File.separator + "images_produits" ;
        File images_dir = new File(wd);
        if (images_dir.exists()) {
            System.out.println(images_dir + " already exists");
        } else if (images_dir.mkdirs()) {
            System.out.println(images_dir + " was created");
        } else {
            System.out.println(images_dir + " was not created");
        }
        Desktop desktop = Desktop.getDesktop();
       desktop.open(file);
             String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1 , file.getName().length()) ;
           
            img_src_btn.setText(wd+File.separator+"image_produit_id_rdm"+rand_id+"."+ext);
            File dest = new File(wd+File.separator+"image_produit_id_rdm"+rand_id+"."+ext);
             while (dest.exists()) {  
           rand_id = (int)((Math.random() * ((999 - 100) + 1)) + 100) ;
           img_src_btn.setText(wd+File.separator+"image_produit_id_rdm"+rand_id+"."+ext);
            System.out.println(images_dir + " already exists");
        } 
            
    }
     public void saveFile(File file) throws IOException{
        String wd = System.getProperty("user.home") + File.separator + "Documents" ;
        wd += File.separator + "images_produits" ;
        File images_dir = new File(wd);
        if (images_dir.exists()) {
            System.out.println(images_dir + " already exists");
        } else if (images_dir.mkdirs()) {
            System.out.println(images_dir + " was created");
        } else {
            System.out.println(images_dir + " was not created");
        }
        
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1 , file.getName().length()) ;
            File dest = new File(wd+File.separator+"image_produit_id_rdm"+rand_id+"."+ext);
            
           
            Files.copy(file.toPath(), dest.toPath(),StandardCopyOption.REPLACE_EXISTING);

    }
            




    
    
}