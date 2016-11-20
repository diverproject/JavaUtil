package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.ArrayUtil;
import org.diverproject.util.collection.Index;

/**
 * <p><h1>Vetor Abstrato</h1></p>
 *
 * <p>Nesta caso o índice é feita através de um vetor e podendo ser manipulados.
 * Implementa alguns métodos que são semelhantes entre a índice estática e dinâmica.
 * Diferente de um índice ele irá ordenar os elementos por índice no vetor usado.</p>
 *
 * <p>A diferença entre os dois modelos é uma função adicional ao adicionar e remover.
 * No caso do dinâmico o tamanho da lista é fixa e no dinâmico pode ser variável.</p>
 *
 * @see AbstractCollection
 * @see Index
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public abstract class AbstractArray<E> extends AbstractCollection<E> implements Index<E>
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
	 * Constrói um novo índice abstrata inicializando o vetor para armazenamento.
	 * Neste caso o tamanho inicial do vetor é o DEFAULT_SIZE para tal.
	 */

	public AbstractArray()
	{
		this(DEFAULT_SIZE);
	}

	/**
	 * Constrói um novo índice abstrato inicializando o vetor para armazenamento.
	 * @param start quantos elementos poderão ser armazenados nessa lista.
	 */

	public AbstractArray(int start)
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

	private boolean decreaseSize()
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
				return iteration != size;
			}

			@Override
			@SuppressWarnings("unchecked")
			public E next()
			{
				for (int i = iteration; i < elements.length; i++)
					if (elements[i] != null)
					{
						iteration++;
						Object selected = elements[i];

						return (E) selected;
					}

				return null;
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
