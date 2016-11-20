package org.diverproject.util.stream;

/**
 * <h1>Saída</h1>
 *
 * <p>Usado para determinar uma stream como saída de dados na aplicação, também é um escritor na comunicação.
 * Possui alguns procedimentos básicos que irá permitir trabalhar com dados primitivos através dos bytes.
 * Classes que implementem essa interface deverão especificar como será feito a conversão dos dados.</p>
 *
 * @see Output
 *
 * @author Andrew Mello
 */

public interface Output extends Writer
{
	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Para esse caso será colocado um byte direto, funcionando como uma simples escrita.
	 * @param value valor de um único byte do qual será escrito na comunicação estabelecida.
	 */

	void putByte(byte value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Para esse caso será colocado um ou mais bytes direto, funcionando como uma simples escrita.
	 * @param values bytes ou um vetor de bytes que deverão ser escritos na stream.
	 */

	void putBytes(byte... values);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso o caracter que for passado será convertido em um byte e escrito em seguida.
	 * @param value valor de um byte representando um caracter a ser escrito.
	 */

	void putChar(char value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso os caracteres que forem passados serão convertidos em bytes e escrito em seguida.
	 * @param values caracter ou um vetor de caracteres que deverão ser escritos na stream.
	 */

	void putChars(char... values);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso o número que for passado será convertido em um byte e escrito em seguida.
	 * @param value valor de dois bytes representando um número short a ser escrito.
	 */

	void putShort(short value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso os números que forem passados serão convertidos em bytes e escritos em seguida.
	 * @param values números short ou um vetor de short que deverão ser escritos na stream.
	 */

	void putShorts(short... value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso o número que for passado será convertido em um byte e escrito em seguida.
	 * @param value valor de quatro bytes representando um número int a ser escrito.
	 */

	void putInt(int value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso os números que forem passados serão convertidos em bytes e escritos em seguida.
	 * @param values números inteiros ou um vetor de int que deverão ser escritos na stream.
	 */

	void putInts(int... value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso o número que for passado será convertido em um byte e escrito em seguida.
	 * @param value valor de oito bytes representando um número long a ser escrito.
	 */

	void putLong(long value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso os números que forem passados serão convertidos em bytes e escritos em seguida.
	 * @param values números long ou um vetor de long que deverão ser escritos na stream.
	 */

	void putLongs(long... value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso o número que for passado será convertido em um byte e escrito em seguida.
	 * @param value valor de quatro bytes representando um número float a ser escrito.
	 */

	void putFloat(float value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso os números que forem passados serão convertidos em bytes e escritos em seguida.
	 * @param values números flutuantes ou um vetor de float que deverão ser escritos na stream.
	 */

	void putFloats(float... value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso o número que for passado será convertido em um byte e escrito em seguida.
	 * @param value valor de quatro bytes representando um número double a ser escrito.
	 */

	void putDouble(double value);

	/**
	 * Deve escrever um determinado valor dentro da comunicação de acordo com o ponteiro de escrita.
	 * Nesse caso os números que forem passados serão convertidos em bytes e escritos em seguida.
	 * @param values números double ou um vetor de double que deverão ser escritos na stream.
	 */

	void putDoubles(double... value);

	/**
	 * Deve escrever uma determinada string dentro da comunicação de acordo com o ponteiro de escrita.
	 * O primeiro byte escrito de cada string será para definir a quantidade de caracteres.
	 * @param str string contendo os bytes representados em caracteres que serão escritos.
	 */

	void putString(String str);

	/**
	 * Deve escrever determinadas strings dentro da comunicação de acordo com o ponteiro de escrita.
	 * O primeiro byte escrito de cada string será para definir a quantidade de caracteres.
	 * @param values strings ou um vetor com strings que deverão ser escritos.
	 */

	void putStrings(String... values);

	/**
	 * Deve escrever uma determinada string dentro da comunicação de acordo com o ponteiro de escrita.
	 * Para esse caso será escrito todo o conteúdo possível da string e preencher o resto com nulos.
	 * @param str string contendo os bytes representados em caracteres que serão escritos.
	 * @param length quantos caracteres deverão fazer parte do contexto da string.
	 */

	void putString(String str, int length);

	/**
	 * Deve escrever determinadas strings dentro da comunicação de acordo com o ponteiro de escrita.
	 * Para esse caso será escrito todo o conteúdo possível da string e preencher o resto com nulos.
	 * @param length quantos caracteres deverão fazer parte do contexto de cada string.
	 * @param values strings ou um vetor com strings que deverão ser escritos.
	 */

	void putStrings(int length, String... values);

	/**
	 * Flush serve para liberar os dados da stream para a fonte do mesmo (arquivo ou conexão).
	 * Utilizado quando o gerenciador tem que enviar os dados mas não pode fechar a stream ainda.
	 */

	void flush();
}
