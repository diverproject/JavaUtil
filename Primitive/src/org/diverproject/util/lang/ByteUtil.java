package org.diverproject.util.lang;

import java.nio.ByteBuffer;

import org.diverproject.util.UtilException;

/**
 * <p><h1>Utilitário para Byte</h1></p>
 *
 * Classe com métodos utilitários para manusear valores do tipo byte.
 * Todos os métodos envolvem retornar atributos do tipo <b>byte</b>.
 * Em alguns casos há métodos que retornam objetos do tipo <b>Byte</b>.
 * Apesar de ambos serem do mesmo tipo, algumas operações necessitam
 * utilizar o objeto ao invés do atributo, e vice-versa.
 */

public class ByteUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private ByteUtil()
	{
		
	}

	/**
	 * Exibe um determinado vetor de bytes no console separado por índice.
	 * Por padrão esse método irá exibir apenas 20 elementos por linha.
	 * @param array vetor com os bytes que serão exibidos no console.
	 */

	public static void print(byte[] array)
	{
		print((short) 20, array);
	}

	/**
	 * Exibe um determinado vetor de bytes no console em linhas.
	 * @param perline quantos elementos será exibido por linha.
	 * @param array vetor com os bytes que serão impressos.
	 */

	public static void print(short perline, byte[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (i % perline == 0)
				System.out.printf("\ni%4d: ", i + 1);

			System.out.printf("[%5d]", array[i]);
		}

		System.out.println();
	}

	/**
	 * Faz toda uma verificação para garantir que uma determina seja convertida para um byte.
	 * Irá considerar valores hexadecimais apenas se forem seguidos do pré-fixo 0x que irá determinar tal.
	 * @param value string contendo o valor numérico ou hexadecimal do qual deverá ser convertido.
	 * @return valor numérico contido na string passada se assim for um.
	 * @throws UtilException apenas se houver falha na conversão.
	 */

	public static byte parseString(String value) throws UtilException
	{
		if (value.startsWith("0x"))
			return HexUtil.parseByte(value);

		StringUtil.isParseNumber(value);

		boolean negative = false;

		if (value.startsWith("-"))
			negative = true;

		if (negative)
		{
			if (value.length() > Byte.toString(Byte.MIN_VALUE).length())
				throw new UtilException("valor muito pequeno");
		}

		else if (value.length() > Byte.toString(Byte.MAX_VALUE).length())
			throw new UtilException("valor muito grande");

		byte parse = 0;

		for (int i = negative ? 1 : 0; i < value.length(); i++)
		{
			char c = value.charAt(i);

			if (!Character.isDigit(c))
				throw new UtilException("valor não numérico");

			parse = (byte) ((parse * 10) + Character.digit(c, 10));
		}

		return parse;
	}

	/**
	 * Obtém os bytes de um determinado caracter, caracteres possuem 2 bytes.
	 * @param character caracter que será obtido os seus bytes.
	 * @return vetor contendo dois bytes do caracter passado.
	 */

	public static byte[] parseChar(char character)
	{
		byte bytes[] = new byte[Character.BYTES];

		bytes[1] = (byte) ((character >> 0) & 255);
		bytes[0] = (byte) ((character >> 8) & 255);

		return bytes;
	}

	/**
	 * Obtém os bytes de um determinado short, shorts possuem 2 bytes.
	 * @param value número short que será obtido os seus bytes.
	 * @return vetor contendo dois bytes do short passado.
	 */

	public static byte[] parseShort(short value)
	{
		byte bytes[] = new byte[Short.BYTES];

		bytes[1] = (byte) ((value >> 0) & 255);
		bytes[0] = (byte) ((value >> 8) & 255);

		return bytes;
	}

	/**
	 * Obtém os bytes de um determinado int, ints possuem 4 bytes.
	 * @param value número int que será obtido os seus bytes.
	 * @return vetor contendo quatro bytes do int passado.
	 */

	public static byte[] parseInt(int value)
	{
		byte bytes[] = new byte[Integer.BYTES];

		bytes[3] = (byte) ((value >> 0) & 255);
		bytes[2] = (byte) ((value >> 8) & 255);
		bytes[1] = (byte) ((value >> 16) & 255);
		bytes[0] = (byte) ((value >> 24) & 255);

		return bytes;
	}

	/**
	 * Obtém os bytes de um determinado long, longs possuem 8 bytes.
	 * @param value número long que será obtido os seus bytes.
	 * @return vetor contendo oito bytes do long passado.
	 */

	public static byte[] parseLong(long value)
	{
		return ByteBuffer.allocate(Long.BYTES).putLong(value).array();
	}

	/**
	 * Obtém os bytes de um determinado float, floats possuem 4 bytes.
	 * @param value número float que será obtido os seus bytes.
	 * @return vetor contendo quatro bytes do float passado.
	 */

	public static byte[] parseFloat(float value)
	{
		return ByteBuffer.allocate(Float.BYTES).putFloat(value).array();
	}

	/**
	 * Obtém os bytes de um determinado double, doubles possuem 8 bytes.
	 * @param value número double que será obtido os seus bytes.
	 * @return vetor contendo oito bytes do double passado.
	 */

	public static byte[] parseDouble(double value)
	{
		return ByteBuffer.allocate(Double.BYTES).putDouble(value).array();
	}

	/**
	 * Converte um vetor do tipo byte para um vetor de objetos
	 * do tipo Byte. Apesar de serem do mesmo tipo, algumas operações
	 * divergem esses dois tipos de dados apesar de serem o mesmo.
	 * @param array vetor contendo os bytes que serão convertidos.
	 * @return vetor de objetos do tipo Byte com os bytes convertidos.
	 */

	public static Byte[] parseArray(byte[] array)
	{
		Byte parsed[] = new Byte[array.length];

		for (int i = 0; i < array.length; i++)
			parsed[i] = array[i];

		return parsed;
	}

	/**
	 * Converte um vetor de caracteres para um vetor de bytes.
	 * Cada caracter irá gerar 2 bytes no vetor retornado.
	 * @param array vetor de caracteres que será convertido.
	 * @return vetor contendo os bytes dos caracteres.
	 */

	public static byte[] parseArrayChar(char[] array)
	{
		ByteBuffer buffer = ByteBuffer.allocate(Character.BYTES * array.length);

		for (char c : array)
			buffer.put(parseChar(c));

		return buffer.array();
	}

	/**
	 * Converte um vetor de shorts para um vetor de bytes.
	 * Cada short irá gerar 2 bytes no vetor retornado.
	 * @param array vetor de short que será convertido.
	 * @return vetor contendo os bytes dos short.
	 */

	public static byte[] parseArrayShort(short[] array)
	{
		ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES * array.length);

		for (short s : array)
			buffer.put(parseShort(s));

		return buffer.array();
	}

	/**
	 * Converte um vetor de ints para um vetor de bytes.
	 * Cada int irá gerar 4 bytes no vetor retornado.
	 * @param array vetor de int que será convertido.
	 * @return vetor contendo os bytes dos int.
	 */

	public static byte[] parseArrayInt(int[] array)
	{
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * array.length);

		for (int l : array)
			buffer.put(parseInt(l));

		return buffer.array();
	}

	/**
	 * Converte um vetor de longs para um vetor de bytes.
	 * Cada long irá gerar 8 bytes no vetor retornado.
	 * @param array vetor de long que será convertido.
	 * @return vetor contendo os bytes dos long.
	 */

	public static byte[] parseArrayLong(long[] array)
	{
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES * array.length);

		for (long l : array)
			buffer.put(parseLong(l));

		return buffer.array();
	}

	/**
	 * Converte um vetor de floats para um vetor de bytes.
	 * Cada float irá gerar 8 bytes no vetor retornado.
	 * @param array vetor de float que será convertido.
	 * @return vetor contendo os bytes dos float.
	 */

	public static byte[] parseArrayFloat(float[] array)
	{
		ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES * array.length);

		for (float f : array)
			buffer.put(parseFloat(f));

		return buffer.array();
	}

	/**
	 * Converte um vetor de doubles para um vetor de bytes.
	 * Cada double irá gerar 8 bytes no vetor retornado.
	 * @param array vetor de double que será convertido.
	 * @return vetor contendo os bytes dos double.
	 */

	public static byte[] parseArrayDouble(double[] array)
	{
		ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES * array.length);

		for (double d : array)
			buffer.put(parseDouble(d));

		return buffer.array();
	}

	/**
	 * Pega uma parte de um determinado vetor de bytes de acordo cos os parâmetros.
	 * @param array vetor do qual será subtraído uma parte (cortada).
	 * @param offset índice inicial do qual deverá ser pego os bytes.
	 * @param length quantos bytes devem ser pegos a partir do offset.
	 * @return vetor contendo os bytes da parte recortada.
	 */

	public static byte[] subarray(byte[] array, int offset, int length)
	{
		if (offset > array.length - 1 || offset + length > array.length)
			return new byte[]{};

		byte select[] = new byte[length];

		for (int i = 0; i < length; i++)
			select[i] = array[offset + i];

		return select;
	}

	/**
	 * Inverte a ordem dos bytes de um determinado vetor.
	 * Não é necessário utilizar como função (retorno),
	 * o vetor passado é invertido internamente.
	 * @param array vetor que terá os bytes invertidos
	 * @return vetor com os byte invertidos.
	 */

	public static byte[] invertArray(final byte[] array)
	{
		byte[] temp = array.clone();

		for (int i = 0; i < array.length; i++)
			array[i] = temp[array.length - i - 1];

		return array;
	}

	/**
	 * Cria um novo byte de acordo com um valor numérico inteiro.
	 * Verifica se o valor está dentro dos limites 0~255.
	 * @param value valor que será convertido para um byte.
	 * @return byte do valor passado (valor absoluto e não bits).
	 */

	public static byte putInt(int value)
	{
		return (byte) IntUtil.limit(value, 0, 255);
	}

	/**
	 * Verifica se o valor está dentro do limite permitido que foi definido.
	 * Caso o valor ultrapasse os limites permitidos será definidos com os mesmos.
	 * @param value valor que será verificado se está dentro dos limites.
	 * @param min limite mínimo permitido que value pode ter.
	 * @param max limite máximo permitido que value pode ter.
	 * @return valor se estive dentro dos limites definido, limite mínimo se for
	 * menor que o permitido ou limite máximo se for maior que o permitido.
	 */

	public static byte limit(byte value, byte min, byte max)
	{
		return value < min ? min : value > max ? max : value;
	}

	/**
	 * Verifica se o valor é menor que o limite determinado por parâmetro.
	 * @param value valor que será verificado se está ou não dentro do mínimo permitido.
	 * @param min valor mínimo que deve ser permitido retornar após verificar.
	 * @return se o valor for menor que o permitido, esse por sua vez (min)
	 * será retornado, caso contrário irá retornar o valor normal.
	 */

	public static byte min(byte value, byte min)
	{
		return value < min ? min : value;
	}

	/**
	 * Verifica se o valor é maior que o limite determinado por parâmetro.
	 * @param value valor que será verificado se está ou não dentro do máximo permitido.
	 * @param max valor máximo que deve ser permitido retornar após verificar.
	 * @return se o valor for maior que o permitido, esse por sua vez (max)
	 * será retornado, caso contrário irá retornar o valor normal.
	 */

	public static byte max(byte value, byte max)
	{
		return value > max ? max : value;
	}

	/**
	 * Constrói um novo vetor que irá armazenar valores primitivos.
	 * @param array vetor de objetos do tipo byte a ser usado.
	 * @return vetor de valores primitivos do tipo byte criado.
	 */

	public static byte[] parseArrayByte(Byte[] array)
	{
		byte[] parsed = new byte[array.length];

		for (int i = 0; i < parsed.length; i++)
			parsed[i] = array[i] == null ? 0 : array[i];

		return parsed;
	}

	/**
	 * Constrói um novo vetor que irá armazenar objetos do tipo byte.
	 * @param array vetor de valores primitivos do tipo byte.
	 * @return vetor de objetos do tipo byte que foi criado.
	 */

	public static Byte[] parseArrayByte(byte[] array)
	{
		Byte[] parsed = new Byte[array.length];

		for (int i = 0; i < parsed.length; i++)
			parsed[i] = array[i];

		return parsed;
	}

	/**
	 * Permite obter o valor de um objeto do tipo Byte ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @return valor real do objeto ou 0 se for nulo.
	 */

	public static byte valueOf(Byte value)
	{
		return valueOf(value, (byte) 0);
	}

	/**
	 * Permite obter o valor de um objeto do tipo Byte ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @param valueNull valor que será retornado cao seja nulo.
	 * @return valor real do objeto ou valueNull se for nulo.
	 */

	public static byte valueOf(Byte value, byte valueNull)
	{
		return value == null ? valueNull : value;
	}

	/**
	 * Faz a conversão do valor absoluto de um número short para um número byte.
	 * @param value valor numérico de 2 bytes que será convertido em byte.
	 * @return valor máximo de um byte se value excedê-lo, valor mínimo de um
	 * byte se value excedê-lo ou o seu valor absoluto convertido para byte.
	 */

	public static byte cast(short value)
	{
		return value > Byte.MAX_VALUE ? Byte.MAX_VALUE : value < Byte.MIN_VALUE ? Byte.MIN_VALUE : (byte) value;
	}

	/**
	 * Faz a conversão do valor absoluto de um número int para um número byte.
	 * @param value valor numérico de 4 bytes que será convertido em byte.
	 * @return valor máximo de um byte se value excedê-lo, valor mínimo de um
	 * byte se value excedê-lo ou o seu valor absoluto convertido para byte.
	 */

	public static byte cast(int value)
	{
		return value > Byte.MAX_VALUE ? Byte.MAX_VALUE : value < Byte.MIN_VALUE ? Byte.MIN_VALUE : (byte) value;
	}

	/**
	 * Faz a conversão do valor absoluto de um número long para um número byte.
	 * @param value valor numérico de 8 bytes que será convertido em byte.
	 * @return valor máximo de um byte se value excedê-lo, valor mínimo de um
	 * byte se value excedê-lo ou o seu valor absoluto convertido para byte.
	 */

	public static byte cast(long value)
	{
		return value > Byte.MAX_VALUE ? Byte.MAX_VALUE : value < Byte.MIN_VALUE ? Byte.MIN_VALUE : (byte) value;
	}
}
