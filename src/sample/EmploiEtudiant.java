package sample;

import Classes.*;
import Classes.Module;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EmploiEtudiant implements Initializable {

    @FXML
    private GridPane gp;

    Utilisateur u = new Utilisateur();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LinkedList<Seance> lls = new LinkedList<>();
        try {
            lls = Seance.getSeanceEtudiant(Etudiant.getSectionE(u.getIdd()));
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        for(Seance s : lls){
            //on détermine la colone et la ligne ou se doit se trouver la seance
            int l = Seance.getNumJourE(s.getJour())+1;
            int c = Seance.NumHeure(s.getHorraire())+1;

            String text = null;
            try {
                text = s.getType()+" "+ Module.getModule(s.getModule().getId_module()).getNom_module()+" "+ Section.getSection(s.getSection().getId_Section()).getAnnee_scolaire()+" "+Section.getSection(s.getSection().getId_Section()).getSpecialite()+" "+Section.getSection(s.getSection().getId_Section()).getCode_Section();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            javafx.scene.control.Button b = new javafx.scene.control.Button(text);
            b.getStyleClass().add("rounded-btn");

            b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        Seance.openSeanceEtudiant(s.getSection().getId_Section(),s.getHorraire(),s.getJour());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            gp.add(b,c,l);
        }
    }
}
