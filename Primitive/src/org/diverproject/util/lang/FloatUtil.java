package org.diverproject.util.lang;

import java.nio.ByteBuffer;

import org.diverproject.util.UtilException;
import org.diverproject.util.collection.StringExtend;

/**
 * <p><h1>Utilitário para Float</h1></p>
 *
 * Classe com métodos utilitários para manusear valores do tipo float.
 * Todos os métodos envolvem retornar atributos do tipo <b>float</b>.
 * Em alguns casos há métodos que retornam objetos do tipo <b>float</b>.
 * Apesar de ambos serem do mesmo tipo, algumas operações necessitam
 * utilizar o objeto ao invés do atributo, e vice-versa.
 */

public class FloatUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private FloatUtil()
	{
		
	}

	/**
	 * Exibe um determinado vetor de floats no console separado por índice.
	 * Por padrão esse método irá exibir apenas 20 elementos por linha.
	 * @param array vetor com os números do tipo float que serão exibidos no console.
	 */

	public static void print(float[] array)
	{
		print((float) 20, array);
	}

	/**
	 * Exibe um determinado vetor de floats no console em linhas.
	 * @param perline quantos elementos serão exibidos por linha.
	 * @param array vetor com os caracteres que serão impressos.
	 */

	public static void print(float perline, float[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (i % perline == 0)
				System.out.printf("\ni%4d: ", i + 1);

			System.out.printf("[%.5f]", array[i]);
		}

		System.out.println();
	}

	/**
	 * Verifica se uma determinada string pode ser convertida para um número float.
	 * Valida se a string é composta apenas por números seja positivo ou negativo, e
	 * por fim verifica se está dentro dos limites permitidos para um número float.
	 * @param str string do qual deve ser verificada se pode converter.
	 * @return true se for possível converter para um número float.
	 */

	public static boolean isFloat(String str)
	{
		if (!StringUtil.isDecimal(str))
			return false;

		if (str.length() > (str.startsWith("-") ? 6 : 5))
			return false;

		if (str.startsWith("-") && str.length() == 6 && str.compareTo(Float.toString(Float.MIN_VALUE)) > 0)
			return false;

		else if (str.length() == 5 && str.compareTo(Float.toString(Float.MAX_VALUE)) > 0)
			return false;

		return true;
	}

	/**
	 * Percorre uma array de strings verificando a string pode ser convertida para float.
	 * @param array vetor que será verificado se pode ser convertido em float.
	 * @return true se todas as string podem ser convertidas ou false caso uma ou mais não consiga.
	 */

	public static boolean isfloat(String[] array)
	{
		for (String string : array)
			if (!isFloat(string))
				return false;

		return true;
	}

	/**
	 * Método que deve fazer a conversão de uma string para valor float.
	 * Nesse caso há a existência uma exception e deve ser tratada.
	 * @param value string contendo o valor numérico a ser convertido.
	 * @return valor convertido a partir da string passada.
	 * @throws UtilException string nula, em branco, muito grande.
	 */

	public static float parseString(String str) throws UtilException
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

		float value = 0.0f;
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
	 * Converte uma determinada string para um valor do tipo numérico float.
	 * @param str string do qual deve ser convertida para um número float.
	 * @return valor numérico da string passada ou 0 caso não seja numérico.
	 */

	public static float parseByte(String str)
	{
		return parse(str, (float) 0);
	}

	/**
	 * Converte uma determinada string para um valor do tipo numérico float.
	 * @param str string do qual deve ser convertida para um número float.
	 * @param fail valor que será retornado caso a string não seja numérica.
	 * @return valor numérico da string se for válida ou <b>fail</b> caso contrário.
	 */

	public static float parse(String str)
	{
		return parse(str, 0.0f);
	}

	/**
	 * Converte uma determinada string para um valor do tipo numérico float.
	 * @param str string do qual deve ser convertida para um número float.
	 * @param fail valor que será retornado caso a string não seja numérica.
	 * @return valor numérico da string se for válida ou <b>fail</b> caso contrário.
	 */

	public static float parse(String str, float fail)
	{
		try {
			return Float.parseFloat(str);
		} catch (NumberFormatException e) {
			return fail;
		}
	}

	/**
	 * Um byte possui um valor de até 8 bits que equivalem a um valor binário
	 * entre 0 e 255. Esse método permite obter esse valor binário (decimal).
	 * @param b byte que será convertido para um valor numérico float.
	 * @return valor numérico float de 0 a 255 do byte respectivo.
	 */

	public static float parseByte(byte b)
	{
		float value = (float) b;

		return (float) (value >= 0 ? value : 256 + value);
	}

	/**
	 * Analisa um vetor de bytes e tenta criar um número float a partir destes bytes.
	 * Quando o vetor de bytes for nulo, branco ou mais de 4 índices retorna 0 (inválido).
	 * @param bytes vetor contendo os bytes que deve criar o número float.
	 * @return número float criado a partir dos bytes passado.
	 */

	public static float parseBytes(byte[] bytes)
	{
		if (bytes == null || bytes.length == 0 || bytes.length > 4)
			return 0;

		return ByteBuffer.wrap(bytes).getFloat();
	}

	/**
	 * Analisa um vetor de bytes e tenta criar um número float a partir destes bytes.
	 * Quando o vetor de bytes for nulo, branco ou mais de 4 índices retorna 0 (inválido).
	 * Esse método também irá inverter a ordem de como os bytes devem ser posicionados.
	 * @param bytes vetor contendo os bytes que deve criar o número float.
	 * @return número float criado a partir dos bytes passado.
	 */

	public static float parseBytesInvert(byte[] bytes)
	{
		if (bytes == null || bytes.length == 0 || bytes.length > 4)
			return 0;

		return ByteBuffer.wrap(ByteUtil.invertArray(bytes)).getFloat();
	}

	/**
	 * Converte um determinado vetor de strings para valores numéricos
	 * do tipo float. O método é feito percorrendo o vetor e passando
	 * as strings para o método parse(array[i]) de modo que utiliza as
	 * regras definidas na documentação do mesmo.
	 * @param array vetor de strings que serão convertidas para float.
	 * @return vetor com números do tipo float.
	 */

	public static float[] parseArray(String[] array)
	{
		float parse[] = new float[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = parseByte(array[i]);

		return parse;
	}

	/**
	 * Obtém o código ascii de todos os caracteres do vetor através
	 * de um simples cast de char para float. Os valores são salvos
	 * em um novo vetor do tipo float armazenando respectivamente.
	 * @param array vetor dos caracteres que serão convertidos.
	 * @return vetor com código ascii salvos em um vetor float.
	 */

	public static float[] parseArrayChar(char[] array)
	{
		float parse[] = new float[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = (float) array[i];

		return parse;
	}

	/**
	 * Constrói um novo vetor que irá armazenar valores primitivos.
	 * @param array vetor de objetos do tipo float a ser usado.
	 * @return vetor de valores primitivos do tipo float criado.
	 */

	public static float[] parseArrayFloat(Float[] array)
	{
		float[] parsed = new float[array.length];

		for (int i = 0; i < parsed.length; i++)
			parsed[i] = array[i] == null ? 0 : array[i];

		return parsed;
	}

	/**
	 * Constrói um novo vetor que irá armazenar objetos do tipo float.
	 * @param array vetor de valores primitivos do tipo float.
	 * @return vetor de objetos do tipo float que foi criado.
	 */

	public static Float[] parseArrayFloat(float[] array)
	{
		Float parse[] = new Float[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = array[i];

		return parse;
	}

	/**
	 * Obtém objetos float a partir dos valores numéricos float.
	 * @param array vetor com os valores numéricos do tipo float.
	 * @return vetor com os objetos float dos números float.
	 */

	public static float[] parseArrayInt(float[] array)
	{
		float parse[] = new float[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = array[i];

		return parse;
	}

	/**
	 * Copia uma parte de um determinado vetor de números float. O modo de cópia
	 * é feito por um ponto inicial <b>offset</b> (índice). A partir deste
	 * ponto ele irá obter os próximos n índices e inseri-los no vetor.
	 * @param array vetor que será copiado uma parte.
	 * @param offset ponto inicial da cópia (índice).
	 * @param length quantos elementos serão copiados.
	 * @return vetor apenas com os elementos copiados.
	 */

	public static float[] subarray(float[] array, int offset, int length)
	{
		if (offset > array.length - 1 || offset + length > array.length)
			return new float[]{};

		float select[] = new float[length];

		for (int i = 0; i < length; i++)
			select[i] = array[offset + i];

		return select;
	}

	/**
	 * Obtém o valor percentual float entre dois números.
	 * Apesar de serem double, podem ser inseridos de quaisquer
	 * tipos. Onde um se insere o valor que seria igual a 100%
	 * e o outro o valor que deseja saber a porcentagem.
	 * @param complete valor que deseja obter a porcentagem.
	 * @param total valor que representa 100% da quantidade.
	 * @return percentual float dos valores acima.
	 */

	public static float toPorcent(double complete, double total)
	{
		return (float) ((double) (complete / total) * 100);
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

	public static float limit(float value, float min, float max)
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

	public static float min(float value, float min)
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

	public static float max(float value, float max)
	{
		return value > max ? max : value;
	}

	/**
	 * Faz uma verificação para retornar o maior número.
	 * @param a primeiro valor flutuante.
	 * @param b segundo valor flutuante.
	 * @return maior valor flutuante.
	 */

	public static float major(float a, float b)
	{
		return a > b ? a : b;
	}

	/**
	 * Faz a comparação entre dois números flutuantes a fim de obter um resultado.
	 * @param a primeiro número, quem será comparado com quem.
	 * @param b segundo número, usado para fazer a comparação.
	 * @return (1) se for maior, (-1) se for menor ou (0) se for igual,
	 * a comparação é feita entre o valor A com B e não B com A.
	 */

	public static float compare(float a, float b)
	{
		if (a > b)
			return 1;

		else if (a < b)
			return -1;

		return 0;
	}

	/**
	 * Faz uma verificação para retornar o menor número.
	 * @param a primeiro valor flutuante.
	 * @param b segundo valor flutuante.
	 * @return menor valor flutuante.
	 */

	public static float minor(float a, float b)
	{
		return a < b ? a : b;
	}

	/**
	 * Permite obter o valor de um objeto do tipo Float ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @return valor real do objeto ou 0 se for nulo.
	 */

	public static float valueOf(Float value)
	{
		return valueOf(value, 0L);
	}

	/**
	 * Permite obter o valor de um objeto do tipo Float ainda que nulo.
	 * @param value referência do objeto que será obtido seu valor.
	 * @param valueNull valor que será retornado cao seja nulo.
	 * @return valor real do objeto ou valueNull se for nulo.
	 */

	public static float valueOf(Float value, float valueNull)
	{
		return value == null ? valueNull : value;
	}

	/**
	 * Verifica se um determinado valor está ou não dentro do limite estabelecido.
	 * @param value valor do qual deve ser verificado se está no limite.
	 * @param min limite mínimo do intervalo para ser válido.
	 * @param max limite máximo do intervalo para ser válido.
	 * @return true se estiver dentro do limite ou false caso contrário.
	 */

	public static boolean interval(float value, float min, float max)
	{
		return value >= min && value <= max;
	}
}
