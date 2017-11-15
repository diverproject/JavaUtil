package org.diverproject.util.collection;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;

/**
 * <p><h1>Mapa</h1></p>
 *
 * <p>Estruturas de dados do tipo mapa funciona de modo que todos elementos possuam uma chave.
 * Essa chave será usada para interagir com o elemento como remover ou selecionar o mesmo.
 * A chave não pode ser usada por dos elementos por mais que sejam iguais ou distintos.</p>
 *
 * <p>Nesse caso a iteração pode ser feita não apenas dos elementos como das chaves também.
 * Remover elementos neste caso pode ser feito ou pela chave ou referência do elemento em si.
 * Sempre que um novo elemento for adicionado é obrigatório ter uma chave identificadora.</p>
 *
 * @see Collection
 *
 * @author Andrew
 *
 * @param <K> qual será o tipo de dado que será usado como chave.
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public interface Map<K, E> extends Collection<E>
{
	/**
	 * Verifica se um determinada chave já está sendo utilizada no mapeador.
	 * @param key chave do qual deve ser verificado a existência.
	 * @return true se conter ou false caso contrário.
	 */

	boolean containsKey(K key);

	/**
	 * Permite adicionar um novo elemento ligado a uma chave de localização.
	 * @param key chave que será usada para definir a localização do elemento.
	 * @param element referência do elemento do qual deseja adicionar ao mapeador.
	 * @return true se conseguir adicionar false se valor nulo ou chave usada.
	 */

	boolean add(K key, E element);

	/**
	 * Permite remover um determinado elemento do mapeador pela sua referência.
	 * @param element referência do elemento do qual deseja remover do mapeador.
	 * @return true se conseguir remover false caso não encontre no mesmo.
	 */

	boolean remove(E element);

	/**
	 * Permite remover um determinado elemento do mapeador pela sua chave.
	 * @param key chave respectiva ao elemento do qual será removido.
	 * @return true se conseguir remover false caso não encontre no mesmo.
	 */

	boolean removeKey(K key);

	/**
	 * Permite renomear a chave de um determinado elemento para se possível.
	 * @param oldKey chave antiga é a chave utilizada no momento pelo elemento.
	 * @param newKey qual será a nova chave a ser definida a chave antiga.
	 * @return true se conseguir renomear ou false se uma chave for inválida,
	 * chave inválida pode ser chave já existente (nova) ou então nula.
	 */

	boolean renameKey(K oldKey, K newKey);

	/**
	 * Permite atualizar um determinado valor no mapeador pela sua chave.
	 * @param key chave respectiva ao elemento do qual será atualizado.
	 * @param value referência do valor que será definido a chave.
	 * @return true se existir e atualizar ou false caso contrário.
	 */

	boolean update(K key, E value);

	/**
	 * Permite obter um determinado elemento pela sua chave definida.
	 * @param key chave respectiva ao elemento desejado do mapeador.
	 * @return aquisição do elemento respectivo na chave passada.
	 */

	E get(K key);

	/**
	 * Deve fazer a iteração de todos as chaves existente no mapeador.
	 * @return aquisição de uma nova iteração das chaves.
	 */

	Iterator<K> iteratorKey();

	/**
	 * Deve fazer a iteração de todos os elementos com chaves existente no mapeador.
	 * @return aquisição de uma nova iteração dos elementos com chaves.
	 */

	Iterator<MapItem<K, E>> iteratorItems();

	/**
	 * Essa iteração irá passar por todos os elementos retornando suas chaves.
	 * Pode ser usado a fim de obter os elementos através de suas chaves.
	 * @return aquisição de uma iteração das chaves usadas.
	 */

	Iterable<K> iterateKey();

	/**
	 * Essa iteração irá passar por todos os elementos retornando seu item mapeado.
	 * O item mapeado é composto pela sua chave e o valor do elemento armazenado.
	 * @return aquisição de uma iteração dos itens mapeados.
	 */

	Iterable<MapItem<K, E>> iterateItems();

	/**
	 * <p><h1>Item Mapeado</h1></p>
	 *
	 * <p>Usado para permitir uma iteração que retorne tanto o elemento quando a chave dos itens armazenados.
	 * A forma como as coleções são feitas, a iteração deve ser apenas dos elementos, possuindo ainda uma
	 * iteração apenas para as chaves dos elementos, usando esta para unir as duas iterações.</p>
	 *
	 * @author Andrew Mello
	 *
	 * @param <K> qual o tipo da chave usada.
	 * @param <E> qual o tipo do elemento usado.
	 */

	public class MapItem<K, E>
	{
		/**
		 * Chave usada para identificar o elemento.
		 */
		private K key;

		/**
		 * Valor armazenado na chave desse item.
		 */
		private E value;

		/**
		 * Constrói um novo item mapeado sendo necessário determinar o seu valor e chave.
		 * @param key chave usada para identificar o elemento no mapeador.
		 * @param value valor armazenado na respectiva chave acima.
		 */

		public MapItem(K key, E value)
		{
			this.key = key;
			this.value = value;
		}

		public K getKey()
		{
			return key;
		}
	
		public E getValue()
		{
			return value;
		}
	
		public void setValue(E value)
		{
			this.value = value;
		}

		@Override
		public String toString()
		{
			ObjectDescription description = new ObjectDescription(getClass());

			description.append("key", key);
			description.append("value", value);

			return description.toString();
		}
	}
}
