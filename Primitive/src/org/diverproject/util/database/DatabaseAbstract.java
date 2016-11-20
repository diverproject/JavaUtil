package org.diverproject.util.database;

import java.lang.reflect.Array;

/**
 * <p><h1>Banco de Dados Abstrata</h1></p>
 *
 * <p>Implementa a interface para um banco de dados de modo a simplificar a herança.
 * Nessa classe é implementado os métodos que possuem funcionalidades iguais em todo banco.
 * Como por exemplo a verificação do espaço disponível no banco de dados ou tamanho do mesmo.</p>
 *
 * @author Andrew
 *
 * @param <E>
 */

public abstract class DatabaseAbstract<E> implements IDatabase<E>
{
	/**
	 * Nome do banco de dados para ser mostrado em toString().
	 */
	private String name;

	/**
	 * Quantidade de elementos atualmente inseridos.
	 */
	protected int size;

	/**
	 * Vetor que irá guardar os elementos.
	 */
	protected Object[] elements;

	/**
	 * Tipo de dado do qual está sendo guardado nesse banco.
	 */
	protected Class<?> generic;

	/**
	 * Constrói um novo banco de dados de acordo com os parâmetros a baixos.
	 * @param name nome que será dado ao banco de dados para reconhecimento.
	 * @param generic tipo de dado que a classe irá armazenar, necessário
	 * para que <b>elements</b> e <b>select</b> possa fazer o cast dos dados.
	 * @param max tamanho do qual terá o vetor interno para guardar elementos.
	 */

	@SuppressWarnings("unchecked")
	public DatabaseAbstract(String name, Class<?> generic, int max)
	{
		if (max < 10)
			max = 10;

		this.name = name;
		this.generic = generic;
		this.elements = (E[]) Array.newInstance(generic, max);
	}

	@Override
	public boolean isEmpety()
	{
		return size == 0;
	}

	@Override
	public boolean isFully()
	{
		return size == elements.length;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public int length()
	{
		return elements.length;
	}

	@Override
	public E[] select(int... index)
	{
		@SuppressWarnings("unchecked")
		E[] selected = (E[]) Array.newInstance(generic, index.length);

		for (int i = 0; i < index.length; i++)
			selected[i] = select(index[i]);

		return selected;
	}

	/**
	 * Verifica se um determinado índice é válido.
	 * @param index número do índice que será verificado.
	 * @return true se for válido ou false caso contrário.
	 */

	protected boolean isIndex(int index)
	{
		return index >= 0 && index < elements.length;
	}

	/**
	 * Procedimento que deve informar os dados contidos dentro desse banco de dados.
	 * Mostrando o nome das variáveis e em seguida os seus valores respectivos.
	 * @return string formatada o nome das variáveis e valores respectivos.
	 */

	protected String toStringBody()
	{
		return String.format("name: %s, generic: %s, size: %d, length: %d", name, generic.getSimpleName(), size(), length());
	}

	@Override
	public String toString()
	{
		return String.format("%s[%s]", getClass().getSimpleName(), toStringBody());
	}
}
