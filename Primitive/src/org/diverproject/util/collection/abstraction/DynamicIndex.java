package org.diverproject.util.collection.abstraction;

import org.diverproject.util.collection.ArrayUtil;

/**
 * <p><h1>Índice Dinâmico</h1></p>
 *
 * <p>Esse tipo de estrutura de dados utiliza o conceito de estrutura por índice.
 * Onde utiliza um índice para posicionar seus elementos em uma estrutura interna.
 * A estrutura utilizado no caso como o próprio nome já diz é um vetor.</p>
 *
 * <p>A forma como trabalha os índices usados para posicionar os elementos no mesmo,
 * também é bem simples, posiciona os elementos no vetor ordenando-os de acordo com
 * o índice que foi passado para tal quando adicionados na lista.</p>
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public class DynamicIndex<E> extends AbstractIndex<E>
{
	private boolean repet;

	@Override
	@SuppressWarnings("unchecked")
	public boolean add(int index, E element)
	{
		if (isFull())
			elements = (Attach<E>[]) ArrayUtil.increseIn(Attach.class, elements, DEFAULT_SIZE);

		Attach<E> attach = new Attach<E>();
		attach.i = index;
		attach.value = element;

		for (int i = 0; i < elements.length; i++)
		{
			if (elements[i] == null)
			{
				elements[i] = attach;
				break;
			}

			if (elements[i].i == index && !repet)
				return false;

			if (elements[i].i >= index)
			{
				ArrayUtil.moveRight(elements, i);
				elements[i] = attach;
				break;
			}
		}

		size++;

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(int index)
	{
		if (isEmpty())
			return false;

		for (int i = 0; i < elements.length; i++)
			if (elements[i].i == index)
			{
				size--;
				elements[i] = null;
			}

		if (elements.length - size >= DEFAULT_SIZE)
			elements = (Attach<E>[]) ArrayUtil.decreaseIn(Attach.class, elements, DEFAULT_SIZE);

		return false;
	}

	@Override
	public boolean update(int index, E element)
	{
		if (isEmpty())
			return false;

		for (int i = 0; i < elements.length; i++)
			if (elements[i].i == index)
			{
				elements[i].value = element;
				return true;
			}

		return false;
	}

	@Override
	public boolean change(int first, int second)
	{
		if (size() < 2)
			return false;

		Attach<E> firstAttach = null;
		Attach<E> secondAttach = null;

		for (int i = 0; i < elements.length; i++)
			if (elements[i].i == first)
			{
				firstAttach = elements[i];
				break;
			}

		for (int i = 0; i < elements.length; i++)
			if (elements[i].i == second)
			{
				secondAttach = elements[i];
				break;
			}

		if (firstAttach != null && secondAttach != null)
		{
			E aux = secondAttach.value;

			secondAttach.value = firstAttach.value;
			firstAttach.value = aux;

			return true;
		}

		return false;
	}

	@Override
	public E get(int index)
	{
		for (int i = 0; i < elements.length; i++)
		{
			if (elements[i] == null)
				return null;

			if (elements[i].i == index)
				return elements[i].value;
		}

		return null;
	}

	/**
	 * Quando habilitado permite que dois elementos possuam o mesmo índice.
	 * @return true se estiver habilitado ou false caso contrário.
	 */

	public boolean isRepet()
	{
		return repet;
	}

	/**
	 * Quando habilitado permite que dois elementos possuam o mesmo índice.
	 * Por padrão essa opção vem desabilitada nesse tipo de indexação.
	 * @param repet true para habilitar ou false para desabilitar.
	 */

	public void setRepet(boolean repet)
	{
		this.repet = repet;
	}
}
