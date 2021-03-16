package sample;

import Classes.Devoir;
import Classes.Enseignant;
import Classes.Module;
import Classes.Utilisateur;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class DevoirEnseignantController implements Initializable {
 @FXML
 private ListView devoirs;
 @FXML
 private  ComboBox<String> modules;
 @FXML
 private ComboBox<String> sections;
 @FXML
 private TextField titre;
 @FXML
 private DatePicker dateEnvoi;
 @FXML
 private DatePicker dateRemise;
 @FXML
 private TextArea explication;

 private Label id_module = new Label(),
         dateeEnvoi= new Label(),
         dateeRemise= new Label();

    private Enseignant e = new Enseignant();
    private Utilisateur u = new Utilisateur();
    private Stage stickyNotesStage = new Stage();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        devoirs.getItems().addAll(e.afficherHistoriqueDevoirs(u.getIdd()));
        modules.setDisable(true);
        stickyNotesStage = new Stage();
        stickyNotesStage.initStyle(StageStyle.UNDECORATED);


        try {
            sections.setItems(Module.SetComboSp(Module.ModuleProf(u.getIdd())));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }

    }

    public void SetupModuleCombo() throws SQLException, ClassNotFoundException {
         modules.setDisable(false);
        modules.setItems(Module.SetComboM(Module.ModuleProf(u.getIdd()), sections.getSelectionModel().getSelectedItem()));



        //trier la liste des devoirs par section
    }
    public void publierDevoir(){
        e.publierDevoir(titre.getText(),modules.getSelectionModel().getSelectedItem() +" "+ sections.getSelectionModel().getSelectedItem(), u.getIdd(), java.sql.Date.valueOf(dateEnvoi.getValue()),java.sql.Date.valueOf(dateRemise.getValue()), explication.getText());
        devoirs.getItems().removeAll();
        devoirs.getItems().add(titre.getText()); // on update la liste des devoirs à coté

    }
    public void afficherDetailsDevoir(){
        StackPane stickyNotesPane = new StackPane();
        ArrayList<Label> arr = new ArrayList<>();
        arr.add(id_module);
        arr.add(dateeEnvoi);
        arr.add(dateeRemise);

        //setting up the labels in the hoverPnae ---on les rajoute au panel et on ajuste leur position
        stickyNotesPane.getChildren().addAll(arr);
        stickyNotesPane.getChildren().get(0).setTranslateX(0);
        stickyNotesPane.getChildren().get(1).setTranslateX(0);
        stickyNotesPane.getChildren().get(2).setTranslateX(0);
        stickyNotesPane.getChildren().get(0).setTranslateY(-33);
        stickyNotesPane.getChildren().get(1).setTranslateY(5);
        stickyNotesPane.getChildren().get(2).setTranslateY(43);

        stickyNotesPane.setPrefSize(200, 150);
        stickyNotesPane.setStyle("-fx-background-color: #82c46c;");
        stickyNotesStage.setScene(new Scene(stickyNotesPane));


        devoirs.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                devoirs.setCellFactory(lv -> {
                    ListCell<String> cell = new ListCell<String>() {
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setText(null);
                            } else {
                                setText(item);
                            }
                        }
                    };
                    cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                        if (isNowHovered && ! cell.isEmpty()) {

                            e.afficherDetailsDevoir(cell.getText());
                            id_module.setText("Module: ".concat(Module.getId_module()));
                            dateeEnvoi.setText("Date d'envoi: ".concat(Devoir.getDateEnvoi().toString()));
                            dateeRemise.setText("Date de Remise: ".concat(Devoir.getDateRemise().toString()));
                            stickyNotesStage.show();
                        } else {

                            stickyNotesStage.hide();
                        }
                    });
                    return cell ;
                });

            } else {
                stickyNotesStage.hide();
            }
        });
    }
    public void cacherDetailsDevoir(){
        stickyNotesStage.hide();
    }

    public void supprimerDevoir() throws SQLException {
        if(devoirs.getSelectionModel().getSelectedItem() !=null ){
            e.SuppDevoir(devoirs.getSelectionModel().getSelectedItem().toString());
            devoirs.getItems().remove(titre.getText());
        }
    }

}
