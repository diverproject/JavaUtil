package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;

/**
 * <p><h1>Mapeador Estático</h1></p>
 *
 * <p>Primeiramente para definir o que é um mapeador, é uma estrutura e dados do qual,
 * armazena seus elementos de alguma maneira ligados a uma chave para identifica-los.
 * Assim externamente não é possível saber como estes são organizados na tabela.</p>
 *
 * <p>No caso do mapeador estático a capacidade da estrutura de dados não muda.
 * De modo que o tamanho definido no construtor será o limite de elementos que
 * podem ser inseridos no mapeador em quanto este existir.</p>
 *
 * @see AbstractMap
 *
 * @author Andrew
 *
 * @param <K> qual será o tipo de dado que será usado como chave.
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public class StaticMap<K, E> extends AbstractMap<K, E>
{
	/**
	 * Constrói um novo mapeador estático iniciando o vetor interno.
	 * Neste caso não determina o tipo de dado obtido por toArray.
	 * @param start capacidade de elementos que podem ser adicionados.
	 */

	public StaticMap(int start)
	{
		this(null, start);
	}

	/**
	 * Constrói um novo mapeador estático iniciando o vetor interno.
	 * @param generic tipo de dado que será obtido por toArray.
	 * @param start capacidade de elementos que podem ser adicionados.
	 */

	public StaticMap(Class<?> generic, int start)
	{
		super(generic, start);
	}

	@Override
	public boolean add(K key, E element)
	{
		if (isFull())
			return false;

		elements[size++] = new MapElement<K, E>(key, element);

		return true;
	}

	@Override
	public E get(K key)
	{
		return null;
	}

	@Override
	public Iterator<K> iteratorKey()
	{
		return new Iterator<K>()
		{
			private int iterate;

			@Override
			public boolean hasNext()
			{
				return iterate < size;
			}

			@Override
			public K next()
			{
				return elements[iterate++].key;
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
	public Iterator<MapItem<K, E>> iteratorItems()
	{
		return new Iterator<MapItem<K, E>>()
		{
			private int iterate;

			@Override
			public boolean hasNext()
			{
				return iterate < size;
			}

			@Override
			public MapItem<K, E> next()
			{
				return new MapItem<K, E>(elements[iterate++].key, elements[iterate++].value);
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
}
