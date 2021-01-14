package sample;

public enum TypeU {

    Etudiant,Enseignant;
    TypeU() {}

    public String value() {
        return name();
    }

    public static TypeU fromvalue(String v)
    {
        return valueOf(v);
    }
}
