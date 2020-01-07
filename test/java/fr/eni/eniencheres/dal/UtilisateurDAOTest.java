package fr.eni.eniencheres.dal;

import fr.eni.eniencheres.bo.Utilisateur;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

public class UtilisateurDAOTest {
	
	private static Logger LOG = LoggerFactory.getLogger(UtilisateurDAOTest.class);

    UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();

    @Test
    public void insertTest() throws DALException{
    	int initialCount = utilisateurDAO.selectAll().size();
    	LOG.info("before test<insertTest>, utilisateurs count :" + String.valueOf(initialCount));
        long id = System.currentTimeMillis();
        Utilisateur u1 = new Utilisateur("titi" + String.valueOf(id), "Nom1", "Robert", "tito@gmail.com",
                "0606060606", "12 rue du Corbeau et du Renard", "44444", "TDCDM", "motdepasse", 600, false);
        utilisateurDAO.insert(u1);
        Utilisateur u2 = new Utilisateur("toto" + String.valueOf(id + 1), "Nom2", "Robert", "tito@gmail.com",
                "0606060606", "12 rue du Corbeau et du Renard", "44444", "TDCDM", "motdepasse", 600, false);
        utilisateurDAO.insert(u2);
        Utilisateur u3 = new Utilisateur("tutu" + String.valueOf(id + 2), "Nom3", "Robert", "tito@gmail.com",
                "0606060606", "12 rue du Corbeau et du Renard", "44444", "TDCDM", "motdepasse", 600, true);
        utilisateurDAO.insert(u3);
        Assert.assertEquals("Test des insert",initialCount + 3,  utilisateurDAO.selectAll().size());
        
        
    }

}
