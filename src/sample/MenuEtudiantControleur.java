package sample;

import Classes.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;


public class MenuEtudiantControleur { // ce truc est fait pour controler le classromm / pareil pout MenuEnseignantControleur

    private Parent fxml;

    @FXML
    Button decon;

    @FXML
    public void showDeco(){
        decon.setVisible(true);
    }
    @FXML
    public void hideDeco(){
        decon.setVisible(false);
    }

    Utilisateur u = new Utilisateur();
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label id;

    @FXML
    private BorderPane etudiantPane;


    public void setUser(){  // on recup les infos de l'utilisateur pour les afficher dans le toolbar (appelle a cette méthode dans MenuEtudiant
        nom.setText(u.getNom());
        prenom.setText(u.getPrenom());
        id.setText(u.getIdd());
    }

    @FXML
    public void deco(MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).getScene().getWindow().hide();
        open("Sample.fxml");
    }

    public void open(String name){  //celle ci ouvre seulement
        Parent root = null;
        Stage primaryStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource(name));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
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

    public void getPage(String fxmlFileName) {   //ouvrir le fichier fxml convenable selon le bouton
        Pane vue= null;
        try {
            URL filePath = this.getClass().getResource("/sample/" + fxmlFileName + ".fxml");



            if (filePath == null) throw new java.io.FileNotFoundException("Cette page est corrompue");

            vue = new FXMLLoader().load(filePath);
            etudiantPane.setCenter(vue);

        }catch(Exception e ) {
            e.printStackTrace();
        }

    }

    @FXML
    public void getEmploi(){
        getPage("EmploiEtudiant");
    }

    @FXML
    public void getDrive(){
        getPage("Drive");
    }
    @FXML
    public void getDevoir(){
        getPage("DevoirsEtudiant");
    }
    @FXML
    public void getNotes(){
        getPage("NotesEtudiant");
    }
    @FXML
    public void getEnseignants(){
        getPage("EnseignantsEtudiant");
    }
    public void getClassroom(){
        getPage("Classroom");
    }

}

