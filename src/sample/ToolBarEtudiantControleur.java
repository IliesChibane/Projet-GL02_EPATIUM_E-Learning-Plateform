package sample;

import Classes.Utilisateur;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class ToolBarEtudiantControleur {   // ce truc est fait pour controler le menu , il contiendra les fonctions de chaque bouton / pareil pour ToolBarEtudiant
    Utilisateur u = new Utilisateur();
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label id;

    @FXML
    public void setUser(){  // on recup les infos de l'utilisateur pour les afficher dans le toolbar (appelle a cette méthode dans MenuEtudiant
        nom.setText(u.getNom());
        prenom.setText(u.getPrenom());
        id.setText(u.getId());
    }

    @FXML
    public void Drive(){

        FXMLLoader loader = new FXMLLoader();
        FXMLLoader loader2 = new FXMLLoader();
        Pane root = null;

            try {
                root = loader.load(getClass().getResource("Drive.fxml").openStream());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        DriveControleur drv = (DriveControleur) loader.getController();

            //drv.loadFiles;
        MenuEtudiantControleur drv1 = (MenuEtudiantControleur) loader2.getController();

           //if (vbox == null)  System.out.println("shit.");
            if (root!=null) {
                drv1.setVbox(root);
                }
            }

}
