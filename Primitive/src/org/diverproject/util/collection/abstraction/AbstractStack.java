package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.Stack;

/**
 * <p><h1>Pilha Abstrata</h1></p>
 *
 * <p>O conceito de pilha é bem simples, sempre irá trabalhar com o topo da pilha.
 * Empilhar significa que o elemento será colocado no topo da pilha, em quanto
 * o desempilhar irá remover o elemento do topo além de retornar o removido.</p>
 *
 * <p>Não é possível trabalhar com qualquer outro elemento da pilha a não ser do topo.
 * É possível fazer iteração ou ainda então construir um vetor com todos os elementos.
 * Também possuí o modo dinâmico e estático como a maioria das estruturas.</p>
 *
 * @see AbstractCollection
 * @see Stack
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public abstract class AbstractStack<E> extends AbstractCollection<E> implements Stack<E>
{
	/**
	 * Tamanho padrão usado para iniciar pilhas ou aumentar e reduzir capacidade.
	 */
	public static final int DEFAULT_SIZE = 10;

	/**
	 * Vetor que irá armazenar os elementos empilhados.
	 */
	Object elements[];

	/**
	 * Constrói uma nova pilha abstrata inicializando o vetor dos elementos.
	 * @param size tamanho inicial para a capacidade de elementos.
	 */

	public AbstractStack(int size)
	{
		elements = new Object[size];
	}

	@Override
	public void clear()
	{
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
		for (int i = 0; i < size; i++)
			if (elements[i] != null)
				if (elements[i].equals(element))
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
				return iterate < size();
			}

			@Override
			@SuppressWarnings("unchecked")
			public E next()
			{
				return (E) elements[iterate++];
			}

			@Override
			public String toString()
			{
				ObjectDescription description = new ObjectDescription(getClass());

				description.append("iterate", iterate);
				description.append("size", size);

				return description.toString();
			}
		};
	}
}
