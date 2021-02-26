package sample;

import Classes.Utilisateur;
import Connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParametresController {
    @FXML
    private Button closeButton;
    @FXML
    private PasswordField ancienMdp;
    @FXML
    private PasswordField nouveauMdp;
    private Utilisateur u = new Utilisateur();
    @FXML private TextField nom;
    @FXML private TextField prenom;
    @FXML private TextField email;
    private String prob;
    private boolean doo = true;



    public void getPage(String fxmlFileName) {   //ouvrir le fichier fxml convenable selon le bouton

        try {
            URL filePath = this.getClass().getResource("/sample/" + fxmlFileName + ".fxml");
            if (filePath == null) throw new java.io.FileNotFoundException("Cette page est corrompue");
          //  vue = new FXMLLoader().load(filePath);


        }catch(Exception e ) {
            e.printStackTrace();
        }

    }



    @FXML
    private void quit() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public String chercherPassword(){
        ResultSet rs =null;
        PreparedStatement ps = null;
        ConnectionClass cc = new ConnectionClass();

        try {
            Connection conn = ConnectionClass.c;

            String sql =  "Select mdp From enseignant where id_prof = ? ";
            String sql2 = "Select mdp From etudiant WHERE matricule = ? ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, u.getIdd());
            rs = ps.executeQuery();

            if(rs.next()){
                u.setMdp(rs.getString("mdp"));
                // on prend ces informations pour les afficher lors de l'ouverture de la fenetre
                u.setMode("enseignant");

            }
            ps = conn.prepareStatement(sql2);
            ps.setString(1,  u.getIdd());

            rs = ps.executeQuery();

            if (rs.next()) {
                u.setMode("etudiant");
                u.setMdp(rs.getString("mdp"));

            }

        } catch (Exception e) {

            Dialogue.afficherDialogue(e.getMessage());
        }finally {
            try {
                assert ps != null;
                ps.close();
                assert rs != null;
                rs.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }

        }

        return u.getMdp();
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

    @FXML
    private void changerMdp(){
          if(ancienMdp.getText().equals(chercherPassword())){

              u.changerMotDePasse(nouveauMdp.getText());
              Dialogue.afficherDialogue("Votre mot de passe a été changé !");
              quit();

          }else{
              Dialogue.afficherDialogue("Votre Ancien mot de passe n'est pas correct");
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

    @FXML
    private void changerDonnees() throws IOException {
        validerEmail();
        validerNom();
        validerPreNom();
        if(doo == true) {
            if(u.changerDonnees(nom.getText(), prenom.getText(), email.getText())) {
                Dialogue.afficherDialogue("Vos informations ont ete mises à jour !");
                quit();

            }else{
                Dialogue.afficherDialogue("Il y a eu un problème avec la mise à jour");
            }
        }
    }

    @FXML
    private void afficherDonnes(){
        u.afficherInfos();
        nom.setText(u.getNom());
        prenom.setText(u.getPrenom());
        email.setText(u.getEmail());
    }
}
