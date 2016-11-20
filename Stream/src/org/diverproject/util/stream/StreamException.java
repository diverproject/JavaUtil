package org.diverproject.util.stream;

import org.diverproject.util.UtilException;

/**
 * <p><h1>Exceção de Streams</h1></p>
 *
 * <p>Esse tipo Exception ocorre sempre que houver um problema no gerenciamento
 * das streams, quando bem programado e estático for os processamentos, menor a
 * chance desse tipo de exceção ocorrer. Utilizado por toda a package.</p>
 */

public class StreamException extends UtilException
{
	/**
	 * Número da serialização dessa classe.
	 */
	private static final long serialVersionUID = 5518392083117147768L;

	/**
	 * Constrói uma nova exceção para gerenciamento de stream.
	 * @param message mensagem informando o que desencadeou a exception.
	 */

	public StreamException(String message)
	{
		super(message);
	}

	/**
	 * Constrói uma nova exceção para gerenciamento de stream.
	 * Nesse caso considera a mensagem de uma exceção.
	 * Além disso mostra o nome da exception (classe).
	 * @param e exceção do qual está sendo gerada.
	 */

	public StreamException(Exception e)
	{
		super(e);
	}

	/**
	 * Constrói uma nova exceção para gerenciamento de stream.
	 * @param format formato que terá a mensagem informativa.
	 * @param args argumentos que irá completar o formato da mensagem.
	 */

	public StreamException(String format, Object... args)
	{
		super(format, args);
	}

	/**
	 * Constrói uma nova exceção para gerenciamento de stream.
	 * Nesse caso a mensagem será usada de uma exceção já criada, porém permite adicionar
	 * um determinado conteúdo extra como dados que será posicionado entre aspas.
	 * @param e exceção para usar a mensagem armazenada no mesmo como exceção.
	 * @param format string contendo o formato do conteúdo extra.
	 * @param args argumentos respectivos a formatação da mensagem.
	 */

	public StreamException(Exception e, String format, Object... args)
	{
		super(e, format, args);
	}
}
