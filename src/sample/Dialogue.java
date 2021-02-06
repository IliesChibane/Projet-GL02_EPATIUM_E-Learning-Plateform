package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;


public class Dialogue {
    @FXML
    private Label infoo = new Label();
    @FXML
    private static AnchorPane pane = new AnchorPane();
    private static Stage primaryStage;

    public static void afficherDialogue(String phrase){
        //Alert alert = new Alert(Alert.AlertType.INFORMATION, phrase, ButtonType.CLOSE);
        //alert.showAndWait();

        FXMLLoader loader = new FXMLLoader();
        try {
            primaryStage = new Stage();
            pane = loader.load(Dialogue.class.getResource("DialoguePane.fxml").openStream());
            Dialogue diag = (Dialogue) loader.getController();
            Scene scene = new Scene(pane);
            scene.setFill(Color.TRANSPARENT);
            ///scene.getStylesheets().add( Dialogue.class.getResource("/sample/style.css").toExternalForm());
            pane.getStylesheets().add(Dialogue.class.getResource("/sample/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setTitle("Plateforme Epatium");
            Image image = new Image("/Pictures/logo.png");
            primaryStage.getIcons().add(image);
            primaryStage.show();
            diag.setMessage(phrase);


        }catch(Exception e ) {
            e.printStackTrace();
        }
    }

    @FXML
    private void quit() {
        Stage stage = (Stage) infoo.getScene().getWindow();
        stage.hide();
        stage.close();
    }
    private void setMessage(String msg){
        this.infoo.setWrapText(true);
        this.infoo.setText(msg);
    }
}
