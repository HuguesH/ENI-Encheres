package fr.eni.eniencheres.dal.jdbc;

import fr.eni.eniencheres.conf.MyLogger;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

abstract class ConnectionProvider {

	private static Logger LOG = MyLogger.getLogger(ConnectionProvider.class);

	private static DataSource dataSource;

	private static final String JNDI_DATASOURCE = "jdbc/pool_cnx";

	private static final String MOCK_URL_H2 = "jdbc:h2:file:./target/test-db;MODE=MSSQLSERVER";
	private static final String MOCK_USER = "sa";
	private static final String MOCK_PASSWORD = "";
	
	static
	{
		try {
			Context context = new InitialContext();
			ConnectionProvider.dataSource = (DataSource)context.lookup("java:comp/env/"+JNDI_DATASOURCE);
			LOG.log(Level.INFO, "USE JNDI DataSource : {} {} ", new Object[]{JNDI_DATASOURCE,dataSource});

		} catch (NamingException e){
			LOG.log(Level.SEVERE, "Not found JNDI Resource DataSource with name : {}", JNDI_DATASOURCE);
			// Use the Tomcat Pool DataSource, similar to the web servelt container
			org.apache.tomcat.jdbc.pool.DataSource dataSourceTomcat = new org.apache.tomcat.jdbc.pool.DataSource();
			dataSourceTomcat.setDriverClassName("org.h2.Driver");
			dataSourceTomcat.setUrl(MOCK_URL_H2);
			dataSourceTomcat.setUsername(MOCK_USER);
			dataSourceTomcat.setPassword(MOCK_PASSWORD);
			dataSource = dataSourceTomcat;
			LOG.log(Level.WARNING, "USE MOCK Datasource {} with URL : {} ", new Object[]{dataSource,MOCK_URL_H2});
		}
		// Initialise or Check DataBase with Flyway
		Flyway flyway = Flyway.configure().dataSource(dataSource).load();
		flyway.migrate();
	}
	/**
	 * Cette methode retourne une connection operationnelle issue du pool de connexion
	 * vers la base de donnees. 
	 * @return
	 **/

	public static Connection getConnection() throws SQLException
	{
		return ConnectionProvider.dataSource.getConnection();
	}

}

