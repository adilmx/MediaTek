
package controllers;

import beans.Client;
import beans.Commande;
import beans.Facture;
import beans.Journale;
import beans.Produit;
import beans.Type;
import dao.ClientDao;
import dao.CommandeDao;
import dao.FactureDao;
import dao.JournaleDao;
import dao.ProduitDao;
import dao.TypeDao;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.itextpdf.layout.element.Paragraph;
import java.io.FileNotFoundException;


import com.itextpdf.kernel.pdf.PdfDocument; 
import com.itextpdf.kernel.pdf.PdfWriter; 

import com.itextpdf.layout.Document; 
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Table; 
import com.itextpdf.layout.property.TextAlignment;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;



public class FactureController implements Initializable {

    //declarations
    private final ProduitDao produitDao = new ProduitDao() ;
    private final TypeDao typeDao = new TypeDao();
    private final FactureDao factureDao = new FactureDao();
    private final CommandeDao commandeDao = new CommandeDao();
    private final ClientDao clientDao = new ClientDao();
    private final JournaleDao journaleDao = new JournaleDao();
    
    
    private Produit produit;
    private Facture facture;
    private Commande commande;
    
    private Produit produit_type;
    private List<Produit> produits = new ArrayList<>();
    
    
    
     private static int if_selected = 0;
    private static int  alert_journale = 0;
    
    
    private List<Client> clients_arr = new ArrayList<>();
    private final List<String> tp = new ArrayList<>();
    
    ObservableList<CheckBox> ch_to_select = FXCollections.observableArrayList();
    ObservableList<ComboBox> qte_combo_selected = FXCollections.observableArrayList();
    
    private Type type;
    private ImageView imageView ;
    private AnchorPane anchorePane;
    private AnchorPane anchorePane_sub;
    private CheckBox checkBox;
    
    private Text text1 ;
    private Text text2 ;
    private Text text3 ;
    
     private File file;
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private static double rand_id  ;
    
    private static int id_fact_pdf = 0;
    
    private static double total_price = 0 ;
    private List<Journale> journales = new ArrayList<>();
    @FXML
    private Button back;
    @FXML
    private AnchorPane ap_list_pro;
    @FXML
    private GridPane gp;
    @FXML
    private Button cmd_btn;
    @FXML
    private ComboBox<String> users_combo;
    @FXML
    private CheckBox pdf_out_btn;
    @FXML
    private TextField name_pdf_txt;
    @FXML
    private Button link_pdf_btn;
//////////////////
    private static int count_test =0;
    ///////////////////////////
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
        //get items for combobox
         try {
            clients_arr = clientDao.getAllClient();
        } catch (SQLException ex) {
            Logger.getLogger(CreationProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
         clients_arr.forEach((client) -> {
             tp.add(client.getNom_Clt()+" "+client.getPrenom_Clt()+"_id:"+client.getCode_Clt());
        });
       
        ObservableList<String> clients_ = FXCollections.observableArrayList(tp);
        users_combo.setPromptText("Clients");
        users_combo.setItems(clients_);
        
        ///////////
        if(produits.isEmpty()){
         try {
            produits = produitDao.getAllProduit();
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
        }
        int i = 0;
        int j = 0;
        int last = 0;
       for(Produit produit: produits) { 
           
            try {
                File imageFile = new File(produit.getPic_src_pro());
                String fileLocation = imageFile.toURI().toString();
                Image fxImage = new Image(fileLocation);  
                imageView = new ImageView(fxImage);
                imageView.setFitHeight(100);
                imageView.setFitWidth(200);
                
                checkBox = new CheckBox("Ajouter");
                checkBox.setId(""+produit.getId_pro());
                ch_to_select.add(checkBox);
                
                type = typeDao.getTypeById(produit.getType_pro());
                text1 = new Text("nom produit: "+produit.getLib_pro());
                text2 = new Text("type produit: "+type.getLib_type());
                text3 = new Text("prix produit: "+produit.getPrix_unit_pro()+"DH");
                
                 ////
                ComboBox<String> qte_combo = new ComboBox<>();
                tp.clear();
                for (int k = produit.getQte_dispo(); k >= 1 ; k--) {
                    tp.add(""+k);
                    
                }

                ObservableList<String> qte_arr = FXCollections.observableArrayList(tp);
                qte_combo.setPromptText("Quantite commande");
                qte_combo.setItems(qte_arr);
                qte_combo.setId(""+produit.getId_pro());
                qte_combo_selected.add(qte_combo);
                
                ////
                anchorePane_sub = new AnchorPane();
                anchorePane = new AnchorPane();
                
                
                anchorePane_sub.setTopAnchor(imageView,30.0) ;
                anchorePane_sub.setLeftAnchor(imageView,16.0) ;
                anchorePane_sub.setRightAnchor(imageView,600.0) ;
                anchorePane_sub.setBottomAnchor(imageView,20.0) ;
                
                
                anchorePane_sub.setRightAnchor(checkBox,35.0) ;
                anchorePane_sub.setBottomAnchor(checkBox,22.0) ;
                anchorePane_sub.setLeftAnchor(checkBox,712.0) ;
                
                 anchorePane_sub.setRightAnchor(qte_combo,135.0) ;
                anchorePane_sub.setBottomAnchor(qte_combo,22.0) ;
                anchorePane_sub.setLeftAnchor(qte_combo,600.0) ;
                
               
                anchorePane_sub.setTopAnchor(text1,23.0) ;
                anchorePane_sub.setLeftAnchor(text1,281.0) ;
                anchorePane_sub.setRightAnchor(text1,0.0) ;
                
                anchorePane_sub.setTopAnchor(text2,60.0) ;
                anchorePane_sub.setLeftAnchor(text2,281.0) ;
                anchorePane_sub.setRightAnchor(text2,0.0) ;
                
                anchorePane_sub.setRightAnchor(text3,0.0) ;
                anchorePane_sub.setLeftAnchor(text3,281.0) ;
                anchorePane_sub.setBottomAnchor(text3,20.0) ;
                
                
                anchorePane_sub.getChildren().addAll(imageView,checkBox,text1,text2,text3,qte_combo);
                anchorePane_sub.getStyleClass().add("card-v3");
                
                
                
                
                text1.getStyleClass().add("txt-v5");
                text2.getStyleClass().add("txt-v5");
                text3.getStyleClass().add("txt-v5");
                
                checkBox.getStyleClass().add("chk-v1");
                checkBox.setCursor(Cursor.HAND);
                checkBox.setId(""+produit.getId_pro());
                /*button.setId(l);*/
                gp.add(anchorePane_sub, i, j);
                last++;
                if (j >  0 ) {
                    if(last != produits.size()){
                        j++;
                        gp.addRow(j);  
                        gp.setPrefHeight(gp.getPrefHeight() + 204.5);
                        ap_list_pro.setPrefHeight(ap_list_pro.getPrefHeight() + 203.5);
                    }
                }else{
                    j++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }    

    @FXML
    private void back_fnc(ActionEvent event) {
        
    }

    @FXML
    private void cmd_fnc(ActionEvent event) throws IOException, FileNotFoundException {
     
            //if at least one item is selected
            ch_to_select.forEach((cb) -> {
                if(cb.isSelected()){
                    if_selected++;
                }
            });
            
            if(!users_combo.getSelectionModel().isEmpty()){
                if(if_selected != 0){
                    
                    try {
                        String client_selected = users_combo.getSelectionModel().getSelectedItem().substring(users_combo.getSelectionModel().getSelectedItem().lastIndexOf(":") + 1, users_combo.getSelectionModel().getSelectedItem().length()  ) ;
                            int id_fact_success ;

                            facture = new Facture();
                            facture.setId_clt(Integer.parseInt(client_selected));
                            id_fact_success = factureDao.saveFacture(facture);
                            if(id_fact_success != 0){
                                id_fact_pdf = id_fact_success;
                                System.out.println(" 12314 kj :: "+id_fact_pdf);
                                Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                showAlert(Alert.AlertType.INFORMATION, owner, "success", "la facture a bien ete creer");
                            }else{
                                Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                showAlert(Alert.AlertType.ERROR, owner, "Erreur", "problem lors de creeation de facture client !");
                            }
                        
                        
                        ch_to_select.forEach((cb) -> {
                            if(cb.isSelected()){
                                if_selected++;
                                try {
                                    journales = journaleDao.getJournaleById(Integer.parseInt(cb.getId())) ;
                                    alert_journale = journales.size() ;
                                    
                                    commande = new Commande();
                                    commande.setId_fact(id_fact_pdf);
                                    commande.setId_pro(Integer.parseInt(cb.getId()));
                                    qte_combo_selected.forEach((combo) -> {
                                        if(combo.getId().equalsIgnoreCase(cb.getId()) ){
                                            String qte_coice = combo.getSelectionModel().getSelectedItem().toString() ;
                                            commande.setQte_com(Integer.parseInt(qte_coice));
                                        }
                                    });
                                    produit = new Produit();
                                    produit = produitDao.getProduitById(Integer.parseInt(cb.getId()));
                                    int success = commandeDao.saveCommande(commande);
                                    
                                    if(success == 1){
                                        
                                        count_test++;
                                        System.out.println("teeeeeest count :: "+count_test);
                                        
                                        journales = journaleDao.getJournaleById(Integer.parseInt(cb.getId())) ;
                                        alert_journale =  journales.size() - alert_journale  ;
                                        if(alert_journale != 0){
                                        Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        showAlert(Alert.AlertType.INFORMATION, owner, "success", "la commande de produit ["+produit.getLib_pro()+"] a bien ete cree");
                                        showAlert(Alert.AlertType.WARNING, owner, "alert", "Le produit ["+produit.getLib_pro()+"] est entrain de se vider de stock veuillez avertir les responsables de stock");
                                        
                                        
                                        }else{
                                            Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                            
                                        showAlert(Alert.AlertType.INFORMATION, owner, "success", "la commande de produit ["+produit.getLib_pro()+"] a bien ete cree");
                                        
                                        
                                        }
                                        
                                        
                                        }else if(success == 0){
                                        Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        showAlert(Alert.AlertType.ERROR, owner, "Erreur", "problem lors de creeation de commande !");
                                    }else{
                                        Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        showAlert(Alert.AlertType.ERROR, owner, "Erreur", "le stockage est insuffisant verifier la quantite choisie");
                                    
                                    }
                                } catch (SQLException ex) {
                                    System.out.println(" yeeah boy  "+ex.getMessage());
                                }
                            }
                        });
                         //start saving pdf
                                        if(pdf_out_btn.isSelected()){
                                         if(!link_pdf_btn.getText().equalsIgnoreCase("LIEN DE DOSSIER PDF") && !name_pdf_txt.getText().isEmpty()){
                                             Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                             String dest_pdf = link_pdf_btn.getText()+File.separator+name_pdf_txt.getText()+".pdf";
                                             String code_clt = users_combo.getSelectionModel().getSelectedItem().substring(users_combo.getSelectionModel().getSelectedItem().lastIndexOf(":") + 1, users_combo.getSelectionModel().getSelectedItem().length()  ) ;
                                             
                                             try {
                                                 System.out.println("heeeree id_fact_pdf  "+id_fact_pdf);
                                                 int pdf_res= savePDF(dest_pdf,Long.parseLong(code_clt),id_fact_pdf);
                                                 if(pdf_res != 0){
                                                    showAlert(Alert.AlertType.INFORMATION, owner, "success", "votre pdf a bien ete cree consulter : "+dest_pdf);
                                       
                                                 }else{
                                                   showAlert(Alert.AlertType.WARNING, owner,"pdf echec", "votre pdf n'a pas ete enregestrer correctemment");
                                       
                                                 }
                                             } catch (SQLException ex) {
                                                 Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
                                             }
                                         }
                                        }
                                        //end saving pdf
                                        //start reloading
                                            FXMLLoader loader = new FXMLLoader();
                                            loader.setLocation(getClass().getResource("/fxml/Facture.fxml"));
                                            Parent root = loader.load();
                                            Scene scene = new Scene(root);
                                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                            window.setTitle("facture");
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
                                            //end reloading

                    } catch (SQLException ex) {
                        Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    showAlert(Alert.AlertType.ERROR, owner, "Erreur", "aucune produit n'a ete selectionne svp choisissez une pour continuer !");
                }
            }else{
                Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
                showAlert(Alert.AlertType.ERROR, owner, "Erreur", "aucun client n'a ete selectionne svp choisissez un pour continuer !");
                
            }
       
    }

     private int savePDF(String dest_pdf , Long code_Clt , int id_fact) throws FileNotFoundException, IOException, SQLException {
         
         //starting get data
         Client client_pdf ;
         Produit produit_pdf ;
         Facture facture_pdf ;
         List<Commande> commandes_pdf;
         
        client_pdf = clientDao.getClientById(code_Clt) ;
         System.out.println(" save_nom_cli : "+client_pdf.getNom_Clt()+"id_cli_"+client_pdf.getCode_Clt());
          System.out.println(" id_fact "+id_fact);
        facture_pdf = factureDao.getFactureByIdFact(id_fact);
        commandes_pdf = commandeDao.getCommandeByIdFact(id_fact);
        
        if(commandes_pdf != null){
           
         
      //ending get data
         
         
       // Creating a PdfDocument object   
      String dest = dest_pdf;   
      PdfWriter writer = new PdfWriter(dest);       
         
      // Creating a PdfDocument object      
      PdfDocument pdf = new PdfDocument(writer);                  
    
      // Creating a Document object
      Document doc = new Document(pdf);
      
      doc.setBackgroundColor(ColorConstants.GRAY) ;
      Paragraph logo = new Paragraph("M E D I A T E K").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(20);
      Paragraph date = new Paragraph("DATE COMMANDE : "+facture_pdf.getDate_fact()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setFontColor(ColorConstants.DARK_GRAY);
      
      Paragraph clt = new Paragraph("CLIENT : "+client_pdf.getNom_Clt()+" "+client_pdf.getPrenom_Clt()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(10)
                .setFontColor(ColorConstants.DARK_GRAY);
      Paragraph title = new Paragraph("FACTURE D'ACHAT N* "+id_fact_pdf).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(30)
                .setFontColor(ColorConstants.RED);
      
      Paragraph esp1 = new Paragraph("\n \n \n");
      /* Paragraph esp2 = new Paragraph("\n \n \n \n \n \n \n \n \n"); */
      
      title.setTextAlignment(TextAlignment.CENTER);    
      date.setTextAlignment(TextAlignment.RIGHT);
      
      // Creating a table         
      Table table = new Table(6);    
      
      table.setTextAlignment(TextAlignment.CENTER);
 
      // Adding cells to the table       
      table.addCell("NOM DE PRODUIT");   
       
      table.addCell("TYPE DE PRODUI");   
      
      table.addCell("QUANTITE");  
       
      table.addCell("PRIX(DH)(HT)"); 
      
       table.addCell("TVA(20)%");  
       
      table.addCell("PRIX(DH)(TTC)");
      
      // Creating an Area Break          
      AreaBreak aB = new AreaBreak(); 
      int i_table = 0;
      
       for (Commande cmd : commandes_pdf) {
                produit_pdf = produitDao.getProduitById(cmd.getId_pro());
                Type type_pdf ;
                type_pdf = typeDao.getTypeById(produit_pdf.getType_pro());
                table.addCell(produit_pdf.getLib_pro()); 
                table.addCell(type_pdf.getLib_type()); 
                table.addCell(""+cmd.getQte_com()); 
                double price = produit_pdf.getPrix_unit_pro()*cmd.getQte_com();
                table.addCell(""+price);  
                table.addCell(""+price*0.2); 
                table.addCell(""+price*1.2);
                total_price = total_price + price*1.2 ;
                i_table++;
                if(i_table > 10){
                    doc.add(aB);
                    i_table = 0;
                }
            }
      
      Paragraph prix_tot = new Paragraph("PRIX TOTALE : "+total_price+"DH").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)) 
                .setFontSize(14)
                .setFontColor(ColorConstants.RED)
              .setTextAlignment(TextAlignment.RIGHT) ;
                
   
      // Adding area break to the PDF       
      
      // Adding Table to document  
      doc.add(logo); 
      doc.add(date); 
      doc.add(clt); 
      doc.add(title);  
      doc.add(esp1);
      doc.add(table); 
      /* doc.add(esp2); */
      doc.add(prix_tot);          
         
      // Closing the document       
      doc.close();
      
      System.out.println("Table created successfully..");   
       return 1;
      }
      return 0;
    }
     
    @FXML
    private void user_combo_fnc(ActionEvent event) {
    }

    @FXML
    private void link_pdf_btn_fnc(ActionEvent event) throws IOException {
         Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        file = directoryChooser.showDialog(window) ;
        if(file != null ){
           link_pdf_btn.setText(file.getAbsolutePath());
        }else{
            System.out.println("no file");
        }
    }
    
    
}
