package Classes;


import Connectivity.ConnectionClass;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Note {

    private StringProperty note;
    private Module module;
    private Etudiant etudiant;

    public Note(){
        this.note = new SimpleStringProperty();
        this.module = new Module();
        this.etudiant = new Etudiant();
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    static Connection conn = ConnectionClass.c;

    //recupere la note d'un etudiant dans un module (peut etre une note de cours de td ou de tp)
    public static Note getNote(String id_Module,String id_Etudiant, String t) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "Select * From notes where id_module = ? and id_etudiant = ? and Type = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1,id_Module);
        ps.setString(2,id_Etudiant);
        ps.setString(3,t);

        rs = ps.executeQuery();

        Note n = new Note();

        while(rs.next())
        {
            n.setNote(String.valueOf(rs.getFloat("note")));
            n.setEtudiant(Etudiant.getEtudiant(rs.getString("id_etudiant")));
            n.setModule(Module.getModule(id_Module));

        }

        return n;
    }
}
