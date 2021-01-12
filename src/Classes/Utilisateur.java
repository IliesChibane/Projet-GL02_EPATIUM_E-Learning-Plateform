package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Connectivity.ConnectionClass;

public class Utilisateur {
	
	
	public Boolean identification(String id, String mdp) {
		
		ResultSet rs =null;
		PreparedStatement  ps = null;
		ConnectionClass cc = new ConnectionClass();
		Boolean status = false;
		try {
			Connection conn = cc.getConnection();
			String sql =  "SELECT nom_prof AS nom, mdp, id_prof, '' AS matricule FROM enseignant WHERE id_prof = ? and mdp = ?  UNION ALL SELECT nom_etudiant AS nom, mdp, matricule,'' AS id_prof FROM etudiant WHERE matricule =? and mdp = ? order BY nom ";
			
			ps = conn.prepareStatement(sql);			
			ps.setString(1, id);
			ps.setString(2, mdp);
			ps.setString(3, id);
			ps.setString(4, mdp);
			 
			 rs = ps.executeQuery();
		     while (rs.next()) {
		    	 status = true;
		            System.out.println("Bienvenue à EPATIUM "+ rs.getString("nom"));
		        }
			
		} catch (Exception e) {


			System.out.println(e.getMessage());
		}finally {
			try {
				ps.close();
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
		Boolean status = false;
		String sql ="";
		
		
		
		try {
			
			Connection conn = cc.getConnection();
			if (type =="Enseignant") {
				
			
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
			
			if (type =="Etudiant") {
				
				
				 sql =  "insert into etudiant values(?,?,?,?,?,?)";
				
			    ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, nom);
				ps.setString(3, prenom);
				ps.setString(4, mdp);
				ps.setInt(5, groupe);
				ps.setString(6, specialite.concat(section).concat(niveau) );
				System.out.println("printing id_sec to check " + specialite.concat(section).concat(niveau) );
				
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
				ps.close();
			
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			
		}

                   return status;    
	        
	}

}


