package fr.eni.eniencheres.bll;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TraitementUtilisateur
 */
@WebServlet("/TraitementConnexion")
public class TraitementConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TraitementConnexion() {
		super();
	}

	/**
	 * Deconnecte l'utilisateur de sa session et forward vers /TraitementAccueil
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//recuperation de la session
		HttpSession session = request.getSession();
		
		//deconnexion de la session
		session.invalidate();
		
		//forward vers accueil
		RequestDispatcher rd = request.getRequestDispatcher("/TraitementAccueil");
		rd.forward(request, response);
	}

	/**
	 * Controle a connection de l'utilisateur en hashant le mot de passe puis redirige en consequence vers :
	 * -/TraitementAccueil si la connexion est acceptee
	 * -/Connexion si la connexion est refusee
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log("INFO : ", "method doPost Servlet : Connexion");
		
		
		//recuperation du pseudo choisi et identifie pseudo le cas échéant
		String identifiantConnexion = request.getParameter("identifiant");
		String pseudoConnexion=(identifiantConnexion.contains("@")?false:true)?identifiantConnexion:null;
		
		//Creer un Hash du mot de passe des la reception pour et ne transporter que le hash dans les methodes.
		String mdpConnexion = UtilisateurManager.hash(request.getParameter("motDePasse"));

		//TODO Lire le Cookie remember me dans le cooKie
		
		//teste si les identifiants sont disponibles et récupère le pseudo
		boolean connexionOk = isIdentOK(identifiantConnexion, mdpConnexion);
		
		//redirection adaptee
		if (connexionOk) {
			if (pseudoConnexion==null) {
				//si l'utilisateur a utilisé son email on recherche son pseudo
				UtilisateurManager utilisateurManager = new UtilisateurManager();
				pseudoConnexion=utilisateurManager.getPseudoByEmail(identifiantConnexion);
			}
			//TODO Fixer le problème de redirection
			HttpSession session = request.getSession(true);
			session.setAttribute("pseudo", pseudoConnexion);
			RequestDispatcher rd = request.getRequestDispatcher("/TraitementAccueil");
			rd.forward(request, response);
		} else {
			request.setAttribute("erreurConnexion", "La connexion a �chou�. Veuillez r�essayer.");
			RequestDispatcher rd = request.getRequestDispatcher("/Connexion");
			rd.forward(request, response);
		}

	}

	/**
	 * teste si pseudo et mdp correrspondent a� une entree dans la bdd
	 * TODO reflechir a centraliser les regeles de gestion dans le manager La servlet regroupe tout ce qui est Http
	 * @param pseudoConnexion
	 * @param mdpConnexion
	 * @return boolean connexion
	 * 
	 */
	private boolean isIdentOK(final String identifiant, final String mdpConnexion) {
		//Access aux donnees en base
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Map<String[], String> listeIdentifiants = utilisateurManager.getAllPseudoEmail();
		
		//teste si le pseudo existe d�j�
		boolean connexionOk = false;
		for(Entry<String[], String> user : listeIdentifiants.entrySet()) {
			String pseudo = user.getKey()[0];
			String email = user.getKey()[1];
			String mdp = user.getValue();
		    if (pseudo.equals(identifiant)||email.equals(identifiant)) {
				if (mdp.equals(mdpConnexion)) {
					connexionOk = true;
				}
		    }
		}
		return connexionOk;
	}

	
	private static void log(final String level, final String message) {
		System.out.println(level + message);
	}
}
