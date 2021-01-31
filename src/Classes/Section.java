package Classes;

import Connectivity.ConnectionClass;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

public class Section
{
    private StringProperty id_Section;
    private StringProperty code_Section;
    private StringProperty specialite;
    private StringProperty annee_scolaire;

    public Section() {
        this.id_Section = new SimpleStringProperty();
        this.code_Section = new SimpleStringProperty();
        this.specialite = new SimpleStringProperty();
        this.annee_scolaire = new SimpleStringProperty();
    }

    public String getId_Section() {
        return id_Section.get();
    }

    public StringProperty id_SectionProperty() {
        return id_Section;
    }

    public void setId_Section(String id_Section) {
        this.id_Section.set(id_Section);
    }

    public String getCode_Section() {
        return code_Section.get();
    }

    public StringProperty code_SectionProperty() {
        return code_Section;
    }

    public void setCode_Section(String code_Section) {
        this.code_Section.set(code_Section);
    }

    public String getSpecialite() {
        return specialite.get();
    }

    public StringProperty specialiteProperty() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite.set(specialite);
    }

    public String getAnnee_scolaire() {
        return annee_scolaire.get();
    }

    public StringProperty annee_scolaireProperty() {
        return annee_scolaire;
    }

    public void setAnnee_scolaire(String annee_scolaire) {
        this.annee_scolaire.set(annee_scolaire);
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
    //Méthode me permettant de récupérer l'id de la section de la bdd cette méthode m'a servis dans pas mal de cas
    public static String GetIDS(String sec) throws SQLException {
        ResultSet rs =null;
        PreparedStatement ps = null;
        String[] s;

        s = sec.split(" ");

        String sql = "Select * from section where annee_scolaire = ? and specialite = ? and code_section = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1, s[0]);
        ps.setString(2, s[1]);
        ps.setString(3, s[2]);
        rs = ps.executeQuery();

        if(rs.next())
        {
            return rs.getString(1);
        }
        else
            return null;
    }

    public static LinkedList<Etudiant> getEtudiantSection(String idS) throws SQLException {

        ResultSet rs =null;
        PreparedStatement ps = null;

        String sql = "Select * From etudiant where id_section = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1,idS);

        rs = ps.executeQuery();

        LinkedList<Etudiant> lle = new LinkedList<>();

        while(rs.next())
        {
            Etudiant e = new Etudiant();

            e.setMatricule(rs.getString("matricule"));
            e.setNom(rs.getString("nom_etudiant"));
            e.setPrenom(rs.getString("prenom_etudiant"));

            lle.add(e);
        }

        return lle;
    }

    public static Section getSection(String id) throws SQLException {

        Section s = new Section();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "Select * from section where id_section= ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1,id);

        rs = ps.executeQuery();

        if(rs.next())
        {
            s.setId_Section(id);
            s.setSpecialite(rs.getString(1));
            s.setCode_Section(rs.getString(2));
            s.setAnnee_scolaire(rs.getString(3));
        }

        return s;
    }
}
