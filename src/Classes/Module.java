package Classes;

import Connectivity.ConnectionClass;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

//class module utiliser pour manipuler les information d'un module et pour stocker certaines méthodes neccesaire au fontionement du logiciel
public class Module {
    private StringProperty id_module;
    private StringProperty nom_module;
    private Section section;
    private Enseignant prof_cours;
    private Enseignant prof_td;
    private Enseignant prof_tp;

    public Module() {
        this.id_module = new SimpleStringProperty();
        this.nom_module = new SimpleStringProperty();
        this.section = new Section();
        this.prof_cours = new Enseignant();
        this.prof_td = new Enseignant();
        this.prof_tp = new Enseignant();
    }

    public String getId_module() {
        return id_module.get();
    }

    public StringProperty id_moduleProperty() {
        return id_module;
    }

    public void setId_module(String id_module) {
        this.id_module.set(id_module);
    }

    public String getNom_module() {
        return nom_module.get();
    }

    public StringProperty nom_moduleProperty() {
        return nom_module;
    }

    public void setNom_module(String nom_module) {
        this.nom_module.set(nom_module);
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Enseignant getProf_cours() {
        return prof_cours;
    }

    public void setProf_cours(Enseignant prof_cours) {
        this.prof_cours = prof_cours;
    }

    public Enseignant getProf_td() {
        return prof_td;
    }

    public void setProf_td(Enseignant prof_td) {
        this.prof_td = prof_td;
    }

    public Enseignant getProf_tp() {
        return prof_tp;
    }

    public void setProf_tp(Enseignant prof_tp) {
        this.prof_tp = prof_tp;
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

    //Methode permetant de récuperer l'integralite des modules que le prof enseigne
    public static LinkedList<Module> ModuleProf(String id) throws SQLException, ClassNotFoundException {

        LinkedList<Module> llm = new LinkedList<Module>();
        ResultSet rs =null;
        PreparedStatement ps = null;

        String sql = "Select * FROM Module where id_prof_cours = ? OR id_prof_td = ? OR id_prof_tp = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, id);
        ps.setString(3, id);
        rs = ps.executeQuery();

        while(rs.next())
        {
            Module m = new Module();
            m.id_module = new SimpleStringProperty(rs.getString(1));
            m.nom_module = new SimpleStringProperty(rs.getString(2));
            m.section.setId_Section(rs.getString(3));
            m.prof_cours.setId(rs.getString(4));
            m.prof_td.setId(rs.getString(5));
            m.prof_tp.setId(rs.getString(6));

            ResultSet rs1 =null;
            PreparedStatement ps1 = null;

            /*On utilise cette requête afin d'obtenir les informations personelle des profs enseignant le module
            la table module ne contenant que l'id il est neccesaire de récupérer le reste dans la tables enseignant*/
            String sql1 = "Select * FROM enseignant where id_prof = ?";
            /*On utilise cette requête afin d'obtenir les informations de la section dans lequel le module est enseigné
            la table module ne contenant que l'id il est neccesaire de récupérer le reste dans la tables section*/
            String sql2 = "Select * FROM section where id_section = ?";
            ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, rs.getString(4));
            rs1 = ps1.executeQuery();
            if(rs1.next())
            {
                m.prof_cours.setNom(rs1.getString(2));
                m.prof_cours.setPrenom(rs1.getString(3));
            }

            ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, rs.getString(5));
            rs1 = ps1.executeQuery();
            if(rs1.next())
            {
                m.prof_td.setNom(rs1.getString(2));
                m.prof_td.setPrenom(rs1.getString(3));
            }

            ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, rs.getString(6));
            rs1 = ps1.executeQuery();
            if(rs1.next())
            {
                m.prof_tp.setNom(rs1.getString(2));
                m.prof_tp.setPrenom(rs1.getString(3));
            }

            ps1 = conn.prepareStatement(sql2);
            ps1.setString(1, rs.getString(3));
            rs1 = ps1.executeQuery();
            if(rs1.next())
            {
                m.section.setCode_Section(rs1.getString("code_section"));
                m.section.setSpecialite(rs1.getString(3));
                m.section.setAnnee_scolaire(rs1.getString(4));
            }

            rs1.close();
            ps1.close();

            llm.add(m);
        }
        rs.close();
        ps.close();
        return llm;
    }

    //on utilise cette méthode pour récupérer les informations nécessaire pour remplir la combobox des spécialités que le prof enseigne
    //cette méthode est utilisé dans le controller AjouterSeance
    public static ObservableList<String> SetComboSp(LinkedList<Module> llm)
    {
        LinkedList<String> lls = new LinkedList<String>();
        for (Module m : llm)
        {
            //On récupére l'année la spéciallité et le code de la section pour connaitre la section exacte par exemple L3 ISIL B
            lls.add(m.getSection().getAnnee_scolaire()+" "+m.getSection().getSpecialite()+" "+m.getSection().getCode_Section());
        }

        return FXCollections.observableList(lls);
    }

    //Meme principe mais pour les modules que le prof enseigne dans la dite section
    public static ObservableList<String> SetComboM(LinkedList<Module> llm,String section)
    {
        LinkedList<String> lls = new LinkedList<String>();
        for (Module m : llm)
        {
            String s =m.getSection().getAnnee_scolaire()+" "+m.getSection().getSpecialite()+" "+m.getSection().getCode_Section();
            if(s.equals(section))
                lls.add(m.getNom_module());
        }

        return FXCollections.observableList(lls);
    }

    //Meme principe mais pour le type de la séance par exemple si le prof enseigne uniquement le tp seul l'option tp lui sera proposé
    public static ObservableList<String> SetComboT(LinkedList<Module> llm,String section,String module,String id)
    {
        LinkedList<String> lls = new LinkedList<String>();
        for (Module m : llm)
        {
            String s =m.getSection().getAnnee_scolaire()+" "+m.getSection().getSpecialite()+" "+m.getSection().getCode_Section();
            if((s.equals(section))&&(m.getNom_module().equals(module))) {
                if (m.prof_cours.getId().equals(id)) {
                    lls.add("cours");
                }
                if (m.prof_td.getId().equals(id)) {
                    lls.add("TD");
                }
                if (m.prof_tp.getId().equals(id)) {
                    lls.add("tp");
                }
            }
        }

        return FXCollections.observableList(lls);
    }

    //Méthode me permettant de récupérer l'id du module de la bdd cette méthode m'a servis dans pas mal de cas
    public static String getIDMODULE(String nom, String idS) throws SQLException {
        ResultSet rs =null;
        PreparedStatement ps = null;

        String sql = "Select id_module FROM Module where nom_module = ? and id_section = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1, nom);
        ps.setString(2, idS);
        rs= ps.executeQuery();
        if(rs.next())
            return rs.getString(1);
        return null;
    }

    public static Module getModule(String id) throws SQLException {
        Module m = new Module();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "Select * from module where id_module = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1,id);

        rs = ps.executeQuery();

        if(rs.next())
        {
            m.setId_module(id);
            m.setNom_module(rs.getString("nom_module"));
            m.getSection().setId_Section(rs.getString("id_section"));
            m.getProf_cours().setId(rs.getString(4));
            m.getProf_td().setId(rs.getString(5));
            m.getProf_tp().setId(rs.getString(6));
        }

        return m;
    }
}
