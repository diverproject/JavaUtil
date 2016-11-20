package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.ArrayUtil;
import org.diverproject.util.collection.List;

/**
 * <p><h1>Lista Abstrata</h1></p>
 *
 * <p>Nesta caso a lista é feita através de um vetor e podendo ser manipulados.
 * Implementa alguns métodos que são semelhantes entre a lista estática e dinâmica.</p>
 *
 * <p>A diferença entre os dois modelos é uma função adicional ao adicionar e remover.
 * Somente o modo estático que terá de fato uma mudança maior em relação a tal que
 * é se o vetor interno deve ser aumentado ou reduzido para de fato ser dinâmico.</p>
 *
 * @see AbstractCollection
 * @see List
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E>
{
	/**
	 * Tamanho padrão para iniciar uma lista, reduzir ou aumentar o tamanho desta.
	 */
	public static final int DEFAULT_SIZE = 10;

	/**
	 * Vetor que irá armazenar os elementos da lista.
	 */
	protected Object elements[];

	/**
	 * Constrói uma nova lista abstrata inicializando o vetor para armazenamento.
	 * Neste caso o tamanho inicial do vetor é o DEFAULT_SIZE para tal.
	 */

	public AbstractList()
	{
		this(DEFAULT_SIZE);
	}

	/**
	 * Constrói uma nova lista abstrata inicializando o vetor para armazenamento.
	 * @param start quantos elementos poderão ser armazenados nessa lista.
	 */

	public AbstractList(int start)
	{
		elements = new Object[start];
	}

	@Override
	public void clear()
	{
		size = 0;
		elements = new Object[elements.length];
	}

	@Override
	public int length()
	{
		return elements.length;
	}

	@Override
	public boolean contains(E element)
	{
		for (Object object : elements)
			if (object != null)
				if (object.equals(element))
					return true;

		return false;
	}

	@Override
	public boolean remove(E element)
	{
		if (element == null)
			return false;

		for (int i = 0; i < elements.length; i++)
			if (elements[i] != null)
				if (elements[i].equals(element))
					if (ArrayUtil.moveLeft(elements, i))
						return decreaseSize();

		return false;
	}

	@Override
	public boolean remove(int index)
	{
		boolean removed = false;

		if (ArrayUtil.moveLeft(elements, index))
			removed = decreaseSize();

		return removed;
	}

	@Override
	public boolean update(int index, E element)
	{
		if (index < 0 || index >= size)
			return false;

		elements[index] = element;

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E get(int index)
	{
		if (index >= 0 && index < size)
			return (E) elements[index];

		return null;
	}

	protected boolean increaseSize()
	{
		size++;

		return true;
	}

	protected boolean decreaseSize()
	{
		size--;

		return true;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			private int iteration;

			@Override
			public boolean hasNext()
			{
				return iteration < size;
			}

			@Override
			@SuppressWarnings("unchecked")
			public E next()
			{
				return (E) elements[iteration++];
			}

			@Override
			public String toString()
			{
				ObjectDescription description = new ObjectDescription(getClass());

				description.append("iteration", iteration);
				description.append("size", size);
				description.append("elements", elements.length);

				return description.toString();
			}
		};
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		description.append("size", size());
		description.append("length", length());

		for (int i = 0; i < elements.length; i++)
			description.append(Integer.toString(i),	elements[i]);

		return description.toString();
	}
}
