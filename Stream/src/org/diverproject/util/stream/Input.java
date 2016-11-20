package org.diverproject.util.stream;

/**
 * <h1>Entrada</h1>
 *
 * <p>Usado para determinar uma stream como entrada de dados na aplicação, também é um leitor na comunicação.
 * Possui alguns procedimentos básicos que irá permitir trabalhar com dados primitivos através dos bytes.
 * Classes que implementem essa interface deverão especificar como será feito a conversão dos bytes.</p>
 *
 * @see Reader
 *
 * @author Andrew Mello
 */

public interface Input extends Reader
{
	/**
	 * Funciona como uma simples leitura por read(), retornando o próximo byte disponível a leitura.
	 * @return aquisição do próximo byte da comunicação, caso não haja mais nenhum retorna 0.
	 */

	byte getByte();

	/**
	 * Chama repetidas vezes o método getByte() a fim de ler diversos bytes de uma só vez.
	 * @param size quantidade de bytes do qual deseja que o vetor retornado possua.
	 * @return vetor contendo todos os bytes desejados de acordo com o tamanho.
	 */

	byte[] getBytes(int size);

	/**
	 * Chama repetidas vezes o método getByte() a fim de ler diversos bytes de uma só vez.
	 * @param array vetor do qual deseja carregá-lo com os próximos bytes a serem lidos.
	 */

	void getBytes(byte[] array);

	/**
	 * Faz a leitura do próximo byte disponível para ser lido dentro da comunicação estabelecida.
	 * Através do calor do byte passado converte esse para um valor em caracter.
	 * @return aquisição do próximo byte da comunicação convertido para caracter.
	 */

	char getChar();

	/**
	 * Chama repetidas vezes o método getChar() afim de ler diversos caracteres de uma só vez.
	 * @param size quantidade de caracteres do qual deseja que o vetor retornado possua.
	 * @return vetor contendo todos os caracteres desejados de acordo com o tamanho.
	 */

	char[] getChars(int size);

	/**
	 * Chama repetidas vezes o método getChar() afim de ler diversos caracteres de uma só vez.
	 * @param array vetor do qual deseja carregá-lo com os próximos caracteres a serem lidos.
	 */

	void getChars(char[] array);

	/**
	 * Faz a leitura dos próximos dois bytes disponíveis para serem lidos na comunicação estabelecida.
	 * Utiliza da verificação de comunicação invertida caso esteja habilitada, invertendo os bytes.
	 * @return aquisição dos próximos dois bytes da comunicação convertidos para um número short.
	 */

	short getShort();

	/**
	 * Chama repetidas vezes o método getShort() afim de ler diversos short de uma só vez.
	 * @param size quantidade de shorts do qual deseja que o vetor retornado possua.
	 * @return vetor contendo todos os shorts desejados de acordo com o tamanho.
	 */

	short[] getShorts(int size);

	/**
	 * Chama repetidas vezes o método getShort() afim de ler diversos short de uma só vez.
	 * @param array vetor do qual deseja carregá-lo com os próximos shorts a serem lidos.
	 */

	void getShorts(short[] array);

	/**
	 * Faz a leitura dos próximos quatro bytes disponíveis para serem lidos na comunicação estabelecida.
	 * Utiliza da verificação de comunicação invertida caso esteja habilitada, invertendo os bytes.
	 * @return aquisição dos próximos quatro bytes da comunicação convertidos para um número int.
	 */

	int getInt();

	/**
	 * Chama repetidas vezes o método getInt() afim de ler diversos inteiros de uma só vez.
	 * @param size quantidade de inteiros do qual deseja que o vetor retornado possua.
	 * @return vetor contendo todos os inteiros desejados de acordo com o tamanho.
	 */

	int[] getInts(int size);

	/**
	 * Chama repetidas vezes o método getInt() afim de ler diversos inteiros de uma só vez.
	 * @param array vetor do qual deseja carregá-lo com os próximos inteiros a serem lidos.
	 */

	void getInts(int[] array);

	/**
	 * Faz a leitura dos próximos oito bytes disponíveis para serem lidos na comunicação estabelecida.
	 * Utiliza da verificação de comunicação invertida caso esteja habilitada, invertendo os bytes.
	 * @return aquisição dos próximos oito bytes da comunicação convertidos para um número long.
	 */

	long getLong();

	/**
	 * Chama repetidas vezes o método getLong() afim de ler diversos longs de uma só vez.
	 * @param size quantidade de longs do qual deseja que o vetor retornado possua.
	 * @return vetor contendo todos os longs desejados de acordo com o tamanho.
	 */

	long[] getLongs(int size);

	/**
	 * Chama repetidas vezes o método getLong() afim de ler diversos longs de uma só vez.
	 * @param array vetor do qual deseja carregá-lo com os próximos longs a serem lidos.
	 */

	void getLongs(long[] array);

	/**
	 * Faz a leitura dos próximos quatro bytes disponíveis para serem lidos na comunicação estabelecida.
	 * Utiliza da verificação de comunicação invertida caso esteja habilitada, invertendo os bytes.
	 * @return aquisição dos próximos quatro bytes da comunicação convertidos para um número float.
	 */

	float getFloat();

	/**
	 * Chama repetidas vezes o método getFloat() afim de ler diversos floats de uma só vez.
	 * @param size quantidade de floats do qual deseja que o vetor retornado possua.
	 * @return vetor contendo todos os floats desejados de acordo com o tamanho.
	 */

	float[] getFloats(int size);

	/**
	 * Chama repetidas vezes o método getFloat() afim de ler diversos float de uma só vez.
	 * @param array vetor do qual deseja carregá-lo com os próximos floats a serem lidos.
	 */

	void getFloats(float[] array);

	/**
	 * Faz a leitura dos próximos oito bytes disponíveis para serem lidos na comunicação estabelecida.
	 * Utiliza da verificação de comunicação invertida caso esteja habilitada, invertendo os bytes.
	 * @return aquisição dos próximos oito bytes da comunicação convertidos para um número double.
	 */

	double getDouble();

	/**
	 * Chama repetidas vezes o método getFloat() afim de ler diversos doubles de uma só vez.
	 * @param size quantidade de doubles do qual deseja que o vetor retornado possua.
	 * @return vetor contendo todos os doubles desejados de acordo com o tamanho.
	 */

	double[] getDoubles(int size);

	/**
	 * Chama repetidas vezes o método getFloat() afim de ler diversos double de uma só vez.
	 * @param array vetor do qual deseja carregá-lo com os próximos doubles a serem lidos.
	 */

	void getDoubles(double[] array);

	/**
	 * Faz a leitura dos próximos <b>n</b> bytes disponíveis para serem lidos na comunicação estabelecida.
	 * O número obtido desse próximo byte irá determinar qual o tamanho da string em bytes.
	 * @return aquisição da próxima <b>n</b> bytes da comunicação convertidos para uma string.
	 */

	String getString();

	/**
	 * Chama repetidas vezes o método getStrings() afim de ler diversos strings de uma só vez.
	 * Aqui o primeiro byte será considerado o tamanho da string seguido do conteúdo.
	 * @param size quantidade de string do qual deseja que o vetor retornado possua.
	 * @return vetor contendo todos os string desejados de acordo com o tamanho.
	 */

	String[] getStrings(int size);

	/**
	 * Chama repetidas vezes o método getStrings() afim de ler diversos strings de uma só vez.
	 * Aqui o primeiro byte será considerado o tamanho da string seguido do conteúdo.
	 * @param array vetor do qual deseja carregá-lo com os próximas strings a serem lidos.
	 */

	void getStrings(String[] array);

	/**
	 * Faz a leitura dos próximos bytes disponíveis para serem lidos na comunicação estabelecida.
	 * @param length quantos bytes serão lidos para formar a próxima string na comunicação.
	 * @return aquisição de uma nova string contendo os próximos <b>length</b> bytes.
	 */

	String getString(int length);

	/**
	 * Chama repetidas vezes o método getStrings() afim de ler diversos strings de uma só vez.
	 * Aqui irá considerar os próximos <b>size</b> bytes como parte do conteúdo da string.
	 * Além disso, deverá recortar o mesmo assim que achar um byte do tipo NULL (byte: 0).
	 * @param size quantidade de string do qual deseja que o vetor retornado possua.
	 * @param length quantos caracteres deverá possuir cada string que for obtida.
	 * @return vetor contendo todos os string desejados de acordo com o tamanho.
	 */

	String[] getStrings(int size, int length);

	/**
	 * Chama repetidas vezes o método getStrings() afim de ler diversos strings de uma só vez.
	 * Aqui irá considerar os próximos <b>size</b> bytes como parte do conteúdo da string.
	 * Além disso, deverá recortar o mesmo assim que achar um byte do tipo NULL (byte: 0).
	 * @param array vetor do qual deseja carregá-lo com os próximas strings a serem lidos.
	 * @param length quantidade de string do qual deseja que o vetor retornado possua.
	 */

	void getStrings(String[] array, int length);
}
