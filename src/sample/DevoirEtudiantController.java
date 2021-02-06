package sample;

import Classes.Devoir;
import Classes.Enseignant;
import Classes.Etudiant;
import Classes.Module;
import Classes.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class DevoirEtudiantController implements Initializable {

    @FXML
    private ListView devoirsAttribues;
    @FXML
    Label titre;
    @FXML
    Label module;
    @FXML
    Label dateRemise;
    @FXML
    Label explication;


    private Etudiant e = new Etudiant();
    private Utilisateur u = new Utilisateur();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        devoirsAttribues.getItems().addAll(e.voirListeDevoirs(u.getIdSec()));
        titre.setWrapText(true);
        module.setWrapText(true);
        dateRemise.setWrapText(true);
        explication.setWrapText(true);

    }

    public void showDetailsDevoir(){
        String title = devoirsAttribues.getSelectionModel().getSelectedItem().toString();
        if(title != null){
            e.consulterDevoir(title);
            titre.setText(title);
            module.setText("Module : ".concat(Module.getId_module()));
            dateRemise.setText("A rendre le : "+Devoir.getDateRemise().toString());
            explication.setText("Explication : "+Devoir.getExplication());

        }
    }

}
