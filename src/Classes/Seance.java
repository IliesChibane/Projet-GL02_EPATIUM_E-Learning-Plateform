package Classes;

import Connectivity.ConnectionClass;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

public class Seance {


    private StringProperty lien;
    private StringProperty horraire;
    private StringProperty type;
    private  StringProperty jour;
    private StringProperty Date;
    private Section section;
    private Module module;
    private Enseignant prof;

    public Seance() {
        this.lien = new SimpleStringProperty();
        this.horraire = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.jour = new SimpleStringProperty();
        this.Date = new SimpleStringProperty();
        this.section = new Section();
        this.module = new Module();
        this.prof = new Enseignant();
    }

    public String getLien() {
        return lien.get();
    }

    public StringProperty lienProperty() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien.set(lien);
    }

    public String getHorraire() {
        return horraire.get();
    }

    public StringProperty horraireProperty() {
        return horraire;
    }

    public void setHorraire(String horraire) {
        this.horraire.set(horraire);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getJour() {
        return jour.get();
    }

    public StringProperty jourProperty() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour.set(jour);
    }

    public String getDate() {
        return Date.get();
    }

    public StringProperty dateProperty() {
        return Date;
    }

    public void setDate(String date) {
        this.Date.set(date);
    }

    public Enseignant getProf() {
        return prof;
    }

    public void setProf(Enseignant prof) {
        this.prof = prof;
    }

    static ConnectionClass cc = new ConnectionClass();
    static Connection conn;

    static {
        try {
            conn = cc.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Méthode qui permet d'obtenir les horaires disponible en vérifiant les horraire libre de la section et du prof
    public static ObservableList<String> getHorraireDispo(String id_Section, String id_Prof, String jour) throws SQLException {
        LinkedList<String> h = new LinkedList<String>();
        LinkedList<String> hnd = new LinkedList<String>();
        LinkedList<String> hd = new LinkedList<String>();

        ResultSet rs =null;
        PreparedStatement ps = null;

        //on crée une liste initiale contenant tout les horaire
        h.add("8:00-9:30");
        h.add("9:40-11:10");
        h.add("11:20-12:50");
        h.add("13:00-14:30");
        h.add("14:40-16:30");

        String sql = "Select horaire from seance where id_section = ? and jour = ? or id_prof = ? and jour = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1, id_Section);
        ps.setString(2, jour);
        ps.setString(3, id_Prof);
        ps.setString(4, jour);
        rs = ps.executeQuery();

        //on recupere la liste de tout les horaire dèja occuper
        while(rs.next())
        {
            hnd.add(rs.getString(1));
        }

        for(String i : h)
        {
            boolean b = false;

            for (String j : hnd)
            {
                //par la suite on enlève tout les horaire pris de la liste initiale
                if (i.equals(j)) {
                    b = true;
                    break;
                }
            }

            if(!b)
                hd.add(i);
        }


        return FXCollections.observableList(hd);
    }

    //méthode permettant d'ajouter une seance a la bdd
    public static void AjoutSeane(String section, String module, String type, String dateS,String jour, String heure, String link, String id_prof) throws SQLException {

        PreparedStatement ps = null;

        String sql = "INSERT INTO seance values(?,?,?,?,?,?,?,?)";

        ps = conn.prepareStatement(sql);
        ps.setString(1,heure);
        ps.setString(2,section);
        ps.setString(3,module);
        ps.setString(4,type);
        ps.setString(5,link);
        ps.setString(6,id_prof);
        ps.setString(7,jour);
        ps.setString(8,dateS);
        ps.executeUpdate();

        ps.close();
    }

    //méthode permettant d'avoir les jours de la semaine sous forme de int
    public static int getNumJour(String j)
    {
        switch (j){
            case "Dimanche":
                return 0;
            case "Lundi":
                return 1;
            case "Mardi":
                return 2;
            case "Mercredi":
                return 3;
            case "Jeudi":
                return 4;
            case "Vendredi":
                return 5;
            case "Samedi":
                return 6;
            default: return -1;
        }
    }

    public static int getNumJourE(String j)
    {
        switch (j){
            case "Dimanche":
                return 1;
            case "Lundi":
                return 2;
            case "Mardi":
                return 3;
            case "Mercredi":
                return 4;
            case "Jeudi":
                return 5;
            case "Samedi":
                return 0;
            default: return -1;
        }
    }

    //méthode permettant d'avoir les horaires sous forme de int
    public static  int NumHeure(String h)
    {
        switch (h){
            case "8:00-9:30":
                return 0;
            case "9:40-11:10":
                return 1;
            case "11:20-12:50":
                return 2;
            case "13:00-14:30":
                return 3;
            case "14:40-16:30":
                return 4;
            default: return -1;
        }
    }

    //méthode qui permet d'obtenir la date exacte a la quelle la séance aura lieu
    public static String DateSeance(int jour) throws ParseException {
        //récupération de la date du jour
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        int i=0,js=date.getDay();
        //si par exemple on est lundi et que l'enseignant programme une séance pour lundi on considère qu'il réfère au lundi de la semaine prochaine
        //comme sa pas de séance programmer a la dernière minute (coucou compil XD)
        if(js==jour)
        {
            i = 7;
        }
        else{
            //dans le cas contraire on calcule cmb de jour il reste avant la séance
            while(js!=jour)
            {
                js++;
                i++;
                if(jour==7)
                {
                    jour = 0;
                }
            }
        }

        String dt = date.toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.setTime(sdf.parse(dt));
        //on fini avec quelque conversation de string a date et de mise a jour de format et on détermine la date de la seance en ajoutant le nombre de jour avant la séance a la date courante
        c.add(Calendar.DATE, i);

        return sdf.format(c.getTime());
    }

    //permet de déterminer quel semaine on est par exemple la semaine du samedi 30 janvier 2021-jeudi 4 fevrier 2021
    //cette methode permet de savoir les seance de quel semaine faut il afficher
    //le fonctionement est le meme que la précédente on prend la date courante et on détermine la du samedi et du jeudi
    public static String getsemaine() throws ParseException {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        int i = 0;

        if(date.getDay()!=6)
        {
            i = 6-date.getDay();
        }

        String debutsemaine = date.toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.setTime(sdf.parse(debutsemaine));

        c.add(Calendar.DATE, (5-i));

        return sdf.format(c.getTime());
    }

    //permet d'obtenir l'ensemble des seances du prof de la semaine pour les afficher par la suite sur l'emploie du temps
    public static LinkedList<Seance> getSeanceProf(String id) throws SQLException, ParseException {

        LinkedList<Seance> lls = new LinkedList<Seance>();

        ResultSet rs =null;
        PreparedStatement ps = null;
        String sql = "Select * from seance where id_prof = ? and dates < ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,id);
        ps.setString(2,Seance.getsemaine());
        rs = ps.executeQuery();
        while(rs.next())
        {
            Seance s = new Seance();
            s.setHorraire(rs.getString("horaire"));
            s.getSection().setId_Section(rs.getString("id_section"));
            s.getProf().setId(id);
            s.setType(rs.getString("types"));
            s.setLien(rs.getString("link"));
            s.getModule().setId_module(rs.getString("id_module"));
            s.setJour(rs.getString("jour"));
            s.setDate(rs.getString("dates"));

            lls.add(s);
        }
        return lls;
    }

    public static LinkedList<Seance> getSeanceEtudiant(String id) throws SQLException, ParseException {

        LinkedList<Seance> lls = new LinkedList<Seance>();

        ResultSet rs =null;
        PreparedStatement ps = null;
        String sql = "Select * from seance where id_section = ? and dates < ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,id);
        ps.setString(2,Seance.getsemaine());
        rs = ps.executeQuery();
        while(rs.next())
        {
            Seance s = new Seance();
            s.setHorraire(rs.getString("horaire"));
            s.getSection().setId_Section(rs.getString("id_section"));
            s.getProf().setId(id);
            s.setType(rs.getString("types"));
            s.setLien(rs.getString("link"));
            s.getModule().setId_module(rs.getString("id_module"));
            s.setJour(rs.getString("jour"));
            s.setDate(rs.getString("dates"));

            lls.add(s);
        }
        return lls;
    }

    //permet d'ouvrir la réunion sur le navigateur par défaut de l'utilisateur
    public static void openSeanceProf(String id,String h, String j) throws SQLException {
        ResultSet rs =null;
        PreparedStatement ps = null;
        String sql = "Select link from seance where id_prof = ? and horaire = ? and jour = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1,id);
        ps.setString(2,h);
        ps.setString(3,j);

        rs = ps.executeQuery();

        if(rs.next())
        {
            Desktop desktop = java.awt.Desktop.getDesktop();
            try {
                URI oURL = new URI(rs.getString(1));
                desktop.browse(oURL);
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    //permet d'ouvrir la réunion sur le navigateur par défaut de l'utilisateur
    public static void openSeanceEtudiant(String id,String h, String j) throws SQLException {
        ResultSet rs =null;
        PreparedStatement ps = null;
        String sql = "Select link from seance where id_section = ? and horaire = ? and jour = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1,id);
        ps.setString(2,h);
        ps.setString(3,j);

        rs = ps.executeQuery();

        if(rs.next())
        {
            Desktop desktop = java.awt.Desktop.getDesktop();
            try {
                URI oURL = new URI(rs.getString(1));
                desktop.browse(oURL);
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }

    }
}
