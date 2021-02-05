package Classes;

import java.util.Date;

public class Devoir {
    private static String titreDevoir, idModule, idProf, explication;
    private static Date dateEnvoi, dateRemise;


    public static String getTitreDevoir() {
        return titreDevoir;
    }

    public static void setTitreDevoir(String titreDevoir) {
        Devoir.titreDevoir = titreDevoir;
    }

    public static String getIdModule() {
        return idModule;
    }

    public static void setIdModule(String idModule) {
        Devoir.idModule = idModule;
    }

    public static String getIdProf() {
        return idProf;
    }

    public static void setIdProf(String idProf) {
        Devoir.idProf = idProf;
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
