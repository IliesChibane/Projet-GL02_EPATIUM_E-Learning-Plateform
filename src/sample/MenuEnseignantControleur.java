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

public class MenuEnseignantControleur {


    @FXML
    Button decon;

    @FXML
    private javafx.scene.control.Label iddd;
    @FXML
    private javafx.scene.control.Label nomm;
    @FXML
    private Label prenoom;

    private Utilisateur u = new Utilisateur();


    public void setUser(){
        nomm.setText(u.getNom());
        prenoom.setText(u.getPrenom());
        iddd.setText(u.getId());
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
}
