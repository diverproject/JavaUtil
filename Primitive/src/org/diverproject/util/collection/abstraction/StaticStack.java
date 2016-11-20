package org.diverproject.util.collection.abstraction;

/**
 * <p><h1>Pilha Estática</h1></p>
 *
 * <p>Pilhas estáticas não possui nada de especial se comparado com o conceito de pilha.
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

public class StaticStack<E> extends AbstractStack<E>
{
	/**
	 * Constrói uma nova pilha estática e inicializa o vetor para armazenamento.
	 * A capacidade inicial neste construtor é definido por DEFAULT_SIZE.
	 */

	public StaticStack()
	{
		super(DEFAULT_SIZE);
	}

	/**
	 * Constrói uma nova pilha estática e inicializa o vetor para armazenamento.
	 * @param size tamanho para a capacidade de elementos na pilha.
	 */

	public StaticStack(int length)
	{
		super(length);
	}

	@Override
	public boolean push(E element)
	{
		if (isFull())
			return false;

		elements[size++] = element;

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E pop()
	{
		if (isEmpty())
			return null;

		return (E) elements[size--];
	}
}
