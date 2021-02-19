package sample;

import Classes.Seance;
import Classes.Utilisateur;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class ModifierSupprimerSeanceController implements Initializable {

    @FXML
    private ComboBox<JourSemaine> jour;
    @FXML
    private ComboBox<String> horraire;
    @FXML
    private TextField link;

    @FXML
    private Button closeButton;

    private final Seance sec = EmploiEnseignantController.seance;
    private Utilisateur u = new Utilisateur();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Seance.InitSuppSeance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        jour.setItems(FXCollections.observableArrayList(JourSemaine.values()));
        jour.setPromptText(sec.getJour());
        try {
            horraire.setItems(Seance.getHorraireDispo(sec.getSection().getId_Section(),u.getIdd(),sec.getJour()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        horraire.setPromptText(sec.getHorraire());
        link.setText(sec.getLien());
        System.out.println(jour.getSelectionModel().getSelectedItem()==null);
    }

    public void SetupH() throws SQLException {
        horraire.setItems(null);
        horraire.setPromptText("");
        horraire.setItems(Seance.getHorraireDispo(sec.getSection().getId_Section(),u.getIdd(),jour.getSelectionModel().getSelectedItem().toString()));
    }

    @FXML
    private void quit() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void ModifSeance() throws ParseException, SQLException {
        String j,h,l = link.getText();

        if(jour.getSelectionModel().getSelectedItem()==null)
        {
            j = jour.getPromptText();
        }
        else
            j = jour.getSelectionModel().getSelectedItem().toString();

        if(horraire.getSelectionModel().getSelectedItem()==null)
        {
            h = horraire.getPromptText();
        }
        else
            h = horraire.getSelectionModel().getSelectedItem();

        //----------obtention de la date courante--------------------
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String DateS = Seance.DateSeance(Seance.getNumJour(j));
        //-----------------------------------------------------------

        Seance.ModifSeance(j,h,DateS,l,sec.getSection().getId_Section(),sec.getJour(),sec.getHorraire(),sec.getDate());

        quit();
    }

    @FXML
    private void Supp() throws SQLException {
        Seance.SuppSeance(sec.getSection().getId_Section(),sec.getJour(),sec.getHorraire(),sec.getDate());

        quit();
    }
}
