package org.diverproject.util.stream.implementation.output;

import org.diverproject.util.ObjectDescription;

/**
 * <h1>Saída de Dados com Vetor Nomeada</h1>
 *
 * <p>Funciona da mesma forma que uma Saída de Dados com Vetor.
 * Sua única diferença é a de possuir um nome vinculado ao mesmo.
 * Isso pode ser usado como forma de identificação de auxílio.</p>
 *
 * @see OutputByteArray
 *
 * @author Andrew Mello
 */

public class OutputByteArrayNamed extends OutputByteArray
{
	/**
	 * Nome da saída.
	 */
	private String name;

	/**
	 * Cria um novo escritor de dados em vetor a partir de um vetor os os dados serão escritos.
	 * Para esse caso os bytes serão vinculados a saída de dados e não serão clonados (padrão).
	 * @param name nome que será vinculado a essa saída para um auxílio na identificação.
	 * @param data referência do vetor de bytes que será considerado como dados para escrita.
	 */

	public OutputByteArrayNamed(String name, byte[] data)
	{
		this(name, data, false);
	}

	/**
	 * Cria um novo escritor de dados em vetor a partir de um vetor os os dados serão escritos.
	 * @param name nome que será vinculado a essa saída para um auxílio na identificação.
	 * @param data referência do vetor de bytes que será considerado como dados para escrita.
	 * @param copy permite definir se o vetor será clonado dentro da stream.
	 */

	public OutputByteArrayNamed(String name, byte[] data, boolean copy)
	{
		super(data, copy);

		this.name = name;
	}

	/**
	 * Nome dessa saída de dados é usada como auxílio para a identificação do mesmo.
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
