package sample;

import Classes.Module;
import Classes.Seance;
import Classes.Section;
import Classes.Utilisateur;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
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
import javafx.fxml.FXMLLoader;

public class EmploiEnseignantController implements Initializable {

    @FXML
    private GridPane gp;
    private static GridPane gps;

    static Utilisateur u = new Utilisateur();

    static Seance seance;
    static LinkedList<Button>  llb= new LinkedList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LinkedList<Seance> lls = new LinkedList<>();
        try {
            lls = Seance.getSeanceProf(u.getIdd());
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        gps = gp;
        for(Seance s : lls){
            //on détermine la colone et la ligne ou se doit se trouver la seance
            int l = Seance.getNumJourE(s.getJour())+1;
            int c = Seance.NumHeure(s.getHorraire())+1;
            seance = s;

            String text = null;
            text = s.getType()+" "+ s.getModule().getNom_module()+" "+s.getSection().getId_Section();

            javafx.scene.control.Button b = new javafx.scene.control.Button(text);
            b.getStyleClass().add("rounded-btn");
            b.setPrefSize(114,75);
            b.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY){
                try {
                    Seance.openSeanceProf(u.getIdd(),s.getHorraire(),s.getJour());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }}
            });
            //en cas de clique droit ouvre une autre fenetre permettant de modifier ou supprimer la seance☺
            b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierSupprimerSeance.fxml"));
                        Stage primaryStage = new Stage();
                        ModifierSupprimerSeanceController.sec = s;
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("ModifierSupprimerSeance.fxml"));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }



                        assert root != null;
                        Scene scene = new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.initStyle(StageStyle.UNDECORATED);
                        primaryStage.show();
                    }
                }
            });
            gps.add(b,c,l);
            llb.add(b);
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

    public static void refresh(){
        for(Button b : llb)
        {
            gps.getChildren().remove(b);
        }
        llb.clear();
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
            seance = s;

            String text = null;
            text = s.getType()+" "+ s.getModule().getNom_module()+" "+s.getSection().getId_Section();

            javafx.scene.control.Button b = new javafx.scene.control.Button(text);
            b.getStyleClass().add("rounded-btn");
            b.setPrefSize(114,75);
            b.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY){
                    try {
                        Seance.openSeanceProf(u.getIdd(),s.getHorraire(),s.getJour());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }}
            });
            //en cas de clique droit ouvre une autre fenetre permettant de modifier ou supprimer la seance☺
            b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        Stage primaryStage = new Stage();
                        ModifierSupprimerSeanceController.sec = s;
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("ModifierSupprimerSeance.fxml"));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        assert root != null;
                        Scene scene = new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.initStyle(StageStyle.UNDECORATED);
                        primaryStage.show();
                    }
                }
            });
            gps.add(b,c,l);
            llb.add(b);
        }
    }



}
