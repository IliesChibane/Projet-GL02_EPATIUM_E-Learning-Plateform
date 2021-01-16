package sample;

import Classes.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ToolBarEnseignantControleur {
    Utilisateur u = new Utilisateur();
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label id;

    @FXML
    public void setUser() {
        nom.setText(u.getNom());
        prenom.setText(u.getPrenom());
        id.setText(u.getId());
    }
}
