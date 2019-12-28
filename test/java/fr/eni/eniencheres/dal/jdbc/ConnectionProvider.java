package fr.eni.eniencheres.dal.jdbc;


import java.sql.Connection;
import java.sql.SQLException;

import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;


abstract class ConnectionProvider {

	private static Logger log = Logger.getLogger("ConnectionProvider");

	private static Connection connection;
	
	static
	{
		try {
			connection = DriverManager.getConnection("jdbc:hsqldb:file:./target/test-db", "sa", "");
			
        } catch (SQLException e) {
            printSQLException(e);
        }
		
	}

	public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                log.log(Level.SEVERE,"SQLState: " + ((SQLException) e).getSQLState());
                log.log(Level.SEVERE,"Error Code: " + ((SQLException) e).getErrorCode());
                log.log(Level.SEVERE,"Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    log.log(Level.SEVERE,"Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
	
	/**
	 * Cette methode retourne une connection operationnelle issue du pool de connexion
	 * vers la base de donnees. 
	 * @return
	 **/

	public static Connection getConnection() throws SQLException
	{
		return connection;
	}

}

