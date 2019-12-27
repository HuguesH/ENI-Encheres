package fr.eni.eniencheres.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.eniencheres.bll.managers.CategorieManager;

/**
 * Servlet implementation class TraitementAccueil
 */
@WebServlet("/TraitementAccueil")
public class TraitementAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TraitementAccueil() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Forward vers /Accueil en envoyant la liste des libell�s
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategorieManager categorieManager = new CategorieManager();
		List<String> listeLibellesCategories = new ArrayList<>();
		listeLibellesCategories = categorieManager.getAllLibelles();
		request.setAttribute("libelles", listeLibellesCategories);
		RequestDispatcher rd = request.getRequestDispatcher("/Accueil");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
