package sample;
import Classes.Fichier;
import Classes.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import jdk.jshell.execution.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class DriveControleur implements Initializable {

    @FXML
    private FileInputStream fis;
    private Utilisateur u = new Utilisateur();
    private ImageView img = new ImageView(new Image("/Pictures/dossier.png",100,100,false,false));


    @FXML
    private TableView<Fichier> table;
    @FXML
    private TableColumn<Fichier, String> icon;
    @FXML
    private TableColumn<Fichier, String> name;

    @FXML
    private TextField searchBox;

    public void initialize(URL location, ResourceBundle resource){

        icon.setCellValueFactory(new PropertyValueFactory<>("type"));
        name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        u.consulterFichiers(this.table, this.img);
    }

    public void chooseFile(ActionEvent event){

        FileChooser fc = new FileChooser();
      /*  fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")   //on peut les utiliser pour filtrer le type de fichiers à upload
        );*/
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);
        String FILE_NAME ="";
        FileInputStream FILE_DATA = null;
        if (selectedFiles != null){
            for(int i=0; i< selectedFiles.size();i++) {
                FILE_NAME = selectedFiles.get(i).getName();
                img = new ImageView(new Image("/Pictures/dossier.png",100,100,false,false));
                Fichier f = new Fichier(img, FILE_NAME);
                table.getItems().add(f);
                u.uploadFichier(FILE_NAME,FILE_DATA,selectedFiles.get(i) );
                // u.consulterFichiers(this.listView);
            }
        }

    }
    public void downloadFile(ActionEvent event){
        String file_name = table.getSelectionModel().getSelectedItem().getNom();
        u.telechargerFichier(file_name);
    }

    public void searchFile(){
        u.chercherFicher(this.searchBox, this.table);
    }


}