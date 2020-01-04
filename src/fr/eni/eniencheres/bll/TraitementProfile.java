package fr.eni.eniencheres.bll;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.eniencheres.bo.Utilisateur;
import fr.eni.eniencheres.conf.LogFilter;
import fr.eni.eniencheres.conf.MyLogger;

/**
 * Servlet implementation class TraitementProfile
 */
@WebServlet("/TraitementProfile")
public class TraitementProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = MyLogger.getLogger(TraitementProfile.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TraitementProfile() {
        super();
    }

	/**
	 * Redirige vers :
	 * -/MonProfile en cas de modificationdu profile avec l'objet utilisateur en attribut de requete
	 * -/TraitementConnexion en casde suppression apres avoir supprimer l'utilisateur de la BDD
	 * -/Profile en cas de simple affichage avec en parametre l'objet utilisateur 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOG.info("void doGet");

		//Sans Controle de session ou de token cette methode ne doit rien faire !!!  
		
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur;
		boolean modifier=false;
		boolean supprimer=false;
		
		//récupère le parametre modifier ou supprimer
		if(request.getParameter("modifier")!=null) {
			modifier=true;
		}
		if(request.getParameter("supprimer")!=null) {
			supprimer=true;
		}
		
		//cas de mofification
		if (modifier) {
			String pseudoAAfficher = (String) request.getParameter("pseudoAAfficher");
			utilisateur = utilisateurManager.getByPseudo(pseudoAAfficher);
			request.setAttribute("utilisateurAAfficher", utilisateur);
			RequestDispatcher rd = request.getRequestDispatcher("/MonProfile");
			rd.forward(request, response);
		}
		
		//cas de suppression
		if (supprimer) {
			String pseudoASupprimer = (String) request.getParameter("pseudoASupprimer");
			utilisateur = utilisateurManager.getByPseudo(pseudoASupprimer);
			System.out.println("effacer utilisateur : " + utilisateur.getNom());
			utilisateurManager.delete(utilisateur);
			RequestDispatcher rd = request.getRequestDispatcher("/TraitementConnexion");
			rd.forward(request, response);
		}
		
		//cas de simple affichage
		String pseudoAAfficher = (String) request.getParameter("pseudoAAfficher");
		utilisateur = utilisateurManager.getByPseudo(pseudoAAfficher);		
		request.setAttribute("utilisateurAAfficher", utilisateur);
		RequestDispatcher rd = request.getRequestDispatcher("/Profile");
		rd.forward(request, response);
	}

	/**
	 * En cas de creation de compte : Controle l'unicite de l'email et du pseudo avant d'ajouter l'utilisateur dans la BDD ou de retourner une erreur le cas �ch�ant
	 * En cas de modification du profile : controle l'unicite de l'email et la concordance des passwords avant de modifier la BDD
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOG.info("void doPost");
		//TODO A FINIR le contrôle de l'unicite de l'email
		//TODO Demander une checkbox pour la omdification de mot de passe
		HttpSession session = request.getSession();
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		String pseudo = (String) session.getAttribute("pseudo");
		String email = request.getParameter("email");
		Map<String, String[]> listeIdentifiantsUniques = utilisateurManager.getAllIdentifiantsUniques();
		boolean pseudoOk = true;
		boolean emailOk = true;
		Utilisateur utilisateurModifie=null;
		
		String redirectURI = "/WEB-INF/jsp/monProfile.jsp";
		
		//teste si pseudo ou l'email correspond à  une entrée dans la bdd
		for(Entry<String, String[]> user : listeIdentifiantsUniques.entrySet()) {
			String pseudoBdd = user.getKey();
			String emailBdd = user.getValue()[1];
		    if (pseudoBdd.equals(pseudo)) {
				pseudoOk = false;
				if (emailBdd.equals(email)) {
					emailOk=false;
				}
				utilisateurModifie = utilisateurManager.getByPseudo(pseudo);
		    }
		}
		
		//crée une instance pojo utilisateur avec les données
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setNom(request.getParameter("nom"));
		utilisateur.setPrenom(request.getParameter("prenom"));
		utilisateur.setEmail(request.getParameter("email"));
		utilisateur.setTelephone(request.getParameter("telephone"));
		utilisateur.setRue(request.getParameter("rue"));
		utilisateur.setCodePostal(request.getParameter("codePostal"));
		utilisateur.setVille(request.getParameter("ville"));
		utilisateur.setMotDePasse(UtilisateurManager.hash(request.getParameter("password")));
		utilisateur.setCredit(0);
		utilisateur.setAdministrateur(false);
		
		//si le pseudo existe déjà
		if (!pseudoOk) {
			//cas de l'update on contrôle l'unicité de l'email
			if (request.getParameter("update")!=null) {
				//l'email n'existe pas déjà  : on modifie l'utilisateur et on update la bdd
				if (emailOk) {
					utilisateurModifie.setEmail(utilisateur.getEmail());
					utilisateurModifie.setNom(utilisateur.getNom());
					utilisateurModifie.setPrenom(utilisateur.getPrenom());
					utilisateurModifie.setTelephone(utilisateur.getTelephone());
					utilisateurModifie.setRue(utilisateur.getRue());
					utilisateurModifie.setCodePostal(utilisateur.getCodePostal());
					utilisateurModifie.setVille(utilisateur.getVille());
					utilisateurManager.updateUtilisateur(utilisateurModifie);
					redirectURI = "/Accueil" ;
				//l'email existe déjà : on envoie l'erreur sur l'email et l'objet utilisateur
				} else {
					request.setAttribute("erreurMessage", "Veuillez choisir un autre email.");
					request.setAttribute("utilisateur", utilisateur);
				}
			//cas de la création : on envoie l'erreur sur le pseudo et l'objet utilisateur
			} else {
				request.setAttribute("erreurMessage", "Veuillez choisir un autre email.");
				request.setAttribute("utilisateur", utilisateur);

			}
		//le pseudo n'existe pas
		}else {
			//on contrôle l'unicité de l'email
			if (!emailOk) {
				//on envoie l'erreur sur l'email et l'objet utilisateur
				request.setAttribute("erreurMessage", "Veuillez choisir un autre email.");
				request.setAttribute("utilisateur", utilisateur);
			}
			//on controle la concordance des mots de passes
			if(utilisateur.getMotDePasse().equals(UtilisateurManager.hash(request.getParameter("password2")))) {
				//on envoie l'erreur sur les mots de passe et l'objet utilisateur
				utilisateur.setMotDePasse("");
				//TODO la condition ci-dessus et le message n'ont pas rapport. Revoir l'algo
				// La création d'un utilisateur passe par ce code.
				request.setAttribute("erreurMessage", "Les mots de passes ne correspondent pas.");
				request.setAttribute("utilisateur", utilisateur);
			}
			//sinon on inclut l'utilisateur
			utilisateur.setPseudo(request.getParameter("pseudo"));
			utilisateurManager.addUtilisateur(utilisateur);
			session.setAttribute("pseudo", utilisateur.getPseudo());
			redirectURI = "/TraitementAccueil";
		}
		
		LOG.log(Level.INFO, "requestDispatcher.forward on URI : {0}", redirectURI);
		RequestDispatcher rd = request.getRequestDispatcher(redirectURI);
		rd.forward(request, response);
	}

}
