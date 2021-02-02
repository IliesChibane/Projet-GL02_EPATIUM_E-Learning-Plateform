package Classes;

import Connectivity.ConnectionClass;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Etudiant{

    private StringProperty matricule, nom, prenom;

    public Etudiant(){
        this.matricule = new SimpleStringProperty();
        this.nom = new SimpleStringProperty();
        this.prenom = new SimpleStringProperty();
    }
    public String getMatricule() {
        return matricule.get();
    }

    public StringProperty matriculeProperty() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule.set(matricule);
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
    static Connection conn= ConnectionClass.c;
    public static String getSectionE(String id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "Select id_section From etudiant where matricule = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1,id);

        rs = ps.executeQuery();
        if(rs.next())
        {
            return rs.getString(1);
        }
        return "";
    }
    public static LinkedList<Module> getModule(String id) throws SQLException {

        LinkedList<Module> llm = new LinkedList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "Select * From Module where id_section = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1,id);

        rs = ps.executeQuery();

        while (rs.next())
        {
            Module m = new Module();
            m.setId_module(rs.getString(1));
            m.setNom_module(rs.getString(2));
            m.getSection().setId_Section(id);
            m.getProf_cours().setId(rs.getString(4));
            m.getProf_td().setId(rs.getString(5));
            m.getProf_tp().setId(rs.getString(6));

            llm.add(m);
        }
        return llm;
    }

    public static LinkedList<Enseignant> getListeEnseignant(LinkedList<Module> llm) throws SQLException {
        LinkedList<Enseignant> llen = new LinkedList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM enseignant where id_prof = ANY (?)";

        String[] id = new String[100];
        int i =0;

        for(Module m : llm)
        {
            id[i] = m.getProf_cours().getId();
            i++;
            id[i] = m.getProf_td().getId();
            i++;
            id[i] = m.getProf_tp().getId();
            i++;
        }

        ps = conn.prepareStatement(sql);

        ps.setArray(1, conn.createArrayOf("text", id));

        rs = ps.executeQuery();

        while (rs.next())
        {
            Enseignant e = new Enseignant();

            e.setId(rs.getString(1));
            e.setNom(rs.getString(2));
            e.setPrenom(rs.getString(3));
            e.setEmail("mail");

            llen.add(e);
        }

        return llen;
    }
}
