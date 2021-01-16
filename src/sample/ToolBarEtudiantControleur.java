package sample;

import Classes.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class ToolBarEtudiantControleur {   // ce truc est fait pour controler le menu , il contiendra les fonctions de chaque bouton / pareil pour ToolBarEtudiant
    Utilisateur u = new Utilisateur();
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label id;

    @FXML
    public void setUser(){  // on recup les infos de l'utilisateur pour les afficher dans le toolbar (appelle a cette méthode dans MenuEtudiantControleur car la fonction affciher toolbar et labas)
        nom.setText(u.getNom());
        prenom.setText(u.getPrenom());
        id.setText(u.getId());
    }
}
