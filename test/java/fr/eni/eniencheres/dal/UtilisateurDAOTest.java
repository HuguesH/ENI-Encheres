package fr.eni.eniencheres.dal;

import fr.eni.eniencheres.bo.Utilisateur;
import fr.eni.eniencheres.dal.DAOFactory;
import fr.eni.eniencheres.dal.UtilisateurDAO;
import fr.eni.eniencheres.dal.DALException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;

public class UtilisateurDAOTest {

    UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();

    @Test
    @Ignore
    public void insertDifferentTest() throws DALException{
        long id = System.currentTimeMillis();
        Utilisateur u1 = new Utilisateur("titi" + String.valueOf(id), "DELAFONTAINE", "Robert", "tito@gmail.com",
                "0606060606", "12 rue du Corbeau et du Renard", "44444", "TDCDM", "motdepasse", 600, false);
        utilisateurDAO.insert(u1);
        Utilisateur u2 = new Utilisateur("toto" + String.valueOf(id + 1), "DELAFONTAINE", "Robert", "tito@gmail.com",
                "0606060606", "12 rue du Corbeau et du Renard", "44444", "TDCDM", "motdepasse", 600, false);
        Utilisateur u3 = new Utilisateur("tutu" + String.valueOf(id + 2), "DELAFONTAINE", "Robert", "tito@gmail.com",
                "0606060606", "12 rue du Corbeau et du Renard", "44444", "TDCDM", "motdepasse", 600, true);

        
    }

}
