package Classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class Fichier {
    private SimpleStringProperty nom;
    private ImageView type;

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public ImageView getType() {
        return type;
    }

    public Fichier(ImageView type, String nom){
        this.type = type;
        this.nom = new SimpleStringProperty(nom);
    }
}
