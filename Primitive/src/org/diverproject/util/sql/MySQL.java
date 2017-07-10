package org.diverproject.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.diverproject.util.ObjectDescription;

/**
 * <p>Utilizado para permitir conexões com banco de dados do tipo MySQL.
 * Este por sua vez requer a utilização de uma biblioteca que permita
 * a conexão do mesmo. Normalmente se utiliza o MYSQL Connector.</p>
 *
 * <p>A finalidade e tornar a conexão mais simples e transparente aos
 * desenvolvedores que desejam efetuar uma conexão MYSQL. De modo
 * que após a instância você possa definir os dados de conexão que
 * são: host, database, usuário e senha. Após isso é possível iniciar
 * a conexão por connect(). Se tudo estiver certo irá criar a conexão.</p>
 */

public class MySQL
{
	/**
	 * Endereço do host do qual está o MYSQL.
	 */
	private String host;

	/**
	 * Nome do banco de dados que será utilizado.
	 */
	private String database;

	/**
	 * Usuário que irá permitir o acesso a esse banco.
	 */
	private String username;

	/**
	 * Senha respectiva do usuário que será utilizado.
	 */
	private String password;

	/**
	 * Referência da conexão quando for criada.
	 */
	private Connection connection;

	/**
	 * Porta para conexão com o banco de dados MySQL.
	 */
	private int port;

	/**
	 * TODO
	 */
	private boolean useLegacyDatetimeCode;

	/**
	 * Fuso horário que a conexão deve considerar.
	 */
	private String serverTimezone;

	/**
	 * Converter horários que estejam com zeros em nulo.
	 */
	private boolean zeroDateTimeToNull;

	/**
	 * Faz a conexão com o banco de dados MYSQL de acordo com os dados definidos.
	 * @throws ClassNotFoundException ocorre caso não haja a biblioteca do MYSQL.
	 * @throws SQLException algum problema ao tentar se conectar com o banco.
	 */

	public void connect() throws ClassNotFoundException, SQLException
	{
		if (host == null || host.length() == 0)
			throw new SQLException("host não configurado");

		if (database == null || database.length() == 0)
			throw new SQLException("banco de dados não configurado");

		if (username == null || username.length() == 0)
			throw new SQLException("usuário não configurado");

		if (password == null)
			password = "";

		if (port == 0)
			port = 3306;

		Class.forName("com.mysql.jdbc.Driver");

		String arguments = "&serverTimezone=America/Sao_Paulo";

		if (serverTimezone != null)
			arguments = "&serverTimezone=" +serverTimezone;

		if (useLegacyDatetimeCode)
			arguments += "&useLegacyDatetimeCode=true";
		else
			arguments += "&useLegacyDatetimeCode=false";

		if (zeroDateTimeToNull)
			arguments += "&zeroDateTimeBehavior=convertToNull";

		String url = String.format("jdbc:mysql://%s:%d/%s?%s", host, port, database, arguments);
		connection = DriverManager.getConnection(url, username, password);
	}

	/**
	 * A utilização dessa classe e permitir a facilidade de conexão com o MYSQL.
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

	/**
	 * O host é o endereço do qual se encontra o banco de dados, normalmente um IP.
	 * @param host nome do host que será utilizado para fazer a conexão.
	 */

	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * O banco de dados define qual conjunto de tabelas será usado quando queries forem feitas.
	 * @return nome do banco de dados que está sendo utilizado nessa conexão.
	 */

	public String getDatabase()
	{
		return database;
	}

	/**
	 * O banco de dados define qual conjunto de tabelas será usado quando queries forem feitas.
	 * @param database nome do banco de dados que será utilizado nessa conexão.
	 */

	public void setDatabase(String database)
	{
		this.database = database;
	}

	/**
	 * O nome de usuário é necessário para fazer uma conexão com o banco de dados MYSQL.
	 * @param username nome do usuário que fará o acesso ao banco de dados definido.
	 */

	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * Definir apenas caso haja uma, caso contrário não é necessário.
	 * @param password senha respectiva do usuário da conexão.
	 */

	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Definir apenas caso a porta não seja a padrão (3306), caso contrário não é necessário.
	 * @param port nova porta em que o MySQL está realizando as conexões.
	 */

	public void setPort(int port)
	{
		if (port > 0 && port < 65536)
			this.port = port;
	}

	/**
	 * TODO
	 * @param useLegacyDatetimeCode
	 */

	public void setUseLegacyDatetimeCode(boolean useLegacyDatetimeCode)
	{
		this.useLegacyDatetimeCode = useLegacyDatetimeCode;
	}

	/**
	 * Permite definir o fuso horário do qual a conexão com o banco deverá considerar.
	 * @param serverTimezone nome que se refere ao fuso horário desejado, padrão: "America/Sao_paulo".
	 * @see https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
	 */

	public void setServerTimezone(String serverTimezone)
	{
		this.serverTimezone = serverTimezone;
	}

	/**
	 * Através desta opção os resultados das consultas efetuadas no banco de dados que possuir
	 * um DateTime com valores todos igual a zero será considerado na conversão como nulo.
	 * @param enable true para habilitar ou false para desabilitar essa opação.
	 */

	public void setZeroDateTimeToNull(boolean enable)
	{
		this.zeroDateTimeToNull = enable;
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		description.append("host", host);
		description.append("port", port);
		description.append("database", database);
		description.append("username", username);
		description.append("password", password);

		if (useLegacyDatetimeCode)
			description.append("useLegacyDatetimeCode");
		
		if (zeroDateTimeToNull)
			description.append("zeroDateTimeToNUll");

		if (serverTimezone != null)
			description.append("serverTimeZone", serverTimezone);

		return description.toString();
	}
}
