package org.diverproject.util;

/**
 * <p><h1>Descrição do Objeto</h1></p>
 *
 * <p>Essa classe é utilizada nos métodos toString que todos objetos possuem para visualizar.
 * Possuindo um padrão para exibir as informações do objeto de acordo com Java Util.
 * Nele deve ser definido a classe que será descrevida e em seguida, pode ser definido
 * todos os atributos contido nele, como também o nome que será dado a esse na string.</p>
 *
 * @author Andrew
 */

public class ObjectDescription
{
	/**
	 * Nome da classe que será descrevida.
	 */
	private String className;

	/**
	 * Conteúdo respectivo aos atributos definidos.
	 */
	private String attributes;

	/**
	 * Constrói uma nova descrição para objetos sendo necessário definir a classe.
	 * @param c referência da classe do qual será usada para descrever o objeto.
	 */

	public ObjectDescription(Class<?> c)
	{
		if (c != null)
			className = c.getSimpleName();
		attributes = "";
	}

	/**
	 * Anexar um determinado campo do objeto (atributo) para essa descrição.
	 * @param value referência da variável do qual está querendo colocar na descrição.
	 */

	public void append(Object value)
	{
		attributes += String.format("%s, ", value);
	}

	/**
	 * Anexar um determinado campo do objeto (atributo) para essa descrição.
	 * @param field qual será o nome do campo que está sendo anexado (nome da variável).
	 * @param value referência da variável do qual está querendo colocar na descrição.
	 */

	public void append(String field, Object value)
	{
		attributes += String.format("%s: %s, ", field, value);
	}

	@Override
	public String toString()
	{
		if (className != null)
		{
			if (attributes.length() > 2)
				return String.format("%s[%s]", className, attributes.substring(0, attributes.length() - 2));

			return String.format("%s[%s]", className, attributes);
		}

		if (attributes.length() > 2)
			return String.format("%s", attributes.substring(0, attributes.length() - 2));

		return String.format("%s", attributes);

	}
}
