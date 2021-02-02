package Classes;

import java.io.*;
import java.sql.*;
import java.util.Locale;
import java.util.function.Predicate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ListView;
import Connectivity.ConnectionClass;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sample.Dialogue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class Utilisateur {
	private static String nom, prenom, idd;  //on a besoin de es infos pour les afficher dans le menu
	final ObservableList<Fichier> data = FXCollections.observableArrayList();

	public Utilisateur()
	{

	}

	public static String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		Utilisateur.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		Utilisateur.prenom = prenom;
	}

	public String getIdd() {
		return idd;
	}

	public void setIdd(String idd) {
		Utilisateur.idd = idd;
	}

	public String identification(String id, String mdp) {

		ResultSet rs =null;
		PreparedStatement  ps = null;
		ConnectionClass cc = new ConnectionClass();

		String mode = "rien"; // Pour ouvrir la fenetre convenable (etudiant/enseignant)
		try {
			Connection conn = ConnectionClass.c;
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
				Dialogue.afficherDialogue("Bienvenue à EPATIUM "+ nom+", Status : enseignant");
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

				Dialogue.afficherDialogue("Bienvenue à EPATIUM "+nom+", Status : etudiant");
			}

		} catch (Exception e) {

			Dialogue.afficherDialogue(e.getMessage());
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
		boolean status = false;
		String sql ="";



		try {

			Connection conn = ConnectionClass.c;
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
				String concat = niveau +" "+specialite+" "+section;
				ps.setString(6, concat);

				rs = ps.executeUpdate();
				if ( rs >0 ) {
					status = true;
				}

			}

		} catch (Exception e) {


			Dialogue.afficherDialogue(e.getMessage());
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
		boolean status = false;
		String sql ="";

		try {

			Connection conn =  ConnectionClass.c;

			sql =  "insert into fichiers values(?,?)";

			ps = conn.prepareStatement(sql);
			ps.setString(1, FILE_NAME);
			FILE_DATA = new FileInputStream(file);
			ps.setBinaryStream(2,(InputStream) FILE_DATA, (int) file.length());

			rs = ps.executeUpdate();
			if ( rs >0 ) {
				status = true;
				Dialogue.afficherDialogue("Fichier téléchargé vers le serveur avec succès");
			}

		} catch (Exception e) {


			Dialogue.afficherDialogue(e.getMessage());
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
		String name ="";

		try {
			Connection conn = ConnectionClass.c;

			String sql =  "Select * From fichiers ";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while(rs.next()){
				name = rs.getString("file_name");
				icon = new ImageView(new Image("/Pictures/dossier.png",100,100,false,false));
				Fichier f = new Fichier(icon, name);
				data.add(f);
			}
			fichiers.setItems(data);

		} catch (Exception e) {


			Dialogue.afficherDialogue(e.getMessage());
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

		InputStream is ;
		OutputStream os;


		try {
			Connection conn = ConnectionClass.c;

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

			}

		} catch (Exception e) {


			Dialogue.afficherDialogue(e.getMessage());
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

	public void chercherFicher(TextField searchFiled, TableView table){
		FilteredList<Fichier> filteredList = new FilteredList<Fichier>(data, e->true);
		searchFiled.setOnKeyReleased(e ->{
			searchFiled.textProperty().addListener((ObservableValue, oldValue, newValue)->{
				filteredList.setPredicate((Predicate<? super Fichier>) fichier->{
					if(newValue ==null || newValue.isEmpty()) return true;
					String lowerCaseSearch = newValue.toLowerCase();
					if(fichier.getNom().toLowerCase().contains(lowerCaseSearch)) return true;
					return false;
				});
			});
			SortedList<Fichier> sortedData = new SortedList<>(filteredList);
			sortedData.comparatorProperty().bind(table.comparatorProperty());
			table.setItems(sortedData);
		});


	}
}


