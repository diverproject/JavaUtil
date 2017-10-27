package org.diverproject.util;

import org.diverproject.util.lang.StringUtil;

/**
 * <p><h1>Exceção de Utilitários</h1></p>
 *
 * <p>Essa exceção não possui nenhuma funcionalidade extra além a de permitir mensagem formatada.
 * Utilizado pelas classes utilitárias ou outras classes do conjunto de bibliotecas utilitárias.</p>
 *
 * @author Andrew
 */

@SuppressWarnings("serial")
public class UtilException extends Exception
{
	/**
	 * Mensagem que foi gerada quando a exceção foi criada.
	 */
	private String message;

	/**
	 * Constrói uma nova exceção de utilitário sendo necessário definir uma mensagem.
	 * @param message mensagem que será exibida quando a exceção for gerada.
	 */

	public UtilException(String message)
	{
		this.message = message;
	}

	/**
	 * Constrói uma nova exceção de utilitário sendo necessário definir uma mensagem.
	 * @param format string contendo o formato da mensagem que será exibida.
	 * @param args argumentos respectivos a formatação da mensagem.
	 */

	public UtilException(String format, Object... args)
	{
		this.message = String.format(format, args);
	}

	/**
	 * Constrói uma nova exceção de utilitário sendo necessário definir a exceção.
	 * Nesse caso irá construir uma nova exceção a partir de uma exceção existente.
	 * Utilizando a mensagem dessa exceção como mensagem desta.
	 * @param e exceção do qual será considerada para criar uma nova.
	 */

	public UtilException(Exception e)
	{
		if (e.getClass() == this.getClass())
			message = e.getMessage();
		else
			message = StringUtil.addStringException(e.getMessage(), e);

		this.setStackTrace(e.getStackTrace());
	}

	/**
	 * Constrói uma nova exceção de utilitário sendo necessário definir uma mensagem.
	 * Nesse caso a mensagem será usada de uma exceção já criada, porém permite adicionar
	 * um determinado conteúdo extra como dados que será posicionado entre aspas.
	 * @param e exceção para usar a mensagem armazenada no mesmo como exceção.
	 * @param format string contendo o formato do conteúdo extra.
	 * @param args argumentos respectivos a formatação da mensagem.
	 */

	public UtilException(Exception e, String format, Object... args)
	{
		message = String.format(format, args);
		message = StringUtil.addStringData(e.getMessage(), message);

		if (e.getClass() != this.getClass())
			message = StringUtil.addStringException(message, this);

		this.setStackTrace(e.getStackTrace());
	}

	@Override
	public String getMessage()
	{
		return message;
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());
		description.append(message);

		return description.toString();
	}
}
