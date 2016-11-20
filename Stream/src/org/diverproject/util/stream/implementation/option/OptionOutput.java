package org.diverproject.util.stream.implementation.option;

import org.diverproject.util.stream.StreamException;

/**
 * <p><h1>Stream para Saída de Opções</h1></p>
 *
 * <p>Essa interface é implementada pelas streams que permite salvar dados de opções formatadas.
 * Por padrão o sistema de stream Util utiliza um byte para determinar o tipo do dado salvo,
 * outro byte para determinar o tamanho do nome da opções e em seguida o nome dessa opção.
 * E por último deverá salvar o dado da preferência de acordo com o tipo de dado que será salvo.</p>
 *
 * <p>Interface que irá obrigar a classe a implementar todos os métodos para se obter essas opções.
 * Esses getters são para todos os tipos de opções que irão retornar atributos primitivos do java.</p>
 *
 * @author Andrew
 */

public interface OptionOutput extends StreamOption
{
	/**
	 * Flush serve para liberar os dados da stream para a fonte do mesmo (arquivo ou conexão).
	 * Utilizado quando o gerenciador tem que enviar os dados mas não pode fechar a stream ainda.
	 */

	void flush();

	/**
	 * Fecha por completo uma stream, fechando os objetos do java (input/output) e excluindo o buffer.
	 */

	void close();

	/**
	 * Escreve uma nova opção na stream do tipo byte.
	 * @param name nome da opção do qual está sendo escrita.
	 * @param value valor respectiva ao nome da opção passada.
	 */

	void putByte(String name, byte value);

	/**
	 * Escreve uma nova opção na stream do tipo char.
	 * @param name nome da opção do qual está sendo escrita.
	 * @param value valor respectiva ao nome da opção passada.
	 */

	void putChar(String name, char value);

	/**
	 * Escreve uma nova opção na stream do tipo short.
	 * @param name nome da opção do qual está sendo escrita.
	 * @param value valor respectiva ao nome da opção passada.
	 */

	void putShort(String name, short value);

	/**
	 * Escreve uma nova opção na stream do tipo int.
	 * @param name nome da opção do qual está sendo escrita.
	 * @param value valor respectiva ao nome da opção passada.
	 * @throws StreamException stream fechada ou falha na escrita.
	 */

	void putInt(String name, int value);

	/**
	 * Escreve uma nova opção na stream do tipo long.
	 * @param name nome da opção do qual está sendo escrita.
	 * @param value valor respectiva ao nome da opção passada.
	 */

	void putLong(String name, long value);

	/**
	 * Escreve uma nova opção na stream do tipo float.
	 * @param name nome da opção do qual está sendo escrita.
	 * @param value valor respectiva ao nome da opção passada.
	 */

	void putFloat(String name, float value);

	/**
	 * Escreve uma nova opção na stream do tipo double.
	 * @param name nome da opção do qual está sendo escrita.
	 * @param value valor respectiva ao nome da opção passada.
	 */

	void putDouble(String name, double value);

	/**
	 * Escreve uma nova opção na stream do tipo string.
	 * @param name nome da opção do qual está sendo escrita.
	 * @param value valor respectiva ao nome da opção passada.
	 */

	void putString(String name, String value);

	/**
	 * Escreve uma nova opção na stream do tipo boolean.
	 * @param name nome da opção do qual está sendo escrita.
	 * @param value valor respectiva ao nome da opção passada.
	 */

	void putBoolean(String name, boolean value);
}
