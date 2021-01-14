package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connectivity.ConnectionClass;

public class Utilisateur {
	
	
	public Boolean identification(String id, String mdp) {
		
		ResultSet rs =null;
		PreparedStatement  ps = null;
		ConnectionClass cc = new ConnectionClass();
		boolean status = false;
		try {
			Connection conn = cc.getConnection();
			/*Il est preferable de creer de requete distincte pour la connexion etant donner que l'on fournis 
			un nom unique lors de la recherche sa n'augmente pas la complexite */
			String sql =  "Select * From enseignant where id_prof = ? and mdp = ?";
			String sql2 = "Select * From etudiant WHERE matricule = ? and mdp = ?";
			
			ps = conn.prepareStatement(sql);			
			ps.setString(1, id);
			ps.setString(2, mdp);
			rs = ps.executeQuery();

			if(rs.next()){
				status = true;
				System.out.println("Bienvenue à EPATIUM "+ rs.getString("nom_prof")+" Status : enseignant");
			}
			ps = conn.prepareStatement(sql2);
			ps.setString(1, id);
			ps.setString(2, mdp);
			rs = ps.executeQuery();

		     if (rs.next()) {
		    	 status = true;
		    	 System.out.println("Bienvenue à EPATIUM "+ rs.getString("nom_etudiant")+" Status : etudiant");
		        }
			
		} catch (Exception e) {


			System.out.println(e.getMessage());
		}finally {
			try {
				assert ps != null;
				ps.close();
				assert rs != null;
				rs.close();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			
		}

                   return status;
	       }
	
	
	
	public Boolean inscription(String id, String nom, String prenom, String mdp, int groupe, String type, String specialite, String section, String niveau) {
		
		int rs = 0;
		PreparedStatement  ps = null;
		ConnectionClass cc = new ConnectionClass();
		boolean status = false;
		String sql ="";
		
		
		
		try {
			
			Connection conn = cc.getConnection();
			if (type.equals("Enseignant")) {
				
			
			 sql =  "insert into enseignant values(?,?,?,?)";
			
		     ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, nom);
			ps.setString(3, prenom);
			ps.setString(4, mdp);
			
			
			 rs = ps.executeUpdate();
		     if ( rs >0 ) {
		    	 status = true;
		            System.out.println("Row insertes successfullyyyyy for student");
		     }
		     
		    }
			
			if (type.equals("Etudiant")) {
				
				
				 sql =  "insert into etudiant values(?,?,?,?,?,?)";
				
			    ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, nom);
				ps.setString(3, prenom);
				ps.setString(4, mdp);
				ps.setInt(5, groupe);
				String concat = specialite.concat(section).concat(niveau);
				ps.setString(6, concat);
				System.out.println("printing id_sec to check " + concat);
				
				 rs = ps.executeUpdate();
			     if ( rs >0 ) {
			    	 status = true;
			            System.out.println("Row insertes successfullyyyyy");
			     }
			     
			    }
			
		} catch (Exception e) {


			System.out.println(e.getMessage());
		}finally {
			try {
				assert ps != null;
				ps.close();
			
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			
		}

                   return status;    
	        
	}

}


