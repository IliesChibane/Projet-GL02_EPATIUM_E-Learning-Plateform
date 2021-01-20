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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;

public class MenuEtudiantControleur { // ce truc est fait pour controler le classromm / pareil pout MenuEnseignantControleur

    @FXML
    private VBox vbox;
    private Parent fxml;

    @FXML
    static VBox vbox2 ;
    @FXML
    Button decon;



    @FXML
    public void hideToolBar() {
        vbox.getChildren().clear();
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
    public void afficherToolBar()  {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * 3);
        t.play();
        t.setOnFinished((e)  -> {

            FXMLLoader loader = new FXMLLoader();
            Pane root = null;

            try {
                root = loader.load(getClass().getResource("ToolBarEtudiant.fxml").openStream());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


            // root = loader.load(getClass().getResource("ToolBarEtudiant.fxml").openStream());
                ToolBarEtudiantControleur menu = (ToolBarEtudiantControleur) loader.getController();
                menu.setUser();

                if (root!=null){
                    vbox.getChildren().clear();
                    vbox.getChildren().addAll(root);
                }
        });
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
    public void hover(){
        System.out.println("you are innnnn");
    }

    public void setVbox(Pane root){
        if(root == null) System.out.println("le roooooooooooooot est vide.");
        vbox2.getChildren().clear();
        vbox2.getChildren().addAll(root);
    }


  /*  public VBox getVbox(){
        return vbox2;
    }
    */
}

