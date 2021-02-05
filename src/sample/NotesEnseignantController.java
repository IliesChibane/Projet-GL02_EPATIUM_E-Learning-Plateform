package sample;

import Classes.*;
import Classes.Module;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
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

    TreeMap<String, String> NC = new TreeMap<String, String>();
    TreeMap<String, String> NTD = new TreeMap<String, String>();
    TreeMap<String, String> NTP = new TreeMap<String, String>();


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

        module.setDisable(false);
        module.setItems(Module.SetComboM(Module.ModuleProf(u.getIdd()),section.getSelectionModel().getSelectedItem()));
    }

    public void SetupTableNote() throws SQLException {

        TableNote.setItems(null);

        String idS = Section.GetIDS(section.getSelectionModel().getSelectedItem());
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

        a = Module.getModule(idM).getProf_cours().getId().equals(u.getIdd());
        b = Module.getModule(idM).getProf_td().getId().equals(u.getIdd());
        c = Module.getModule(idM).getProf_td().getId().equals(u.getIdd());

        if(!a)
        {
            ExamC.setEditable(false);
        }
        if(!b)
        {
            TDC.setEditable(false);
        }
        if(!c)
        {
            TPC.setEditable(false);
        }

    }


    public void Noter() throws SQLException {
        boolean a=ExamC.isEditable(),b=TDC.isEditable(),c=TPC.isEditable();
        LinkedList<NEM> llnem = new LinkedList<>();
        String idS = Section.GetIDS(section.getSelectionModel().getSelectedItem());
        String idM = Module.getIDMODULE(module.getSelectionModel().getSelectedItem(),idS);

        if(a)
        {
            Set<String> key = NC.keySet();
            for(String NCK : key) {
                PreparedStatement ps = null;
                String sql = "Insert into notes values(?,?,?,?)";
                ps = NEM.conn.prepareStatement(sql);
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
                String sql = "Insert into notes values(?,?,?,?)";
                ps = NEM.conn.prepareStatement(sql);
                ps.setFloat(1,Float.parseFloat(NTD.get(NCK).toString()));
                ps.setString(2,idM);
                ps.setString(3,"TD");
                ps.setString(4,NCK);
                ps.executeUpdate();
                ps.close();
            }
        }

        if(b)
        {
            Set<String> key = NTP.keySet();
            for(String NCK : key) {
                PreparedStatement ps = null;
                String sql = "Insert into notes values(?,?,?,?)";
                ps = NEM.conn.prepareStatement(sql);
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
            NC.put(nem.getEtudiant().getMatricule(),edditedCell.getNewValue().toString());
        }
    }

    public void NoteTd(TableColumn.CellEditEvent edditedCell){

        if(!edditedCell.getNewValue().toString().equals(""))
        {
            NEM nem = TableNote.getSelectionModel().getSelectedItem();
            NTD.put(nem.getEtudiant().getMatricule(),edditedCell.getNewValue().toString());
        }
    }

    public void NoteTP(TableColumn.CellEditEvent edditedCell){

        if(!edditedCell.getNewValue().toString().equals(""))
        {
            NEM nem = TableNote.getSelectionModel().getSelectedItem();
            NTP.put(nem.getEtudiant().getMatricule(),edditedCell.getNewValue().toString());
        }
    }
}
