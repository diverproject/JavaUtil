package org.diverproject.util.lang;

/**
 * <p><h1>Utilitário para Binários</h1></p>
 *
 * Os utilitários em UBinary permitem trabalhar com valores do tipo binário.
 * A linguagem java não possui atributos do tipo binário, eles são salvos em strings,
 * assim sendo os valores binários quando utilizados por determinados métodos,
 * irão verificar a consistência dos dados validando-os. É possível transformar
 * diversos outros tipos de variáveis em números binários como int, long e char.
 */

public class BinaryUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private BinaryUtil()
	{
		
	}

	/**
	 * Verifica se uma determinada string é de fato um numérico binário.
	 * Números binários irão conter somente os números 0 e/ou 1, quaisquer
	 * outros valores sejam letras ou números, não são aceitos por este tipo.
	 * @param binary string contendo o valor binário que será verificado.
	 * @return true se for válido ou false caso contrário.
	 */

	public static boolean isBinary(String binary)
	{
		if (binary == null || binary.length() == 0)
			return false;

		for (char c : binary.toCharArray())
			if (!(c == '0' || c == '1'))
				return false;

		return true;
	}

	/**
	 * Este método serve apenas para visão dos dados binários, em alguns casos os dados
	 * binários vêem em um número exato de casas binárias, sendo preenchidos com vários
	 * zeros no inicio da string. Com este método qualquer zero no inicio da string será
	 * removido (apenas aqueles que não alterem o verdadeiro valor do número binário).
	 * @param binary valor binário do qual será removido os zeros desnecessários.
	 * @return valor binário limpo sem os zeros na frente.
	 */

	public static int removeZeros(String binary)
	{
		return StringUtil.remInitWhile(binary, "0").length();
	}

	/**
	 * Converte um determinado caracter em um número binário. A conversão é feita
	 * a partir do código ascii do caracter que permite obter um número inteiro
	 * que pode ser convertido para um número binário.
	 * @param character que será convertido para binário.
	 * @return valor binário do caracter convertido.
	 */

	public static String parse(char character)
	{
		return Integer.toBinaryString((int) character);
	}

	/**
	 * <p>Converte vários caracteres seguidos em números binários. Caso valor binário
	 * dos caracteres irão ocupar até 16 casas binárias, totalizando um valor máximo
	 * de 65535 por caracter. Os valores binários são unidos de acordo com a ordem dos
	 * caracteres inseridos por parâmetro.</p>
	 * <i>Este método é utilizado para complementar conversões.</i>
	 * @param characters que serão convertidos para binário.
	 * @return valor binário dos caracteres convertidos.
	 */

	public static String parse(char[] characters)
	{
		String binary = "";

		for (int i = 0; i < characters.length; i++)
			binary += StringUtil.addStartWhile(parse(characters[i]), "0", 16);

		return binary;
	}

	/**
	 * <p>Analisa um determinado byte e o converte para um valor binário.
	 * Cada byte pode possuir no máximo 8 bits (8 casas binárias).</p>
	 * <i>Não retorna complementação com 0 (zeros) no valor retornado.</i>
	 * @param value byte que será convertido para binário.
	 * @return valor binário do byte convertido.
	 */

	public static String parse(byte value)
	{
		return Integer.toBinaryString((int) value);
	}

	/**
	 * Analisa vários bytes seguidos e converte todos eles para valores binários.
	 * Onde cada byte poderá possuir no máximo 8 bits (8 casa binárias)>.
	 * Os valores binários obtidos são todos salvos dentro de uma única
	 * string, cada byte irá ocupar 8 casas binárias para inserir seu valor.
	 * @param array vetor de bytes que serão convertidos para binários.
	 * @return string única com o valor binário dos bytes inseridos.
	 */

	public static String parse(byte[] array)
	{
		String binary = "";

		for (int i = 0; i < array.length; i++)
			binary += StringUtil.addStartWhile(parse(array[i]), "0", 8);

		return binary;
	}

	/**
	 * Transforma um valor binário para um valor numérico 'long'.
	 * Caso o valor não seja numérico, será retornado 0 e se o
	 * valor binário ultrapassar as 63 casas binárias limites, será
	 * considerado automaticamente como o valor máximo de um 'long'.
	 * @param string valor binários que será convertido para long.
	 * @return valor numérico do tipo 'long' convertido.
	 */

	public static long toLong(String string)
	{
		if (!isBinary(string))
			return 0;

		if (string.length() > 63)
			return Long.MAX_VALUE;

		return Long.parseLong(string, 2);
	}

	/**
	 * Transforma um valor binário para um valor numérico 'int'.
	 * Caso o valor não seja numérico, será retornado 0 e se o
	 * valor binário ultrapassar as 31 casas binárias limites, será
	 * considerado automaticamente como o valor máximo de um 'int'.
	 * @param string valor binários que será convertido para int.
	 * @return valor numérico do tipo 'int' convertido.
	 */

	public static int toInt(String value)
	{
		if (!isBinary(value))
			return 0;

		if (value.length() > 31)
			return Integer.MAX_VALUE;

		return Integer.parseInt(value, 2);
	}

	/**
	 * Transforma um valor binário para um valor numérico 'short'.
	 * Caso o valor não seja numérico, será retornado 0 e se o
	 * valor binário ultrapassar as 7 casas binárias limites, será
	 * considerado automaticamente como o valor máximo de um 'short'.
	 * @param string valor binários que será convertido para short.
	 * @return valor numérico do tipo 'short' convertido.
	 */

	public static short toShort(String value)
	{
		if (!isBinary(value))
			return 0;

		if (value.length() > 7)
			return 127;

		return (short) Integer.parseInt(value, 2);
	}

	/**
	 * Transforma um determinado valor binário em um caracter.
	 * Esse método deve ser utilizado apenas para valores binários
	 * que sejam menor do que 65535, caso contrário o caracter
	 * não irá corresponder corretamente. A tabela ascii agrega
	 * um limite de bytes por caracter, resultando em 65535 slots.
	 * @param string valor binário que será convertido para caracter.
	 * @return caracter convertido a partir do valor binário.
	 */

	public static char toChar(String string)
	{
		return (char) toInt(string);
	}

	/**
	 * Transforma um determinado valor binário em um byte.
	 * Esse método deve ser utilizado apenas para valores binários
	 * que sejam menor do que 127, caso contrário o byte gerado
	 * será retornado como -1 pois não o limite é de 127 possibilitadas.
	 * @param string valor binário que será convertido para um byte.
	 * @return byte convertido a partir do valor binário.
	 */

	public static byte toByte(String string)
	{
		return (byte) toInt(string);
	}

	/**
	 * Dado um determinado vetor de números do tipo 'long',
	 * este método irá converter respectivamente os valores
	 * para um vetor de string contendo seus valores binários.
	 * @param array vetor numérico que será convertido.
	 * @return vetor dos binários obtidos a partir de array.
	 */

	public static String[] parseArray(long[] array)
	{
		if (array == null)
			return null;

		String parse[] = new String[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = Long.toBinaryString(array[i]);

		return parse;
	}

	/**
	 * Dado um determinado vetor de números do tipo 'int',
	 * este método irá converter respectivamente os valores
	 * para um vetor de string contendo seus valores binários.
	 * @param array vetor numérico que será convertido.
	 * @return vetor dos binários obtidos a partir de array.
	 */

	public static String[] parseArray(int[] array)
	{
		if (array == null)
			return null;

		String parse[] = new String[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = Integer.toBinaryString(array[i]);

		return parse;
	}

	/**
	 * Dado um determinado vetor de números do tipo 'short',
	 * este método irá converter respectivamente os valores
	 * para um vetor de string contendo seus valores binários.
	 * @param array vetor numérico que será convertido.
	 * @return vetor dos binários obtidos a partir de array.
	 */

	public static String[] parseArray(short[] array)
	{
		if (array == null)
			return null;

		String parse[] = new String[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = Integer.toBinaryString(array[i]);

		return parse;
	}

	/**
	 * Dado um determinado vetor de caracteres únicos,
	 * este método irá converter respectivamente os valores
	 * para um vetor de string contendo seus valores binários.
	 * @param array vetor de caracteres que será convertido.
	 * @return vetor dos binários obtidos a partir de array.
	 */

	public static String[] parseArray(char[] array)
	{
		if (array == null)
			return null;

		String parse[] = new String[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = parse(array[i]);

		return parse;
	}

	/**
	 * Dado um determinado vetor de bytes,
	 * este método irá converter respectivamente os valores
	 * para um vetor de string contendo seus valores binários.
	 * @param array vetor de bytes que será convertido.
	 * @return vetor dos binários obtidos a partir de array.
	 */

	public static String[] parseArray(byte[] array)
	{
		if (array == null)
			return null;

		String parse[] = new String[array.length];

		for (int i = 0; i < array.length; i++)
			parse[i] = parse(array[i]);

		return parse;
	}
}
