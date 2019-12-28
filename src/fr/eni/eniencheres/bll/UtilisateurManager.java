package fr.eni.eniencheres.bll;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import fr.eni.eniencheres.bo.Utilisateur;
import fr.eni.eniencheres.dal.DALException;
import fr.eni.eniencheres.dal.DAOFactory;
import fr.eni.eniencheres.dal.UtilisateurDAO;

//TODO vérifier les catch block
public class UtilisateurManager {
	
	private UtilisateurDAO utilisateurDAO;

	/**
	 * constructeur de UtilisateurManager
	 */
	 public UtilisateurManager() {
		this.utilisateurDAO=DAOFactory.getUtilisateurDAO();
	}
	
	/**
	 * retourne l'objet utilisateur par son pseudo
	 * @param pseudo
	 * @return Utilisateur
	 */
	 public Utilisateur getByPseudo(String pseudo) {
		Utilisateur utilisateur=null;
		try {
			utilisateur=utilisateurDAO.selectByPseudo(pseudo);
		} catch (DALException e) {
			e.printStackTrace();
		}
		return utilisateur;
	}
	
	/**
	 * retourne un dictionnaire des pseudos/motdepasse
	 * @return Map<String, String>
	 */
	 public Map<String, String> getAllIdentifiants(){
		Map<String, String> allIdentifiants = new HashMap<String, String>();
		try {
			allIdentifiants = utilisateurDAO.selectAllIdentifiants();
		} catch (DALException e) {
			e.printStackTrace();
		}
		return allIdentifiants;
	}
	
	/**
	 * retourne un dictionnaire des pseudos/motdepasse
	 * @return Map<String, String>
	 */
	public void addUtilisateur(Utilisateur utilisateur) {
		try {
			utilisateurDAO.insert(utilisateur);
		} catch (DALException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ajoute l'utilisateur dans la base de donnees
	 * @param utilisateur
	 */
	public void updateUtilisateur(Utilisateur utilisateur) {
		try {
			utilisateurDAO.update(utilisateur);
		} catch (DALException e) {
			e.printStackTrace();
		}
	}

	/**
	 * met a jour l'utilisateur dans la base de donnees
	 * @param utilisateur
	 */
	 public void delete(Utilisateur utilisateur) {
		try {
			utilisateurDAO.delete(utilisateur);
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * encode le mot de passe avant manipulation.
	 * Copy past from https://www.mkyong.com/java/java-sha-hashing-example/
	 * @param motDePasse
	 * @return String
	 */
	static String hash(String motDePasse) {
		String encoded = null;
		try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hashInBytes = md.digest(motDePasse.getBytes(StandardCharsets.UTF_8));

			// bytes to hex
	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashInBytes) {
	            sb.append(String.format("%02x", b));
	        }
	        encoded = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encoded;

		
	}

	/**
	 * retourne un dictionnaire de tous les pseudos/motdepasse+email
	 * @return Map<String, String>
	 */
	public Map<String, String[]> getAllIdentifiantsUniques() {
		Map<String, String[]> allIdentifiantsUniques = new HashMap<>();
		try {
			allIdentifiantsUniques = utilisateurDAO.selectAllIdentifiantsUniques();
		} catch (DALException e) {
			e.printStackTrace();
		}
		return allIdentifiantsUniques;
	}
}

