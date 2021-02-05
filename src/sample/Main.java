package sample;
import Connectivity.ConnectionClass;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
       try {
           ConnectionClass cc= new ConnectionClass();
           cc.getConnection();

        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        Scene scene = new Scene(root);
       scene.setFill(Color.TRANSPARENT);  
       scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
      
        
        primaryStage.setTitle("Plateforme Epatium");
        Image image = new Image("/Pictures/logo.png");
        primaryStage.getIcons().add(image);
        primaryStage.show();

    
    }catch(Exception e) {
           Dialogue.afficherDialogue(e.getMessage());
    }
    	
    }


    public static void main(String[] args) {
        launch(args);
    }
}
