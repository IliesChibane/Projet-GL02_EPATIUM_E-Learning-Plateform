package Connectivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
   
       public Connection getConnection() throws ClassNotFoundException, SQLException {
      Connection c = null;
      String ConnectionURL = "jdbc:postgresql://b8fojjzzhg2rped0lfip-postgresql.services.clever-cloud.com:5432/b8fojjzzhg2rped0lfip";
      String user = "ua7komohvxkrx42wknxo";
      String password = "6oVwVccM9s4MbDWFbn4J";
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager.getConnection(ConnectionURL,user, password);
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
      }
 
      return c;
   
}
}