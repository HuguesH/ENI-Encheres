package fr.eni.eniencheres.bll.managers;

import fr.eni.eniencheres.bo.ArticleVendu;
import fr.eni.eniencheres.dal.ArticleVenduDAO;
import fr.eni.eniencheres.dal.DALException;
import fr.eni.eniencheres.dal.DAOFactory;

public class ArticleManager {

	private ArticleVenduDAO articleDAO;

	/**
	 * Constructeur de ArticleManager
	 */
	public ArticleManager() {
		this.articleDAO=DAOFactory.getArticleDAO();
	}
	
	/**
	 * Ajoute un article dans la BDD
	 * @param article
	 */	 
	public void addArticle(ArticleVendu article) {
		try {
			articleDAO.insert(article);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
