package org.diverproject.util.lang;

import java.nio.ByteBuffer;

import org.diverproject.util.UtilException;

/**
 * <p><h1>Utilitário para Long</h1></p>
 *
 * Classe com métodos utilitários para manusear valores do tipo long.
 * Todos os métodos envolvem retornar atributos do tipo <b>long</b>.
 * Em alguns casos há métodos que retornam objetos do tipo <b>long</b>.
 * Apesar de ambos serem do mesmo tipo, algumas operações necessitam
 * utilizar o objeto ao invés do atributo, e vice-versa.
 */

public class LongUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private LongUtil()
	{
		
	}

	/**
	 * Exibe um determinado vetor de longs no console separado por índice.
	 * Por padrão esse método irá exibir apenas 20 elementos por linha.
	 * @param array vetor com os números do tipo long que serão exibidos no console.
	 */

	public static void print(long[] array)
	{
		print((long) 20, array);
	}

	/**
	 * Exibe um determinado vetor de longs no console em linhas.
	 * @param perline quantos elementos serão exibidos por linha.
	 * @param array vetor com os caracteres que serão impressos.
	 */

	public static void print(long perline, long[] array)
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
	 * Verifica se uma determinada string pode ser convertida para um número long.
	 * Valida se a string é composta apenas por números seja positivo ou negativo, e
	 * por fim verifica se está dentro dos limites permitidos para um número long.
	 * @param str string do qual deve ser verificada se pode converter.
	 * @return true se for possível converter para um número long.
	 */

	public static boolean islong(String str)
	{
		if (!StringUtil.isNumber(str))
			return false;

		if (str.length() > (str.startsWith("-") ? 6 : 5))
			return false;

		if (str.startsWith("-") && str.length() == 6 && str.compareTo(Long.toString(Long.MIN_VALUE)) > 0)
			return false;

		else if (str.length() == 5 && str.compareTo(Long.toString(Long.MAX_VALUE)) > 0)
			return false;

		return true;
	}

	/**
	 * Percorre uma array de strings verificando a string pode ser convertida para long.
	 * @param array vetor que será verificado se pode ser convertido em long.
	 * @return true se todas as string podem ser convertidas ou false caso uma ou mais não consiga.
	 */

	public static boolean islong(String[] array)
	{
		for (String string : array)
			if (!islong(string))
				return false;

		return true;
	}

	/**
	 * Faz toda uma verificação para garantir que uma determina seja convertida para um número longo.
	 * Irá considerar valores hexadecimais apenas se forem seguidos do pré-fixo 0x que irá determinar tal.
	 * @param value string contendo o valor numérico ou hexadecimal do qual deverá ser convertido.
	 * @return valor numérico contido na string passada se assim for um.
	 * @throws UtilException apenas se houver falha na conversão.
	 */

	public static long parseString(String value) throws UtilException
	{
		if (value.startsWith("0x"))
			return HexUtil.parseLong(value);

		StringUtil.isParseNumber(value);

		boolean negative = false;

		if (value.startsWith("-"))
			negative = true;

		if (negative)
		{
			if (value.length() > Long.toString(Long.MIN_VALUE).length())
				throw new UtilException("valor muito pequeno");
		}

		else if (value.length() > Long.toString(Long.MAX_VALUE).length())
			throw new UtilException("valor muito grande");

		long parse = 0;

		for (int i = negative ? 1 : 0; i < value.length(); i++)
		{
			char c = value.charAt(i);

			if (!Character.isDigit(c))
				throw new UtilException("valor não numérico");

			parse = (parse * 10) + Character.digit(c, 10);
		}

		return parse;
	}

	/**
	 * Converte uma determinada string para um valor do tipo numérico long.
	 * @param str string do qual deve ser convertida para um número long.
	 * @return valor numérico da string passada ou 0 caso não seja numérico.
	 */

	public static long parse(String str)
	{
		return parse(str, 0L);
	}

	/**
	 * Converte uma determinada string para um valor do tipo numérico long.
	 * @param str string do qual deve ser convertida para um número long.
	 * @param fail valor que será retornado caso a string não seja numérica.
	 * @return valor numérico da string se for válida ou <b>fail</b> caso contrário.
	 */

	public static long parse(String str, long fail)
	{
		if (!islong(str))
			return fail;

		return Long.parseLong(str);
	}

	/**
	 * Um byte possui um valor de até 8 bits que equivalem a um valor binário
	 * entre 0 e 255. Esse método permite obter esse valor binário (decimal).
	 * @param b byte que será convertido para um valor numérico long.
	 * @return valor numérico long de 0 a 255 do byte respectivo.
	 */

	public static long parseByte(byte b)
	{
		long value = (long) b;

		return (long) (value >= 0 ? value : 256 + value);
	}

	/**
	 * Analisa um vetor de bytes e tenta criar um número long a partir destes bytes.
	 * Quando o vetor de bytes for nulo, branco ou mais de 4 índices retorna 0 (inválido).
	 * @param bytes vetor contendo os bytes que deve criar o número long.
	 * @return número long criado a partir dos bytes passado.
	 */

	public static long parseBytes(byte[] bytes)
	{
		return ByteBuffer.wrap(bytes).getLong(0);
	}

	/**
	 * Analisa um vetor de bytes e tenta criar um número long a partir destes bytes.
	 * Quando o vetor de bytes for nulo, branco ou mais de 4 índices retorna 0 (inválido).
	 * Esse método também irá inverter a ordem de como os bytes devem ser posicionados.
	 * @param bytes vetor contendo os bytes que deve criar o número long.
	 * @return número long criado a partir dos bytes passado.
	 */

	public static long parseBytesInvert(byte[] bytes)
	{
		return ByteBuffer.wrap(ByteUtil.invertArray(bytes)).getLong(0);
	}

	/**
	 * Converte uma determinada string em valor hexadecimal em número long.
	 * Caso o valor não seja um hexadecimal será retornado 0 por padrão da conversão.
	 * @param str valor hexadecimal salvo em uma string (com ou sem 0x no inicio).
	 * @return valor numérico long obtido da conversão do valor em hexadecimal.
	 */

	public static long parseHex(String str)
	{
		if (!HexUtil.isHexLong(str))
			return 0;

		return Long.parseLong(HexUtil.clearHex(str), 16);
	}

	/**
	 * Converte um determinado vetor de strings para valores numéricos
	 * do tipo long. O método é feito percorrendo o vetor e passando
	 * as strings para o método parse(array[i]) de modo que utiliza as
	 * regras definidas na documentação do mesmo.
	 * @param array vetor de strings que serão convertidas para long.
	 * @return vetor com números do tipo long.
	 */

	public static long[] parseArray(String[] array)
	{
		long parse[] = new long[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = parse(array[i]);

		return parse;
	}

	/**
	 * Constrói um novo vetor que irá armazenar valores primitivos.
	 * @param array vetor de objetos do tipo long a ser usado.
	 * @return vetor de valores primitivos do tipo long criado.
	 */

	public static long[] parseArrayChar(char[] array)
	{
		long parse[] = new long[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = (long) array[i];

		return parse;
	}

	/**
	 * Constrói um novo vetor que irá armazenar objetos do tipo long.
	 * @param array vetor de valores primitivos do tipo long.
	 * @return vetor de objetos do tipo long que foi criado.
	 */

	public static Long[] parseArrayLong(long[] array)
	{
		Long parse[] = new Long[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = array[i];

		return parse;
	}

	/**
	 * Obtém o valor numérico de objetos do tipo long.
	 * Passa os valores para um vetor numérico do tipo long.
	 * @param array vetor com os objetos do tipo long.
	 * @return vetor long com os valores dos objetos.
	 */

	public static long[] parseArrayLong(Long[] array)
	{
		long parse[] = new long[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = array[i] == null ? 0 : array[i];

		return parse;
	}

	/**
	 * Obtém objetos long a partir dos valores numéricos long.
	 * @param array vetor com os valores numéricos do tipo long.
	 * @return vetor com os objetos long dos números long.
	 */

	public static long[] parseArrayInt(long[] array)
	{
		long parse[] = new long[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = array[i];

		return parse;
	}

	/**
	 * Copia uma parte de um determinado vetor de números long. O modo de cópia
	 * é feito por um ponto inicial <b>offset</b> (índice). A partir deste
	 * ponto ele irá obter os próximos n índices e inseri-los no vetor.
	 * @param array vetor que será copiado uma parte.
	 * @param offset ponto inicial da cópia (índice).
	 * @param length quantos elementos serão copiados.
	 * @return vetor apenas com os elementos copiados.
	 */

	public static long[] subarray(long[] array, int offset, int length)
	{
		if (offset > array.length - 1 || offset + length > array.length)
			return new long[]{};

		long select[] = new long[length];

		for (int i = 0; i < length; i++)
			select[i] = array[offset + i];

		return select;
	}

	/**
	 * Obtém o valor percentual long entre dois números.
	 * Apesar de serem double, podem ser inseridos de quaisquer
	 * tipos. Onde um se insere o valor que seria igual a 100%
	 * e o outro o valor que deseja saber a porcentagem.
	 * @param complete valor que deseja obter a porcentagem.
	 * @param total valor que representa 100% da quantidade.
	 * @return percentual long dos valores acima.
	 */

	public static long toPorcent(double complete, double total)
	{
		return (long) ((double) (complete / total) * 100);
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

	public static long limit(long value, long min, long max)
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

	public static long min(long value, long min)
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

	public static long max(long value, long max)
	{
		return value > max ? max : value;
	}

	/**
	 * Permite obter o valor de um objeto do tipo Long ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @return valor real do objeto ou 0 se for nulo.
	 */

	public static long valueOf(Long value)
	{
		return valueOf(value, 0L);
	}

	/**
	 * Permite obter o valor de um objeto do tipo Long ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @param valueNull valor que será retornado cao seja nulo.
	 * @return valor real do objeto ou valueNull se for nulo.
	 */

	public static long valueOf(Long value, long valueNull)
	{
		return value == null ? valueNull : value;
	}
}
