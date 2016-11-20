package org.diverproject.util.collection.abstraction;

import org.diverproject.util.collection.ArrayUtil;

/**
 * <p><h1>Vetor Dinâmico</h1></p>
 *
 * <p>Esse tipo de estrutura de dados utiliza o conceito de estrutura por índice.
 * Onde utiliza um índice para posicionar seus elementos em uma estrutura interna.
 * A estrutura utilizado no caso como o próprio nome já diz é um vetor.</p>
 *
 * <p>A forma como trabalha os índices usados para posicionar os elementos no mesmo,
 * também é bem simples, onde o índice nos procedimentos são respectivos aos
 * índices do vetor interno usado para armazenar os elementos deste.</p>
 *
 * <p>No caso do modo dinâmico para vetor, ele só será redimensionável no momento em
 * que for adicionado um elemento com um índice do qual nao seja válido porém positivo.
 * Por tanto deve ser usado os índices cuidadosamente para não crescer muito.</p>
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public class DynamicArray<E> extends AbstractArray<E>
{
	private int last;

	@Override
	public boolean add(int index, E element)
	{
		if (index < 0)
			return false;

		if (index > elements.length)
			elements = ArrayUtil.resizeTo(elements, index + 1);

		if (last < index)
			last = index;

		size++;
		elements[index] = element;

		return true;
	}

	@Override
	public boolean remove(int index)
	{
		if (!isIndex(index))
			return false;

		size--;
		elements[index] = null;

		if (index == last)
			for (int i = last - 1; i >= 0; i--)
				if (elements[i] != null)
				{
					last = i;
					break;
				}

		if (!isEmpty())
			ArrayUtil.resizeTo(elements, last + (last % DEFAULT_SIZE));
		else
			elements = new Object[DEFAULT_SIZE];

		return true;
	}

	@Override
	public boolean update(int index, E element)
	{
		if (!isIndex(index))
			return false;

		elements[index] = element;

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean change(int first, int second)
	{
		if (!isIndex(first) || !isIndex(second))
			return false;

		E aux = (E) elements[second];
		elements[second] = elements[first];
		elements[first] = aux;

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E get(int index)
	{
		return isIndex(index) ? (E) elements[index] : null;
	}

	/**
	 * Procedimento que irá fazer a validação para índice válido ou inválido.
	 * @param index número do índice do qual deve ser validado.
	 * @return true se for válido ou false caso não seja.
	 */

	protected boolean isIndex(int index)
	{
		return index >= 0 && index < elements.length;
	}
}
