package fr.eni.eniencheres.dal.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.flywaydb.core.Flyway;

abstract class ConnectionProvider {

	private static Logger LOG = Logger.getLogger("ConnectionProvider");

	private static DataSource dataSource;
	private static final String JDBC_URL_H2 = "jdbc:h2:file:./target/test-db;MODE=MSSQLSERVER";
	private static final String JDBC_USER = "sa";
	private static final String JDBC_PASSWORD = "";

	static {

		dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl(JDBC_URL_H2);
		dataSource.setUsername(JDBC_USER);
		dataSource.setPassword(JDBC_PASSWORD);

		Flyway flyway = Flyway.configure().dataSource(dataSource).load();
		flyway.migrate();

	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				LOG.log(Level.SEVERE, "SQLState: " + ((SQLException) e).getSQLState());
				LOG.log(Level.SEVERE, "Error Code: " + ((SQLException) e).getErrorCode());
				LOG.log(Level.SEVERE, "Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					LOG.log(Level.SEVERE, "Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	/**
	 * Cette methode retourne une connection operationnelle issue du pool de
	 * connexion vers la base de donnees.
	 * 
	 * @return
	 **/

	public static Connection getConnection() throws SQLException {
		LOG.log(Level.WARNING, "!!!  USE MOCK Connection with URL : " + JDBC_URL_H2);
		return dataSource.getConnection();
	}

}
