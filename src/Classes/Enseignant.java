package Classes;

import Connectivity.ConnectionClass;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.Dialogue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

//Classe Enseignant utiliser pour manipuler les information de l'enseignant
public class Enseignant {

    private StringProperty id, nom, prenom, email;
    private Utilisateur u = new Utilisateur();
    public Enseignant() {

        this.id = new SimpleStringProperty();
        this.nom = new SimpleStringProperty();
        this.prenom = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void publierDevoir(String titre, String idModule, String idProf, Date dateEnvoi, Date dateRemise, String explication) {

        int rs2 = 0;
        ResultSet rs = null;
        PreparedStatement ps = null;
        boolean moduleExists = false;

        try {
            Connection conn = ConnectionClass.c;

            String sql = "Select * From module where id_module = ? ";
            String sql2 = "insert into devoir values(?,?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, idModule);
            rs = ps.executeQuery();

            if (rs.next()) {
                moduleExists = true;
            }

            if (moduleExists) {

                ps = conn.prepareStatement(sql2);
                ps.setString(1, titre);
                ps.setString(2, idModule);
                ps.setString(3, idProf);
                ps.setDate(4, (java.sql.Date) dateEnvoi);
                ps.setDate(5, (java.sql.Date) dateRemise);
                ps.setString(6, explication);


                rs2 = ps.executeUpdate();
                if (rs2 > 0) {
                    Dialogue.afficherDialogue("Devoir publié avec succès");
                }
            } else {
                Dialogue.afficherDialogue("Le module est inexistant");
            }


        } catch (Exception ex) {


        } finally {
            try {
                assert ps != null;
                ps.close();
                assert rs != null;
                rs.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }

        }

    }

    public ArrayList<String> afficherHistoriqueDevoirs(String idProf) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        final ArrayList<String>  devoirs = new ArrayList<String>();


        String titreDevoir = "";

        try {
            Connection conn = ConnectionClass.c;

            String sql = "Select * From devoir where id_prof = ?  AND date_remise BETWEEN  NOW() + '-4 months' AND NOW() ";

            ps = conn.prepareStatement(sql);
            ps.setString(1,idProf);

            rs = ps.executeQuery();

            while (rs.next()) {
                titreDevoir = rs.getString("titre_devoir");

                devoirs.add(titreDevoir);
            }


        } catch (Exception e) {


            Dialogue.afficherDialogue(e.getMessage());
        } finally {
            try {
                assert ps != null;
                ps.close();
                assert rs != null;
                rs.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }

        }
        return devoirs;
    }

    public void afficherDetailsDevoir(String titreDevoir) {
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            Connection conn = ConnectionClass.c;

            String sql = "Select * From devoir where titre_devoir = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, titreDevoir);

            rs = ps.executeQuery();

            while (rs.next()) {
                Devoir.setTitreDevoir(titreDevoir);
                Module.setId_module(rs.getString("id_module"));
                Devoir.setDateEnvoi(rs.getDate("date_envoi"));
                Devoir.setDateRemise(rs.getDate("date_remise"));
                Devoir.setExplication(rs.getString("explication"));
            }

        } catch (Exception e) {
            Dialogue.afficherDialogue(e.getMessage());

        } finally {
            try {
                assert ps != null;
                ps.close();
                assert rs != null;
                rs.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }

        }


    }

}
