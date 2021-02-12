package sample;

import Classes.Enseignant;
import Classes.Etudiant;
import Classes.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.ResourceBundle;

public class EnseignantsEtudiantController implements Initializable {

    @FXML
    private TableView ListeEnseignant;
    @FXML
    private TableColumn<Enseignant, String> Matriculle;
    @FXML private TableColumn<Enseignant, String> Nom;
    @FXML private TableColumn<Enseignant, String> Prenom;
    @FXML private TableColumn<Enseignant, String> mail;



    Utilisateur u = new Utilisateur();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListeEnseignant.setItems(null);
        LinkedList<Enseignant> lle = null;
        try {
            lle = Etudiant.getListeEnseignant(Etudiant.getModule(Etudiant.getSectionE(u.getIdd())));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ObservableList<Enseignant> le = FXCollections.observableArrayList();
        for(Enseignant e : lle)
        {
            le.add(e);
            Matriculle.setCellValueFactory(cellData-> cellData.getValue().idProperty());
            Nom.setCellValueFactory(cellData-> cellData.getValue().nomProperty());
            Prenom.setCellValueFactory(cellData-> cellData.getValue().prenomProperty());
            mail.setCellValueFactory(cellData-> cellData.getValue().emailProperty());
            ListeEnseignant.setItems(le);
        }
    }
    public void loadThing() throws IOException {
        Stage primaryStage =new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("Email.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }


}
