package sample;

import Classes.Annonce;
import Classes.Etudiant;
import Classes.Module;
import Classes.Utilisateur;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ClassroomController extends ScrollPane implements Initializable {


    @FXML
    AnchorPane anchorpane;
    @FXML
    VBox vboxx;
    @FXML
    ScrollPane sp;
    @FXML
    TextField contenu;
    @FXML
    ComboBox<String> module;
    @FXML Button publier;


    private static ObservableList<Annonce> posts = FXCollections.observableArrayList();
    private ObservableList<HBox> boxes = FXCollections.observableArrayList();
    private  Utilisateur u = new Utilisateur();
    private int postsNum;
    private  int i=0;
    private int j;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        publier.setDisable(true); // on ne peut pas publier d'annonce sans avoir selection le destinataire
        posts.removeAll();  //on clean notre liste
        // on initialise la liste des module que le prof enseigne
        try {
            module.setItems(Module.getIDModuleProf(u.getIdd()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        sp.setContent(null);//on clean notre liste
        getPosts(); // on charge tous les posts de la bd et on leur prepare des hbox
        ajouterAnnonce();   // on charge les annonces

        //dans le cas ou l'utilisateur est un etudiant on masque tout les composants permettant de publier une annonce
        if(Utilisateur.typeUtilisateur(u.getIdd()).equals("Etudiant"))
        {
            contenu.setDisable(true);
            contenu.setVisible(false);
            publier.setDisable(true);
            publier.setVisible(false);
            module.setDisable(true);
            module.setVisible(false);
        }
    }

    private void ajouterAnnonce() {

        sp.setContent(null);
        if(postsNum == 0){  //s'il n ya plus de posts à affciher on arrete le scroll
            ImageView img = new ImageView(new Image("/Pictures/empty.png",500,500,false,false));  // on leur affiche que c'est fini
            sp.setContent(img);
        }
        int l = 0;
        vboxx = new VBox();
        i = 0;
        while (postsNum != 0) {

                boxes.get(i).fillHeightProperty().set(true);
                vboxx.getChildren().add(boxes.get(i));
                i++;
                l++;
                postsNum--;


        }
        postsNum = j;
        vboxx.fillWidthProperty().set(true);
        sp.setContent(vboxx);

    }
    private void getPosts(){
        posts = FXCollections.observableArrayList();
        boxes = FXCollections.observableArrayList();
        posts.addAll(u.chargerAnnonce());
        postsNum = posts.size();
        for (int k = 0; k < postsNum; k++) {

            HBox box = new HBox();
            box.getStyleClass().add("trans-btn-white-border");
            box.setPrefSize(400, 400);
            box.setPadding(new Insets(10, 10, 50, 50));
            box.setSpacing(10);

            ImageView im = new ImageView(new Image("/Pictures/user.png",50,50,false,false));

            VBox vb1 = new VBox(); // pour l'image
            vb1.setPrefSize(50,50);
            vb1.getChildren().add(im);
            box.getChildren().add(vb1);


            VBox vb2 = new VBox(); //pour le temps
            vb2.setPrefSize(200,100);
            Label time = new Label();
            time.setText(posts.get(k).getDatePubli().toString());
            time.setFont(Font.font("Arial", FontWeight.THIN,12));
            time.autosize();
            vb2.getChildren().add(time);
            box.getChildren().add(vb2);



            VBox vb3 = new VBox(); // pour le contenu
            HBox hb1 = new HBox();
            hb1.setPrefSize(50,50);
            HBox hb2 = new HBox();
            hb2.setPrefSize(50,50);
            HBox hb3 = new HBox();
            vb3.setPrefSize(340,100);
            Label content = new Label();
            content.setWrapText(true);
            content.setText(posts.get(k).getContenu());
            hb3.getChildren().add(content);
            vb3.getChildren().add(hb1);
            vb3.getChildren().add(hb2);
            vb3.getChildren().add(hb3);
            box.getChildren().add(vb3);


            boxes.add(box);
        }
    }
    //permet d'activer le bouton publier apres avoir choisis le destinataire
    @FXML public void ButtonActiver(){ publier.setDisable(false);}

    @FXML
    public void publier() throws SQLException {
        //permet d'eviter de publier des msg vide
        if((!contenu.getText().equals(""))){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Annonce.AjoutAnonce(contenu.getText(),module.getSelectionModel().getSelectedItem(),u.getIdd(),timestamp);
        //on re actualise le classroom pour y afficher le msg
        sp.setContent(null);
        getPosts();
        ajouterAnnonce();}
    }

}
