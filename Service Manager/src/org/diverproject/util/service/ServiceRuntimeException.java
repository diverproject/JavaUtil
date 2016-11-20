package org.diverproject.util.service;

import org.diverproject.util.UtilException;

/**
 * <h1>Exception para Serviço</h1>
 *
 * <p>Esse tipo de exceção é um RuntimeException, ou seja, não gera TryCatch automático.
 * É utilizado garantir que determinados procedimentos não falhem causado danos ao sistema.
 * Esse tipo de exceção pode ser tratada por TryCtach mas não é obrigatório na codificação.
 * Quando detectado exceções desse tipo a thread que gerou ele será interrompido.</p>
 *
 * @see UtilRuntimeException
 *
 * @author Andrew
 */

public class ServiceRuntimeException extends UtilException
{
	/**
	 * Número de serialização desse tipo de exceção.
	 */
	private static final long serialVersionUID = 2926428005702877844L;

	/**
	 * Constrói uma nova exceção Asckaryn de tratamento não obrigatório para serviços.
	 * @param message mensagem que será exibida quando a exceção for gerada.
	 */

	public ServiceRuntimeException(String message)
	{
		super(message);
	}

	/**
	 * Constrói uma nova exceção Asckaryn de tratamento não obrigatório para serviços.
	 * @param format string contendo o formato da mensagem que será exibida.
	 * @param args argumentos respectivos a formatação da mensagem.
	 */

	public ServiceRuntimeException(String format, Object... args)
	{
		super(format, args);
	}

	/**
	 * Constrói uma nova exceção de utilitário sendo necessário definir a exceção.
	 * Nesse caso irá construir uma nova exceção a partir de uma exceção existente.
	 * Utilizando a mensagem dessa exceção como mensagem desta.
	 * @param e exceção do qual será considerada para criar uma nova.
	 */

	public ServiceRuntimeException(Exception e)
	{
		super(e);
	}

	/**
	 * Constrói uma nova exceção Asckaryn de tratamento não obrigatório para serviços.
	 * Nesse caso a mensagem será usada de uma exceção já criada, porém permite adicionar
	 * um determinado conteúdo extra como dados que será posicionado entre aspas.
	 * @param e exceção para usar a mensagem armazenada no mesmo como exceção.
	 * @param format string contendo o formato do conteúdo extra.
	 * @param args argumentos respectivos a formatação da mensagem.
	 */

	public ServiceRuntimeException(Exception e, String format, Object... args)
	{
		super(e, format, args);
	}
}
