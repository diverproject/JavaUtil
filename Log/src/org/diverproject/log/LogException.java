package org.diverproject.log;

import org.diverproject.util.UtilRuntimeException;

/**
 * <h1>Exceção de Registro</h1>
 *
 * <p>Esse tipo Exception ocorre sempre que houver um problema no gerenciamento
 * dos registros, quando bem programado e estático for os processamentos, menor a
 * chance desse tipo de exceção ocorrer. Utilizado por toda a projeto de Log.</p>
 */

public class LogException extends UtilRuntimeException
{
	/**
	 * Número da serialização dessa classe.
	 */
	private static final long serialVersionUID = 5518392083117147768L;

	/**
	 * Constrói uma nova exceção para gerenciamento de registros.
	 * @param message mensagem informando o que desencadeou a exception.
	 */

	public LogException(String message)
	{
		super(message);
	}

	/**
	 * Constrói uma nova exceção para gerenciamento de registros.
	 * Nesse caso considera a mensagem de uma exceção.
	 * Além disso mostra o nome da exception (classe).
	 * @param e exceção do qual está sendo gerada.
	 */

	public LogException(Exception e)
	{
		super(e);
	}

	/**
	 * Constrói uma nova exceção para gerenciamento de registros.
	 * @param format formato que terá a mensagem informativa.
	 * @param args argumentos que irá completar o formato da mensagem.
	 */

	public LogException(String format, Object... args)
	{
		super(format, args);
	}

	/**
	 * Constrói uma nova exceção para gerenciamento de registros.
	 * Nesse caso a mensagem será usada de uma exceção já criada, porém permite adicionar
	 * um determinado conteúdo extra como dados que será posicionado entre aspas.
	 * @param e exceção para usar a mensagem armazenada no mesmo como exceção.
	 * @param format string contendo o formato do conteúdo extra.
	 * @param args argumentos respectivos a formatação da mensagem.
	 */

	public LogException(Exception e, String format, Object... args)
	{
		super(e, format, args);
	}
}
