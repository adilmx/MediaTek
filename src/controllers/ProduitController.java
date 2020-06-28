package controllers;

import beans.Produit;
import beans.Type;
import dao.ProduitDao;
import dao.TypeDao;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;


public class ProduitController implements Initializable {

    //declarations
    private final ProduitDao produitDao = new ProduitDao() ;
    private final TypeDao typeDao = new TypeDao();
    
   
    
    private Produit produit;
    private Produit produit_type;
    private List<Produit> produits = new ArrayList<>();;
    
    private List<Type> types_arr = new ArrayList<>();
    private List<String> tp = new ArrayList<>();
    
    private Type type;
    private ImageView imageView ;
    private AnchorPane anchorePane;
    private AnchorPane anchorePane_sub;
    private Button button;
    private Text text1 ;
    private Text text2 ;
    
    @FXML
    private Text text;
    @FXML
    private AnchorPane ap_list_pro;
    @FXML
    private GridPane gp;
    @FXML
    private Button back;
    @FXML
    private ComboBox<String> types_filter;
    @FXML
    private Button create_btn;
    @FXML
    private Button srh_btn;
    @FXML
    private TextField srh_txt;

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
     public void initdata(List<Produit> p) throws SQLException{
       
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {//get products list
        //get items for combobox
         try {
            types_arr = typeDao.getAllTypes();
        } catch (SQLException ex) {
            Logger.getLogger(CreationProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
         tp.add("touts");
        for (Type type : types_arr) {
            tp.add(type.getLib_type());
        }
       
        ObservableList<String> types = FXCollections.observableArrayList(tp);
        types_filter.setItems(types);
        
        types_filter.getSelectionModel().select(0);
        
        ///////////
        if(produits.isEmpty()){
         try {
            produits = produitDao.getAllProduit();
             System.out.println("i am here !!");
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
        }
        int i = 0;
        int j = 0;
        int last = 0;
       for(Produit produit: produits) { 
            File imageFile = new File(produit.getPic_src_pro());
           String fileLocation = imageFile.toURI().toString();
         Image fxImage = new Image(fileLocation);  
            imageView = new ImageView(fxImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(200);
            button = new Button("plus d'infos");
            text1 = new Text("nom produit: "+produit.getLib_pro());
            text2 = new Text("prix produit: "+produit.getPrix_unit_pro()+"DH");
            anchorePane_sub = new AnchorPane();
            anchorePane = new AnchorPane();
            
            
            anchorePane_sub.setTopAnchor(imageView,14.0) ;
            anchorePane_sub.setLeftAnchor(imageView,16.0) ;
            anchorePane_sub.setRightAnchor(imageView,16.0) ;
            anchorePane_sub.setBottomAnchor(imageView,96.0) ;
            
            
            anchorePane_sub.setRightAnchor(button,18.0) ;
            anchorePane_sub.setBottomAnchor(button,20.0) ;
            
            anchorePane_sub.setTopAnchor(text1,117.0) ;
            anchorePane_sub.setLeftAnchor(text1,18.0) ;
            anchorePane_sub.setBottomAnchor(text1,44.0) ;
            
            anchorePane_sub.setTopAnchor(text2,145.0) ;
            anchorePane_sub.setLeftAnchor(text2,18.0) ;
            anchorePane_sub.setBottomAnchor(text2,16.0) ;
           
            
            anchorePane_sub.getChildren().addAll(imageView,button,text1,text2);
            
            anchorePane_sub.setPrefSize(267, 237);
            anchorePane_sub.getStyleClass().add("card-v1");
            anchorePane.setTopAnchor(anchorePane_sub,7.0) ;
            anchorePane.setLeftAnchor(anchorePane_sub,17.0) ;
            anchorePane.setRightAnchor(anchorePane_sub,17.0) ;
            anchorePane.setBottomAnchor(anchorePane_sub,7.0) ;
            
            anchorePane.getChildren().add(anchorePane_sub);
            
            
            button.getStyleClass().add("btn-v2");
            button.setCursor(Cursor.HAND);
            /*button.setId(l);*/
            gp.add(anchorePane, i, j);
            button.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    //start
                     FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/AboutProduit.fxml"));
                Parent root;
                    try {
                        root = loader.load();
                    
                Scene scene = new Scene(root);

                AboutProduitController controller = loader.getController();
                controller.initdata(produit);
                
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Produits");
                /*window.getIcons().add(new Image("/images/logo_trans.png"));*/

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
                } catch (IOException ex) {
                        Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //end
                }
            });
             i++;
             last++;
            if (i >  2 ) {
                if(last != produits.size()){
                    j++;
                i = 0;
                if (j >= gp.getRowConstraints().size()) {
                gp.addRow(j);
                ap_list_pro.setPrefHeight(ap_list_pro.getPrefHeight() + 255.5);
                }
                
            }
            }
            
        }
    }     

    @FXML
    private void back_fnc(ActionEvent event) {
        
    }

    @FXML
    private void Gotocreate_fnc(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/CreationProduit.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Create product");
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
    private void filter_types_fnc(ActionEvent event) throws IOException, SQLException {
         if(types_filter.getSelectionModel().getSelectedItem() != "touts"){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/Produit_filter.fxml"));
                Parent root = loader.load();
                ProduitFilterController controller = loader.getController();
                
                 type = typeDao.getTypeByLib(types_filter.getSelectionModel().getSelectedItem());
                 produits = produitDao.getProduitByType(type.getId_type());
                 
                  controller.initdata(produits);
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("product");
                window.setScene(scene);
                 window.sizeToScene();
                 if(produits.isEmpty()){
            showAlert(Alert.AlertType.ERROR, window, "OOPs!pas des produits", "Pas de produit pour ce type ! vous pouvez creer une pour lui");
        }
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
                 FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/Produit.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("product");
                window.setScene(scene);
                window.sizeToScene();
                  try {
            produits = produitDao.getAllProduit();
             System.out.println("i am here !!");
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
           if(produits.isEmpty()){
            showAlert(Alert.AlertType.ERROR, window, "OOPs!pas des produits", "Pas de produit enregitree! vous pouvez creer une maintenant");
        }
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
    }

    @FXML
    private void srh_btn_fnc(ActionEvent event) throws IOException, SQLException {
        if(srh_txt.getText().isEmpty()){
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, window, "inserer u", "Pas de produit a chercher! svp remplissez la case de recherche");
       
        }else{
             FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/Produit_filter.fxml"));
                Parent root = loader.load();
                ProduitFilterController controller = loader.getController();
                
                 produits = produitDao.getProduitByLib(srh_txt.getText());
                 
                  controller.initdata_srh(produits);
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("product");
                window.setScene(scene);
                 window.sizeToScene();
                 if(produits.isEmpty()){
            showAlert(Alert.AlertType.ERROR, window, "OOPs!pas des produits", "Pas de produit avec ce nom !");
        }
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
    }

   
    
    
}
