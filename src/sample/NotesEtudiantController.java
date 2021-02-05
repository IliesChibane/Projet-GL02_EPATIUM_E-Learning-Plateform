package sample;

import Classes.NEM;
import Classes.Utilisateur;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NotesEtudiantController implements Initializable  {

    @FXML private TableView<NEM> TableNote;
    @FXML private TableColumn<NEM, String> ModuleC;
    @FXML private TableColumn<NEM, String> ExamC;
    @FXML private TableColumn<NEM, String> TDC;
    @FXML private TableColumn<NEM, String> TPC;

    Utilisateur u = new Utilisateur();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableNote.setItems(null);
        ObservableList<NEM> OLN = null;
        try {
            OLN = NEM.getNoteEtudiant(u.getIdd());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ModuleC.setCellValueFactory(cellData-> cellData.getValue().getModule().nom_moduleProperty());
        ExamC.setCellValueFactory(cellData-> cellData.getValue().getNote_Cours().noteProperty());
        TDC.setCellValueFactory(cellData-> cellData.getValue().getNote_TD().noteProperty());
        TPC.setCellValueFactory(cellData-> cellData.getValue().getNote_TP().noteProperty());

        TableNote.setItems(OLN);
    }
}
