package org.diverproject.util.collection.abstraction;

/**
 * <p><h1>Vetor Estático</h1></p>
 *
 * <p>Esse tipo de estrutura de dados utiliza o conceito de estrutura por índice.
 * Onde utiliza um índice para posicionar seus elementos em uma estrutura interna.
 * A estrutura utilizado no caso como o próprio nome já diz é um vetor.</p>
 *
 * <p>A forma como trabalha os índices usados para posicionar os elementos no mesmo,
 * também é bem simples, onde o índice nos procedimentos são respectivos aos
 * índices do vetor interno usado para armazenar os elementos deste.</p>
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public class StaticArray<E> extends AbstractArray<E>
{
	/**
	 * Constrói um novo vetor estático iniciando o vetor de elementos.
	 * Neste caso o tamanho inicial do vetor é o DEFAULT_SIZE para tal.
	 */

	public StaticArray()
	{
		this(DEFAULT_SIZE);
	}

	/**
	 * Constrói um novo vetor estático iniciando o vetor de elementos.
	 * @param start quantos elementos poderão ser armazenados nessa lista.
	 */

	public StaticArray(int start)
	{
		elements = new Object[start];
	}

	@Override
	public boolean add(int index, E element)
	{
		if (!isIndex(index) || elements[index] != null)
			return false;

		size++;
		elements[index] = element;

		return true;
	}

	@Override
	public boolean remove(int index)
	{
		if (!isIndex(index) && elements[index] != null)
			return false;

		size--;
		elements[index] = null;

		return true;
	}

	@Override
	public boolean update(int index, E element)
	{
		if (!isIndex(index))
			return false;

		if (elements[index] == null)
			size++;

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
