package sample;

import Classes.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class EmailControleur {
    @FXML private Button closeButton;
    @FXML private TextField mailfrom;
    @FXML private PasswordField password;
    @FXML private TextField subject;
    @FXML private TextArea contenu;
    Utilisateur u = new Utilisateur();



    @FXML
    private void quit() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void envoyerEmail() throws IOException {
        u.envoyerEmail( u.chercherDestinataireEmail(u.getIdd()),mailfrom,password,contenu,subject );
        quit();
    }


}
