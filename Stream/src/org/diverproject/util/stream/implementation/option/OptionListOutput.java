package org.diverproject.util.stream.implementation.option;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.stream.Input;
import org.diverproject.util.stream.Output;

/**
 * <h1>Entrada de Opções por Lista</h1>
 *
 * <p>Permite fazer a leitura de um determinado arquivo como listagem de opções.
 * Esse arquivo irá carregar todas as opções contendo seus nomes e valores.
 * Em seguida será possível usar os getters para obter esses valores.</p>
 *
 * @see OptionReadByInput
 * @see Input
 *
 * @author Andrew
 */

public class OptionListOutput extends OptionWriteByOutput
{
	/**
	 * Constrói uma nova entrada para opções de acordo com um arquivo especificado.
	 * @param output referência do objeto contendo os bytes que serão lidos.
	 */

	public OptionListOutput(Output output)
	{
		super(output);
	}

	/**
	 * Através de uma iteração irá escrever todas as opções que deste forem iteradas.
	 * @param iteration referência do objeto iterável que será escrito.
	 */

	public void write(Iterable<StreamOptionValue<?>> iteration)
	{
		write(iteration.iterator());
	}

	/**
	 * Através de uma iteração irá escrever todas as opções que deste forem iteradas.
	 * @param iterator referência do objeto com iteração que será escrito.
	 */

	public void write(Iterator<StreamOptionValue<?>> iterator)
	{
		while (iterator.hasNext())
			write(iterator.next());
	}

	/**
	 * Procedimento interno chamado por cada iteração que houver de uma opção encontrada.
	 * @param option referência do objeto contendo o nome e valor da opção em questão.
	 */

	private void write(StreamOptionValue<?> option)
	{
		if (option.getValue() instanceof Boolean)
			putByte(option.getName(), (Byte) option.getValue());

		else if (option.getValue() instanceof Character)
			putChar(option.getName(), (Character) option.getValue());

		else if (option.getValue() instanceof Short)
			putShort(option.getName(), (Short) option.getValue());

		else if (option.getValue() instanceof Integer)
			putInt(option.getName(), (Integer) option.getValue());

		else if (option.getValue() instanceof Long)
			putLong(option.getName(), (Long) option.getValue());

		else if (option.getValue() instanceof Float)
			putFloat(option.getName(), (Float) option.getValue());

		else if (option.getValue() instanceof Double)
			putDouble(option.getName(), (Double) option.getValue());

		else if (option.getValue() instanceof String)
			putString(option.getName(), (String) option.getValue());

		else if (option.getValue() instanceof Boolean)
			putBoolean(option.getName(), (Boolean) option.getValue());
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		return description.toString();
	}
}
