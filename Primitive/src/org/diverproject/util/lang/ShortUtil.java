package org.diverproject.util.lang;

import org.diverproject.util.UtilException;

/**
 * <p><h1>Utilitário para Short</h1></p>
 *
 * Classe com métodos utilitários para manusear valores do tipo short.
 * Todos os métodos envolvem retornar atributos do tipo <b>short</b>.
 * Em alguns casos há métodos que retornam objetos do tipo <b>Short</b>.
 * Apesar de ambos serem do mesmo tipo, algumas operações necessitam
 * utilizar o objeto ao invés do atributo, e vice-versa.
 */

public class ShortUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private ShortUtil()
	{
		
	}

	/**
	 * Exibe um determinado vetor de curtos no console separado por índice.
	 * Por padrão esse método irá exibir apenas 20 elementos por linha.
	 * @param array vetor com os números do tipo long que serão exibidos no console.
	 */

	public static void print(short[] array)
	{
		print((short) 20, array);
	}

	/**
	 * Exibe um determinado vetor de curtos no console em linhas.
	 * @param perline quantos elementos serão exibidos por linha.
	 * @param array vetor com os caracteres que serão impressos.
	 */

	public static void print(short perline, short[] array)
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
	 * Verifica se uma determinada string pode ser convertida para um número curto.
	 * Valida se a string é composta apenas por números seja positivo ou negativo, e
	 * por fim verifica se está dentro dos limites permitidos para um número curto.
	 * @param str string do qual deve ser verificada se pode converter.
	 * @return true se for possível converter para um número curto.
	 */

	public static boolean isShort(String str)
	{
		if (!StringUtil.isNumber(str))
			return false;

		if (str.length() > (str.startsWith("-") ? 6 : 5))
			return false;

		if (str.startsWith("-") && str.length() == 6 && str.compareTo(Short.toString(Short.MIN_VALUE)) > 0)
			return false;

		else if (str.length() == 5 && str.compareTo(Short.toString(Short.MAX_VALUE)) > 0)
			return false;

		return true;
	}

	/**
	 * Percorre uma array de strings verificando a string pode ser convertida para curto.
	 * @param array vetor que será verificado se pode ser convertido em curto.
	 * @return true se todas as string podem ser convertidas ou false caso uma ou mais não consiga.
	 */

	public static boolean isShort(String[] array)
	{
		for (String string : array)
			if (!isShort(string))
				return false;

		return true;
	}

	/**
	 * Faz toda uma verificação para garantir que uma determina seja convertida para um número curto.
	 * Irá considerar valores hexadecimais apenas se forem seguidos do pré-fixo 0x que irá determinar tal.
	 * @param value string contendo o valor numérico ou hexadecimal do qual deverá ser convertido.
	 * @return valor numérico contido na string passada se assim for um.
	 * @throws UtilException apenas se houver falha na conversão.
	 */

	public static short parseString(String value) throws UtilException
	{
		if (value.startsWith("0x"))
			return HexUtil.parseShort(value);

		StringUtil.isParseNumber(value);

		boolean negative = false;

		if (value.startsWith("-"))
			negative = true;

		if (negative)
		{
			if (value.length() > Short.toString(Short.MIN_VALUE).length())
				throw new UtilException("valor muito pequeno");
		}

		else if (value.length() > Short.toString(Short.MAX_VALUE).length())
			throw new UtilException("valor muito grande");

		short parse = 0;

		for (int i = negative ? 1 : 0; i < value.length(); i++)
		{
			char c = value.charAt(i);

			if (!Character.isDigit(c))
				throw new UtilException("valor não numérico");

			parse = (short) ((parse * 10) + Character.digit(c, 10));
		}

		return parse;
	}

	/**
	 * Converte uma determinada string para um valor do tipo numérico curto.
	 * @param str string do qual deve ser convertida para um número curto.
	 * @return valor numérico da string passada ou 0 caso não seja numérico.
	 */

	public static short parse(String str)
	{
		return parse(str, (short) 0);
	}

	/**
	 * Converte uma determinada string para um valor do tipo numérico curto.
	 * @param str string do qual deve ser convertida para um número curto.
	 * @param fail valor que será retornado caso a string não seja numérica.
	 * @return valor numérico da string se for válida ou <b>fail</b> caso contrário.
	 */

	public static short parse(String str, short fail)
	{
		if (!isShort(str))
			return fail;

		return Short.parseShort(str);
	}

	/**
	 * Um byte possui um valor de até 8 bits que equivalem a um valor binário
	 * entre 0 e 255. Esse método permite obter esse valor binário (decimal).
	 * @param b byte que será convertido para um valor numérico curto.
	 * @return valor numérico curto de 0 a 255 do byte respectivo.
	 */

	public static short parseByte(byte b)
	{
		short value = (short) b;

		return (short) (value >= 0 ? value : 256 + value);
	}

	/**
	 * Analisa um vetor de bytes e tenta criar um número curto a partir destes bytes.
	 * Quando o vetor de bytes for nulo, branco ou mais de 4 índices retorna 0 (inválido).
	 * @param bytes vetor contendo os bytes que deve criar o número curto.
	 * @return número curto criado a partir dos bytes passado.
	 */

	public static short parseBytes(byte[] bytes)
	{
		short value = 0;

		int count = IntUtil.minor(bytes.length, Short.BYTES);

		for (int i = 0, j = 0; i < count; i++, j += 8)
		{
			int parsed = (bytes[bytes.length - i - 1] & 255) << j;
			value |= parsed;
		}

		return value;
	}

	/**
	 * Analisa um vetor de bytes e tenta criar um número curto a partir destes bytes.
	 * Quando o vetor de bytes for nulo, branco ou mais de 4 índices retorna 0 (inválido).
	 * Esse método também irá inverter a ordem de como os bytes devem ser posicionados.
	 * @param bytes vetor contendo os bytes que deve criar o número curto.
	 * @return número curto criado a partir dos bytes passado.
	 */

	public static short parseBytesInvert(byte[] bytes)
	{
		short value = 0;

		int count = IntUtil.minor(bytes.length, Short.BYTES);

		for (int i = 0, j = 0; i < count; i++, j += 8)
		{
			int parsed = (bytes[i] & 255) << j;
			value |= parsed;
		}

		return value;
	}

	/**
	 * Converte uma determinada string em valor hexadecimal em número curto.
	 * Caso o valor não seja um hexadecimal será retornado 0 por padrão da conversão.
	 * @param str valor hexadecimal salvo em uma string (com ou sem 0x no inicio).
	 * @return valor numérico curto obtido da conversão do valor em hexadecimal.
	 */

	public static short parseHex(String str)
	{
		if (!HexUtil.isHexShort(str))
			return 0;

		return Short.parseShort(HexUtil.clearHex(str), 16);
	}

	/**
	 * Converte um determinado vetor de strings para valores numéricos
	 * do tipo short. O método é feito percorrendo o vetor e passando
	 * as strings para o método parse(array[i]) de modo que utiliza as
	 * regras definidas na documentação do mesmo.
	 * @param array vetor de strings que serão convertidas para short.
	 * @return vetor com números do tipo short.
	 */

	public static short[] parseArray(String[] array)
	{
		short parse[] = new short[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = parse(array[i]);

		return parse;
	}

	/**
	 * Obtém o código ascii de todos os caracteres do vetor através
	 * de um simples cast de char para short. Os valores são salvos
	 * em um novo vetor do tipo short armazenando respectivamente.
	 * @param array vetor dos caracteres que serão convertidos.
	 * @return vetor com código ascii salvos em um vetor short.
	 */

	public static short[] parseArrayChar(char[] array)
	{
		short parse[] = new short[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = (short) array[i];

		return parse;
	}

	public static Short[] parseArrayByte(short[] array)
	{
		Short[] parsed = new Short[array.length];

		for (int i = 0; i < parsed.length; i++)
			parsed[i] = array[i];

		return parsed;
	}

	/**
	 * Constrói um novo vetor que irá armazenar valores primitivos.
	 * @param array vetor de objetos do tipo short a ser usado.
	 * @return vetor de valores primitivos do tipo short criado.
	 */

	public static short[] parseArrayShort(Short[] array)
	{
		short parse[] = new short[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = array[i] == null ? 0 : array[i];

		return parse;
	}

	/**
	 * Constrói um novo vetor que irá armazenar objetos do tipo short.
	 * @param array vetor de valores primitivos do tipo short.
	 * @return vetor de objetos do tipo short que foi criado.
	 */

	public static Short[] parseArrayInt(short[] array)
	{
		Short parse[] = new Short[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = array[i];

		return parse;
	}

	/**
	 * Copia uma parte de um determinado vetor de números short. O modo de cópia
	 * é feito por um ponto inicial <b>offset</b> (índice). A partir deste
	 * ponto ele irá obter os próximos n índices e inseri-los no vetor.
	 * @param array vetor que será copiado uma parte.
	 * @param offset ponto inicial da cópia (índice).
	 * @param length quantos elementos serão copiados.
	 * @return vetor apenas com os elementos copiados.
	 */

	public static short[] subarray(short[] array, int offset, int length)
	{
		if (offset > array.length - 1 || offset + length > array.length)
			return new short[]{};

		short select[] = new short[length];

		for (int i = 0; i < length; i++)
			select[i] = array[offset + i];

		return select;
	}

	/**
	 * Obtém o valor percentual curto entre dois números.
	 * Apesar de serem double, podem ser inseridos de quaisquer
	 * tipos. Onde um se insere o valor que seria igual a 100%
	 * e o outro o valor que deseja saber a porcentagem.
	 * @param complete valor que deseja obter a porcentagem.
	 * @param total valor que representa 100% da quantidade.
	 * @return percentual curto dos valores acima.
	 */

	public static short toPorcent(double complete, double total)
	{
		return (short) ((double) (complete / total) * 100);
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

	public static short limit(short value, short min, short max)
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

	public static short min(short value, short min)
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

	public static short max(short value, short max)
	{
		return value > max ? max : value;
	}

	/**
	 * Verifica se um determinado valor está ou não dentro do limite estabelecido.
	 * @param value valor do qual deve ser verificado se está no limite.
	 * @param min limite mínimo do intervalo para ser válido.
	 * @param max limite máximo do intervalo para ser válido.
	 * @return true se estiver dentro do limite ou false caso contrário.
	 */

	public static boolean interval(short value, short min, short max)
	{
		return value >= min && value <= max;
	}

	/**
	 * Permite obter o valor de um objeto do tipo Short ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @return valor real do objeto ou 0 se for nulo.
	 */

	public static short valueOf(Short value)
	{
		return valueOf(value, (short) 0);
	}

	/**
	 * Permite obter o valor de um objeto do tipo Short ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @param valueNull valor que será retornado cao seja nulo.
	 * @return valor real do objeto ou valueNull se for nulo.
	 */

	public static short valueOf(Short value, short valueNull)
	{
		return value == null ? valueNull : value;
	}

	/**
	 * Faz a conversão do valor absoluto de um número int para um número short.
	 * @param value valor numérico de 4 bytes que será convertido em short.
	 * @return valor máximo de um short se value excedê-lo, valor mínimo de um
	 * short se value excedê-lo ou o seu valor absoluto convertido para short.
	 */

	public static short cast(int value)
	{
		return value > Short.MAX_VALUE ? Short.MAX_VALUE : value < Short.MIN_VALUE ? Short.MIN_VALUE : (short) value;
	}

	/**
	 * Faz a conversão do valor absoluto de um número long para um número short.
	 * @param value valor numérico de 8 bytes que será convertido em short.
	 * @return valor máximo de um short se value excedê-lo, valor mínimo de um
	 * short se value excedê-lo ou o seu valor absoluto convertido para short.
	 */

	public static short cast(long value)
	{
		return value > Short.MAX_VALUE ? Short.MAX_VALUE : value < Short.MIN_VALUE ? Short.MIN_VALUE : (short) value;
	}
}
