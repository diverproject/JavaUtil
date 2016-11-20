package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.ArrayUtil;
import org.diverproject.util.collection.Map;

/**
 * <p><h1>Mapeador Abstrato</h1></p>
 *
 * <p>Nesta caso o mapeador é feita através de um vetor e podendo ser manipulados
 * Implementa alguns dos métodos para um mapeador que são básicos e de mesma ideia.
 * O restante é implementado obrigatoriamente pelos mapeadores mais especificados.</p>
 *
 * @see AbstractCollection
 * @see Map
 *
 * @author Andrew
 *
 * @param <K> qual será o tipo de dado que será usado como chave.
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public abstract class AbstractMap<K, E> extends AbstractCollection<E> implements Map<K, E>
{
	/**
	 * Número de base para aumentar, reduzir ou iniciar a capacidade do mapeador.
	 */
	public static final int DEFAULT_SIZE = 16;

	/**
	 * Referência mapeador dos elementos que podem ou já foram armazenados.
	 */
	protected MapElement<K, E> elements[];

	/**
	 * Constrói um novo mapeador inicializando o vetor dos elementos.
	 * Não define nenhum tipo genérico para ser usado por toArray.
	 * Define o tamanho iniciado deste como DEFAULT_SIZE também.
	 */

	public AbstractMap()
	{
		this(null, DEFAULT_SIZE);
	}

	/**
	 * Constrói um novo mapeador inicializando o vetor dos elementos.
	 * Não define nenhum tipo genérico para ser usado por toArray.
	 * @param start qual será o tamanho do mapeador inicialmente.
	 */

	public AbstractMap(int start)
	{
		this(null, start);
	}

	/**
	 * Constrói um novo mapeador inicializando o vetor dos elementos.
	 * @param generic tipo de dado que será obtido por toArray.
	 */

	public AbstractMap(Class<?> generic)
	{
		this(generic, DEFAULT_SIZE);
	}

	/**
	 * Constrói um novo mapeador inicializando o vetor dos elementos.
	 * @param generic tipo de dado que será obtido por toArray.
	 * @param start qual será o tamanho do mapeador inicialmente.
	 */

	@SuppressWarnings("unchecked")
	public AbstractMap(Class<?> generic, int start)
	{
		setGeneric(generic);

		elements = new MapElement[start];
	}

	@Override
	@SuppressWarnings("unchecked")
	public void clear()
	{
		size = 0;
		elements = new MapElement[elements.length];
	}

	@Override
	public int length()
	{
		return elements.length;
	}

	@Override
	public boolean contains(E element)
	{
		for (MapElement<K, E> hash : elements)
			if (hash.value.equals(element))
				return true;

		return false;
	}

	@Override
	public boolean containsKey(K key)
	{
		for (MapElement<K, E> hash : elements)
			if (hash != null && hash.key.equals(key))
				return true;

		return false;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			private int iterate;

			@Override
			public boolean hasNext()
			{
				return iterate < size;
			}

			@Override
			public E next()
			{
				return elements[iterate++].value;
			}

			@Override
			public String toString()
			{
				ObjectDescription description = new ObjectDescription(getClass());

				description.append("generic", getGeneric());
				description.append("iterate", iterate);
				description.append("size", size);

				return description.toString();
			}
		};
	}

	@Override
	public boolean removeKey(K key)
	{
		for (int i = 0; i < size; i++)
		{
			MapElement<K, E> hash = elements[i];

			if (hash.key.equals(key))
				if (ArrayUtil.moveLeft(elements, i))
				{
					size--;
					return true;
				}
		}

		return false;
	}

	@Override
	public boolean remove(E element)
	{
		for (int i = 0; i < size; i++)
		{
			MapElement<K, E> hash = elements[i];

			if (hash.value.equals(element))
			{
				size--;
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean renameKey(K oldKey, K newKey)
	{
		for (MapElement<K, E> element : elements)
			if (element != null && element.key.equals(oldKey))
			{
				element.key = newKey;
				return true;
			}

		return false;
	}

	@Override
	public boolean update(K key, E value)
	{
		if (key == null || value == null)
			return false;

		for (int i = 0; i < elements.length; i++)
			if (elements[i] != null)
				if (elements[i].key.equals(key))
				{
					elements[i].value = value;
					return true;
				}

		return false;
	}

	@Override
	public Iterable<K> iterateKey()
	{
		return new Iterable<K>()
		{
			@Override
			public Iterator<K> iterator()
			{
				return iteratorKey();
			}
		};
	}

	@Override
	public Iterable<MapItem<K, E>> iterateItems()
	{
		return new Iterable<Map.MapItem<K,E>>()
		{
			@Override
			public Iterator<MapItem<K, E>> iterator()
			{
				return iteratorItems();
			}
		};
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		for (int i = 0; i < size; i++)
		{
			MapElement<K, E> hash = elements[i];
			description.append(hash.key.toString(), hash.value);
		}

		return description.toString();
	}
}
