package sample;

import Classes.Module;
import Classes.Seance;
import Classes.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EmploiEnseignantController implements Initializable {

    @FXML
    private GridPane gp;

    @FXML
    private Button J2;

    Utilisateur u = new Utilisateur();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LinkedList<Seance> lls = new LinkedList<>();
        try {
            lls = Seance.getSeanceProf(u.getId());
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        for(Seance s : lls){
            //on détermine la colone et la ligne ou se doit se trouver la seance
            int l = Seance.getNumJour(s.getJour())+1;
            int c = Seance.NumHeure(s.getHorraire())+1;
            for(Node n : gp.getChildren())
            {
                if(GridPane.getColumnIndex(n) == c && GridPane.getRowIndex(n) == l)
                {
                    //et apres s'y etre positioner on est sensé écrire les info de la seance sur le bouton mais sa fontionne pas
                    // on obtiens un javafx.fxml.LoadException:Caused by: java.lang.NullPointerException
                    n.setAccessibleText(s.getType()+" "+s.getModule().getNom_module()+" "+s.getSection().getSpecialite()+" "+s.getSection().getCode_Section());
                }
            }

        }
    }

    public void testM () throws IOException {
        Stage primaryStage =new Stage();

        Parent root= FXMLLoader.load(getClass().getResource("AjouterSeance.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
