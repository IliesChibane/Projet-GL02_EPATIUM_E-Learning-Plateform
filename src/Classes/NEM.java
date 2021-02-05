package Classes;

import Connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class NEM {

    private Etudiant etudiant;
    private Module module;
    private Note Note_Cours;
    private Note Note_TD;
    private Note Note_TP;

    public NEM(){
        this.etudiant = new Etudiant();
        this.module = new Module();
        this.Note_Cours = new Note();
        this.Note_TD = new Note();
        this.Note_TP = new Note();
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Note getNote_Cours() {
        return Note_Cours;
    }

    public void setNote_Cours(Note note_Cours) {
        Note_Cours = note_Cours;
    }

    public Note getNote_TD() {
        return Note_TD;
    }

    public void setNote_TD(Note note_TD) {
        Note_TD = note_TD;
    }

    public Note getNote_TP() {
        return Note_TP;
    }

    public void setNote_TP(Note note_TP) {
        Note_TP = note_TP;
    }

    static ConnectionClass cc = new ConnectionClass();
    public static Connection conn;

    static {
        try {
            conn = cc.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<NEM> getNEM(String id_Module, String id_Section) throws SQLException {
        LinkedList<NEM> llnem = new LinkedList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        //String sql = "SELECT Distinct id_etudiant FROM notes WHERE id_module = ? AND id_etudiant IN (SELECT matricule FROM etudiant WHERE id_section = ?)";
        String sql = "Select matricule FROM etudiant where id_section = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,id_Section);

        rs = ps.executeQuery();

        while(rs.next())
        {
            NEM n = new NEM();
            n.setNote_Cours(Note.getNote(id_Module,rs.getString("matricule"),"Exam"));
            n.setNote_TD(Note.getNote(id_Module,rs.getString("matricule"),"TD"));
            n.setNote_TP(Note.getNote(id_Module,rs.getString("matricule"),"TP"));
            n.setEtudiant(Etudiant.getEtudiant(rs.getString("matricule")));
            n.setModule(Module.getModule(id_Module));

            llnem.add(n);
        }
        return FXCollections.observableList(llnem);

    }
}
