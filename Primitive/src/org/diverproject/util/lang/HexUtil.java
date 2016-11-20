package org.diverproject.util.lang;

import org.diverproject.util.UtilException;

/**
 * Classe que permite trabalhar com valores em hexadecimal.
 * A linguagem java não possui um atributo especifico para determinar
 * valores em hexadecimal, utilizamos strings para manuseá-los.
 * Os utilitários aqui permitem validar e converter valores para
 * um valor em string no formato hexadecimal e a operação inversa.
 */

public class HexUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private HexUtil()
	{
		
	}

	/**
	 * Verifica se uma determinada string está em um formato hexadecimal ou não.
	 * Valida tanto strings que possui 0x na frente ou não, basta conter valores
	 * numéricos e letras entre A e F para serem considerados hexadecimais.
	 * @param str string do qual deverá ser validada se é ou não hexadecimal.
	 * @param bytes quantos bytes pode ter (caracteres hexadecimal).
	 * @return true se for um valor hexadecimal ou false caso contrário.
	 */

	public static boolean isHex(String str, int bytes)
	{
		if (str == null || str.length() == 0)
			return false;

		if (str.startsWith("0x"))
			str = str.substring(2, str.length());

		if (str.length() > bytes * 2 || (str.length() == bytes * 2 && str.charAt(0) > 55))
			return false;

		for (char c : str.toLowerCase().toCharArray())
			if (c < '0' || c > '9')
				if (c < 'a' || c > 'f')
					return false;

		return true;
	}

	/**
	 * Verifica se uma determinada string está em um formato hexadecimal ou não.
	 * Também valida se esse valor hexadecimal pode ser convertido para um número short.
	 * @param str string do qual deverá ser validada se é um hexadecimal short.
	 * @return true se for um valor hexadecimal short ou false caso contrário.
	 */

	public static boolean isHexShort(String str)
	{
		return isHex(str, Short.BYTES);
	}

	/**
	 * Verifica se uma determinada string está em um formato hexadecimal ou não.
	 * Também valida se esse valor hexadecimal pode ser convertido para um número int.
	 * @param str string do qual deverá ser validada se é um hexadecimal int.
	 * @return true se for um valor hexadecimal int ou false caso contrário.
	 */

	public static boolean isHexInt(String str)
	{
		return isHex(str, Integer.BYTES);
	}

	/**
	 * Verifica se uma determinada string está em um formato hexadecimal ou não.
	 * Também valida se esse valor hexadecimal pode ser convertido para um número long.
	 * @param str string do qual deverá ser validada se é um hexadecimal long.
	 * @return true se for um valor hexadecimal long ou false caso contrário.
	 */

	public static boolean isHexLong(String str)
	{
		return isHex(str, Long.BYTES);
	}

	/**
	 * Verifica se uma determina string é hexadecimal, caso não seja retorna '0'.
	 * Quando é um hexadecimal remove o '0x' do inicio da string se existir.
	 * @param str string do qual deve ser verificada e removido pré-fixo.
	 * @return string limpa, ou seja, sem o pré-fixo 0x no inicio.
	 */

	public static String clearHex(String str)
	{
		return str.startsWith("0x") ? str.substring(2, str.length()) : str;
	}

	/**
	 * Obtém o valor hexadecimal em string de um determinado byte.
	 * @param value byte que será obtido o valor em hexadecimal.
	 * @return valor hexadecimal em string do byte.
	 */

	public static String parseByte(byte value)
	{
		return Integer.toHexString((int) value).toUpperCase();
	}

	/**
	 * Obtém o valor numérico que seja considerado um valor hexadecimal.
	 * @param value string contendo o valor hexadecimal que será convertido.
	 * @return número obtido após a analise e conversão do hexadecimal.
	 * @throws UtilException apenas no caso de não ser um valor hexadecimal.
	 */

	public static byte parseByte(String value) throws UtilException
	{
		if (HexUtil.isHex(value, 1))
		{
			if (value.startsWith("0x"))
				return Byte.parseByte(value.substring(2, value.length()), 16);

			return Byte.parseByte(value, 16);
		}

		throw new UtilException("hexadecimal inválido");
	}

	/**
	 * Obtém o valor hexadecimal em string do código ascii de um caracter.
	 * @param character que será obtido o valor em hexadecimal.
	 * @return valor hexadecimal em string do caracter.
	 */

	public static String parseChar(char character)
	{
		return Integer.toHexString((int) character).toUpperCase();
	}

	/**
	 * Obtém o valor hexadecimal em string de um valor numérico curto.
	 * @param value número do qual será obtido o valor em hexadecimal.
	 * @return valor hexadecimal em string do número curto.
	 */

	public static String parseShort(short value)
	{
		return Integer.toHexString(value).toUpperCase();
	}

	/**
	 * Obtém o valor hexadecimal em string de um valor numérico curto.
	 * @param value número do qual será obtido o valor em hexadecimal.
	 * @param lenght quantas casas hexadecimais deve ter no mínimo.
	 * @return valor hexadecimal em string do número curto.
	 */

	public static String parseShort(short value, int lenght)
	{
		return StringUtil.addStartWhile(parseShort(value), "0", lenght);
	}

	/**
	 * Obtém o valor numérico que seja considerado um valor hexadecimal.
	 * @param value string contendo o valor hexadecimal que será convertido.
	 * @return número obtido após a analise e conversão do hexadecimal.
	 * @throws UtilException apenas no caso de não ser um valor hexadecimal.
	 */

	public static short parseShort(String value) throws UtilException
	{
		if (HexUtil.isHex(value, 2))
		{
			if (value.startsWith("0x"))
				return Short.parseShort(value.substring(2, value.length()), 16);

			return Short.parseShort(value, 16);
		}

		throw new UtilException("hexadecimal inválido");
	}

	/**
	 * Obtém o valor hexadecimal em string de um valor numérico inteiro.
	 * @param value número do qual será obtido o valor em hexadecimal.
	 * @return valor hexadecimal em string do número inteiro.
	 */

	public static String parseInt(int value)
	{
		return Integer.toHexString(value);
	}

	/**
	 * Obtém o valor hexadecimal em string de um valor numérico inteiro.
	 * @param value número do qual será obtido o valor em hexadecimal.
	 * @param lenght quantas casas hexadecimais deve ter no mínimo.
	 * @return valor hexadecimal em string do número inteiro.
	 */

	public static String parseInt(int value, int lenght)
	{
		return StringUtil.addStartWhile(parseInt(value), "0", lenght).toUpperCase();
	}

	/**
	 * Obtém o valor numérico que seja considerado um valor hexadecimal.
	 * @param value string contendo o valor hexadecimal que será convertido.
	 * @return número obtido após a analise e conversão do hexadecimal.
	 * @throws UtilException apenas no caso de não ser um valor hexadecimal.
	 */

	public static int parseInt(String value) throws UtilException
	{
		if (HexUtil.isHex(value, 4))
		{
			if (value.startsWith("0x"))
				return Integer.parseInt(value.substring(2, value.length()), 16);

			return Integer.parseInt(value, 16);
		}

		throw new UtilException("hexadecimal inválido");
	}

	/**
	 * Obtém o valor hexadecimal em string de um valor numérico longo.
	 * @param value número do qual será obtido o valor em hexadecimal.
	 * @return valor hexadecimal em string do número long.
	 */

	public static String parseLong(long value)
	{
		return Long.toHexString(value).toUpperCase();
	}

	/**
	 * Obtém o valor hexadecimal em string de um valor numérico longo.
	 * @param value número do qual será obtido o valor em hexadecimal.
	 * @param lenght quantas casas hexadecimais deve ter no mínimo.
	 * @return valor hexadecimal em string do número longo.
	 */

	public static String parseLong(long value, int lenght)
	{
		return StringUtil.addStartWhile(parseLong(value), "0", lenght).toUpperCase();
	}

	/**
	 * Obtém o valor numérico que seja considerado um valor hexadecimal.
	 * @param value string contendo o valor hexadecimal que será convertido.
	 * @return número obtido após a analise e conversão do hexadecimal.
	 * @throws UtilException apenas no caso de não ser um valor hexadecimal.
	 */

	public static long parseLong(String value) throws UtilException
	{
		if (HexUtil.isHex(value, 8))
		{
			if (value.startsWith("0x"))
				return Long.parseLong(value.substring(2, value.length()), 16);

			return Long.parseLong(value, 16);
		}

		throw new UtilException("hexadecimal inválido");
	}
}
