package org.diverproject.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <p>Utilizado para permitir conexões com banco de dados do tipo SQLite.
 * Este por sua vez requer a utilização de uma biblioteca que permita
 * a conexão do mesmo. Normalmente se utiliza o SQLite Connector.</p>
 *
 * <p>A finalidade e tornar a conexão mais simples e transparente aos
 * desenvolvedores que desejam efetuar uma conexão SQLite. De modo que
 * seja necessário apenas passar a referência do arquivo que será lido.</p>
 */

public class SQLite
{
	/**
	 * Referência da conexão quando for criada.
	 */
	private Connection connection;

	/**
	 * Constrói uma nova conexão com um determinado arquivo do tipo banco de dados.
	 * @param path caminho do qual se encontra o arquivo que será usado na conexão.
	 * @throws ClassNotFoundException ocorre apenas se não houver o SQLite Connector.
	 * @throws SQLException problema que pode ocorrer durante a conexão SQL.
	 */

	public SQLite(String path) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" + path);
	}

	/**
	 * A utilização dessa classe e permitir a facilidade de conexão com o SQLite.
	 * @return conexão com o banco de dados efetuada.
	 */

	public Connection getConnection()
	{
		return connection;
	}

	/**
	 * Permite fechar a conexão com o banco de dados de modo seguro.
	 * @throws SQLException exceção que pode ocorrer.
	 */

	public void closeConnection() throws SQLException
	{
		connection.close();
	}
}
