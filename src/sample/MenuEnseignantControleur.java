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

public class MenuEnseignantControleur {


    @FXML
    Button decon;

    @FXML
    private static javafx.scene.control.Label iddd;
    @FXML
    private static javafx.scene.control.Label nomm;
    @FXML
    private static Label prenoom;
    @FXML
    private BorderPane enseignantPane;

    public static Label getNomm() {
        return nomm;
    }

    public static void setNomm(Label nomm) {
        MenuEnseignantControleur.nomm = nomm;
    }

    public static Label getPrenoom() {
        return prenoom;
    }

    public static void setPrenoom(Label prenoom) {
        MenuEnseignantControleur.prenoom = prenoom;
    }

    @FXML
    private ImageView photoProfile;
    @FXML
    Button param;

    private Utilisateur u = new Utilisateur();


    public void setUser(){
        nomm.setText(u.getNom());
        nomm.getStyleClass().add("white-text");
        prenoom.setText(u.getPrenom());
        prenoom.getStyleClass().add("white-text");
        iddd.setText(u.getIdd());
        iddd.getStyleClass().add("white-text");
        setPhotoProfile();

    }
    public void setPhotoProfile(){
        photoProfile.setImage(new Image("/Pictures/teacher.png",550,525,false,false));
    }


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
