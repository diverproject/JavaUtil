package org.diverproject.util.stream.implementation.option;

import org.diverproject.util.stream.Output;
import org.diverproject.util.stream.implementation.option.OptionOutput;

/**
 * <h1>Opções Escritas em Input</h1>
 *
 * <p>Classe com implementação básica para se escrever opções em bytes dentro de um Output.
 * Fará a implementação básica dos métodos que permitem escrever os dados corretamente.
 * Deverá ser herdado por uma classe que especifique a ordem de escrita das opções.</p>
 *
 * @see OptionOutput
 * @see Output
 *
 * @author Andrew
 */

public class OptionWriteByOutput implements OptionOutput
{
	/**
	 * Referência da stream que será usada para escrever os dados.
	 */
	private Output output;

	/**
	 * Cria uma nova stream que permite colocar valores através de um nome (opções).
	 * @param output referência da stream que será usada para escrever os dados.
	 */

	public OptionWriteByOutput(Output output)
	{
		this.output = output;
	}

	@Override
	public void close()
	{
		output.close();
	}

	@Override
	public void putByte(String name, byte value)
	{
		output.putByte(OPTION_BYTE);
		output.putString(name);
		output.putByte(value);

		flush();
	}

	@Override
	public void putChar(String name, char value)
	{
		output.putByte(OPTION_CHAR);
		output.putString(name);
		output.putChar(value);

		flush();
	}

	@Override
	public void putShort(String name, short value)
	{
		output.putByte(OPTION_SHORT);
		output.putString(name);
		output.putShort(value);

		flush();
	}

	@Override
	public void putInt(String name, int value)
	{
		output.putByte(OPTION_INT);
		output.putString(name);
		output.putInt(value);

		flush();
	}

	@Override
	public void putLong(String name, long value)
	{
		output.putByte(OPTION_LONG);
		output.putString(name);
		output.putLong(value);

		flush();
	}

	@Override
	public void putFloat(String name, float value)
	{
		output.putByte(OPTION_FLOAT);
		output.putString(name);
		output.putFloat(value);	

		flush();	
	}

	@Override
	public void putDouble(String name, double value)
	{
		output.putByte(OPTION_DOUBLE);
		output.putString(name);
		output.putDouble(value);

		flush();		
	}

	@Override
	public void putString(String name, String value)
	{
		output.putByte(OPTION_STRING);
		output.putString(name);
		output.putString(value);

		flush();		
	}

	@Override
	public void putBoolean(String name, boolean value)
	{
		output.putByte(OPTION_BOOLEAN);
		output.putString(name);
		output.putByte((byte) (value ? 1 : 0));

		flush();
	}

	@Override
	public void flush()
	{
		output.flush();
	}
}
