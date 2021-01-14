package sample;

import Classes.Utilisateur;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
	  
	  
	  if (u.identification(id.getText(),mdp.getText())) System.out.println("yeeehaw");
	  else System.out.println("nooooooooooooooo");
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
	  //  type.getSelectionModel().select( type.getSelectionModel().getSelectedItem());;
	}

//@FXML
public void choisir() {
	//Définir une méthode pour choisir etudiant ou bien enseignant
}
  }

