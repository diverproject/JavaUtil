package org.diverproject.util.lang;

import java.nio.ByteBuffer;

import org.diverproject.util.UtilException;

/**
 * <p><h1>Utilitário para Int</h1></p>
 *
 * Classe com métodos utilitários para manusear valores do tipo int.
 * Todos os métodos envolvem retornar atributos do tipo <b>int</b>.
 * Em alguns casos há métodos que retornam objetos do tipo <b>Integer</b>.
 * Apesar de ambos serem do mesmo tipo, algumas operações necessitam
 * utilizar o objeto ao invés do atributo, e vice-versa.
 */

public class IntUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private IntUtil()
	{
		
	}

	/**
	 * Exibe um determinado vetor de inteiros no console separado por índice.
	 * Por padrão esse método irá exibir apenas 20 elementos por linha.
	 * @param array vetor com os números do tipo long que serão exibidos no console.
	 */

	public static void print(int[] array)
	{
		print((short) 20, array);
	}

	/**
	 * Exibe um determinado vetor de inteiros no console em linhas.
	 * @param perline quantos elementos serão exibidos por linha.
	 * @param array vetor com os caracteres que serão impressos.
	 */

	public static void print(short perline, int[] array)
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
	 * Verifica se uma determinada string pode ser convertida para um número inteiro.
	 * Valida se a string é composta apenas por números seja positivo ou negativo, e
	 * por fim verifica se está dentro dos limites permitidos para um número inteiro.
	 * @param str string do qual deve ser verificada se pode converter.
	 * @return true se for possível converter para um número inteiro.
	 */

	public static boolean isInteger(String str)
	{
		if (!StringUtil.isNumber(str))
			return false;

		if (str.length() > (str.startsWith("-") ? 11 : 10))
			return false;

		if (str.startsWith("-") && str.length() == 11 && str.compareTo(Integer.toString(Integer.MIN_VALUE)) > 0)
			return false;

		else if (str.length() == 10 && str.compareTo(Integer.toString(Integer.MAX_VALUE)) > 0)
			return false;

		return true;
	}

	/**
	 * Percorre uma array de strings verificando a string pode ser convertida para inteiro.
	 * @param array vetor que será verificado se pode ser convertido em inteiro.
	 * @return true se todas as string podem ser convertidas ou false caso uma ou mais não consiga.
	 */

	public static boolean isInteger(String[] array)
	{
		for (String string : array)
			if (!isInteger(string))
				return false;

		return true;
	}

	/**
	 * Faz toda uma verificação para garantir que uma determina seja convertida para um número inteiro.
	 * Irá considerar valores hexadecimais apenas se forem seguidos do pré-fixo 0x que irá determinar tal.
	 * @param value string contendo o valor numérico ou hexadecimal do qual deverá ser convertido.
	 * @return valor numérico contido na string passada se assim for um.
	 * @throws UtilException apenas se houver falha na conversão.
	 */

	public static int parseString(String value) throws UtilException
	{
		if (value.startsWith("0x"))
			return HexUtil.parseInt(value);

		StringUtil.isParseNumber(value);

		boolean negative = false;

		if (value.startsWith("-"))
			negative = true;

		if (negative)
		{
			if (value.length() > Integer.toString(Integer.MIN_VALUE).length())
				throw new UtilException("valor muito pequeno");
		}

		else if (value.length() > Integer.toString(Integer.MAX_VALUE).length())
			throw new UtilException("valor muito grande");

		int parse = 0;

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
	 * Converte uma determinada string para um valor do tipo numérico inteiro.
	 * @param str string do qual deve ser convertida para um número inteiro.
	 * @return valor numérico da string passada ou 0 caso não seja numérico.
	 */

	public static int parse(String str)
	{
		return parse(str, 0);
	}

	/**
	 * Converte uma determinada string para um valor do tipo numérico inteiro.
	 * @param str string do qual deve ser convertida para um número inteiro.
	 * @param fail valor que será retornado caso a string não seja numérica.
	 * @return valor numérico da string se for válida ou <b>fail</b> caso contrário.
	 */

	public static int parse(String str, int fail)
	{
		if (!isInteger(str))
			return fail;

		return Integer.parseInt(str);
	}

	/**
	 * Um byte possui um valor de até 8 bits que equivalem a um valor binário
	 * entre 0 e 255. Esse método permite obter esse valor binário (decimal).
	 * @param b byte que será convertido para um valor numérico inteiro.
	 * @return valor numérico inteiro de 0 a 255 do byte respectivo.
	 */

	public static int parseByte(byte b)
	{
		int value = (int) b;

		return value >= 0 ? value : value + 256;
	}

	/**
	 * Analisa um vetor de bytes e tenta criar um número inteiro a partir destes bytes.
	 * Quando o vetor de bytes for nulo, branco ou mais de 4 índices retorna 0 (inválido).
	 * @param bytes vetor contendo os bytes que deve criar o número inteiro.
	 * @return número inteiro criado a partir dos bytes passado.
	 */

	public static int parseBytes(byte[] bytes)
	{
		int value = 0;

		int count = IntUtil.minor(bytes.length, Integer.BYTES);

		for (int i = 0, j = 0; i < count; i++, j += 8)
		{
			int parsed = (bytes[bytes.length - i - 1] & 255) << j;
			value |= parsed;
		}

		return value;
	}

	/**
	 * Analisa um vetor de bytes e tenta criar um número inteiro a partir destes bytes.
	 * Quando o vetor de bytes for nulo, branco ou mais de 4 índices retorna 0 (inválido).
	 * Esse método também irá inverter a ordem de como os bytes devem ser posicionados.
	 * @param bytes vetor contendo os bytes que deve criar o número inteiro.
	 * @return número inteiro criado a partir dos bytes passado.
	 */

	public static int parseBytesInvert(byte[] bytes)
	{
		int value = 0;

		int count = IntUtil.minor(bytes.length, Long.BYTES);

		for (int i = 0, j = 0; i < count; i++, j += 8)
		{
			int parsed = (bytes[i] & 255) << j;
			value |= parsed;
		}

		return value;
	}

	/**
	 * Converte uma determinada string em valor hexadecimal em número inteiro.
	 * Caso o valor não seja um hexadecimal será retornado 0 por padrão da conversão.
	 * @param str valor hexadecimal salvo em uma string (com ou sem 0x no inicio).
	 * @return valor numérico inteiro obtido da conversão do valor em hexadecimal.
	 */

	public static int parseHex(String str)
	{
		if (!HexUtil.isHexInt(str))
			return 0;

		return Integer.parseInt(HexUtil.clearHex(str), 16);
	}

	/**
	 * Converte um determinado vetor de strings para valores numéricos
	 * do tipo int. O método é feito percorrendo o vetor e passando
	 * as strings para o método parse(array[i]) de modo que utiliza as
	 * regras definidas na documentação do mesmo.
	 * @param array vetor de strings que serão convertidas para int.
	 * @return vetor com números do tipo int.
	 */

	public static int[] parseArray(String[] array)
	{
		int parse[] = new int[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = parse(array[i]);

		return parse;
	}

	/**
	 * Obtém o código ascii de todos os caracteres do vetor através
	 * de um simples cast de char para int. Os valores são salvos
	 * em um novo vetor do tipo int armazenando respectivamente.
	 * @param array vetor dos caracteres que serão convertidos.
	 * @return vetor com código ascii salvos em um vetor int.
	 */

	public static int[] parseArrayChar(char[] array)
	{
		int parse[] = new int[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = (int) array[i];

		return parse;
	}

	/**
	 * Constrói um novo vetor que irá armazenar valores primitivos.
	 * @param array vetor de objetos do tipo int a ser usado.
	 * @return vetor de valores primitivos do tipo int criado.
	 */

	public static int[] parseArrayInteger(Integer[] array)
	{
		int parse[] = new int[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = array[i] == null ? 0 : array[i];

		return parse;
	}

	/**
	 * Constrói um novo vetor que irá armazenar objetos do tipo int.
	 * @param array vetor de valores primitivos do tipo int.
	 * @return vetor de objetos do tipo int que foi criado.
	 */

	public static Integer[] parseArrayInteger(int[] array)
	{
		Integer parse[] = new Integer[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = array[i];

		return parse;
	}

	/**
	 * Copia uma parte de um determinado vetor de números int. O modo de cópia
	 * é feito por um ponto inicial <b>offset</b> (índice). A partir deste
	 * ponto ele irá obter os próximos n índices e inseri-los no vetor.
	 * @param array vetor que será copiado uma parte.
	 * @param offset ponto inicial da cópia (índice).
	 * @param length quantos elementos serão copiados.
	 * @return vetor apenas com os elementos copiados.
	 */

	public static int[] subarray(int[] array, int offset, int length)
	{
		if (offset > array.length - 1 || offset + length > array.length)
			return new int[]{};

		int select[] = new int[length];

		for (int i = 0; i < length; i++)
			select[i] = array[offset + i];

		return select;
	}

	/**
	 * Inverte a ordem da posição dos números inteiros de um vetor de int.
	 * @param array vetor de números inteiros que terá os valores invertidos.
	 * @return vetor com a ordem dos números inteiros invertidos.
	 */

	public static int[] invert(int[] array)
	{
		int[] tbuff = new int[array.length];

		for (int i = 0; i < array.length; i++)
			tbuff[i] = array[array.length - i - 1];

		return tbuff;
	}

	/**
	 * Inverte a ordem dos valores dos bytes que criam este valor int.
	 * @param value número inteiro que terá os bytes invertidos.
	 * @return número inteiro com os bytes invertidos.
	 */

	public static int invert(int value)
	{
		return ByteBuffer.wrap(ByteUtil.invertArray(ByteUtil.parseInt(value))).getInt();
	}

	/**
	 * Obtém o valor percentual inteiro entre dois números.
	 * Apesar de serem double, podem ser inseridos de quaisquer
	 * tipos. Onde um se insere o valor que seria igual a 100%
	 * e o outro o valor que deseja saber a porcentagem.
	 * @param complete valor que deseja obter a porcentagem.
	 * @param total valor que representa 100% da quantidade.
	 * @return percentual inteiro dos valores acima.
	 */

	public static int toPorcent(double complete, double total)
	{
		return (int) ((double) (complete / total) * 100);
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

	public static int limit(int value, int min, int max)
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

	public static int min(int value, int min)
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

	public static int max(int value, int max)
	{
		return value > max ? max : value;
	}

	/**
	 * Faz a divisão de dois números inteiros, em seguida verifica se o resultado
	 * será o número arredondado para cima ou se for exato adiciona mais um.
	 * @param val valor do qual deve ser dividido.
	 * @param coe coeficiente da divisão.
	 * @return resultado de acordo com as especificações.
	 */

	public static int div(int val, int coe)
	{
		return val/coe + (val % coe > 0 ? 1 : 0);
	}

	/**
	 * Faz uma verificação para retornar o maior número.
	 * @param a primeiro valor inteiro.
	 * @param b segundo valor inteiro.
	 * @return maior valor inteiro.
	 */

	public static int major(int a, int b)
	{
		return a > b ? a : b;
	}

	/**
	 * Faz a comparação entre dois números inteiros a fim de obter um resultado.
	 * @param a primeiro número, quem será comparado com quem.
	 * @param b segundo número, usado para fazer a comparação.
	 * @return (1) se for maior, (-1) se for menor ou (0) se for igual,
	 * a comparação é feita entre o valor A com B e não B com A.
	 */

	public static int compare(int a, int b)
	{
		if (a > b)
			return 1;

		else if (a < b)
			return -1;

		return 0;
	}

	/**
	 * Faz uma verificação para retornar o menor número.
	 * @param a primeiro valor inteiro.
	 * @param b segundo valor inteiro.
	 * @return menor valor inteiro.
	 */

	public static int minor(int a, int b)
	{
		return a < b ? a : b;
	}

	/**
	 * Verifica se um determinado valor está ou não dentro do limite estabelecido.
	 * @param value valor do qual deve ser verificado se está no limite.
	 * @param min limite mínimo do intervalo para ser válido.
	 * @param max limite máximo do intervalo para ser válido.
	 * @return true se estiver dentro do limite ou false caso contrário.
	 */

	public static boolean interval(int value, int min, int max)
	{
		return value >= min && value <= max;
	}

	/**
	 * Permite obter o valor de um objeto do tipo Integer ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @return valor real do objeto ou 0 se for nulo.
	 */

	public static int valueOf(Integer value)
	{
		return valueOf(value, 0);
	}

	/**
	 * Permite obter o valor de um objeto do tipo Integer ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @param valueNull valor que será retornado cao seja nulo.
	 * @return valor real do objeto ou valueNull se for nulo.
	 */

	public static int valueOf(Integer value, int valueNull)
	{
		return value == null ? valueNull : value;
	}

	/**
	 * Permite obter o valor da diferença entre dois números quaisquer.
	 * A diferença sempre irá considerar um valor positivo independente da ordem.
	 * @param a primeiro valor numérico inteiro para realizar a comparação.
	 * @param b segundo valor numérico inteiro para realizar a comparação.
	 * @return valor numérico inteiro da diferença entre os dois valores.
	 */

	public static int diff(int a, int b)
	{
		if (a > b)
			return a - b;

		return b - a;
	}

	/**
	 * Faz a conversão do valor absoluto de um número long para um número int.
	 * @param value valor numérico de 8 bytes que será convertido em int.
	 * @return valor máximo de um int se value excedê-lo, valor mínimo de um
	 * int se value excedê-lo ou o seu valor absoluto convertido para int.
	 */

	public static int cast(long value)
	{
		return value > Integer.MAX_VALUE ? Integer.MAX_VALUE : value < Integer.MIN_VALUE ? Integer.MIN_VALUE : (int) value;
	}
}
