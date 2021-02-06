package sample;

import Classes.Module;
import Classes.Seance;
import Classes.Section;
import Classes.Utilisateur;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EmploiEnseignantController implements Initializable {

    @FXML
    private GridPane gp;

    Utilisateur u = new Utilisateur();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LinkedList<Seance> lls = new LinkedList<>();
        try {
            lls = Seance.getSeanceProf(u.getIdd());
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        for(Seance s : lls){
            //on détermine la colone et la ligne ou se doit se trouver la seance
            int l = Seance.getNumJourE(s.getJour())+1;
            int c = Seance.NumHeure(s.getHorraire())+1;

            String text = null;
            try {
                text = s.getType()+" "+ Module.getModule(s.getModule().getId_module()).getNom_module()+" "+Section.getSection(s.getSection().getId_Section()).getAnnee_scolaire()+" "+Section.getSection(s.getSection().getId_Section()).getSpecialite()+" "+Section.getSection(s.getSection().getId_Section()).getCode_Section();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            javafx.scene.control.Button b = new javafx.scene.control.Button(text);
            b.getStyleClass().add("rounded-btn"); //j'ai réjouté ceci psk tu l'as oublié x)

            b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            try {
                                Seance.openSeanceProf(u.getIdd(),s.getHorraire(),s.getJour());
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    });
            gp.add(b,c,l);
        }
    }

    public void testM () throws IOException {
        Stage primaryStage =new Stage();

        Parent root= FXMLLoader.load(getClass().getResource("AjouterSeance.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

}
