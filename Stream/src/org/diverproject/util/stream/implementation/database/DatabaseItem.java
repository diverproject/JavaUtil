package org.diverproject.util.stream.implementation.database;

import org.diverproject.util.stream.StreamException;

/**
 * <p><h1>Item do Banco de Dados</h1></p>
 *
 * <p>Um item do banco de dados é um conjunto de dados que forma uma informação única.
 * Por exemplo, em uma database de usuários um item seria um único usuário do mesmo.
 * Se olharmos a database como tabela (normal) seria o mesmo que uma única linha.</p>
 *
 * @author Andrew
 */

public interface DatabaseItem
{
	/**
	 * Tamanho de um item é a quantidade de colunas do mesmo.
	 * @return aquisição do número total de colunas do item.
	 */

	int size();

	/**
	 * Permite obter um determinado dado do item que seja um boolean.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um boolean de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for boolean.
	 */

	Boolean getBoolean(int column);

	/**
	 * Permite obter um determinado dado do item que seja um byte.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um byte de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for byte.
	 */

	Byte getByte(int column);

	/**
	 * Permite obter um determinado dado do item que seja um short.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um short de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for short.
	 */

	Short getShort(int column);

	/**
	 * Permite obter um determinado dado do item que seja um int.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um int de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for int.
	 */

	Integer getInt(int column);

	/**
	 * Permite obter um determinado dado do item que seja um long.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um long de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for long.
	 */

	Long getLong(int column);

	/**
	 * Permite obter um determinado dado do item que seja um float.
	 * @param index índice do elemento do qual será obtido o dado.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um float de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for float.
	 */

	Float getFloat(int column);

	/**
	 * Permite obter um determinado dado do item que seja um double.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um double de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for double.
	 */

	Double getDouble(int column);

	/**
	 * Permite obter um determinado dado do item que seja uma string.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de uma string de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for string.
	 */

	String getString(int column);
	/**
	 * Permite obter um determinado dado do item que seja um vetor de boolean.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um boolean de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for boolean.
	 */

	Boolean[] getArrayBoolean(int column);

	/**
	 * Permite obter um determinado dado do item que seja um vetor de byte.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um byte de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for vetor de byte.
	 */

	Byte[] getArrayByte(int column);

	/**
	 * Permite obter um determinado dado do item que seja um vetor de short.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um short de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for vetor de short.
	 */

	Short[] getArrayShort(int column);

	/**
	 * Permite obter um determinado dado do item que seja um vetor de int.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um int de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for vetor de int.
	 */

	Integer[] getArrayInt(int column);

	/**
	 * Permite obter um determinado dado do item que seja um vetor de long.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um long de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for vetor de long.
	 */

	Long[] getArrayLong(int column);

	/**
	 * Permite obter um determinado dado do item que seja um vetor de float.
	 * @param index índice do elemento do qual será obtido o dado.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um float de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for vetor de float.
	 */

	Float[] getArrayFloat(int column);

	/**
	 * Permite obter um determinado dado do item que seja um vetor de double.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de um double de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for vetor de double.
	 */

	Double[] getArrayDouble(int column);

	/**
	 * Permite obter um determinado dado do item que seja um vetor de string.
	 * @param column número da coluna em que será obtido o dado desejado.
	 * @return aquisição de uma string de posição especificada como 'column'.
	 * @throws StreamException coluna inválida ou então se o dado não for vetor de string.
	 */

	String[] getArrayString(int column);

	/**
	 * Quando esse método for usado significa que apenas até uma determinada coluna é obrigatória.
	 * Usado para quando houver dados alternativos e esses deverão retornar em branco se não houver.
	 * @param column número de colunas que será obrigatório a existência no item em questão.
	 */

	void setMaxRequired(int column);
}
