package org.diverproject.util.database;

/**
 * <p><h1>Database Indexada</h1></p>
 *
 * <p>Estrutura para banco de dados em memória a partir de um banco de dados abstrato.
 * Possui a implementação das funcionalidades para inserir, remover e selecionar elementos.
 * Nesse caso os elementos são organizados por índices respectivos aos do vetor de elementos.</p>
 *
 * @see DatabaseAbstract
 * @see IDatabaseIndex
 *
 * @author Andrew
 *
 * @param <E> tipo de da dado que será armazenado no banco de dados.
 */

public abstract class DatabaseIndex<E> extends DatabaseAbstract<E> implements IDatabaseIndex<E>
{
	/**
	 * Constrói um novo banco de dados de acordo com os parâmetros a baixos.
	 * @param generic tipo de dado que a classe irá armazenar, necessário
	 * para que <b>elements</b> e <b>select</b> possa fazer o cast dos dados.
	 * @param max tamanho do qual terá o vetor interno para guardar elementos.
	 */

	public DatabaseIndex(String name, Class<?> generic, int length)
	{
		super(name, generic, length);
	}
}
