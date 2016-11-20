package org.diverproject.util.stream.implementation.option;

import org.diverproject.util.ObjectDescription;

/**
 * <p><h1>Opção em Stream</h1></p>
 *
 * <p>Objetos desse tipo são criados para que possam salvar dados de opções para stream.
 * Quando uma stream de entrada é criada, esses objetos podem ser adquiridos da stream.
 * De modo que seja possível obter opções carregadas ao invés de sequenciais.</p>
 *
 * @author Andrew
 *
 * @param <E> tipo de objeto que será armazenado.
 */

public class StreamOptionValue<E>
{
	/**
	 * Nome que foi dado a essa opção.
	 */
	private String name;

	/**
	 * Referência do valor que foi dado a essa opção.
	 */
	private E value;

	/**
	 * Constrói uma nova opção onde deve ser definido o seu nome e valor.
	 * @param name nome da opção é utilizado para localizá-la na stream.
	 * @param value valor da opção é respectivo ao valor definido ao mesmo.
	 */

	public StreamOptionValue(String name, E value)
	{
		this.name = name;
		this.value = value;
	}

	/**
	 * Utilizado para que possa ser localizado na stream.
	 * @return aquisição do nome da ação carregada da stream.
	 */

	public String getName()
	{
		return this.name;
	}

	/**
	 * Utilizado para salvar o valor na stream ou usá-lo na aplicação.
	 * @return aquisição do valor carregado da stream dessa opção.
	 */

	public E getValue()
	{
		return value;
	}

	/**
	 * Permite definir um novo valor para que essa opção passe a assumir.
	 * @param value novo valor do qual a preferencia deve considerar.
	 */

	public void setValue(E value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		description.append("name", name);
		description.append("value", value);
		description.append("type", value != null ? value.getClass() : null);

		return description.toString();
	}
}
