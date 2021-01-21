package sample;

import Classes.Utilisateur;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginControleur {

	public Utilisateur u = new Utilisateur();
	
	
@FXML
private TextField id;
 
 @FXML
 private TextField nom;
 
 @FXML
 private TextField prenom;
 
 @FXML
 private TextField mdp;
 
 @FXML
 private TextField confirmationMdp;
 
 @FXML
 private ComboBox<TypeU> type;
 
 @FXML
 private TextField specialite;
 
 @FXML
 private TextField niveau;
 
 @FXML
 private TextField section;
 
 @FXML
 private TextField groupe;
 
 @FXML
 private void authentification(ActionEvent event) {
	  String mode = u.identification(id.getText(),mdp.getText());
	  switch (mode){
          case "etudiant" : open("MenuEtudiant.fxml","etudiant");
          break;
          case "enseignant" : open("MenuEnseignant.fxml","enseignant"); break;
          default : System.out.println("ALLEZ VOUS INSCRIRE DABORD !!!!!");
      }
     ((Node)event.getSource()).getScene().getWindow().hide();
 }
 
 
 @FXML
 private void inscription(ActionEvent event) {
	  
	  if(mdp.getText().equals(confirmationMdp.getText())) {
	  if (u.inscription(id.getText(),nom.getText(),prenom.getText(),mdp.getText(), Integer.parseInt( groupe.getText()),type.getSelectionModel().getSelectedItem().toString(), specialite.getText(), section.getText(), niveau.getText())) System.out.println("yeeehaw");
	  else System.out.println("nooooooooooooooo");
	  }else {
		  System.out.println("Veuillez saisir des mots de passes identiques");
	  }
 }

@FXML 
 public void init() {
       /*Les strings ne sont pas supporter par les combobox de javaFX quand tu veux en remplir une
         tu dois creer une enum comme je viens de faire*/
       this.type.setItems(FXCollections.observableArrayList(TypeU.values()));

	}


 public void open(String fxml, String role){    //ceci est juste pour ouvrir la fenetre convenable
   //  Parent root = null;
     Stage primaryStage = new Stage();
     FXMLLoader loader = new FXMLLoader();
     Pane root = null;
     try {
         root = loader.load(getClass().getResource(fxml).openStream());


         if(role.equals("etudiant")) {
             MenuEtudiantControleur menu = (MenuEtudiantControleur) loader.getController();
             menu.setUser();
             menu.getClassroom();
         }else if(role.equals("enseignant")){
             MenuEnseignantControleur menu = (MenuEnseignantControleur) loader.getController();
             menu.setUser();
             menu.getClassroom();
         }

         Scene scene = new Scene(root);
         scene.setFill(Color.TRANSPARENT);
         scene.getStylesheets().setAll(
                 getClass().getResource("/sample/style.css").toExternalForm()
         );

         primaryStage.setScene(scene);

         primaryStage.initStyle(StageStyle.TRANSPARENT);


         primaryStage.setTitle("Plateforme Epatium");
         Image image = new Image("/Pictures/logo.png");
         primaryStage.getIcons().add(image);
         primaryStage.show();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }


  }

