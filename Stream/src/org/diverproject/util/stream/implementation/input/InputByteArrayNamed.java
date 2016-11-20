package org.diverproject.util.stream.implementation.input;

import org.diverproject.util.ObjectDescription;

/**
 * <h1>Entrada de Dados com Vetor Nomeada</h1>
 *
 * <p>Funciona da mesma forma que uma Entrada de Dados com Vetor.
 * Sua única diferença é a de possuir um nome vinculado ao mesmo.
 * Isso pode ser usado como forma de identificação de auxílio.</p>
 *
 * @see InputByteArray
 *
 * @author Andrew Mello
 */

public class InputByteArrayNamed extends InputByteArray
{
	/**
	 * Nome da entrada.
	 */
	private String name;

	/**
	 * Cria um novo leitor de dados em vetor a partir de um vetor de dados para ser feito a leitura.
	 * Para esse caso os bytes serão vinculados a entrada de dados e não serão clonados (padrão).
	 * @param name nome que será vinculado a essa entrada para um auxílio na identificação.
	 * @param data referência do vetor de bytes que será considerado como dados para leitura.
	 */

	public InputByteArrayNamed(String name, byte data[])
	{
		this(name, data, false);
	}

	/**
	 * Cria um novo leitor de dados padrão a partir de um vetor de dados para ser feito a leitura.
	 * @param name nome que será vinculado a essa entrada para um auxílio na identificação.
	 * @param data referência do vetor de bytes que será considerado como dados para leitura.
	 * @param copy permite definir se o vetor será clonado dentro da stream.
	 */

	public InputByteArrayNamed(String name, byte[] data, boolean copy)
	{
		super(data, copy);

		this.name = name;
	}

	/**
	 * Nome dessa entrada de dados é usada como auxílio para a identificação do mesmo.
	 * Pode ser usada por exemplo por um pacote de dados afim de nomeá-lo.
	 * @return aquisição do nome dessa entrada de dados.
	 */

	public String getName()
	{
		return name;
	}

	@Override
	protected void toString(ObjectDescription description)
	{
		description.append(name);

		super.toString(description);
	}
}
