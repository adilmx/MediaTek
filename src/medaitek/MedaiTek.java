package medaitek;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MedaiTek extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Facture.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setMinHeight(stage.getMinHeight());
        stage.setMinWidth(stage.getMinWidth());
        //strat
        double y = stage.getHeight();
        double x = stage.getWidth();
        System.out.println("x:"+x+"y"+y);
        stage.widthProperty().addListener((obs , oldval ,newval) -> {
        double y_ = stage.getHeight();
        double x_ = stage.getWidth();
        if(x_ <= x && y_ <= y){
            stage.setHeight(y);
            stage.setWidth(x);
            System.out.println("x:"+x_+"y"+y_);
        }else{
            System.out.println("boom");
        }
        });
       stage.heightProperty().addListener((obs , oldval ,newval) -> {
        double y_ = stage.getHeight();
        double x_ = stage.getWidth();
        if(x_ <= x && y_ <= y){
        stage.setHeight(y);
        stage.setWidth(x);
        System.out.println("x:"+x_+"y"+y_);
        }else{
            System.out.println("boom");
        }
        }); 
       //end
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
