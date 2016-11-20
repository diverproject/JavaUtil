package org.diverproject.util.collection.abstraction;

import org.diverproject.util.ObjectDescription;

/**
 * <p><h1>Elemento Mapeado</h1></p>
 *
 * <p>Objetos desse tipo são utilizados pelas estruturas de dados do tipo mapa.
 * Essas estruturas utilizando determinadas chaves para mapear seus elementos.
 * Assim eles são organizados de acordo com a estrutura e a chave definida.</p>
 *
 * <p>Além disso os atributos são definidos como package como a classe também.
 * De modo que estes possam ser utilizados apenas pelo pacote das estruturas.
 * Para ser mais preciso, na implementação da abstração do conceito delas.</p>
 *
 * @author Andrew
 *
 * @param <K> qual será o tipo de chave que será ligado ao elemento.
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

class MapElement<K, E>
{
	/**
	 * Qual será a chama desse elemento para ser mapeável.
	 */
	K key;

	/**
	 * Referência do elemento que será ligado a chave deste.
	 */
	E value;

	/**
	 * Constrói um novo elemento mapeável iniciando seus atributos.
	 * @param key chave que será definido ao elemento mapeável.
	 * @param element referência do elemento que será armazenado.
	 */

	public MapElement(K key, E element)
	{
		this.key = key;
		this.value = element;
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		description.append("key", key);
		description.append("element", value);

		return description.toString();
	}
}
