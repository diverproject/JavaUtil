package org.diverproject.util.lang;

import java.nio.ByteBuffer;

import org.diverproject.util.UtilException;
import org.diverproject.util.collection.StringExtend;

/**
 * <p><h1>Utilitário para Double</h1></p>
 *
 * Classe com métodos utilitários para manusear valores do tipo double.
 * Todos os métodos envolvem retornar atributos do tipo <b>double</b>.
 * Em alguns casos há métodos que retornam objetos do tipo <b>double</b>.
 * Apesar de ambos serem do mesmo tipo, algumas operações necessitam
 * utilizar o objeto ao invés do atributo, e vice-versa.
 */

public class DoubleUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private DoubleUtil()
	{
		
	}

	/**
	 * Exibe um determinado vetor de doubles no console separado por índice.
	 * Por padrão esse método irá exibir apenas 20 elementos por linha.
	 * @param array vetor com os números do tipo double que serão exibidos no console.
	 */

	public static void print(double[] array)
	{
		print((double) 20, array);
	}

	/**
	 * Exibe um determinado vetor de doubles no console em linhas.
	 * @param perline quantos elementos serão exibidos por linha.
	 * @param array vetor com os caracteres que serão impressos.
	 */

	public static void print(double perline, double[] array)
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
	 * Verifica se uma determinada string pode ser convertida para um número double.
	 * Valida se a string é composta apenas por números seja positivo ou negativo, e
	 * por fim verifica se está dentro dos limites permitidos para um número double.
	 * @param str string do qual deve ser verificada se pode converter.
	 * @return true se for possível converter para um número double.
	 */

	public static boolean isdouble(String str)
	{
		if (!StringUtil.isNumber(str))
			return false;

		if (str.length() > (str.startsWith("-") ? 6 : 5))
			return false;

		if (str.startsWith("-") && str.length() == 6 && str.compareTo(Double.toString(Double.MIN_VALUE)) > 0)
			return false;

		else if (str.length() == 5 && str.compareTo(Double.toString(Double.MAX_VALUE)) > 0)
			return false;

		return true;
	}

	/**
	 * Percorre uma array de strings verificando a string pode ser convertida para double.
	 * @param array vetor que será verificado se pode ser convertido em double.
	 * @return true se todas as string podem ser convertidas ou false caso uma ou mais não consiga.
	 */

	public static boolean isdouble(String[] array)
	{
		for (String string : array)
			if (!isdouble(string))
				return false;

		return true;
	}

	/**
	 * Método que deve fazer a conversão de uma string para valor double.
	 * Nesse caso há a existência uma exception e deve ser tratada.
	 * @param value string contendo o valor numérico a ser convertido.
	 * @return valor convertido a partir da string passada.
	 * @throws UtilException string nula, em branco, muito grande.
	 */

	public static double parseString(String str) throws UtilException
	{
		StringUtil.isParseNumber(str);

		boolean negative = false;

		if (str.startsWith("-"))
		{
			str = str.substring(1, str.length());
			negative = true;
		}

		String splited[] = str.split("\\.|\\,");

		if (splited.length == 0)
			throw new UtilException("não é um float");

		if (splited.length > 2)
			throw new UtilException("muitos pontos decimais");

		if (splited[0].length() > 6)
			throw new UtilException("valor muito grande");

		double value = 0.0f;
		StringExtend root = new StringExtend(splited[0]);

		while (root.next())
			value = (value * 10) + Character.digit(root.get(), 10);

		if (splited.length == 2)
		{
			root = new StringExtend(splited[1]);

			for (int i = 1; root.next(); i++)
				value = value + (Character.digit(root.get(), 10) / (float) Math.pow(10, i));
		}

		return negative ? -value : value;
	}

	/**
	 * Converte uma determinada string para um valor do tipo numérico double.
	 * @param str string do qual deve ser convertida para um número double.
	 * @return valor numérico da string passada ou 0 caso não seja numérico.
	 */

	public static double parseByte(String str)
	{
		return parse(str, (double) 0);
	}

	/**
	 * Converte uma determinada string para um valor do tipo numérico double.
	 * @param str string do qual deve ser convertida para um número double.
	 * @param fail valor que será retornado caso a string não seja numérica.
	 * @return valor numérico da string se for válida ou <b>fail</b> caso contrário.
	 */

	public static double parse(String str, double fail)
	{
		if (!isdouble(str))
			return fail;

		return Double.parseDouble(str);
	}

	/**
	 * Um byte possui um valor de até 8 bits que equivalem a um valor binário
	 * entre 0 e 255. Esse método permite obter esse valor binário (decimal).
	 * @param b byte que será convertido para um valor numérico double.
	 * @return valor numérico double de 0 a 255 do byte respectivo.
	 */

	public static double parseByte(byte b)
	{
		double value = (double) b;

		return (double) (value >= 0 ? value : 256 + value);
	}

	/**
	 * Analisa um vetor de bytes e tenta criar um número double a partir destes bytes.
	 * Quando o vetor de bytes for nulo, branco ou mais de 4 índices retorna 0 (inválido).
	 * @param bytes vetor contendo os bytes que deve criar o número double.
	 * @return número double criado a partir dos bytes passado.
	 */

	public static double parseBytes(byte[] bytes)
	{
		if (bytes == null || bytes.length == 0 || bytes.length > 4)
			return 0;

		return ByteBuffer.wrap(bytes).getDouble();
	}

	/**
	 * Analisa um vetor de bytes e tenta criar um número double a partir destes bytes.
	 * Quando o vetor de bytes for nulo, branco ou mais de 4 índices retorna 0 (inválido).
	 * Esse método também irá inverter a ordem de como os bytes devem ser posicionados.
	 * @param bytes vetor contendo os bytes que deve criar o número double.
	 * @return número double criado a partir dos bytes passado.
	 */

	public static double parseBytesInvert(byte[] bytes)
	{
		if (bytes == null || bytes.length == 0 || bytes.length > 4)
			return 0;

		return ByteBuffer.wrap(ByteUtil.invertArray(bytes)).getDouble();
	}

	/**
	 * Converte um determinado vetor de strings para valores numéricos
	 * do tipo double. O método é feito percorrendo o vetor e passando
	 * as strings para o método parse(array[i]) de modo que utiliza as
	 * regras definidas na documentação do mesmo.
	 * @param array vetor de strings que serão convertidas para double.
	 * @return vetor com números do tipo double.
	 */

	public static double[] parseArray(String[] array)
	{
		double parse[] = new double[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = parseByte(array[i]);

		return parse;
	}

	/**
	 * Constrói um novo vetor que irá armazenar valores primitivos.
	 * @param array vetor de objetos do tipo double a ser usado.
	 * @return vetor de valores primitivos do tipo double criado.
	 */

	public static double[] parseArrayChar(char[] array)
	{
		double parse[] = new double[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = (double) array[i];

		return parse;
	}

	/**
	 * Constrói um novo vetor que irá armazenar objetos do tipo double.
	 * @param array vetor de valores primitivos do tipo double.
	 * @return vetor de objetos do tipo double que foi criado.
	 */

	public static Double[] parseArrayDouble(double[] array)
	{
		Double[] parsed = new Double[array.length];

		for (int i = 0; i < parsed.length; i++)
			parsed[i] = array[i];

		return parsed;
	}

	/**
	 * Obtém o valor numérico de objetos do tipo double.
	 * Passa os valores para um vetor numérico do tipo double.
	 * @param array vetor com os objetos do tipo double.
	 * @return vetor double com os valores dos objetos.
	 */

	public static double[] parseArrayDouble(Double[] array)
	{
		double[] parsed = new double[array.length];

		for (int i = 0; i < parsed.length; i++)
			parsed[i] = array[i] == null ? 0 : array[i];

		return parsed;
	}

	/**
	 * Obtém objetos double a partir dos valores numéricos double.
	 * @param array vetor com os valores numéricos do tipo double.
	 * @return vetor com os objetos double dos números double.
	 */

	public static double[] parseArrayInt(double[] array)
	{
		double parse[] = new double[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = array[i];

		return parse;
	}

	/**
	 * Copia uma parte de um determinado vetor de números double. O modo de cópia
	 * é feito por um ponto inicial <b>offset</b> (índice). A partir deste
	 * ponto ele irá obter os próximos n índices e inseri-los no vetor.
	 * @param array vetor que será copiado uma parte.
	 * @param offset ponto inicial da cópia (índice).
	 * @param length quantos elementos serão copiados.
	 * @return vetor apenas com os elementos copiados.
	 */

	public static double[] subarray(double[] array, int offset, int length)
	{
		if (offset > array.length - 1 || offset + length > array.length)
			return new double[]{};

		double select[] = new double[length];

		for (int i = 0; i < length; i++)
			select[i] = array[offset + i];

		return select;
	}

	/**
	 * Obtém o valor percentual double entre dois números.
	 * Apesar de serem double, podem ser inseridos de quaisquer
	 * tipos. Onde um se insere o valor que seria igual a 100%
	 * e o outro o valor que deseja saber a porcentagem.
	 * @param complete valor que deseja obter a porcentagem.
	 * @param total valor que representa 100% da quantidade.
	 * @return percentual double dos valores acima.
	 */

	public static double toPorcent(double complete, double total)
	{
		return (double) ((double) (complete / total) * 100);
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

	public static double limit(double value, double min, double max)
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

	public static double min(double value, double min)
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

	public static double max(double value, double max)
	{
		return value > max ? max : value;
	}

	/**
	 * Permite obter o valor de um objeto do tipo Double ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @return valor real do objeto ou 0 se for nulo.
	 */

	public static double valueOf(Double value)
	{
		return valueOf(value, 0L);
	}

	/**
	 * Permite obter o valor de um objeto do tipo Double ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @param valueNull valor que será retornado cao seja nulo.
	 * @return valor real do objeto ou valueNull se for nulo.
	 */

	public static double valueOf(Double value, double valueNull)
	{
		return value == null ? valueNull : value;
	}
}
