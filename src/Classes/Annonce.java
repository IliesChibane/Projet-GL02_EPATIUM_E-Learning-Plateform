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



}
