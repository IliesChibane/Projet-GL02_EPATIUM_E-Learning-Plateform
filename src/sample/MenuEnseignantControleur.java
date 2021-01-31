package sample;

import Classes.Utilisateur;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.Button;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

public class MenuEnseignantControleur {


    @FXML
    Button decon;

    @FXML
    private javafx.scene.control.Label iddd;
    @FXML
    private javafx.scene.control.Label nomm;
    @FXML
    private Label prenoom;
    @FXML
    private BorderPane enseignantPane;

    private Utilisateur u = new Utilisateur();


    public void setUser(){
        nomm.setText(u.getNom());
        prenoom.setText(u.getPrenom());
        iddd.setText(u.getIdd());
    }


    @FXML
    public void showDeco(){
        decon.setVisible(true);
    }
    @FXML
    public void hideDeco(){
        decon.setVisible(false);
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
            enseignantPane.setCenter(vue);

        }catch(Exception e ) {
            e.printStackTrace();
        }

    }

    @FXML
    public void getEmploi(){
        getPage("EmploiEnseignant");
    }

    @FXML
    public void getDrive(){
        getPage("Drive");
    }
    @FXML
    public void getDevoir(){
        getPage("DevoirsEnseignant");
    }
    @FXML
    public void getNotes(){
        getPage("NotesEnseignant");
    }
    @FXML
    public void getEtudiants(){
        getPage("EtudiantsEnseignant");
    }
    public void getClassroom(){
        getPage("Classroom");
    }
}
