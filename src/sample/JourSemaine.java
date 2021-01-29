package sample;

public enum JourSemaine {
    Samedi, Dimanche, Lundi, Mardi, Mercredi, Jeudi;

    JourSemaine() {}

    public String value() {
        return name();
    }

    public static JourSemaine fromvalue(String v)
    {
        return valueOf(v);
    }
}
