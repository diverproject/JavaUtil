package org.diverproject.util.collection;

import java.lang.reflect.Array;

import org.diverproject.util.lang.HexUtil;
import org.diverproject.util.lang.ShortUtil;
import org.diverproject.util.lang.StringUtil;

/**
 * <p><h1>Utilitário para Vetores</h1></p>
 *
 * <p>Classe do tipo utilitário, portanto deve possuir apenas procedimentos estáticos.
 * Seu construtor é privado de modo que não seja possível construir instâncias de tal.
 * Todos os procedimentos irão trabalhar com vetores ou algo similar e são públicos.</p>
 *
 * <p>Esse utilitário está na parte de coleções pois é o único lugar da biblioteca,
 * do qual irá trabalhar com vetores, que são usados internamente pelas coleções
 * para armazenar os dados nesta inseridos como deve ser feito as estruturas.</p>
 *
 * @author Andrew
 */

public class ArrayUtil
{
	/**
	 * Construtor privado para que não haja instâncias de ArrayUtil.
	 */

	private ArrayUtil()
	{
		
	}

	/**
	 * <p>Procedimento do qual deve fazer com que todos os elementos de um vetor,
	 * passam uma única casa (índice) para o lado esquerdo de modo que assim,
	 * o índice especificado seja considerado como excluído do vetor.</p>
	 * <p>No final desse procedimento o último elemento restante da lista estará
	 * duplicado devido a movimentação forçada dos elementos, por tanto este
	 * será definido sempre como null de modo que dê a impressão de movimentação
	 * de todos os elementos do vetor para a esquerda de uma única vez.</p>
	 * @param array vetor do qual terá um determinado índice removido.
	 * @param index número do índice do qual será perdido no vetor.
	 * @return true se conseguir mover ou false por vetor ou índice inválido.
	 */

	public static boolean moveLeft(Object[] array, int index)
	{
		if (index < 0 || index >= array.length)
			return false;

		for (int i = index; i < array.length - 1; i++)
			array[i] = array[i + 1];

		try {
			array[array.length - 1] = null;
		} catch (Exception e) {
			
		}

		return true;
	}

	/**
	 * <p>Procedimento do qual deve fazer com que todos os elementos de um vetor,
	 * passam uma única casa (índice) para o lado direito de modo que assim,
	 * o índice especificado seja considerado como adicionado no vetor.</p>
	 * <p>Relembrando de que o índice especificado será preenchido com o
	 * valor null para que seja visto como um espaço livre no vetor trabalhado.</p>
	 * @param array vetor do qual terá um determinado índice adicionado.
	 * @param index número do índice do qual será inserido no vetor.
	 * @return true se conseguir mover ou false por vetor ou índice inválido.
	 */

	public static boolean moveRight(Object[] array, int index)
	{
		if (index < 0 || index >= array.length)
			return false;

		for (int i = array.length - 1; i > index; i--)
			array[i] = array[i - 1];

		try {
			array[index] = null;
		} catch (Exception e) {
			
		}

		return true;
	}

	/**
	 * <p>Procedimento do qual irá redimensionar um determinado vetor especificado.
	 * Esse redimensionamento é a construção de um novo vetor com tamanho definido.
	 * Onde os elementos do vetor antigo serão passados para o novo vetor criado.</p>
	 * <p>Caso falte espaço últimos elementos serão perdidos, em quanto se faltar,
	 * os índices serão preenchidos com valores nulos ou zerado de acordo com o tipo.</p>
	 * @param array vetor que será redimensionado e copiado os elementos
	 * @param length comprimento do qual o novo vetor deverá possuir.
	 * @return vetor construído com o comprimento e elementos passados.
	 */

	public static Object[] resizeTo(Object[] array, int length)
	{
		Object old[] = array;
		array = new Object[length];

		for (int i = 0; i < old.length && i < array.length; i++)
			array[i] = old[i];

		return array;
	}

	/**
	 * <p>Procedimento do qual irá redimensionar um determinado vetor especificado.
	 * Esse redimensionamento é a construção de um novo vetor com tamanho maior.
	 * Onde os elementos do vetor antigo serão passados para o novo vetor criado.</p>
	 * <p>Neste caso como sempre será aumentado o tamanho, todos os índices novos
	 * serão preenchidos como valores automáticos do java (nulo ou zero).</p>
	 * @param array vetor que será redimensionado e copiado os elementos
	 * @param length comprimento do qual o novo vetor deverá possuir.
	 * @return vetor construído com o comprimento e elementos passados.
	 */

	public static Object[] increseIn(Object[] array, int length)
	{
		if (array == null || (long) (array.length + length) > Integer.MAX_VALUE)
			return null;

		Object old[] = array;
		Class<?> generic = Object.class;

		length += array.length;
		array = (Object[]) Array.newInstance(generic, length);

		for (int i = 0; i < old.length && i < array.length; i++)
			array[i] = old[i];

		return array;
	}

	/**
	 * <p>Procedimento do qual irá redimensionar um determinado vetor especificado.
	 * Esse redimensionamento é a construção de um novo vetor com tamanho maior.
	 * Onde os elementos do vetor antigo serão passados para o novo vetor criado.</p>
	 * <p>Neste caso como sempre será aumentado o tamanho, todos os índices novos
	 * serão preenchidos como valores automáticos do java (nulo ou zero).</p>
	 * @param generic tipo de dados que será usado para criar o vetor.
	 * @param array vetor que será redimensionado e copiado os elementos
	 * @param length comprimento do qual o novo vetor deverá possuir.
	 * @return vetor construído com o comprimento e elementos passados.
	 */

	@SuppressWarnings("unchecked")
	public static <T> T[] increseIn(Class<T> generic, T[] array, int length)
	{
		if (array == null || (long) (array.length + length) > Integer.MAX_VALUE)
			return null;

		T old[] = array;

		length += array.length;
		array = (T[]) Array.newInstance(generic, length);

		for (int i = 0; i < old.length && i < array.length; i++)
			array[i] = old[i];

		return array;
	}

	/**
	 * <p>Procedimento do qual irá redimensionar um determinado vetor especificado.
	 * Esse redimensionamento é a construção de um novo vetor com tamanho menor.
	 * Onde os elementos do vetor antigo serão passados para o novo vetor criado.</p>
	 * <p>Neste caso como sempre será reduzido o tamanho em caso de falta de espaço,
	 * ou seja, o novo tamanho é menor que a quantidade de elementos do antigo,
	 * esses elementos serão perdidos do último índice até o tamanho do novo vetor.</p>
	 * @param array vetor que será redimensionado e copiado os elementos
	 * @param length comprimento do qual o novo vetor deverá possuir.
	 * @return vetor construído com o comprimento e elementos passados.
	 */

	public static Object[] decreaseIn(Object[] array, int length)
	{
		if (array == null || length > array.length)
			return null;

		Object old[] = array;
		Class<?> generic = Object.class;

		length = array.length - length;
		array = (Object[]) Array.newInstance(generic, length);

		for (int i = 0; i < old.length && i < array.length; i++)
			array[i] = old[i];

		return array;
	}

	/**
	 * <p>Procedimento do qual irá redimensionar um determinado vetor especificado.
	 * Esse redimensionamento é a construção de um novo vetor com tamanho menor.
	 * Onde os elementos do vetor antigo serão passados para o novo vetor criado.</p>
	 * <p>Neste caso como sempre será reduzido o tamanho em caso de falta de espaço,
	 * ou seja, o novo tamanho é menor que a quantidade de elementos do antigo,
	 * esses elementos serão perdidos do último índice até o tamanho do novo vetor.</p>
	 * @param generic tipo de dados que será usado para criar o vetor.
	 * @param array vetor que será redimensionado e copiado os elementos
	 * @param length comprimento do qual o novo vetor deverá possuir.
	 * @return vetor construído com o comprimento e elementos passados.
	 */

	@SuppressWarnings("unchecked")
	public static <T> T[] decreaseIn(Class<T> generic, T[] array, int length)
	{
		if (array == null || length > array.length)
			return null;

		T old[] = array;

		length = array.length - length;
		array = (T[]) Array.newInstance(generic, length);

		for (int i = 0; i < old.length && i < array.length; i++)
			array[i] = old[i];

		return array;
	}

	/**
	 * Cria uma string concatenando todos os valores de um vetor de String com um separador.
	 * O separador é colocado entre dois valores concatenados, não utilizado no fim e inicio.
	 * @param array vetor contendo todos os valores de String à concatenar.
	 * @param separator String contendo o separador que será usando entre os valores.
	 * @return aquisição da string contendo os valores concatenados com separador.
	 */

	public static String join(String[] array, String separator)
	{
		if (array == null || array.length == 0)
			return "";

		String join = array[0];

		for (int i = 1; i < array.length; i++)
			join += separator + array[i];

		return join;
	}

	/**
	 * Cria uma string concatenando todos os valores de um vetor de byte com um separador.
	 * O separador é colocado entre dois valores concatenados, não utilizado no fim e inicio.
	 * @param array vetor contendo todos os valores de byte à concatenar.
	 * @param separator String contendo o separador que será usando entre os valores.
	 * @return aquisição da string contendo os valores concatenados com separador.
	 */

	public static String join(byte[] array, String separator)
	{
		return join(array, separator, false);
	}

	/**
	 * Cria uma string concatenando todos os valores de um vetor de byte com um separador.
	 * O separador é colocado entre dois valores concatenados, não utilizado no fim e inicio.
	 * @param array vetor contendo todos os valores de byte à concatenar.
	 * @param separator String contendo o separador que será usando entre os valores.
	 * @param hex determina que os valores em bytes devem ficar no formado hexadecimal.
	 * @return aquisição da string contendo os valores concatenados com separador.
	 */

	public static String join(byte[] array, String separator, boolean hex)
	{
		if (array == null || array.length == 0)
			return "";

		String join = hex ? joinByteHex(array[0]) : Byte.toString(array[0]);

		for (int i = 1; i < array.length; i++)
			join += separator + (hex ? joinByteHex(array[i]) : Byte.toString(array[i]));

		return join;
	}

	/**
	 * Cria uma string concatenando todos os valores de um vetor de short com um separador.
	 * O separador é colocado entre dois valores concatenados, não utilizado no fim e inicio.
	 * @param array vetor contendo todos os valores de short à concatenar.
	 * @param separator String contendo o separador que será usando entre os valores.
	 * @return aquisição da string contendo os valores concatenados com separador.
	 */

	public static String join(short[] array, String separator)
	{
		if (array == null || array.length == 0)
			return "";

		String join = Short.toString(array[0]);

		for (int i = 1; i < array.length; i++)
			join += separator + Short.toString(array[i]);

		return join;
	}

	/**
	 * Cria uma string concatenando todos os valores de um vetor de int com um separador.
	 * O separador é colocado entre dois valores concatenados, não utilizado no fim e inicio.
	 * @param array vetor contendo todos os valores de int à concatenar.
	 * @param separator String contendo o separador que será usando entre os valores.
	 * @return aquisição da string contendo os valores concatenados com separador.
	 */

	public static String join(int[] array, String separator)
	{
		if (array == null || array.length == 0)
			return "";

		String join = Integer.toString(array[0]);

		for (int i = 1; i < array.length; i++)
			join += separator + Integer.toString(array[i]);

		return join;
	}

	/**
	 * Cria uma string concatenando todos os valores de um vetor de long com um separador.
	 * O separador é colocado entre dois valores concatenados, não utilizado no fim e inicio.
	 * @param array vetor contendo todos os valores de long à concatenar.
	 * @param separator String contendo o separador que será usando entre os valores.
	 * @return aquisição da string contendo os valores concatenados com separador.
	 */

	public static String join(long[] array, String separator)
	{
		if (array == null || array.length == 0)
			return "";

		String join = Long.toString(array[0]);

		for (int i = 1; i < array.length; i++)
			join += separator + Long.toString(array[i]);

		return join;
	}

	/**
	 * Cria uma string concatenando todos os valores de um vetor de float com um separador.
	 * O separador é colocado entre dois valores concatenados, não utilizado no fim e inicio.
	 * @param array vetor contendo todos os valores de float à concatenar.
	 * @param separator String contendo o separador que será usando entre os valores.
	 * @return aquisição da string contendo os valores concatenados com separador.
	 */

	public static String join(float[] array, String separator)
	{
		if (array == null || array.length == 0)
			return "";

		String join = Float.toString(array[0]);

		for (int i = 1; i < array.length; i++)
			join += separator + Float.toString(array[i]);

		return join;
	}

	/**
	 * Cria uma string concatenando todos os valores de um vetor de double com um separador.
	 * O separador é colocado entre dois valores concatenados, não utilizado no fim e inicio.
	 * @param array vetor contendo todos os valores de double à concatenar.
	 * @param separator String contendo o separador que será usando entre os valores.
	 * @return aquisição da string contendo os valores concatenados com separador.
	 */

	public static String join(double[] array, String separator)
	{
		if (array == null || array.length == 0)
			return "";

		String join = Double.toString(array[0]);

		for (int i = 1; i < array.length; i++)
			join += separator + Double.toString(array[i]);

		return join;
	}

	/**
	 * Método interno para converter um valor de byte em um valor hexadecimal de 00 até FF.
	 * @param b valor do byte que será convertido para um valor hexadecimal.
	 * @return aquisição da string contendo o formato do byte em hexadecimal.
	 */

	private static String joinByteHex(byte b)
	{
		String hex = HexUtil.parseShort(ShortUtil.parseByte(b));
		hex = StringUtil.addStartWhile(hex, "0", 2);

		return hex;
	}
}
