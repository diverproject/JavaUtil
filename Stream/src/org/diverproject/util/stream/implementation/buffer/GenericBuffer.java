package org.diverproject.util.stream.implementation.buffer;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.stream.Buffer;
import org.diverproject.util.stream.Input;
import org.diverproject.util.stream.Output;
import org.diverproject.util.stream.implementation.GenericStream;
import org.diverproject.util.stream.implementation.input.InputByteArray;
import org.diverproject.util.stream.implementation.output.OutputByteArray;

/**
 * <h1>Buffer Padrão</h1>
 *
 * <p>As comunicações denominados buffer são aquelas que permitem tanto a entrada quanto saída de dados.
 * Devido a sua funcionalidade é designado uma delegação a essa classe onde será necessário possuir
 * tanto um fluxo de dados de saída quanto um fluxo de dados para entrada de dados no buffer.</p>
 *
 * A ideia é que ambas as comunicações para o fluxo de dados de entrada e saída tenha o mesmo fluxo.
 * Ou seja, os dados que nele irão passar deverão vir de um lugar e retornar para o mesmo lugar.
 *
 * @see Buffer
 * @see Input
 * @see Output
 * @see InputByteArray
 * @see OutputByteArray
 * @see GenericStream
 *
 * @author Andrew Mello
 */

public abstract class GenericBuffer extends GenericStream implements Buffer
{
	/**
	 * Comunicação do fluxo para entrada de dados.
	 */
	protected Input input;

	/**
	 * Comunicação do fluxo para saída de dados.
	 */
	protected Output output;

	/**
	 * Cria uma novo buffer padrão definindo quais são as comunicações de fluxos para entrada e saída de dados.
	 * @param reader referência da comunicação que será usada para fazer a leitura dos dados.
	 * @param writer referência da comunicação que será usada para fazer a escrita dos dados.
	 */

	public GenericBuffer(InputByteArray input, OutputByteArray output)
	{
		this.input = input;
		this.output = output;
	}

	@Override
	public byte[] readAt(String sequence)
	{
		return input.readAt(sequence);
	}

	@Override
	public String readLine()
	{
		return input.readLine();
	}

	@Override
	public byte getByte()
	{
		return input.getByte();
	}

	@Override
	public char getChar()
	{
		return input.getChar();
	}

	@Override
	public short getShort()
	{
		return input.getShort();
	}

	@Override
	public int getInt()
	{
		return input.getInt();
	}

	@Override
	public long getLong()
	{
		return input.getLong();
	}

	@Override
	public float getFloat()
	{
		return input.getFloat();
	}

	@Override
	public double getDouble()
	{
		return input.getDouble();
	}

	@Override
	public String getString(int length)
	{
		return input.getString(length);
	}

	@Override
	public byte[] getBytes(int size)
	{
		return input.getBytes(size);
	}

	@Override
	public void getBytes(byte[] array)
	{
		input.getBytes(array);
	}

	@Override
	public char[] getChars(int size)
	{
		return input.getChars(size);
	}

	@Override
	public void getChars(char[] array)
	{
		input.getChars(array);
	}

	@Override
	public short[] getShorts(int size)
	{
		return input.getShorts(size);
	}

	@Override
	public void getShorts(short[] array)
	{
		input.getShorts(array);
	}

	@Override
	public int[] getInts(int size)
	{
		return input.getInts(size);
	}

	@Override
	public void getInts(int[] array)
	{
		input.getInts(array);
	}

	@Override
	public long[] getLongs(int size)
	{
		return input.getLongs(size);
	}

	@Override
	public void getLongs(long[] array)
	{
		input.getLongs(array);
	}

	@Override
	public float[] getFloats(int size)
	{
		return input.getFloats(size);
	}

	@Override
	public void getFloats(float[] array)
	{
		input.getFloats(array);
	}

	@Override
	public double[] getDoubles(int size)
	{
		return input.getDoubles(size);
	}

	@Override
	public void getDoubles(double[] array)
	{
		input.getDoubles(array);
	}

	@Override
	public String getString()
	{
		return getString(getByte());
	}

	@Override
	public String[] getStrings(int size)
	{
		String array[] = new String[size];

		getStrings(array);

		return array;
	}

	@Override
	public void getStrings(String[] array)
	{
		input.getStrings(array);
	}

	@Override
	public String[] getStrings(int size, int length)
	{
		return input.getStrings(size, length);
	}

	@Override
	public void getStrings(String[] array, int length)
	{
		input.getStrings(array, length);
	}

	@Override
	public void putByte(byte value)
	{
		output.putByte(value);
	}

	@Override
	public void putBytes(byte... values)
	{
		output.putBytes(values);
	}

	@Override
	public void putChar(char value)
	{
		output.putChar(value);
	}

	@Override
	public void putChars(char... values)
	{
		output.putChars(values);
	}

	@Override
	public void putShort(short value)
	{
		output.putShort(value);
	}

	@Override
	public void putShorts(short... values)
	{
		output.putShorts(values);
	}

	@Override
	public void putInt(int value)
	{
		output.putInt(value);
	}

	@Override
	public void putInts(int... values)
	{
		output.putInts(values);
	}

	@Override
	public void putLong(long value)
	{
		output.putLong(value);
	}

	@Override
	public void putLongs(long... values)
	{
		output.putLongs(values);
	}

	@Override
	public void putFloat(float value)
	{
		output.putFloat(value);
	}

	@Override
	public void putFloats(float... values)
	{
		output.putFloats(values);
	}

	@Override
	public void putDouble(double value)
	{
		output.putDouble(value);
	}

	@Override
	public void putDoubles(double... values)
	{
		output.putDoubles(values);
	}

	@Override
	public void putString(String str)
	{
		output.putString(str);
	}

	@Override
	public void putStrings(String... values)
	{
		output.putStrings(values);
	}

	@Override
	public void putString(String str, int length)
	{
		output.putString(str, length);
	}

	@Override
	public void putStrings(int length, String... values)
	{
		output.putStrings(length, values);
	}

	@Override
	public void flush()
	{
		output.flush();
	}

	@Override
	public void writeLine(String line)
	{
		output.writeLine(line);
	}

	@Override
	public void writeBreakLine()
	{
		output.writeBreakLine();
	}

	@Override
	public int offset()
	{
		return input.offset();
	}

	@Override
	public int length()
	{
		return input.length();
	}

	@Override
	public boolean isEmpty()
	{
		return offset() == length();
	}

	@Override
	public boolean isInverted()
	{
		return input.isInverted();
	}

	@Override
	public void setInvert(boolean enable)
	{
		input.setInvert(enable);
		output.setInvert(enable);
	}

	/**
	 * Método interno usado para complementar o toString dos objetos.
	 * @param description referência da descrição desse objeto.
	 */

	protected void toString(ObjectDescription description)
	{
		description.append("offset", offset());
		description.append("length", length());
		description.append("space", space());
		description.append("closed", isClosed());
		description.append("inverted", isInverted());
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		toString(description);

		return description.toString();
	}
}
