package fr.eni.eniencheres.bll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.eniencheres.conf.MyLogger;


/**
 * Servlet implementation class TraitementAccueil
 */
@WebServlet("/TraitementAccueil")
public class TraitementAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = MyLogger.getLogger(TraitementAccueil.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TraitementAccueil() {
        super();
    }

    /**
	 * Forward vers /Accueil en envoyant la liste des libelles
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("void doGet");
		CategorieManager categorieManager = new CategorieManager();
		List<String> listeLibellesCategories = new ArrayList<>();
		listeLibellesCategories = categorieManager.getAllLibelles();
		request.setAttribute("libelles", listeLibellesCategories);
		String redirectURI = "/Accueil";
		LOG.info("void doGet requestDispatcher.forward to URI" + redirectURI);
		RequestDispatcher rd = request.getRequestDispatcher(redirectURI);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("void doPost");

	}

}
