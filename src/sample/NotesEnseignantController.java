package sample;

import Classes.*;
import Classes.Module;
import Connectivity.ConnectionClass;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class NotesEnseignantController implements Initializable {

    @FXML private ComboBox<String> section;
    @FXML private ComboBox<String> module;
    @FXML private TableView<NEM> TableNote;
    @FXML private TableColumn<NEM, String> MatC;
    @FXML private TableColumn<NEM, String> NomC;
    @FXML private TableColumn<NEM, String> PrenomC;
    @FXML private TableColumn<NEM, String> ExamC;
    @FXML private TableColumn<NEM, String> TDC;
    @FXML private TableColumn<NEM, String> TPC;

    Utilisateur u = new Utilisateur();

    static Connection conn = ConnectionClass.c;

    TreeMap<String, String> NC = new TreeMap<String, String>();
    TreeMap<String, String> NTD = new TreeMap<String, String>();
    TreeMap<String, String> NTP = new TreeMap<String, String>();

    TreeMap<String,Boolean> nouvelle_note_cours = new TreeMap<>();
    TreeMap<String,Boolean>  nouvelle_note_td = new TreeMap<>();;
    TreeMap<String,Boolean> nouvelle_note_tp = new TreeMap<>();;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            section.setItems(Module.SetComboSp(Module.ModuleProf(u.getIdd())));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        module.setDisable(true);
        TableNote.setEditable(true);
        ExamC.setCellFactory(TextFieldTableCell.forTableColumn());
        TDC.setCellFactory(TextFieldTableCell.forTableColumn());
        TPC.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public void SetupModuleCombo() throws SQLException, ClassNotFoundException {

        TableNote.setItems(null);
        module.setDisable(false);
        module.accessibleTextProperty().set("");
        module.setItems(null);
        module.setItems(Module.SetComboM(Module.ModuleProf(u.getIdd()),section.getSelectionModel().getSelectedItem()));
    }

    public void SetupTableNote() throws SQLException {

        TableNote.setItems(null);

        String idS = section.getSelectionModel().getSelectedItem();
        String idM = Module.getIDMODULE(module.getSelectionModel().getSelectedItem(),idS);
        Module m = Module.getModule(idM);
        ObservableList<NEM> llnem = NEM.getNEM(idM,idS);

        MatC.setCellValueFactory(cellData-> cellData.getValue().getEtudiant().matriculeProperty());
        NomC.setCellValueFactory(cellData-> cellData.getValue().getEtudiant().nomProperty());
        PrenomC.setCellValueFactory(cellData-> cellData.getValue().getEtudiant().prenomProperty());
        ExamC.setCellValueFactory(cellData-> cellData.getValue().getNote_Cours().noteProperty());
        TDC.setCellValueFactory(cellData-> cellData.getValue().getNote_TD().noteProperty());
        TPC.setCellValueFactory(cellData-> cellData.getValue().getNote_TP().noteProperty());

        TableNote.setItems(llnem);

        boolean a,b,c;

        if(idM != null){
        a = Module.getModule(idM).getProf_cours().getId().equals(u.getIdd());
        b = Module.getModule(idM).getProf_td().getId().equals(u.getIdd());
        c = Module.getModule(idM).getProf_tp().getId().equals(u.getIdd());

        if(!a)
        {
            ExamC.setEditable(false);
        }
        else{
            ExamC.setEditable(true);
        }

        if(!b)
        {
            TDC.setEditable(false);
        }
        else{
            TDC.setEditable(true);
        }

        if(!c)
        {
            TPC.setEditable(false);
        }
        else{
            TPC.setEditable(true);
        }}

    }


    public void Noter() throws SQLException {
        boolean a=ExamC.isEditable(),b=TDC.isEditable(),c=TPC.isEditable();
        LinkedList<NEM> llnem = new LinkedList<>();
        String idS = section.getSelectionModel().getSelectedItem();
        String idM = Module.getIDMODULE(module.getSelectionModel().getSelectedItem(),idS);

        if(a)
        {
            Set<String> key = NC.keySet();
            for(String NCK : key) {
                PreparedStatement ps = null;

                String sql;
                if(!nouvelle_note_cours.get(NCK))
                   sql = "Insert into notes values(?,?,?,?)";
                else{
                    sql = "Update notes set note = ? where id_module = ? and type = ? and id_etudiant = ?";
                }
                ps = conn.prepareStatement(sql);
                ps.setFloat(1,Float.parseFloat(NC.get(NCK).toString()));
                ps.setString(2,idM);
                ps.setString(3,"Exam");
                ps.setString(4,NCK);
                ps.executeUpdate();
                ps.close();
            }
        }

        if(b)
        {
            Set<String> key = NTD.keySet();
            for(String NCK : key) {
                PreparedStatement ps = null;
                String sql;
                if(!nouvelle_note_td.get(NCK))
                    sql = "Insert into notes values(?,?,?,?)";
                else{
                    sql = "Update notes set note = ? where id_module = ? and type = ? and id_etudiant = ?";
                }
                ps = conn.prepareStatement(sql);
                ps.setFloat(1,Float.parseFloat(NTD.get(NCK).toString()));
                ps.setString(2,idM);
                ps.setString(3,"TD");
                ps.setString(4,NCK);
                ps.executeUpdate();
                ps.close();
            }
        }

        if(c)
        {
            Set<String> key = NTP.keySet();
            for(String NCK : key) {
                PreparedStatement ps = null;
                String sql;
                if(!nouvelle_note_tp.get(NCK))
                    sql = "Insert into notes values(?,?,?,?)";
                else{
                    sql = "Update notes set note = ? where id_module = ? and type = ? and id_etudiant = ?";
                }
                ps = conn.prepareStatement(sql);
                ps.setFloat(1,Float.parseFloat(NTP.get(NCK).toString()));
                ps.setString(2,idM);
                ps.setString(3,"TP");
                ps.setString(4,NCK);
                ps.executeUpdate();
                ps.close();
            }
        }
    }

    public void Notecours(TableColumn.CellEditEvent edditedCell){

        if(!edditedCell.getNewValue().toString().equals(""))
        {
            NEM nem = TableNote.getSelectionModel().getSelectedItem();
            if(edditedCell.getOldValue() != null)
            {
                nouvelle_note_cours.put(nem.getEtudiant().getMatricule(),true);
            }else{
                nouvelle_note_cours.put(nem.getEtudiant().getMatricule(),false);
            }
            NC.put(nem.getEtudiant().getMatricule(),edditedCell.getNewValue().toString());
        }
    }

    public void NoteTd(TableColumn.CellEditEvent edditedCell){

        if(!edditedCell.getNewValue().toString().equals(""))
        {
            NEM nem = TableNote.getSelectionModel().getSelectedItem();
            if(edditedCell.getOldValue() != null)
            {
                nouvelle_note_td.put(nem.getEtudiant().getMatricule(),true);
            }else{
                nouvelle_note_td.put(nem.getEtudiant().getMatricule(),false);
            }
            NTD.put(nem.getEtudiant().getMatricule(),edditedCell.getNewValue().toString());
        }
    }

    public void NoteTP(TableColumn.CellEditEvent edditedCell){

        if(!edditedCell.getNewValue().toString().equals(""))
        {
            NEM nem = TableNote.getSelectionModel().getSelectedItem();
            if(edditedCell.getOldValue() != null)
            {
                nouvelle_note_tp.put(nem.getEtudiant().getMatricule(),true);
            }else{
                nouvelle_note_tp.put(nem.getEtudiant().getMatricule(),false);
            }
            NTP.put(nem.getEtudiant().getMatricule(),edditedCell.getNewValue().toString());
        }
    }
}
