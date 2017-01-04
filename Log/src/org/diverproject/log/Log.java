package org.diverproject.log;

/**
 * <h1>Registro</h1>
 *
 * <p>Classe criada internamente no serviço para guardar as informações de um registro gerado.
 * Registros contem um tipo e uma mensagem que é sua informação ou ocorrido a ser registrado.</p>
 *
 * <p>Além disso através dele é possível saber a origem do mesmo através de um StackTraceElement.
 * Nele podemos saber qual a linha e arquivo que contém a chamada tal como o nome do método
 * onde se encontra o chamado, podendo ainda se saber o nome da classe que pode divergir do arquivo.</p>
 *
 * @see StackTraceElement
 *
 * @author Andrew
 */

public class Log
{
	/**
	 * Tipo de mensagem que está sendo gerada.
	 */
	private String type;

	/**
	 * Mensagem contendo as informações/ocorrido a ser registrado.
	 */
	private String message;

	/**
	 * Objeto que irá permitir localizar a origem de onde foi feito o registro da mensagem.
	 */
	private Throwable throwable;

	/**
	 * Referência do Trace contendo informações da origem da chamada.
	 */
	private StackTraceElement stackTraceElement;

	/**
	 * Construtor em visibilidade package para evitar instâncias fora do projeto.
	 * Essa classe deve ser gerada exclusivamente pelo projeto conform necessário.
	 */

	Log(Throwable throwable)
	{
		this.throwable = throwable;
	}

	/**
	 * O tipo da mensagem pode ser de um padrão pré-definido pelo sistema de registro.
	 * Mas pode ainda ser o nome de uma classe quando for o caso de uma Exception.
	 * @return aquisição do tipo de mensagem que será gerada pelo registro.
	 */

	public String getType()
	{
		return type;
	}

	/**
	 * @return aquisição do objeto que identifica a origem do registro.
	 */

	public Throwable getThrowable()
	{
		return throwable;
	}

	/**
	 * O tipo da mensagem pode ser de um padrão pré-definido pelo sistema de registro.
	 * Mas pode ainda ser o nome de uma classe quando for o caso de uma Exception.
	 * @param type tipo de mensagem que deverá ser gerada pelo registro.
	 */

	void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Um registro possui uma mensagem referente a uma informação ou ocorrido.
	 * Essa mensagem possui um conteúdo valioso ao projeto por isso será registrado.
	 * @return aquisição da mensagem do qual deverá ser registrada.
	 */

	public String getMessage()
	{
		return message;
	}

	/**
	 * Método com visão de pacote para evitar que seja alterada por fora.
	 * Permite definir uma mensagem ao registro, definindo um ocorrido/informação.
	 * @param message referência da string contendo a mensagem a ser registrada.
	 */

	void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * Permite definir um objeto que irá conter informações da origem da chamada do log.
	 * Através desse método é possível saber o nome do arquivo, classe, método e linha.
	 * @param stackTraceElement referência do trace obtido de um throwable.
	 */

	void setStackTrace(StackTraceElement stackTraceElement)
	{
		this.stackTraceElement = stackTraceElement;
	}

	/**
	 * Através de um StrackTraceElement será possível saber onde o método log foi chamado.
	 * Aqui será possível saber o nome do arquivo que possui a classe que chamou o log.
	 * @return aquisição do nome do arquivo que contém a classe do chamado.
	 */

	public String getFileName()
	{
		return stackTraceElement.getFileName();
	}

	/**
	 * Através de um StrackTraceElement será possível saber onde o método log foi chamado.
	 * Aqui será possível saber o nome da classe que possui o método que chamou o log.
	 * @return aquisição do nome da classe que gerou a mensagem.
	 */

	public String getClassName()
	{
		String name = stackTraceElement.getClassName();

		return name.substring(name.lastIndexOf('.') + 1);
	}

	/**
	 * Através de um StrackTraceElement será possível saber onde o método log foi chamado.
	 * Aqui será possível saber o nome do método em uma classe que chamou o log.
	 * @return nome do método em uma classe que gerou a mensagem.
	 */

	public String getMethodName()
	{
		return stackTraceElement.getMethodName();
	}

	/**
	 * Através de um StrackTraceElement será possível saber onde o método log foi chamado.
	 * Aqui será possível saber o número da linha do arquivo JAVA do chamado log.
	 * @return aquisição do número da linha de uma arquivo que gerou a mensagem.
	 */

	public int getLineNumber()
	{
		return stackTraceElement.getLineNumber();
	}

	@Override
	public String toString()
	{
		if (type != null)
			return String.format("[%s] %s.%s: %s", getType(), getClassName(), getMethodName(), getMessage());

		return String.format("%s.%s: %s", getClassName(), getMethodName(), getMessage());
	}
}
