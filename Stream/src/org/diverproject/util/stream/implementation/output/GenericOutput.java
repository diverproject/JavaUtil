package org.diverproject.util.stream.implementation.output;

import org.diverproject.util.lang.ByteUtil;
import org.diverproject.util.stream.Output;
import org.diverproject.util.stream.implementation.GenericStream;

/**
 * <h1>Saída Genérica</h1>
 *
 * <p>Essa classe deve ser usada por qualquer tipo de saída de dados no sistema que for criada.
 * Ela irá implementar todos os métodos que são formados chamando outros métodos de si mesmo.
 * Assim, os métodos que não dependem de si próprio deverão ser obrigatoriamente implementados.</p>
 *
 * @see GenericStream
 * @see Output
 *
 * @author Andrew Mello
 */

public abstract class GenericOutput extends GenericStream implements Output
{
	@Override
	public void writeLine(String line)
	{
		for (byte b : line.getBytes())
			write(b);
	}

	@Override
	public void writeBreakLine()
	{
		write((byte) '\n');
	}

	@Override
	public void putByte(byte value)
	{
		write(value);
	}

	@Override
	public void putBytes(byte... values)
	{
		if (!isInverted())
			for (byte value : values)
				write(value);

		else
			for (int i = values.length - 1; i >= 0; i--)
				write(values[i]);
	}

	@Override
	public void putChar(char value)
	{
		write((byte) value);
	}

	@Override
	public void putChars(char... values)
	{
		for (char value : values)
			putChar(value);
	}

	@Override
	public void putShort(short value)
	{
		putBytes(ByteUtil.parseShort(value));
	}

	@Override
	public void putShorts(short... values)
	{
		for (short value : values)
			putShort(value);
	}

	@Override
	public void putInt(int value)
	{
		putBytes(ByteUtil.parseInt(value));
	}

	@Override
	public void putInts(int... values)
	{
		for (int value : values)
			putInt(value);
	}

	@Override
	public void putLong(long value)
	{
		putBytes(ByteUtil.parseLong(value));
	}

	@Override
	public void putLongs(long... values)
	{
		for (long value : values)
			putLong(value);
	}

	@Override
	public void putFloat(float value)
	{
		putBytes(ByteUtil.parseFloat(value));
	}

	@Override
	public void putFloats(float... values)
	{
		for (float value : values)
			putFloat(value);
	}

	@Override
	public void putDouble(double value)
	{
		putBytes(ByteUtil.parseDouble(value));
	}

	@Override
	public void putDoubles(double... values)
	{
		for (double value : values)
			putDouble(value);
	}

	@Override
	public void putString(String str)
	{
		if (str.length() > 255)
			str = str.substring(0, 255);

		putByte((byte) str.length());
		putBytes(str.getBytes());
	}

	@Override
	public void putStrings(String... values)
	{
		for (String value : values)
			putString(value);
	}

	@Override
	public void putString(String str, int length)
	{
		if (length > 255)
			length = 255;

		for (int i = 0; i < length; i++)
		{
			if (i < str.length())
				putChar(str.charAt(i));
			else
				putByte((byte) 0);
		}
	}

	@Override
	public void putStrings(int length, String... values)
	{
		for (String value : values)
			putString(value, length);
	}
}
