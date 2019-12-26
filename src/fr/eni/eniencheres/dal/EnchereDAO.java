package fr.eni.eniencheres.dal;

import fr.eni.eniencheres.bo.Enchere;

public interface EnchereDAO {
	
	/**
	 * Ins�rer une nouvelle ench�re
	 * @param enchere
	 * @throws DALException
	 */
	public void insert(Enchere enchere) throws DALException;
}
