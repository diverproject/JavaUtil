package org.diverproject.util.database;

/**
 * Banco de Dados
 *
 * <p>Implementa alguns dos métodos necessários para se criar um banco de dados.
 * Nesse caso é utilizado um vetor interno de tamanho fixo definido no construtor.
 * Outras classes devem herdar desta para implementar o modo que será operado.
 * O modo de operação se refere a inserir, atualizar, remover e selecionar.</p>
 *
 * <p>Além disso deve ser definido o tipo de classe que será utilizada para instanciar.
 * De modo que quando for necessário mexer em <b>elements</b> seja possível usar sem cast.</p>
 *
 * @see DatabaseAbstract
 * @see IDatabaseNoIndex
 *
 * @author Andrew
 *
 * @param <E> tipo de dado que será armazenado.
 */

public abstract class DatabaseNoIndex<E> extends DatabaseAbstract<E> implements IDatabaseNoIndex<E>
{
	/**
	 * Constrói um novo banco de dados de acordo com os parâmetros a baixos.
	 * @param name nome que será dado ao banco de dados para reconhecimento.
	 * @param generic tipo de dado que a classe irá armazenar, necessário
	 * para que <b>elements</b> e <b>select</b> possa fazer o cast dos dados.
	 * @param max tamanho do qual terá o vetor interno para guardar elementos.
	 */

	public DatabaseNoIndex(String name, Class<?> generic, int max)
	{
		super(name, generic, max);
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
	public boolean remove(int index)
	{
		if (!isIndex(index) || elements[index] == null)
			return false;

		size--;
		elements[index] = null;

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E select(int index)
	{
		if (isIndex(index))
			return (E) elements[index];

		return null;
	}
}
