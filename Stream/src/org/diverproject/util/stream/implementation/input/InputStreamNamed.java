package org.diverproject.util.stream.implementation.input;

import org.diverproject.util.ObjectDescription;

/**
 * <h1>Stream de Entrada Nomeada</h1>
 *
 * <p>Funciona da mesma forma que uma Stream de Entrada.
 * Sua única diferença é a de possuir um nome vinculado ao mesmo.
 * Isso pode ser usado como forma de identificação de auxílio.</p>
 *
 * @see InputStream
 *
 * @author Andrew Mello
 */

public class InputStreamNamed extends InputStream
{
	/**
	 * Nome da entrada.
	 */
	private String name;

	/**
	 * Cria uma nova stream através de uma stream de entrada de dados pré-especificada.
	 * Para esse caso não será considerado qualquer limite de dados para se ler.
	 * @param name nome que será vinculado a essa entrada para um auxílio na identificação.
	 * @param is referência da stream que será usada para escrever os dados.
	 */

	public InputStreamNamed(String name, java.io.InputStream is)
	{
		super(is);

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
