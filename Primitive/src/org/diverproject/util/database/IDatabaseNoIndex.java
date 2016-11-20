package org.diverproject.util.database;

/**
 * <p><h1>Database sem Índice</h1></p>
 *
 * <p>Interface que permite a implementação da estrutura básica de um banco de dados.
 * A diferença para DatabaseIndex é que nesse caso a inserção não precisa de índice.
 * Portanto espera-se que internamente no método seja definido o índice do elemento./p>
 *
 * @see IDatabase
 *
 * @author Andrew
 *
 * @param <E> tipo de da dado que será armazenado no banco de dados.
 */

public interface IDatabaseNoIndex<E> extends IDatabase<E>
{
	/**
	 * Permite adicionar um novo elemento e esse elemento deve ser posicionado no banco.
	 * @param element referência do elemento que irá ocupar o índice passado.
	 * @return true se adicionado ou false caso o índice seja inválido ou ocupado.
	 */

	boolean insert(E element);
}
