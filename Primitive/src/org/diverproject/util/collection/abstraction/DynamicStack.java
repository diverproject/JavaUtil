package org.diverproject.util.collection.abstraction;

import org.diverproject.util.collection.ArrayUtil;

/**
 * <p><h1>Pilha Dinâmica</h1></p>
 *
 * <p>Pilhas dinâmicas não possui nada de especial se comparado com o conceito de pilha.
 * Por se uma estrutura dinâmica, sua capacidade para armazenamento dos elementos
 * empilhados será variável, ou seja, quando necessário será adicionado mais espaço.
 * O mesmo vale para quando remover elementos, se sobrar espaço reduz a capacidade.</p>
 *
 * @see AbstractStack
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public class DynamicStack<E> extends AbstractStack<E>
{
	/**
	 * Constrói uma nova pilha dinâmica e inicializa o vetor para armazenamento.
	 * A capacidade inicial neste construtor é definido por DEFAULT_SIZE.
	 */

	public DynamicStack()
	{
		super(DEFAULT_SIZE);
	}

	/**
	 * Constrói uma nova pilha dinâmica e inicializa o vetor para armazenamento.
	 * @param size tamanho para a capacidade de elementos na pilha.
	 */

	public DynamicStack(int size)
	{
		super(size);
	}

	@Override
	public boolean push(E element)
	{
		if (isFull())
			elements = ArrayUtil.increseIn(elements, DEFAULT_SIZE);

		elements[size++] = element;

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E pop()
	{
		if (isEmpty())
			return null;

		E pop = (E) elements[size--];

		if (elements.length - size >= DEFAULT_SIZE)
			elements = ArrayUtil.decreaseIn(elements, DEFAULT_SIZE);

		return pop;
	}
}
