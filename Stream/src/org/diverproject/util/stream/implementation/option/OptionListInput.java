package org.diverproject.util.stream.implementation.option;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.abstraction.DynamicList;
import org.diverproject.util.stream.Input;

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

public class OptionListInput extends OptionReadByInput
{
	/**
	 * Lista de opções que já foram carregadas.
	 */
	protected DynamicList<StreamOptionValue<?>> options;

	/**
	 * Constrói uma nova entrada para opções de acordo com um arquivo especificado.
	 * @param input referência do objeto contendo os bytes que serão lidos.
	 */

	public OptionListInput(Input input)
	{
		super(input);
	}

	@Override
	protected void dispatch(StreamOptionValue<?> option)
	{
		options.add(option);
	}

	@Override
	public byte getByte(String name, byte notfound)
	{
		for (StreamOptionValue<?> option : options)
			if (option.getName().equals(name))
				if (option.getValue() instanceof Byte)
					return (Byte) option.getValue();

		return notfound;
	}

	@Override
	public char getChar(String name, char notfound)
	{
		for (StreamOptionValue<?> option : options)
			if (option.getName().equals(name))
				if (option.getValue() instanceof Character)
					return (Character) option.getValue();

		return notfound;
	}

	@Override
	public short getShort(String name, short notfound)
	{
		for (StreamOptionValue<?> option : options)
			if (option.getName().equals(name))
				if (option.getValue() instanceof Short)
					return (Short) option.getValue();

		return notfound;
	}

	@Override
	public int getInt(String name, int notfound)
	{
		for (StreamOptionValue<?> option : options)
			if (option.getName().equals(name))
				if (option.getValue() instanceof Integer)
					return (Integer) option.getValue();

		return notfound;
	}

	@Override
	public long getLong(String name, long notfound)
	{
		for (StreamOptionValue<?> option : options)
			if (option.getName().equals(name))
				if (option.getValue() instanceof Long)
					return (Long) option.getValue();

		return notfound;
	}

	@Override
	public float getFloat(String name, float notfound)
	{
		for (StreamOptionValue<?> option : options)
			if (option.getName().equals(name))
				if (option.getValue() instanceof Float)
					return (Float) option.getValue();

		return notfound;
	}

	@Override
	public double getDouble(String name, double notfound)
	{
		for (StreamOptionValue<?> option : options)
			if (option.getName().equals(name))
				if (option.getValue() instanceof Double)
					return (Double) option.getValue();

		return notfound;
	}

	@Override
	public String getString(String name, String notfound)
	{
		for (StreamOptionValue<?> option : options)
			if (option.getName().equals(name))
				if (option.getValue() instanceof String)
					return (String) option.getValue();

		return notfound;
	}

	@Override
	public boolean getBoolean(String name, boolean notfound)
	{
		for (StreamOptionValue<?> option : options)
			if (option.getName().equals(name))
				if (option.getValue() instanceof Boolean)
					return (Boolean) option.getValue();

		return notfound;
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		for (StreamOptionValue<?> option : options)
			description.append(option.getName(), option.getValue());

		return description.toString();
	}
}
