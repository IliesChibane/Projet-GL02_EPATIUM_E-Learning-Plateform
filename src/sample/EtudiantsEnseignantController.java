package sample;

import Classes.*;
import Classes.Module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EtudiantsEnseignantController implements Initializable {

    @FXML
    private TableView ListeEtudiant;
    @FXML private TableColumn<Etudiant, String> Matriculle;
    @FXML private TableColumn<Etudiant, String> Nom;
    @FXML private TableColumn<Etudiant, String> Prenom;

    @FXML private ComboBox<String> section;

    Utilisateur u = new Utilisateur();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            section.setItems(Module.SetComboSp(Module.ModuleProf(u.getIdd())));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void RemplirListe() throws SQLException {

        ListeEtudiant.setItems(null);
        LinkedList<Etudiant> lle = Section.getEtudiantSection(section.getSelectionModel().getSelectedItem());

       ObservableList<Etudiant> le = FXCollections.observableArrayList();
        for(Etudiant e : lle)
        {
            le.add(e);
            Matriculle.setCellValueFactory(cellData-> cellData.getValue().matriculeProperty());
            Nom.setCellValueFactory(cellData-> cellData.getValue().nomProperty());
            Prenom.setCellValueFactory(cellData-> cellData.getValue().prenomProperty());
            ListeEtudiant.setItems(le);
        }
    }

}
