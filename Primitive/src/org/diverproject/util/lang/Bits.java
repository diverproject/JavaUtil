package org.diverproject.util.lang;

import org.diverproject.util.lang.IntUtil;

/**
 * <p><h1>Bits</h1></p>
 *
 * <p>Deve funcionar como uma classe utilitária que irá trabalhar com dados primitivos em geral.
 * Esse trabalho se refere a conversão de dados de diversas formas e de diferentes maneiras.
 * Todos os procedimentos são estáticos, utilizados quando trabalha-se com bytes.</p>
 *
 * <p>Para usar essa classe deve saber como funciona o armazenamento de dados em bytes.
 * Em seguida entender como é feito a leitura dos bytes para cada tipo de dado primitivo.
 * Assim será possível fazer bom uso das funcionalidades oferecidas por essa classe.</p>
 *
 * @author Andrew Mello
 */

public class Bits
{
	/**
	 * Construtor privado pra evitar instâncias desnecessárias de Bits.
	 */

	private Bits()
	{
		
	}

	/**
	 * Troca a ordem dos bytes usados para por um valor do tipo primitivo short.
	 * @param value valor do qual será usados os bytes para inverter.
	 * @return número short contendo o um valor a partir dos bytes invertidos.
	 */

	public static short swap(short value)
	{
		return Short.reverseBytes(value);
	}

	/**
	 * Troca a ordem dos bytes usados para por um valor do tipo primitivo int.
	 * @param value valor do qual será usados os bytes para inverter.
	 * @return número contendo o um valor a partir dos bytes invertidos.
	 */

	public static int swap(int value)
	{
		return Integer.reverseBytes(value);
	}

	/**
	 * Troca a ordem dos bytes usados para por um valor do tipo primitivo long.
	 * @param value valor do qual será usados os bytes para inverter.
	 * @return número contendo o um valor a partir dos bytes invertidos.
	 */

	public static long swap(long value)
	{
		return Long.reverseBytes(value);
	}

	/**
	 * Constrói um novo número de tipo primitivo numérico inteiro short.
	 * @param bytes vetor com os 8 bytes de menor valor correspondente a 1x.
	 * @return número contendo os bytes passados como conteúdo.
	 */

	public static short makeShort(byte... bytes)
	{
		bytes = fixBytes(bytes, 2);

		return (short) ((bytes[0] << 8) | (bytes[1] & 255));
	}

	/**
	 * Constrói um novo número do tipo primitivo número inteiro int.
	 * @param bytes vetor com os 8 bytes de menor valor correspondente a 1x.
	 * @return número contendo os bytes passados como conteúdo.
	 */

	public static int makeInt(byte... bytes)
	{
		bytes = fixBytes(bytes, 4);

		return (((bytes[0]      ) << 24) |
				((bytes[1] & 255) << 16) |
				((bytes[2] & 255) <<  8) |
				((bytes[3] & 255)      ));
	}

	/**
	 * Constrói um novo número do tipo primitivo número inteiro long.
	 * @param bytes vetor com os 8 bytes de menor valor correspondente a 1x.
	 * @return número contendo os bytes passados como conteúdo ou zero se inválido.
	 */

	public static long makeLong(byte... bytes)
	{
		bytes = fixBytes(bytes, 8);

		return ((((long) bytes[0]      ) << 56) |
				(((long) bytes[1] & 255) << 48) |
				(((long) bytes[2] & 255) << 40) |
				(((long) bytes[3] & 255) << 32) |
				(((long) bytes[4] & 255) << 24) |
				(((long) bytes[5] & 255) << 16) |
				(((long) bytes[6] & 255) <<  8) |
				(((long) bytes[7] & 255)      ));
	}

	/**
	 * Constrói um novo número do tipo primitivo número flutuante double.
	 * @param bytes vetor com os 8 bytes que irão formar o valor flutuante double.
	 * @return número contendo os bytes passados como conteúdo ou zero se inválido.
	 */

	public static float makeFloat(byte... bytes)
	{
		return Float.intBitsToFloat(makeInt(bytes));
	}

	/**
	 * Constrói um novo número do tipo primitivo número flutuante double.
	 * @param bytes vetor com os 8 bytes que irão formar o valor flutuante double.
	 * @return número contendo os bytes passados como conteúdo ou zero se inválido.
	 */

	public static double makeDouble(byte... bytes)
	{
		return Double.longBitsToDouble(makeLong(bytes));
	}

	/**
	 * Obtém o valor do byte usado por um número do tipo inteiro short.
	 * @param value valor do qual será usado para se obter o byte.
	 * @param index qual byte deve ser obtido entre 1 e 2.
	 * @return valor do byte correspondente ao índice.
	 */

	public static byte byteOf(short value, int index)
	{
		index = IntUtil.limit(index, 1, Short.BYTES);

		return (byte) (value >> (index - 1) * 8);
	}

	/**
	 * Obtém o valor do byte usado por um número do tipo inteiro int.
	 * @param value valor do qual será usado para se obter o byte.
	 * @param index qual byte deve ser obtido entre 1 e 4.
	 * @return valor do byte correspondente ao índice.
	 */

	public static byte byteOf(int value, int index)
	{
		index = IntUtil.limit(index, 1, Integer.BYTES);

		return (byte) (value >> (index - 1) * 8);	
	}

	/**
	 * Obtém o valor do byte usado por um número do tipo inteiro long.
	 * @param value valor do qual será usado para se obter o byte.
	 * @param index qual byte deve ser obtido entre 1 e 2.
	 * @return valor do byte correspondente ao índice.
	 */

	public static byte byteOf(long value, int index)
	{
		index = IntUtil.limit(index, 1, Long.BYTES);

		return (byte) (value >> (index - 1) * 8);
	}

	/**
	 * Cada tipo de número primitivo ocupa um determinado valor de bytes em memória.
	 * Esse método é usado internamente para garantir que os parâmetros passados estejam
	 * de acordo com a capacidade de bytes para dado primitivo, se faltar será preenchido.
	 * @param bytes vetor contendo os bytes necessários para realizar a conversão em número.
	 * @param size quantos bytes deverá possuir o vetor retornado deste método.
	 * @return mesmo vetor passado se tiver o o tamanho exato, preenchidos com zero a frente
	 * caso não tenha um tamanho menor, se o tamanho for maior será cortado os primeiros bytes.
	 */

	private static byte[] fixBytes(byte[] bytes, int size)
	{
		if (bytes.length == size)
			return bytes;

		byte bytesFixed[] = new byte[size];
		int diff = size - bytes.length;

		if (diff > 0)
			for (int i = 0; i < bytes.length; i++)
				bytesFixed[diff + i] = bytes[i];

		else
			for (int i = 0; i < size; i++)
				bytesFixed[i] = bytes[i];

		return bytesFixed;
	}
}
