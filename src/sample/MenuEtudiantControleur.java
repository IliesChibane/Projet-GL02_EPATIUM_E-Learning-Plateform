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



import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;

public class MenuEtudiantControleur { // ce truc est fait pour controler le classromm / pareil pout MenuEnseignantControleur

    @FXML
    private VBox vbox;
    private Parent fxml;

    @FXML
    Button decon;

    @FXML
    private Label iddd;
    @FXML
    private Label nomm;
    @FXML
    private Label prenoom;

    private Utilisateur u = new Utilisateur();


/*  @FXML
    public void afficherToolBar() {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * 3);
        t.play();
        t.setOnFinished((e) -> {
            open("ToolBarEtudiant.fxml");
          try {

                fxml = FXMLLoader.load(getClass().getResource("ToolBarEtudiant.fxml"));



            } catch (Exception ex) {
                ex.printStackTrace();
            }finally {
                vbox.getChildren().clear();
                vbox.getChildren().setAll(fxml);

            ;

            }

      });
    }
    */


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
            Stage primaryStage = new Stage();
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
               /* Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                //scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());

                primaryStage.setScene(scene);
                primaryStage.initStyle(StageStyle.TRANSPARENT);

                primaryStage.setTitle("Plateforme Epatium");
                javafx.scene.image.Image image = new Image("/Pictures/logo.png");
                primaryStage.getIcons().add(image);
                primaryStage.show(); */


                if (root!=null){
                    vbox.getChildren().clear();
                    vbox.getChildren().addAll(root);
                }
              /*  nomm.setText(u.getNom());
                prenoom.setText(u.getPrenom());
                iddd.setText(u.getId()); */


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
}

