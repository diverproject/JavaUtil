package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.ArrayUtil;

/**
 * <p><h1>Mapeador Abstrato</h1></p>
 *
 * <p>Primeiramente para definir o que é um mapeador, é uma estrutura e dados do qual,
 * armazena seus elementos de alguma maneira ligados a uma chave para identifica-los.
 * Assim externamente não é possível saber como estes são organizados na tabela.</p>
 *
 * <p>No caso do mapeador dinâmico a capacidade da estrutura de dados varia.
 * Essa variação vai de acordo com o número de elementos adicionados ou removido.
 * Quando adicionados verifica se necessita de mais espaço e quando um for removido,
 * irá verificar se há muito espaço internamente sobrando sem elementos ocupando-os.</p>
 *
 * @see AbstractMap
 *
 * @author Andrew
 *
 * @param <K> qual será o tipo de dado que será usado como chave.
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public class DynamicMap<K, E> extends AbstractMap<K, E>
{
	/**
	 * Constrói um novo mapeador dinâmico iniciando o vetor interno.
	 * Neste caso não determina o tipo de dado obtido por toArray.
	 */

	public DynamicMap()
	{
		this(null, DEFAULT_SIZE);
	}

	/**
	 * Constrói um novo mapeador dinâmico iniciando o vetor interno.
	 * Neste caso não determina o tipo de dado obtido por toArray.
	 * @param start limite da capacidade do vetor inicialmente.
	 */

	public DynamicMap(int start)
	{
		this(null, start);
	}

	/**
	 * Constrói um novo mapeador dinâmico iniciando o vetor interno.
	 * @param generic tipo de dado que será obtido por toArray.
	 */

	public DynamicMap(Class<?> generic)
	{
		this(generic, DEFAULT_SIZE);
	}

	/**
	 * Constrói um novo mapeador dinâmico iniciando o vetor interno.
	 * @param generic tipo de dado que será obtido por toArray.
	 * @param start limite da capacidade do vetor inicialmente.
	 */

	public DynamicMap(Class<?> generic, int start)
	{
		super(generic, start);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean add(K key, E element)
	{
		if (isFull())
			elements = (MapElement<K, E>[]) ArrayUtil.increseIn(elements, DEFAULT_SIZE);

		elements[size++] = new MapElement<K, E>(key, element);

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean removeKey(K key)
	{
		if (super.removeKey(key))
		{
			if (elements.length - DEFAULT_SIZE > size)
				elements = (MapElement<K, E>[]) ArrayUtil.decreaseIn(elements, DEFAULT_SIZE);

			return true;
		}

		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(E element)
	{
		if (super.remove(element))
		{
			if (elements.length - DEFAULT_SIZE > size)
				elements = (MapElement<K, E>[]) ArrayUtil.decreaseIn(elements, DEFAULT_SIZE);

			return true;
		}

		return false;
	}

	@Override
	public E get(K key)
	{
		for (MapElement<K, E> element : elements)
			if (element != null && element.key.equals(key))
				return element.value;

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
				return new MapItem<K, E>(elements[iterate].key, elements[iterate++].value);
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
