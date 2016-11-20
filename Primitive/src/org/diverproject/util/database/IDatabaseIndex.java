package org.diverproject.util.database;

/**
 * <p><h1>Database com Índice</h1></p>
 *
 * <p>Interface que permite a implementação da estrutura básica de um banco de dados.
 * A diferença para DatabaseNoIndex é que nesse caso a inserção precisa de um índice.
 * Assim é possível determinar em que parte do banco o elemento será alocado./p>
 *
 * @see IDatabase
 *
 * @author Andrew
 *
 * @param <E> tipo de da dado que será armazenado no banco de dados.
 */

public interface IDatabaseIndex<E> extends IDatabase<E>
{
	/**
	 * Permite adicionar um novo elemento em um determinado índice.
	 * @param index índice no banco de dados para posicionar o elemento.
	 * @param element referência do elemento que irá ocupar o índice passado.
	 * @return true se adicionado ou false caso o índice seja inválido ou ocupado.
	 */

	boolean insert(int index, E element);
}
