package sample;

import Classes.Annonce;
import Classes.Etudiant;
import Classes.Utilisateur;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.net.URL;
import java.util.ResourceBundle;

public class ClassroomController extends ScrollPane implements Initializable {

    private final static int TRANSITION_DURATION = 200;
    private final static double BASE_MODIFIER = 1;

    @FXML
    AnchorPane anchorpane;
    @FXML
    VBox vboxx;


    private static ObservableList<Annonce> posts = FXCollections.observableArrayList();
    private ObservableList<HBox> boxes = FXCollections.observableArrayList();
    private  Utilisateur u = new Utilisateur();
    private int postsNum;
    private  int i=0;
    private int j;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ScrollBar bar = getVerticalScrollbar(anchorpane);
        bar.getStyleClass().add("trans-btn-white-border");
        bar.setMinSize(30,60);

        //bar.setVisible(false);

        posts.removeAll();  //on clean notre liste
        getPosts(); // on charge tous les posts de la bd et on leur prepare des hbox
        j= postsNum;  //on met le nombre de posts qu'on a ici
        ajouterAnnonce();   // on charge les annonces

        if(bar != null) {

            bar.valueProperty().addListener(this::scrolled);

        }else{
            System.out.println(bar +"yo");
        }
    }

    private void ajouterAnnonce() {

        // System.out.println(postsNum);
        vboxx.getChildren().clear();
        if(postsNum == 0){  //s'il n ya plus de posts à affciher on arrete le scroll
            ImageView img = new ImageView(new Image("/Pictures/empty.png",500,500,false,false));  // on leur affiche que c'est fini
            vboxx.getChildren().add(img);
        }

        int l = 0;
        while ( i < j && l <2) {
            if (posts.get(i).isPub() == false) {
                //  System.out.println("ce post n'est pas encore publié");
                //boxes.get(i).setPadding( new Insets(20, 20, 20, 50));
                vboxx.getChildren().add(boxes.get(i));
                posts.get(i).setPub(true);
                i++;
                l++;
                postsNum--;
            }
        }
    }
    private void getPosts(){
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

    private ScrollBar getVerticalScrollbar(AnchorPane anchorpane) {  //on attrappe le scrollbar de notre scrollPane
        ScrollBar result = null;
        for (Node n : anchorpane.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    result = bar;
                }
            }
        }
        return result;
    }

    void scrolled(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double value = newValue.doubleValue();
         System.out.println("Scrolled to " + value);
        ScrollBar bar = getVerticalScrollbar(anchorpane);
        bar.setOnScroll(new EventHandler<ScrollEvent>(){
            @Override
            public void handle(ScrollEvent event) {

            }


        });
        if (value == bar.getMax()) {

            //vboxx.getChildren().removeAll();
            vboxx.getChildren().remove(0);
            // System.out.println("Adding new Hboxes.");
            //double targetValue = value * posts.size();
            ajouterAnnonce();                      ////////////////////////////////ici on doit afficher les  annonces qui n'ont pas étaient affcihées.
            bar.setValue(60);   //on reset le scrollbar
        }
    }


}
