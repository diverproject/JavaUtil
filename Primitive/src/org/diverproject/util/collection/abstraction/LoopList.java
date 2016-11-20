package org.diverproject.util.collection.abstraction;

import org.diverproject.util.collection.ArrayUtil;

/**
 * <p><h1>Lista Circular</h1></p>
 *
 * <p>Utiliza vetor para armazenar os elementos e possui uma capacidade limitada.
 * Ela funciona como circular pois irá da último índice utilizado até o final
 * da lista, e se assim não encontrar retrocede para o inicio com o mesmo processo.</p>
 *
 * <p>A inserção dos elementos é diferente de todas as outras estruturas, pois irá
 * procurar no vetor uma posição do qual ainda não foi ocupada pela lista.
 * Já a remoção irá apenas remover o elemento, deixando a posição em branco.</p>
 *
 * @see AbstractList
 *
 * @author Andrew
 *
 * @param <E>
 */

public class LoopList<E> extends AbstractList<E>
{
	/**
	 * Constrói uma lista circular e inicializa o vetor para armazenamento.
	 * A capacidade inicial neste construtor é definido por DEFAULT_SIZE.
	 */

	public LoopList(int size)
	{
		super(size);
	}

	@Override
	public boolean add(E element)
	{
		for (int i = size; i < elements.length; i++)
			if (elements[i] == null)
			{
				elements[i] = element;
				return increaseSize();
			}

		for (int i = 0; i < size; i++)
			if (elements[i] == null)
			{
				elements[i] = element;
				return increaseSize();
			}

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
		if (ArrayUtil.moveLeft(elements, index))
			return decreaseSize();

		return false;
	}

	@Override
	public boolean update(int index, E element)
	{
		if (index < 0 || index >= elements.length)
			return false;

		elements[index] = element;

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E get(int index)
	{
		if (index >= 0 && index < elements.length)
			return (E) elements[index];

		return null;
	}
}
