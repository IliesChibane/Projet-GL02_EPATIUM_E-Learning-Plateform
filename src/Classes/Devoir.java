package Classes;

import java.util.Date;

public class Devoir {
    private static String titreDevoir,explication;
    private static Date dateEnvoi, dateRemise;
    private static Module mod = new Module();/********************************************/
    private static Enseignant ens = new Enseignant();/************************************/


    public static String getTitreDevoir() {
        return titreDevoir;
    }

    public static void setTitreDevoir(String titreDevoir) {
        Devoir.titreDevoir = titreDevoir;
    }
    public static String getExplication() {
        return explication;
    }

    public static void setExplication(String explication) {
        Devoir.explication = explication;
    }

    public static Date getDateEnvoi() {
        return dateEnvoi;
    }

    public static void setDateEnvoi(Date dateEnvoi) {
        Devoir.dateEnvoi = dateEnvoi;
    }

    public static Date getDateRemise() {
        return dateRemise;
    }

    public static void setDateRemise(Date dateRemise) {
        Devoir.dateRemise = dateRemise;
    }
}
