package sample;

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
    @FXML private TextField mailto;
    @FXML private TextField mailfrom;
    @FXML private PasswordField password;
    @FXML private TextField subject;
    @FXML private TextArea contenu;


    public void envoyerEmail() throws IOException {

        String to = mailto.getText();
        String host = "smtp.gmail.com";
        final String username = mailfrom.getText();
        final String pass = password.getText();

        //Configuration du serveur
        Properties props = System.getProperties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.port",587);

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username,pass);
            }
        });
        try{

            //Création du mail
            MimeMessage m = new MimeMessage(session);
            m.setFrom(new InternetAddress(username));
            m.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject.getText());
            m.setText(contenu.getText());


            //L'envoi du mail
            Transport.send(m);
            //SentBoolValue.setVisible(true);
            System.out.println("message sent yahoooooooooooooo");


        }catch(MessagingException me){
            Dialogue.afficherDialogue(me.getMessage());
        }

    }
    @FXML
    private void quit() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }


}
