package org.diverproject.util;


import java.sql.Timestamp;
import java.util.Locale;
import java.util.Random;

import org.diverproject.util.collection.Collection;
import org.diverproject.util.collection.List;

/**
 * <h1>Utilitários</h1>
 *
 * <p>Classe que possui diversos métodos estáticos para que possam facilitar a programação.</p>
 *
 * @author Andrew
 */

public class Util
{
	/**
	 * Construtor privado pois não será necessário outras instâncias dessa classe.
	 */

	private Util()
	{
		
	}

	/**
	 * Instância de um objeto Random para ser usado pelos métodos estáticos.
	 */

	private static final Random random = new Random();

	/**
	 * Chamado para realizar uma pausa (dormir) na Thread em que a chamar.
	 * O tempo da pausa será definido através do valor passado por parâmetro.
	 * Esse método não necessita a realização do try catch como de costume.
	 * @param mileseconds tempo da duração da pausa em milissegundos.
	 */

	public static void sleep(long mileseconds)
	{
		if (mileseconds < 1)
			return;

		try {
			Thread.sleep(mileseconds);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Permite obter o nome da classe (getSimpleName) de um determinado objeto.
	 * @param object referência do objeto do qual deseja saber o nome.
	 * @return string contendo null ou o nome da classe se for válida.
	 */

	public static String nameOf(Object object)
	{
		if (object == null)
			return "null";

		return object.getClass().getSimpleName();
	}

	/**
	 * Formata uma determinada string conforme o formato e argumentos passado.
	 * @param format string contendo o formato que a mensagem deverá possuir.
	 * @param args argumentos referentes a formatação que mensagem possui.
	 * @return string formatada conforme o formato e valor dos argumentos.
	 */

	public static String format(String format, Object... args)
	{
		return String.format(Locale.US, format, args);
	}

	/**
	 * Transforma um determinado valor numérico representado em milissegundos para uma string formatada.
	 * Por exemplo: <b>10ms</b>, <b>1s90ms</b>, <b>5m20s</b>, <b>3h20m40s</b>, <b>5d20h15m</b>.
	 * @param ms valor de tempo em milissegundos para ser convertido em uma string.
	 * @return aquisição da string com valor formatado respectivo ao tempo passado.
	 */

	public static String time(long ms)
	{
		if (ms < 1000)
			return String.format(Locale.US, "%dms", ms);

		if (ms < 60000)
			return String.format(Locale.US, "%.2fs%.2fms", (float) ms/1000, (float) ms % 1000);

		if (ms < 3600000)
			return String.format(Locale.US, "%dm%.2fs", (int) ms/60000, (int) ms/1000);

		if (ms < 86400000)
			return String.format(Locale.US, "%dh%dm%ds", (int) ms/3600000, (int) ((ms % 3600000) / 60000), (int) ((ms % 60000) / 1000));

		return String.format(Locale.US, "%d%dh%dm", (int) ms/86400000, (int) ((ms % 86400000) / 3600000), (int) ((ms % 3600000) / 60000));
	}

	/**
	 * Limite o valor de uma determinada string em um número de caracteres.
	 * Caso a string não possua um tamanho maior irá continuar igual.
	 * @param string referência da string que pode vir a ser cortada.
	 * @param length limite de caracteres que a string deverá possuir.
	 * @return aquisição da string recortada ou inteira se não tiver o tamanho.
	 */

	public static String strcap(String string, int length)
	{
		if (string == null)
			return "";

		if (string.length() > length)
			return string.substring(0, length);

		return string;
	}

	/**
	 * Limpa o conteúdo de uma string considerando o limite dela o NUL.
	 * Irá recortar uma determinada string quando encontrar o byte 0.
	 * @param string referência da string do qual deseja limpar.
	 * @return aquisição de uma nova string completamente limpa.
	 */

	public static String strclr(String string)
	{
		int index = string.indexOf("\0");

		if (index > 0)
			return string.substring(0, index);

		return string;
	}

	/**
	 * Permite obter o número do índice de um determinado objeto dentro de uma lista.
	 * @param list referência da lista que contém o objeto a ser localizado.
	 * @param target referência do objeto alvo a ser localizado na lista.
	 * @return aquisição do índice do objeto alvo na lista passada,
	 * casso o objeto não se encontre na lista será retornado 0.
	 */

	@SuppressWarnings("rawtypes")
	public static int indexOn(List list, Object target)
	{
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).equals(target))
				return i;

		return -1;
	}

	/**
	 * Procedimento que verifica o tamanho de um vetor seja ela nula ou não.
	 * Usado apenas para facilitar verificações que consideram null como zero.
	 * @param array referência do vetor do qual será verificada.
	 * @return quantidade de índices no vetor ou 0 (zero) se for null.
	 */

	public static int size(Object array[])
	{
		if (array == null)
			return 0;

		return array.length;
	}

	/**
	 * Procedimento que verifica o tamanho de uma coleção seja ela nula ou não.
	 * Usado apenas para facilitar verificações que consideram null como zero.
	 * @param collection referência da coleção do qual será verificada.
	 * @return quantidade de elementos na coleção ou 0 (zero) se for null.
	 */

	public static int size(Collection<?> collection)
	{
		if (collection == null)
			return 0;

		return collection.size();
	}

	/**
	 * Permite obter um valor numérico inteiro positivo através de Random.
	 * @return aquisição de um número inteiro e positivo aleatório.
	 */

	public static int random()
	{
		int i = random.nextInt();

		return i >= 0 ? i : i * -1;
	}

	/**
	 * Procedimento para efetuar o cast para um valor numérico byte.
	 * @param value número do tipo inteiro a ser convertido para byte.
	 * @return aquisição de um valor numérico do tipo byte com base em value.
	 */

	public static byte b(int value)
	{
		return (byte) value;
	}

	/**
	 * Procedimento para efetuar o cast para um valor numérico short.
	 * @param value número do tipo inteiro a ser convertido para short.
	 * @return aquisição de um valor numérico do tipo short com base em value.
	 */

	public static short s(int value)
	{
		return (short) value;
	}

	/**
	 * Procedimento para efetuar o cast para um valor numérico int.
	 * @param value número do tipo inteiro a ser convertido para int.
	 * @return aquisição de um valor numérico do tipo int com base em value.
	 */

	public static int i(long value)
	{
		return (int) value;
	}

	/**
	 * Converte um objeto do tipo timestamp em um valor do tipo numérico long.
	 * @param timestamp objeto contendo o horário do qual será convertido.
	 * @return zero se for o timestamp for nulo ou o valor respectivo ao mesmo.
	 */

	public static long timestamp(Timestamp timestamp)
	{
		if (timestamp == null)
			return 0;

		return timestamp.getTime();
	}

	/**
	 * Converte um valor numérico inteiro em um objeto do tipo timestamp.
	 * @param time valor numérico para ser convertido em objeto timestmap.
	 * @return nulo se for zero ou o objeto com o timestamp já definido.
	 */

	public static Timestamp timestamp(long time)
	{
		if (time == 0)
			return null;

		return new Timestamp(time);
	}

	/**
	 * Método para agilizar a chamada de currentTimeMillis().
	 * @return aquisição do tempo atual da máquina.
	 */

	public static long now()
	{
		return System.currentTimeMillis();
	}

	/**
	 * Converte um valor inteiro passado em segundos para milissegundos.
	 * @param seconds quantos segundos que devem ser convertidos.
	 * @return aquisição do tempo passado por parâmetro em milissegundos.
	 */

	public static int seconds(int seconds)
	{
		return seconds * 1000;
	}

	/**
	 * Converte um valor inteiro passado em minutos para milissegundos.
	 * @param minutes quantos minutos que devem ser convertidos.
	 * @return aquisição do tempo passado por parâmetro em milissegundos.
	 */

	public static int minutes(int minutes)
	{
		return minutes * 60000;
	}

	/**
	 * Converte um valor inteiro passado em minutos e segundos para milissegundos.
	 * @param minutes quantos minutos que devem ser convertidos.
	 * @param seconds quantos segundos que devem ser convertidos.
	 * @return aquisição do tempo passado por parâmetro em milissegundos.
	 */

	public static int minutes(int minutes, int seconds)
	{
		return minutes(minutes) + seconds(seconds);
	}

	/**
	 * Converte um valor inteiro passado em horas para milissegundos.
	 * @param hours quantas horas que devem ser convertidos.
	 * @return aquisição do tempo passado por parâmetro em milissegundos.
	 */

	public static int hours(int hours)
	{
		return hours * 3600000;
	}

	/**
	 * Converte um valor inteiro passado em horas e minutos para milissegundos.
	 * @param hours quantas horas que devem ser convertidos.
	 * @param minutes quantos minutos que devem ser convertidos.
	 * @param seconds quantos segundos que devem ser convertidos.
	 * @return aquisição do tempo passado por parâmetro em milissegundos.
	 */

	public static int hours(int hours, int minutes)
	{
		return hours(hours) + minutes(minutes);
	}

	/**
	 * Converte um valor inteiro passado em horas, minutos e segundos para milissegundos.
	 * @param hours quantas horas que devem ser convertidos.
	 * @param minutes quantos minutos que devem ser convertidos.
	 * @param seconds quantos segundos que devem ser convertidos.
	 * @return aquisição do tempo passado por parâmetro em milissegundos.
	 */

	public static int hours(int hours, int minutes, int seconds)
	{
		return hours(hours) + minutes(minutes) + seconds(seconds);
	}
}
