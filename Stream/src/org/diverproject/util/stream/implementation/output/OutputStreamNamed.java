package org.diverproject.util.stream.implementation.output;

import org.diverproject.util.ObjectDescription;

/**
 * <h1>Stream de Saída Nomeada</h1>
 *
 * <p>Funciona da mesma forma que uma Stream de Saída.
 * Sua única diferença é a de possuir um nome vinculado ao mesmo.
 * Isso pode ser usado como forma de identificação de auxílio.</p>
 *
 * @see OutputStream
 *
 * @author Andrew Mello
 */

public class OutputStreamNamed extends OutputStream
{
	/**
	 * Nome da saída.
	 */
	private String name;

	/**
	 * Cria uma nova stream através de uma stream de saída de dados pré-especificada.
	 * Para esse caso não será considerado qualquer limite de dados para se escrever.
	 * @param name nome que será vinculado a essa saída para um auxílio na identificação.
	 * @param os referência da stream que será usada para escrever os dados.
	 */

	public OutputStreamNamed(String name, java.io.OutputStream os)
	{
		super(os);

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
