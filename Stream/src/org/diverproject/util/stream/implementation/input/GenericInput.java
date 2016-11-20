package org.diverproject.util.stream.implementation.input;

import static org.diverproject.util.lang.Bits.makeDouble;
import static org.diverproject.util.lang.Bits.makeFloat;
import static org.diverproject.util.lang.Bits.makeInt;
import static org.diverproject.util.lang.Bits.makeLong;
import static org.diverproject.util.lang.Bits.makeShort;

import java.io.ByteArrayOutputStream;

import org.diverproject.util.stream.Input;
import org.diverproject.util.stream.implementation.GenericStream;

/**
 * <h1>Entrada Genérica</h1>
 *
 * <p>Essa classe deve ser usada por qualquer tipo de entrada de dados no sistema que for criada.
 * Ela irá implementar todos os métodos que são formados chamando outros métodos de si mesmo.
 * Assim, os métodos que não dependem de si próprio deverão ser obrigatoriamente implementados.</p>
 *
 * @see GenericStream
 * @see Input
 *
 * @author Andrew
 */

public abstract class GenericInput extends GenericStream implements Input
{
	@Override
	public byte[] readAt(String sequence)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = 0;

		while (space() > 0 && i < sequence.length())
		{
			byte b = read();

			if (sequence.charAt(i) == b)
				i++;
			else
				i = 0;

			if (i != sequence.length())
				baos.write(b);
		}

		return baos.toByteArray();
	}

	@Override
	public String readLine()
	{
		return new String(readAt("\n"));
	}

	@Override
	public char getChar()
	{
		return (char) getByte();
	}

	@Override
	public short getShort()
	{
		byte b0 = read();
		byte b1 = read();

		return !isInverted() ?
				makeShort(b1, b0) :
				makeShort(b0, b1);
	}

	@Override
	public int getInt()
	{
		byte b0 = read();
		byte b1 = read();
		byte b2 = read();
		byte b3 = read();

		return !isInverted() ?
				makeInt(b3, b2, b1, b0) :
				makeInt(b0, b1, b2, b3);
	}

	@Override
	public long getLong()
	{
		byte b0 = read();
		byte b1 = read();
		byte b2 = read();
		byte b3 = read();
		byte b4 = read();
		byte b5 = read();
		byte b6 = read();
		byte b7 = read();

		return !isInverted() ?
				makeLong(b7, b6, b5, b4, b3, b2, b1, b0) :
				makeLong(b0, b1, b2, b3, b4, b5, b6, b7);
	}

	@Override
	public float getFloat()
	{
		byte b0 = read();
		byte b1 = read();
		byte b2 = read();
		byte b3 = read();

		return !isInverted() ?
				makeFloat(b3, b2, b1, b0) :
				makeFloat(b0, b1, b2, b3);
	}

	@Override
	public double getDouble()
	{
		byte b0 = read();
		byte b1 = read();
		byte b2 = read();
		byte b3 = read();
		byte b4 = read();
		byte b5 = read();
		byte b6 = read();
		byte b7 = read();

		return !isInverted() ?
				makeDouble(b7, b6, b5, b4, b3, b2, b1, b0) :
				makeDouble(b0, b1, b2, b3, b4, b5, b6, b7);
	}

	@Override
	public String getString(int length)
	{
		return new String(getBytes(length));
	}

	@Override
	public byte[] getBytes(int size)
	{
		byte array[] = new byte[size];

		getBytes(array);

		return array;
	}

	@Override
	public void getBytes(byte[] array)
	{
		for (int i = 0; i < array.length; i++)
			array[i] = getByte();
	}

	@Override
	public char[] getChars(int size)
	{
		char array[] = new char[size];

		getChars(array);

		return array;
	}

	@Override
	public void getChars(char[] array)
	{
		for (int i = 0; i < array.length; i++)
			array[i] = getChar();		
	}

	@Override
	public short[] getShorts(int size)
	{
		short array[] = new short[size];

		getShorts(array);

		return array;
	}

	@Override
	public void getShorts(short[] array)
	{
		for (int i = 0; i < array.length; i++)
			array[i] = getShort();		
	}

	@Override
	public int[] getInts(int size)
	{
		int array[] = new int[size];

		getInts(array);

		return array;
	}

	@Override
	public void getInts(int[] array)
	{
		for (int i = 0; i < array.length; i++)
			array[i] = getInt();		
	}

	@Override
	public long[] getLongs(int size)
	{
		long array[] = new long[size];

		getLongs(array);

		return array;
	}

	@Override
	public void getLongs(long[] array)
	{
		for (int i = 0; i < array.length; i++)
			array[i] = getLong();		
	}

	@Override
	public float[] getFloats(int size)
	{
		float array[] = new float[size];

		getFloats(array);

		return array;
	}

	@Override
	public void getFloats(float[] array)
	{
		for (int i = 0; i < array.length; i++)
			array[i] = getFloat();		
	}

	@Override
	public double[] getDoubles(int size)
	{
		double array[] = new double[size];

		getDoubles(array);

		return array;
	}

	@Override
	public void getDoubles(double[] array)
	{
		for (int i = 0; i < array.length; i++)
			array[i] = getDouble();		
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
		for (int i = 0; i < array.length; i++)
			array[i] = getString();
	}

	@Override
	public String[] getStrings(int size, int length)
	{
		String array[] = new String[size];

		getStrings(array, length);

		return array;
	}

	@Override
	public void getStrings(String[] array, int length)
	{
		for (int i = 0; i < array.length; i++)
			array[i] = getString(length);
	}
}
