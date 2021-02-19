package sample;

import Classes.Utilisateur;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginControleur implements Initializable {

	public Utilisateur u = new Utilisateur();
	
	
@FXML
private TextField id;
 
 @FXML
 private TextField nom;
 
 @FXML
 private TextField prenom;
 
 @FXML
 private PasswordField mdp;
 
 @FXML
 private PasswordField confirmationMdp;
 
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
 private TextField email;

 private String prob ="";
 private boolean doo = true;
 private ArrayList<TextField> fields= new ArrayList<TextField>();
 
 @FXML
 private void authentification(ActionEvent event) {
	  String mode = u.identification(id.getText(),mdp.getText());
	  switch (mode){
          case "etudiant" : open("MenuEtudiant.fxml","etudiant");
              ((Node)event.getSource()).getScene().getWindow().hide();
          break;
          case "enseignant" : open("MenuEnseignant.fxml","enseignant");
              ((Node)event.getSource()).getScene().getWindow().hide();
          break;
          default : Dialogue.afficherDialogue("Allez vous inscrire d'abord");
      }

 }
 private void validerId(){
     //si l'id du prof dépasse la taille
     if(id.getText().length() >6 && type.getSelectionModel().getSelectedItem().toString().equals("Enseignant")){
         prob = prob.concat(" l'ID de l'enseignant ne doit pas depasser 6 caracteres");
         doo = false;
     }
     // si le matricule de l'étudiant dépasse la taille
     if(id.getText().length() >12 && type.getSelectionModel().getSelectedItem().toString().equals("Etudiant")){
         prob = prob.concat(" l'ID de l'etudiant ne doit pas depasser 12 caracteres");
         doo = false;
     }
     // si le champ de l'id est vide
     if(id.getText().isEmpty()){
         prob = prob.concat(" Veuillez remplir le champ de l'ID ");
         doo = false;
     }


     if (  type.getSelectionModel().getSelectedItem().toString().equals("Etudiant")) {

         if (id.getText().charAt(0) == 'E') { // si le matricule de l'étudiant commence par E
             prob = prob.concat(" Votre ID indique que vous etes un Enseignant alors que vous avez choisit le role Etudiant. ");
             doo = false;
         } else {
                 if(queChiffres(id.getText()) == false){ // si le matricule de l'étudiant ne contient pas que des chiffres
                     prob = prob.concat(" Votre ID doit contenir que des chiffres. ");
                     doo = false;
                 }
             }

     }else{
         if(id.getText().charAt(0) !='E'){ // si l'id de l'enseignant ne commence pas par E
            prob =  prob.concat(" Votre ID n'est pas convenable pour le role Enseignant. ");
             doo = false;
         }else{
             String sub = id.getText().substring(1);
             if(queChiffres(sub) == false) {
                 prob = prob.concat(" Votre ID doit contenir au plus une lettre E au debut. ");
                 doo = false;
             }
         }
     }

     }

 private boolean queChiffres(String phrase) {

     for (int i = 0; i < phrase.length(); i++) {
         if (!(phrase.charAt(i) >= '0') || !(phrase.charAt(i) <= '9')) {
             return false;
         }
     }
     return true;
 }

    private boolean queLettres(String phrase) {
        String str = phrase.toLowerCase();
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (!(ch >= 'a' && ch <= 'z')) {
                return false;
            }
        }
        return true;
    }
 private void validerGroupe(){
     if (type.getSelectionModel().getSelectedItem().toString().equals("Etudiant")) {

             if (queChiffres(groupe.getText()) ==false) {
                 prob = prob.concat(" Il faut remplir le champ du groupe avec des chiffres seulement. ");
                 doo = false;
             }

         }
     }

 private void validerFields(){
     if (type.getSelectionModel().getSelectedItem().toString().equals("Etudiant")) {
         boolean champVide = false;
         for (TextField t : fields) {
             if (t.getText().isEmpty()) {
                 champVide = true;
                 break;
             }
         }
         if (champVide == true) {
             prob = prob.concat(" Tous les champs additionnels de l'etudiant sont obligatoires. ");
             doo = false;
         }
     }
 }
private void validerEmail(){
     if(email.getText().isEmpty()){
         prob = prob.concat(" Le champ email est obligatoire. ");
         doo = false;
     }else {
         if (!email.getText().contains("@")) {
             prob = prob.concat(" Le format de votre email est incorrect. ");
             doo = false;
         }
     }
}
 private void validerMDP(){
     if(mdp.getText().isEmpty() || mdp.getText().isEmpty()){
         prob =  prob.concat(" Il faut remplir le champ du mot de passe ainsi que sa  confirmation. ");
         doo = false;
     }
     if(! mdp.getText().equals(confirmationMdp.getText())){
         prob =  prob.concat(" Les deux champs de mot de passe ne sont pas identiques. ");
         doo = false;
     }
 }
 private void validerNom(){
     if(nom.getText().isEmpty() ){
         prob =  prob.concat(" Le nom est un champ obligatoire. ");
         doo = false;
     }
     if(queLettres(nom.getText()) == false){
         prob =  prob.concat(" Le Nom ne doit pas contenir des caracteres alphanumeriques. ");
         doo = false;
     }
 }
    private void validerPreNom(){
        if(nom.getText().isEmpty()){
            prob =  prob.concat(" Le Prenom est un champ obligatoire. ");
            doo = false;
        }
        if(queLettres(prenom.getText())==false){
            prob =  prob.concat(" Le Prenom ne doit pas contenir des caracteres alphanumeriques. ");
            doo = false;
        }
    }
 
 
 @FXML
 private void inscription(ActionEvent event) {
     prob = "";
     validerId();
     validerNom();
     validerPreNom();
     validerMDP();
     validerGroupe();
     validerEmail();
     validerFields();
	  
	  if(doo) {
	      if(type.getSelectionModel().getSelectedItem().toString().equals("Etudiant")) {
              if (u.inscription(id.getText(), nom.getText(), prenom.getText(), mdp.getText(), Integer.parseInt(groupe.getText()), type.getSelectionModel().getSelectedItem().toString(), specialite.getText(), section.getText(), niveau.getText(), email.getText()))
                  Dialogue.afficherDialogue("Inscription faites avec succès");
              else Dialogue.afficherDialogue("Il y a eu une erreur lors de l'inscription");
          }else{
              if (u.inscription(id.getText(), nom.getText(), prenom.getText(), mdp.getText(), 00, type.getSelectionModel().getSelectedItem().toString(), specialite.getText(), section.getText(), niveau.getText(), email.getText()))
                  Dialogue.afficherDialogue("Inscription faites avec succès");
              else Dialogue.afficherDialogue("Il y a eu une erreur lors de l'inscription");
          }
	  }else {
          Dialogue.afficherDialogue(prob);
	  }
 }
 public void showFields(){
     for (TextField t: fields){
         t.setVisible(true);
     }

 }
 public void hideFields(){
     for (TextField t: fields){
         t.setVisible(false);
     }
 }
 @FXML
 public void continuer(){
     if(type.getSelectionModel().getSelectedItem().toString().equals("Enseignant")) hideFields();
     else showFields();
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fields.clear();
        fields.add(specialite);
        fields.add(niveau);
        fields.add(section);
        fields.add(groupe);
        if(type != null) {
            this.type.setItems(FXCollections.observableArrayList(TypeU.values()));
            this.type.getSelectionModel().select(1);

            for (TextField t : fields) {
                t.setText("");
            }
            hideFields();
        }
    }
}

