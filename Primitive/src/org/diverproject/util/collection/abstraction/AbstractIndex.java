package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.ArrayUtil;
import org.diverproject.util.collection.Index;

/**
 * <p><h1>Índice Abstrato</h1></p>
 *
 * <p>Nesta caso o índice é feita através de um vetor e podendo ser manipulados.
 * Implementa alguns métodos que são semelhantes entre a índice estática e dinâmica.
 * Diferente de um vetor ele irá apenas ordenar por índice mas não respectivamente.</p>
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

public abstract class AbstractIndex<E> extends AbstractCollection<E> implements Index<E>
{
	/**
	 * Tamanho padrão para iniciar uma lista, reduzir ou aumentar o tamanho desta.
	 */
	public static final int DEFAULT_SIZE = 10;

	/**
	 * Vetor que irá armazenar os elementos da lista.
	 */
	protected Attach<E> elements[];

	/**
	 * Constrói um novo índice abstrata inicializando o vetor para armazenamento.
	 * Neste caso o tamanho inicial do vetor é o DEFAULT_SIZE para tal.
	 */

	public AbstractIndex()
	{
		this(DEFAULT_SIZE);
	}

	/**
	 * Constrói um novo índice abstrato inicializando o vetor para armazenamento.
	 * @param start quantos elementos poderão ser armazenados nessa lista.
	 */

	@SuppressWarnings("unchecked")
	public AbstractIndex(int start)
	{
		elements = new Attach[start];
	}

	@Override
	@SuppressWarnings("unchecked")
	public void clear()
	{
		size = 0;
		elements = new Attach[elements.length];
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
	@SuppressWarnings("unchecked")
	public boolean remove(E element)
	{
		if (element == null)
			return false;

		for (int i = 0; i < elements.length; i++)
			if (elements[i] != null)
				if (elements[i].value.equals(element))
					if (ArrayUtil.moveLeft(elements, i))
					{
						size--;

						if (elements.length - size >= DEFAULT_SIZE)
							elements = (Attach<E>[]) ArrayUtil.decreaseIn(elements, DEFAULT_SIZE, Attach.class);
					}

		return false;
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
						Object selected = elements[i].value;

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

		for (Attach<E> attach : elements)
			if (attach != null)
				description.append(attach);

		return description.toString();
	}

	protected static class Attach<E>
	{
		public int i;
		public E value;

		@Override
		public String toString()
		{
			return String.format("%d: %s", i, value);
		}
	}
}
