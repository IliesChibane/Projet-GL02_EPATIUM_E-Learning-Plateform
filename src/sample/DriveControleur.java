package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DriveControleur {

    @FXML
   private Button btn1;


    @FXML
    private ListView listView;


    public void btn1Action(MouseEvent event){
        FileChooser fc = new FileChooser();
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);
        if (selectedFiles != null){
            for(int i=0; i< selectedFiles.size();i++) {
                listView.getItems().add(selectedFiles.get(i).getName());
            }
        }else{
            System.out.println("File is not valid");
        }

    }

    public void btn2Action(MouseEvent event){

    }
     public void setVbox(Pane root){


            // menu.vbox2.getChildren().clear();
            // menu.vbox2.getChildren().addAll(root);
        // }
     }

}
