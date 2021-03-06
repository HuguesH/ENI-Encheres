package fr.eni.eniencheres.bll;

import fr.eni.eniencheres.bo.Retrait;
import fr.eni.eniencheres.dal.DALException;
import fr.eni.eniencheres.dal.DAOFactory;
import fr.eni.eniencheres.dal.RetraitDAO;

//TODO vérifier les catch block
//TODO fusionner avec ArticleManager
public class RetraitManager {

	private RetraitDAO retraitDAO;

	/**
	 * constructeur du RetraitManager
	 */
	public RetraitManager() {
		this.retraitDAO=DAOFactory.getRetraitDAO();
	}
	
	/**
	 * ajoute un retrait dans la base de donnees
	 * @param retrait
	 */
	public void addRetrait(Retrait retrait) {
		
		try {
			retraitDAO.insert(retrait);
		} catch (DALException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ajoute un retrait dans la base de donnees en precisant le numero de l'article
	 * @param retrait
	 * @param noArticle
	 */
	public void addRetraitWIdArticle(Retrait retrait, int noArticle) {
		try {
			retraitDAO.insert(retrait, noArticle);
		} catch (DALException e) {
			e.printStackTrace();
		}
	}

}
