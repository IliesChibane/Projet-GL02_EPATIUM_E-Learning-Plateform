package sample;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import Classes.Utilisateur;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Controller implements Initializable {   
	

	
    @FXML
    private VBox vboxx;  
    
    private Parent fxml = null;
	
    
  
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vboxx);
        t.setToX(vboxx.getLayoutX() * 23);
        t.play();
        t.setOnFinished((e) ->{
            
                try {
					//fxml = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
                	final FXMLLoader fxmload = new FXMLLoader(Controller.class.getClassLoader().getResource("SignIn.fxml"));
                     fxml = fxmload.load();
                	
                	
				} catch (Exception ex) {
					
				}
                vboxx.getChildren().removeAll();
                //vboxx.getChildren().setAll(fxml);
                
            
        });
    }
    @FXML
    private void signin(ActionEvent event){
          TranslateTransition t = new TranslateTransition(Duration.seconds(1), vboxx);
        t.setToX(vboxx.getLayoutX() *23);
        t.play();
        t.setOnFinished((e) ->{
            try{
            	
                fxml = FXMLLoader.load(getClass().getResource("SignIn.fxml"));

            }catch(Exception ex){
                ex.printStackTrace();
            }
            vboxx.getChildren().clear();
          
            vboxx.getChildren().setAll(fxml);
         
        });
    }   
    @FXML
    private void signup(ActionEvent event){
          TranslateTransition t = new TranslateTransition(Duration.seconds(1), vboxx);
        t.setToX(0);
        t.play();
        t.setOnFinished((e) ->{
            try{
                fxml = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
                
            }catch(Exception ex){
                
            }
            vboxx.getChildren().clear();
            vboxx.getChildren().setAll(fxml);
        });
    } 
   @FXML
	private void quit() {
    
				 Platform.exit();
	    	        System.exit(0);
    }
}
    