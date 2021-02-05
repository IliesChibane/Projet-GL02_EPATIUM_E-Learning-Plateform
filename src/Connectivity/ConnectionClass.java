package Connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

public class ConnectionClass {
    public static Connection c = null;

    public void getConnection(){

        String ConnectionURL = "jdbc:postgresql://b8fojjzzhg2rped0lfip-postgresql.services.clever-cloud.com:5432/b8fojjzzhg2rped0lfip";
        String user = "ua7komohvxkrx42wknxo";
        String password = "6oVwVccM9s4MbDWFbn4J";
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(ConnectionURL, user, password);
        } catch (PSQLException p) {
            p.printStackTrace();
            System.err.println(p.getClass().getName() + ": " + p.getMessage());
            // System.exit(0);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);

        }

    }

    public static void killConnection(){
        try {
            if(c!=null) c.close();
        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
    }
}