package org.diverproject.util.lang;

/**
 * <p><h1>Utilitário para Boolean</h1></p>
 *
 * Classe com métodos utilitários para manusear valores do tipo boolean.
 * Todos os métodos envolvem retornar atributos do tipo <b>boolean</b>.
 * Em alguns casos há métodos que retornam objetos do tipo <b>boolean</b>.
 * Apesar de ambos serem do mesmo tipo, algumas operações necessitam
 * utilizar o objeto ao invés do atributo, e vice-versa.
 */

public class BooleanUtil
{
	/**
	 * Código para string analisada com valor falso.
	 */
	public static final int BOOLEAN_FALSE = 0;

	/**
	 * Código para string analisada com valor verdadeiro.
	 */
	public static final int BOOLEAN_TRUE = 1;

	/**
	 * Código para string analisada com valor inválido.
	 */
	public static final int BOOLEAN_ERROR = 2;

	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private BooleanUtil()
	{
		
	}

	/**
	 * Verifica se uma determinada string é booleana ou não.
	 * @param value valor que será verificado se é verdadeiro.
	 * @return true se for verdadeiro ou false caso contrário.
	 */

	public static boolean parse(String value)
	{
		return parseString(value) == 1;
	}

	/**
	 * Faz a análise de uma determinada string para verificar se o seu valor é verdadeiro ou falso.
	 * Nesse caso considera os seguintes valores como verdadeiro e falso: 1|0, yes|no, true|false.
	 * @param value string contendo o valor do qual será analisado e validado.
	 * @return 1 se for verdadeiro, 0 se for falso e -1 caso não seja válido.
	 */

	public static int parseString(String value)
	{
		if (value.equals("1") || value.equals("yes") || value.equals("true"))
			return BOOLEAN_TRUE;

		if (value.equals("0") || value.equals("no") || value.equals("false"))
			return BOOLEAN_FALSE;

		return BOOLEAN_ERROR;
	}

	/**
	 * Constrói um novo vetor que irá armazenar valores primitivos.
	 * @param array vetor de objetos do tipo boolean a ser usado.
	 * @return vetor de valores primitivos do tipo boolean criado.
	 */

	public static boolean[] parseArrayBoolean(Boolean[] array)
	{
		boolean[] parsed = new boolean[array.length];

		for (int i = 0; i < parsed.length; i++)
			parsed[i] = array[i] == null ? false : array[i];

		return parsed;
	}

	/**
	 * Constrói um novo vetor que irá armazenar objetos do tipo boolean.
	 * @param array vetor de valores primitivos do tipo boolean.
	 * @return vetor de objetos do tipo boolean que foi criado.
	 */

	public static Boolean[] parseArrayBoolean(boolean[] array)
	{
		Boolean[] parsed = new Boolean[array.length];

		for (int i = 0; i < parsed.length; i++)
			parsed[i] = array[i];

		return parsed;
	}

	/**
	 * Deve verificar se uma determinada string possui um valor booleano.
	 * Os valores aceitos como booleano são: 0/1, false/true, no/yes, nao/sim.
	 * @param value string do qual será avaliado se o conteúdo pode ou não ser boolean.
	 * @return true se possuir um conteúdo considerado booleano ou false caso contrário.
	 */

	public static boolean isBoolean(String value)
	{
		return	value != null && (
				value.equals("0")		|| value.equals("1")	||
				value.equals("false")	|| value.equals("true")	||
				value.equals("no")		|| value.equals("yes")	||
				value.equals("nao")		|| value.equals("sim")	);
	}
}
