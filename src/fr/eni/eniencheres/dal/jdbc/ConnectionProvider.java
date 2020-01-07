package fr.eni.eniencheres.dal.jdbc;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

abstract class ConnectionProvider {

	private static Logger LOG = LoggerFactory.getLogger(ConnectionProvider.class);

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
			LOG.info("USE JNDI DataSource : {} {} ", new Object[]{JNDI_DATASOURCE,dataSource});

		} catch (NamingException e){
			LOG.info("Not found JNDI Resource DataSource with name : {}", JNDI_DATASOURCE);
			// Use the Tomcat Pool DataSource, similar to the web servelt container
			org.apache.tomcat.jdbc.pool.DataSource dataSourceTomcat = new org.apache.tomcat.jdbc.pool.DataSource();
			dataSourceTomcat.setDriverClassName("org.h2.Driver");
			dataSourceTomcat.setUrl(MOCK_URL_H2);
			dataSourceTomcat.setUsername(MOCK_USER);
			dataSourceTomcat.setPassword(MOCK_PASSWORD);
			dataSource = dataSourceTomcat;
			LOG.warn("USE MOCK Datasource with URL : {} ", new Object[]{MOCK_URL_H2});
		}


		migrateDataBase();

	}

	private static void migrateDataBase() {
		Database database = null;
		try {
			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
			Liquibase liquibase = new Liquibase("db/eniChangelog.xml", new ClassLoaderResourceAccessor(), database);

			liquibase.update(new Contexts(), new LabelExpression());
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LiquibaseException e) {
			e.printStackTrace();
		}
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

