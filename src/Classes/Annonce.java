package Classes;

import Connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Dialogue;

import java.sql.*;
import java.util.Date;

public class Annonce {
    private String contenu;
    private Timestamp datePubli;   //j'ai changer le type ici ainsi que ds la bd pour qu'on puisse rajouter le temps.
    private boolean pub = false;

    public boolean isPub() {
        return pub;
    }

    public void setPub(boolean pub) {
        this.pub = pub;
    }

    private Enseignant e = new Enseignant();

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDatePubli() {
        return datePubli;
    }

    public void setDatePubli(Timestamp datePubli) {
        this.datePubli = datePubli;
    }

    public Enseignant getE() {
        return e;
    }

    public void setE(Enseignant e) {
        this.e = e;
    }

    static Connection  conn = ConnectionClass.c;

    //Cette méthode permert d'ajouter une annonce dans la bdd elle est utilisé dans le Controller Classroom
    public static void AjoutAnonce(String contenu,String idm,String idp,Timestamp ts) throws SQLException {

        PreparedStatement ps;

        String sql = "insert into annonce values(?,?,?,?)";

        ps =  conn.prepareStatement(sql);
        ps.setString(1,contenu);
        ps.setString(2,idm);
        ps.setString(3,idp);
        ps.setTimestamp(4,ts);

        ps.executeUpdate();

        ps.close();

    }



}
