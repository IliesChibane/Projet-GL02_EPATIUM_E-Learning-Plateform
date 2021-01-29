package Classes;

import java.io.*;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import Connectivity.ConnectionClass;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class Utilisateur {
	private static String nom, prenom, idd;  //on a besoin de es infos pour les afficher dans le menu

	public String getNom() { // pour les afficher
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getId() {
		return idd;
	}

	public String identification(String id, String mdp) {

		ResultSet rs =null;
		PreparedStatement  ps = null;
		ConnectionClass cc = new ConnectionClass();

		String mode = "rien"; // Pour ouvrir la fenetre convenable (etudiant/enseignant)
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
				nom = rs.getString("nom_prof"); // on prend ces informations pour les afficher lors de l'ouverture de la fenetre
				prenom = rs.getString("prenom_prof");
				idd = rs.getString("id_prof");

				mode = "enseignant";
				System.out.println("Bienvenue à EPATIUM "+ rs.getString("nom_prof")+", Status : enseignant");
			}
			ps = conn.prepareStatement(sql2);
			ps.setString(1, id);
			ps.setString(2, mdp);
			rs = ps.executeQuery();

			if (rs.next()) {
				mode ="etudiant";
				nom = rs.getString("nom_etudiant");
				prenom = rs.getString("prenom_etudiant");
				idd = rs.getString("matricule");

				System.out.println("Bienvenue à EPATIUM "+ rs.getString("nom_etudiant")+", Status : etudiant");
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

		return mode;
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
					System.out.println("Row inserted successfullyyyyy");
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



	public Boolean uploadFichier(String FILE_NAME, FileInputStream FILE_DATA, File file) {

		int rs = 0;
		PreparedStatement  ps = null;
		ConnectionClass cc = new ConnectionClass();
		boolean status = false;
		String sql ="";

		try {

			Connection conn = cc.getConnection();

			sql =  "insert into fichiers values(?,?)";

			ps = conn.prepareStatement(sql);
			ps.setString(1, FILE_NAME);
			FILE_DATA = new FileInputStream(file);
			ps.setBinaryStream(2,(InputStream) FILE_DATA, (int) file.length());

			rs = ps.executeUpdate();
			if ( rs >0 ) {
				status = true;
				System.out.println("imaaaage inserted successfullyyyyy for student");
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

	public void consulterFichiers(TableView fichiers, ImageView icon){

		ResultSet rs =null;
		PreparedStatement ps = null;
		ConnectionClass cc = new ConnectionClass();
		final ObservableList<Fichier> data = FXCollections.observableArrayList();

		String name ="";

		try {
			Connection conn = cc.getConnection();

			String sql =  "Select * From fichiers ";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while(rs.next()){
				name = rs.getString("file_name");
				Fichier f = new Fichier(icon, name);
				data.add(f);
			}
			fichiers.setItems(data);

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



	}
	public void telechargerFichier(String file_name){

		ResultSet rs =null;
		PreparedStatement ps = null;
		ConnectionClass cc = new ConnectionClass();
		InputStream is ;
		OutputStream os;


		try {
			Connection conn = cc.getConnection();

			String sql =  "Select * From fichiers where file_name = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1,file_name);

			rs = ps.executeQuery();

			while(rs.next()){
				is = rs.getBinaryStream("file_data");
				String home = System.getProperty("user.home");
				os = new FileOutputStream(new File(home+"/Downloads/" + file_name));
				byte[] content = new byte[4096];
				int size = 0;
				while((size = is.read(content)) != -1){
					os.write(content,0,size);
				}
				os.close();
				is.close();

				/* String file_path = "file:" + file_name;

				 File f = new File(file_path);

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

				DocumentBuilder builder = factory.newDocumentBuilder();

				Document document = builder.parse(is);

				document.getDocumentElement().normalize();

				Element root = document.getDocumentElement();*/
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



	}
}


