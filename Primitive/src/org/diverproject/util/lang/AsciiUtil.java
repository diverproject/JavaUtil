package org.diverproject.util.lang;

/**
 * <p><h1>Utilitário para Ascii</h1></p>
 *
 * Utilitários ASCII permite a conversão entre caracteres de 2 bytes para valores numéricos.
 * Através de métodos de conversão dos códigos ASCII para binários e de binários para números
 * inteiros (int/long) é possível fazer estas operações de modo que um número do tipo int,
 * possa ser salvo em no máximo até 2 bytes, enquanto um valor do tipo long chega até 4 bytes.
 */

public class AsciiUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private AsciiUtil()
	{
		
	}

	/**
	 * Através do código ascii dos caracteres convertidos para binários,
	 * é possível se obter um valor numérico do tipo 'long' com até 8 bytes
	 * (4 caracteres). Caso não seja inserido caracteres (null) o valor
	 * retornado será 0, se houver mais de 4 caracteres automaticamente
	 * será retornado o limite dos valores do tipo 'long'.
	 * @param characters quais os caracteres que definem o valor.
	 * @return valor numérico 'long' dos caracteres convertidos.
	 */

	public static long parseLong(char... characters)
	{
		if (characters == null || characters.length == 0)
			return 0;

		String binary = BinaryUtil.parse(characters);

		if (characters.length > 4 || BinaryUtil.removeZeros(binary) > 64)
			return Long.MAX_VALUE;

		return Long.parseLong(binary, 2);
	}

	/**
	 * Através do código ascii dos caracteres convertidos para binários,
	 * é possível se obter um valor numérico do tipo 'int' com até 4 bytes
	 * (2 caracteres). Caso não seja inserido caracteres (null) o valor
	 * retornado será 0, se houver mais de 2 caracteres automaticamente
	 * será retornado o limite dos valores do tipo 'int'.
	 * @param characters quais os caracteres que definem o valor.
	 * @return valor numérico 'int' dos caracteres convertidos.
	 */

	public static int parseInt(char... characters)
	{
		if (characters == null || characters.length == 0)
			return 0;

		String binary = BinaryUtil.parse(characters);

		if (characters.length > 2 || StringUtil.remInitWhile(binary, "0").length() > 32)
			return Integer.MAX_VALUE;

		return Integer.parseInt(binary, 2);
	}

	/**
	 * Para permitir que valores numéricos sejam compactados em arquivos,
	 * este método permite converter um valor do tipo 'long' em até 8 bytes
	 * (4 caracteres), ao invés de salvar em dezenas de bytes (um por número).
	 * Para recuperar esse valor, basta chamar o método parseLong() com os
	 * caracteres gerados por este método. Valores long gerados por caracteres
	 * sempre serão gerados em 4 caracteres independente do valor.
	 * @param value valor numérico que será convertido para caracter.
	 * @return caracter convertido a partir do valor inserido.
	 */

	public static char[] toChar(long value)
	{
		String binary = Long.toBinaryString(value);
		binary = StringUtil.addStartMod(binary, "0", 64);

		String bin[] = StringUtil.split(binary, 16);

		return new char[]
		{
			(char) Integer.parseInt(bin[0]),
			(char) Integer.parseInt(bin[1]),
			(char) Integer.parseInt(bin[2]),
			(char) Integer.parseInt(bin[3])
		};
	}

	/**
	 * Para permitir que valores numéricos sejam compactados em arquivos,
	 * este método permite converter um valor do tipo 'int' em até 4 bytes
	 * (2 caracteres), ao invés de salvar em dezenas de bytes (um por número).
	 * Para recuperar esse valor, basta chamar o método parseInt() com os
	 * caracteres gerados por este método. Valores int gerados por caracteres
	 * sempre serão gerados em 2 caracteres independente do valor.
	 * @param value valor numérico que será convertido para caracter.
	 * @return caracter convertido a partir do valor inserido.
	 */

	public static char[] toChar(int value)
	{
		String binary = Integer.toBinaryString(value);
		binary = StringUtil.addStartMod(binary, "0", 32);

		String bin[] = StringUtil.split(binary, 16);

		return new char[]
		{
			(char) Integer.parseInt(bin[0], 2),
			(char) Integer.parseInt(bin[1], 2)
		};
	}
}
