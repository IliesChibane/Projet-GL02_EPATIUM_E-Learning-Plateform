package Classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Classe Enseignant utiliser pour manipuler les information de l'enseignant
public class Enseignant extends Utilisateur {

    private StringProperty nom;
    private StringProperty prenom;

    public Enseignant() {
        this.nom = new SimpleStringProperty();
        this.prenom = new SimpleStringProperty();
    }

    @Override
    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    @Override
    public String getPrenom() {
        return prenom.get();
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

}
