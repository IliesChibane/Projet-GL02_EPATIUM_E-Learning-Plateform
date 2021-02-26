package sample;

import Classes.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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


    @FXML
    Button decon;
    @FXML
    Button param;

    @FXML
    public void showDeco(){
        decon.setVisible(true);
        param.setVisible(true);
    }
    @FXML
    public void hideDeco(){
        decon.setVisible(false);
        param.setVisible(false);
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

    @FXML
    private ImageView photoProfile;


    public void setUser(){  // on recup les infos de l'utilisateur pour les afficher dans le toolbar (appelle a cette méthode dans MenuEtudiant
        nom.setText(u.getNom());
        nom.getStyleClass().add("white-text");
        prenom.setText(u.getPrenom());
        prenom.getStyleClass().add("white-text");
        id.setText(u.getIdd());
        id.getStyleClass().add("white-text");
        setPhotoProfile();
    }
    public void setPhotoProfile(){
        photoProfile.setImage(new Image("/Pictures/student.png",550,525,false,false));
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
    @FXML
    public void getClassroom(){
        getPage("Classroom");
    }


    public void parametres() throws IOException{
        Stage primaryStage =new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("Parametres.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

}

