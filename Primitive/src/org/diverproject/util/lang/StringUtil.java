package org.diverproject.util.lang;

import java.util.Iterator;
import java.util.Random;

import org.diverproject.util.UtilException;

/**
 * Classe com utilitário para trabalhar com variáveis do tipo string.
 * Verificar se string é alpha, numérico, alfanumérico, separar em
 * tamanhos específicos, obter uma única parte da string dentre outros
 * métodos que relacionam string. Na maioria dos métodos envolve
 * trabalhar com uma string retornando essa string que foi trabalhada.
 */

public class StringUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private StringUtil()
	{
		
	}

	/**
	 * Verifica se uma determinada string possui apenas letras.
	 * Este método ainda não possui validação para acentuações.
	 * @param str string que terá seus caracteres verificados.
	 * @return true se possuir apenas letras ou false caso contrário.
	 */

	public static boolean isAlpha(String str)
	{
		/** @TODO validar acentuação. */

		for (char c : str.toCharArray())
			if (!Character.isAlphabetic(c))
				return false;

		return true;
	}

	/**
	 * Obtem uma determinada parte da string, este método
	 * irá pegar n caracteres desde o inicio da string.
	 * Caso um tamanho maior do que o possível for definido
	 * não irá causar erro, apenas irá obter o limite permidio.
	 * @param str string que do qual deseja obter uma parte.
	 * @param length quantos caracteres serão pegos.
	 * @return parte da string que foi cortada.
	 */

	public static String sub(String str, int length)
	{
		return sub(str, 0, length);
	}

	/**
	 * Obtem uma determinada parte da string, este método
	 * irá pegar n caracteres desde o inicio da string.
	 * Caso um tamanho maior do que o possível for definido
	 * não irá causar erro, apenas irá obter o limite permidio.
	 * @param str string que do qual deseja obter uma parte.
	 * @param offset número do primeiro caracter.
	 * @param length quantos caracteres serão pegos.
	 * @return parte da string que foi cortada.
	 */

	public static String sub(String str, int offset, int length)
	{
		if (offset < 0)
			offset = 0;

		if (offset + length > str.length())
			length = str.length() - offset;

		char selected[] = CharUtil.subarray(str.toCharArray(), offset, length);

		return new String(selected);
	}

	/**
	 * Adiciona um determinado valor ao inicio de uma string.
	 * O valor é adicionado até que a string obtenha um tamanho
	 * especificado, caso o tamanho seja maior ou igual cancela.
	 * @param str string do qual terá o valor adicionado no inicio.
	 * @param value valor que será adicionado um valor ao inicio.
	 * @param length qual o tamanho que a string deve possuir.
	 * @return string modificada com o(s) valor(es) adicionado(s).
	 */

	public static String addStartWhile(String str, String value, int length)
	{
		while (str.length() < length)
			str = value + str;

		return str;
	}

	/**
	 * Adiciona um determinado valor ao final de uma string.
	 * O valor é adicionado até que a string obtenha um tamanho
	 * especificado, caso o tamanho seja maior ou igual cancela.
	 * @param str string do qual terá o valor adicionado no final.
	 * @param value valor que será adicionado um valor ao final.
	 * @param length qual o tamanho que a string deve possuir.
	 * @return string modificada com o(s) valor(es) adicionado(s).
	 */

	public static String addEndWhile(String str, String value, int length)
	{
		while (str.length() < length)
			str += value;

		return str;
	}

	/**
	 * Adiciona um determinado valor ao inicio de uma string.
	 * O valor é adicionado até que o tamanho da string seja divisível
	 * por um determinado número, onde o seu resto é zero (MOD %).
	 * @param str string do qual será adicionado um valor ao inicio.
	 * @param value valor que será adicionado um valor ao inicio.
	 * @param mod número pelo qual o tamanho deverá ser divísivel.
	 * @return string modificada com o(s) valor(es) adicionado(s).
	 */

	public static String addStartMod(String str, String value, int mod)
	{
		while (str.length() % mod != 0)
			if (str.length() + value.length() > Integer.MAX_VALUE)
				return str;
			else
				str = value + str;

		return str;
	}

	/**
	 * Adiciona um determinado valor ao final de uma string.
	 * O valor é adicionado até que o tamanho da string seja divisível
	 * por um determinado número, onde o seu resto é zero (MOD %).
	 * @param str string do qual será adicionado um valor ao final.
	 * @param value valor que será adicionado um valor ao final.
	 * @param mod número pelo qual o tamanho deverá ser divísivel.
	 * @return string modificada com o(s) valor(es) adicionado(s).
	 */

	public static String addEndMod(String str, String value, int mod)
	{
		while (str.length() % mod != 0)
			if (str.length() + value.length() > Integer.MAX_VALUE)
				return str;
			else
				str += value;

		return str;
	}

	/**
	 * Divide uma determinada string a cada n caracteres.
	 * @param str string que será divida a cada n caracteres.
	 * @param numOfChars a cada quantos caracteres deverá ser divido.
	 * @return vetor com a partes da string divida.
	 */

	public static String[] split(String str, int numOfChars)
	{
		if (numOfChars < 0)
			numOfChars = 0;

		if (numOfChars > str.length())
			return new String[] { str };

		if (str == null || str.length() < numOfChars)
			return null;

		if (str.length() == 0)
			return new String[]{ "" };

		char chars[] = str.toCharArray();
		String parse[] = new String[(int) Math.ceil((double) ((double)str.length() / (double)numOfChars))];

		for (int i = 0; i < parse.length; i++)
			parse[i] = new String(CharUtil.subarray(chars, i * numOfChars, numOfChars));

		return parse;
	}

	/**
	 * Remove o inicio de uma string enquanto iniciar com um valor.
	 * <i>Foi criado para remover zeros do inicio de valores binários</i>.
	 * @param str string que terá o seu inicio removido.
	 * @param value valor que deverá possuir o inicio.
	 * @return string com o inicio removido se assim for possível.
	 */

	public static String remInitWhile(String str, String value)
	{
		while (str.startsWith(value))
			str = str.substring(1, str.length() - 1);

		return str;
	}

	/**
	 * Verifica se uma determinada string pode ser analisada por outros procedimentos.
	 * @param str qual será a string que deve ser verificada se pode ser analisada.
	 * @throws UtilException string com valor nulo ou ainda então em branco.
	 */

	public static void checkParse(String str) throws UtilException
	{
		if (str == null)
			throw new UtilException("valor nulo");

		if (str.isEmpty())
			throw new UtilException("valor em branco");
	}

	/**
	 * Verifica se uma determinada string é composta
	 * apenas por caracteres numéricos [0~9].
	 * @param str string que será verificada.
	 * @return true se for ou false caso contrário.
	 */

	public static boolean isNumber(String str)
	{
		if (str.startsWith("-"))
			str = str.substring(1, str.length());

		if (str == null || str.length() == 0)
			return false;

		for (char c : str.toCharArray())
			if (!Character.isDigit(c))
				return false;

		return true;
	}

	/**
	 * Verifica se uma determinada string contém apenas valores numéricos e no máximo um ponto decimal.
	 * Nesse caso considera que o ponto decimal possa ser tanto como ponto quanto vírgula.
	 * @param str string contendo o valor que será verificado se é ou não numérico.
	 * @throws UtilException valor nulo, em branco, muitos pontos decimais ou não numérico.
	 */

	public static void isParseNumber(String str) throws UtilException
	{
		checkParse(str);

		if (str.startsWith("-"))
			str = str.substring(1, str.length());

		int dots = 0;

		for (char c : str.toCharArray())
		{
			if (c == '.' || c == ',')
			{
				if (dots == 1)
					throw new UtilException("muitos pontos");

				dots = 1;
			}

			else if (c < '0' || c > '9')
				throw new UtilException("não numérico");
		}
	}

	/**
	 * Verifica se uma determinada string é composta
	 * apenas por caracteres numéricos [0~9] e pode
	 * possuir apenas um único ponto (.) para seprar
	 * valores após a vírugla (próprio da linguagem java).
	 * @param str string que será verificada.
	 * @return true se for ou false caso contrário.
	 */

	public static boolean isFloat(String string)
	{
		if (string == null || string.length() == 0 || string.split(".").length > 2)
			return false;

		for (char c : string.toCharArray())
			if (!Character.isDigit(c) && c != '.')
				return false;

		return true;
	}

	/**
	 * Cria um código aleatório com caracteres alpha-numéricos,
	 * ou seja apenas letras e números. A chance de de vir uma
	 * letra ou número é de 50% para cada lado.
	 * @param size quantos dígitos deve possuir o código.
	 * @return string com o código gerado.
	 */

	public static String genCode(int size)
	{
		String code = "";
		Random random = new Random();

		while (code.length() < size)
		{
			if (random.nextInt(2) == 0)
				code += (char) (65 + random.nextInt(26));
			else
				code += (char) (48 + random.nextInt(10));
		}

		return code;
	}

	/**
	 * Permite adicionar um determinado caracter no meio de uma String,
	 * de modo que seja definido qual a posição na string que ele deve
	 * ser inserido, índice no vetor de caracteres da stirng. Caso o
	 * índice definido seja menor que 0 (zero) ou maior que o tamanho
	 * da string, nada irá acontecer com a string (posição inválida).
	 * @param string qual a string na qual será inserido.
	 * @param c caracter que deve ser inserido na string.
	 * @param index índice da posição em que deve ser adicionado.
	 * @return string com o caracter adicionado na posição escolhida.
	 */

	public static String addIn(String string, char c, int index)
	{
		if (index < 0 || index > string.length())
			return string;

		else if (index == 0 && string.length() > 0)
			return c + string;

		else if (index == string.length())
			return string + c;

		return sub(string, 0, index) + c + sub(string, index, string.length() - index);
	}

	/**
	 * Faz a funcionalidade de apagar um caracter de uma determinada string,
	 * como o botão do teclado BackSpace. Através da posição do caracter desejado
	 * a ser apagado, esta ação é possível ser realizada. Caso a posição seja
	 * menor que 1 (un) ou maior que o tamanho da string nada irá acontecer,
	 * pois estas posições são inválidas para realizar a operação.
	 * @param string qual a string que terá um caracter apagado.
	 * @param index índice do caracter da string que deve ser apagado.
	 * @return string com o caracter do índice definido apagado.
	 */

	public static String backspace(String string, int index)
	{
		if (index > 0 && index <= string.length())
			return sub(string, 0, index - 1) + sub(string, index, string.length() - index);

		return string;
	}

	/**
	 * Faz a funcionalidade de excluír um caracter de uma determinada string,
	 * como o botão do teclado Delete. Através da posição do caracter desejado
	 * a ser apagado, esta ação é possível ser realizada. Caso a posição seja
	 * menor que 0 ou maior/igual ao tamanho da string nada irá acontecer,
	 * pois estas posições são inválidas para realizar a operação.
	 * @param string qual a string que terá um caracter excluído.
	 * @param index índice do caracter da string que deve ser excluído.
	 * @return string com o caracter do índice definido excluído.
	 */

	public static String delete(String string, int index)
	{
		if (index >= 0 && index < string.length())
			return sub(string, 0, index) + sub(string, index + 1, string.length() - index);

		return string;
	}

	/**
	 * Constrói uma string a partir de um vetor de bytes, a diferença deste para o
	 * método padrão que o primeiro byte nulo (0) encontrado será o final da string.
	 * @param bytes vetor contendo os bytes que serão utilizados para criar a string.
	 * @return string criada a partir dos bytes passados até o limite antes do byte nulo.
	 */

	public static String parseBytes(byte[] bytes)
	{
		int i = 0;

		for (; bytes[i] != 0; i++);

		if (i != bytes.length)
			return new String(ByteUtil.subarray(bytes, 0, i));

		return new String(bytes);
	}

	/**
	 * Constrói uma string a partir dos bytes de um determinado número inteiro.
	 * @param value número inteiro do qual deve ser construído a string.
	 * @return string construída a partir dos bytes do número inteiro.
	 */

	public static String parseInt(int value)
	{
		return new String(ByteUtil.parseInt(value));
	}

	/**
	 * Constrói uma string contendo apenas espaçamentos de tabulação (\t).
	 * @param length quantas tabulações devem ser feitas na string.
	 * @return string contendo todas as tabulações.
	 */

	public static String indent(int length)
	{
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < length; i++)
			builder.append("\t");

		return builder.toString();
	}

	/**
	 * Retorna uma string contendo um conteúdo limitado de uma determinada string.
	 * Nesse caso verifica se a string e null retornando um "null", como também
	 * permite que não haja problema ao executar substring por length muito grande.
	 * @param str string do qual deve ser simplificada o seu conteúdo.
	 * @param length quantos caracteres da string serão aceitos.
	 * @return string com o conteúdo simplificado.
	 */

	public static Object less(String str, int length)
	{
		if (str == null)
			return "null";

		if (str.length() < length)
			length = str.length();

		return str.substring(0, length);
	}

	/**
	 * Constrói uma string para ser usada no método toString de um determinado objeto.
	 * A ideia é que essa string contenha os valores do vetor separados por vírgula.
	 * @param objects vetor contendo os objetos que serão convertidos para string.
	 * @return string contendo o valor dos objetos separados por vírgula.
	 */

	public static String toString(Object[] objects)
	{
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < objects.length; i++)
		{
			str.append(objects[i]);

			if (i != objects.length - 1)
				str.append(", ");
		}

		return str.toString();
	}

	/**
	 * Constrói uma string para ser usada no método toString de um determinado objeto.
	 * A ideia é que essa string contenha os valores do vetor separados por vírgula.
	 * @param array vetor contendo os números que serão convertidos para string.
	 * @return string contendo o valor dos objetos separados por vírgula.
	 */

	public static Object toString(byte[] array)
	{
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < array.length; i++)
		{
			str.append(array[i]);

			if (i != array.length - 1)
				str.append(", ");
		}

		return str.toString();
	}

	/**
	 * Constrói uma string para ser usada no método toString de um determinado objeto.
	 * A ideia é que essa string contenha os valores do vetor separados por vírgula.
	 * @param array vetor contendo os números que serão convertidos para string.
	 * @return string contendo o valor dos objetos separados por vírgula.
	 */

	public static Object toString(char[] array)
	{
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < array.length; i++)
		{
			str.append(array[i]);

			if (i != array.length - 1)
				str.append(", ");
		}

		return str.toString();
	}

	/**
	 * Constrói uma string para ser usada no método toString de um determinado objeto.
	 * A ideia é que essa string contenha os valores do vetor separados por vírgula.
	 * @param array vetor contendo os números que serão convertidos para string.
	 * @return string contendo o valor dos objetos separados por vírgula.
	 */

	public static Object toString(short[] array)
	{
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < array.length; i++)
		{
			str.append(array[i]);

			if (i != array.length - 1)
				str.append(", ");
		}

		return str.toString();
	}

	/**
	 * Constrói uma string para ser usada no método toString de um determinado objeto.
	 * A ideia é que essa string contenha os valores do vetor separados por vírgula.
	 * @param array vetor contendo os números que serão convertidos para string.
	 * @return string contendo o valor dos objetos separados por vírgula.
	 */

	public static Object toString(int[] array)
	{
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < array.length; i++)
		{
			str.append(array[i]);

			if (i != array.length - 1)
				str.append(", ");
		}

		return str.toString();
	}

	/**
	 * Constrói uma string para ser usada no método toString de um determinado objeto.
	 * A ideia é que essa string contenha os valores do vetor separados por vírgula.
	 * @param array vetor contendo os números que serão convertidos para string.
	 * @return string contendo o valor dos objetos separados por vírgula.
	 */

	public static Object toString(long[] array)
	{
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < array.length; i++)
		{
			str.append(array[i]);

			if (i != array.length - 1)
				str.append(", ");
		}

		return str.toString();
	}

	/**
	 * Constrói uma string para ser usada no método toString de um determinado objeto.
	 * A ideia é que essa string contenha os valores do vetor separados por vírgula.
	 * @param array vetor contendo os números que serão convertidos para string.
	 * @return string contendo o valor dos objetos separados por vírgula.
	 */

	public static Object toString(float[] array)
	{
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < array.length; i++)
		{
			str.append(array[i]);

			if (i != array.length - 1)
				str.append(", ");
		}

		return str.toString();
	}

	/**
	 * Constrói uma string para ser usada no método toString de um determinado objeto.
	 * A ideia é que essa string contenha os valores do vetor separados por vírgula.
	 * @param array vetor contendo os números que serão convertidos para string.
	 * @return string contendo o valor dos objetos separados por vírgula.
	 */

	public static Object toString(double[] array)
	{
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < array.length; i++)
		{
			str.append(array[i]);

			if (i != array.length - 1)
				str.append(", ");
		}

		return str.toString();
	}

	/**
	 * Constrói uma string para ser usada no método toString de um determinado objeto.
	 * A ideia é que essa string contenha os valores da lista separados por vírgula.
	 * @param iteration iteração que será convertida para string usando toString.
	 * @return string contendo o valor dos objetos separados por vírgula.
	 */

	public static String toString(Iterator<?> iteration)
	{
		if (iteration == null)
			return "null";

		StringBuilder str = new StringBuilder();

		while (iteration.hasNext())
		{
			str.append(iteration.next());

			if (iteration.hasNext())
				str.append(", ");
		}

		return str.toString();
	}

	/**
	 * O padrão dos projetos DiverProject determina o seguinte: "mensagem (dados informativos)".
	 * Esse método irá fazer com que uma mensagem dessa permita inserir mais dados informativos.
	 * De modo simples, onde a mensagem já formatada será juntado a uma string com dados informativos.
	 * @param message string contendo a mensagem completa já gerada no sistema para ser modificada.
	 * @param data string contendo os dados que devem ser adicionados a essa mensagem em questão.
	 * @return string contendo a mensagem antiga adicionado dos dados informativos inseridos.
	 */

	public static String addStringData(String message, String data)
	{
		if (!message.contains("(") && !message.contains("("))
			return String.format("%s (%s)", message, data);

		else if (!message.contains("(") || !message.contains("("))
			return message;

		String content = message.substring(0, message.indexOf('(')).trim();
		String old = message.substring(message.indexOf('(') + 1, message.length() - 1).trim();

		return String.format("%s (%s, %s)", content, data, old);
	}

	/**
	 * O padrão dos projetos DiverProject determina o seguinte: "mensagem [exceção]".
	 * Esse método irá fazer com que uma mensagem já gerada considere o uso de uma determinada classe.
	 * De modo simples, onde a mensagem já formatada terá a o nome da classe que a gerou renomeada.
	 * @param message string contendo a mensagem completa já gerada no sistema para ser modificada.
	 * @param exception qual é a nova exceção do qual será usado o nome da classe para substituir.
	 * @return string contendo a mensagem antiga com o nome da exceção alterado de acordo.
	 */

	public static String addStringException(String message, Exception exception)
	{
		if (message == null)
			return exception.getMessage();

		String simpleName = exception.getClass().getSimpleName();

		if (!message.contains("[") && !message.contains("]"))
			return String.format("%s [%s]", message, simpleName);

		else if (!message.contains("[") || !message.contains("]"))
			return message;

		String content = message.substring(0, message.indexOf('[')).trim();

		return String.format("%s [%s]", content, simpleName);
	}

	/**
	 * Constrói uma string contendo apenas espaçamentos largos conhecidos como tabs de caracter \t.
	 * @param length quantos espaçamentos deverão ser colocados seguidamente na string.
	 * @return string construída a partir da quantidade de espaçamentos definido.
	 */

	public static String getTabs(int length)
	{
		char chars[] = new char[length];

		for (int i = 0; i < chars.length; i++)
			chars[i] = '\t';

		return new String(chars);
	}

	/**
	 * Converte um valor numérico para um valor monetário em string separando as casas.
	 * Por exemplo, um valor numérico de 1000000 seria repassado para "1.000.000".
	 * @param value valor do qual será convertido durante a formatação para string.
	 * @return aquisição da string formatada conforme o parâmetro passado.
	 */

	public static String money(long value)
	{
		boolean negative = value < 0;

		if (negative)
			value *= -1;

		String strv = Long.toString(value);
		String str = "";

		for (int i = 0; i < strv.length(); i++)
		{
			if (i % 3 == 0 && i > 0)
				str = "." + str;

			str = strv.charAt(strv.length() - i - 1) + str;
		}

		str = str.startsWith(".") ? str.substring(1, str.length()) : str;

		if (negative)
			str = "-" + str;

		return str;
	}

	/**
	 * Faz a contagem de quantas vezes um determinado caracter está presente em uma string.
	 * @param str string que será usada para verificar a quantidade de repetição do caracter.
	 * @param c caracter que deverá ser considerado na contagem quando iterar a string.
	 * @return quantidade de vezes que o caracter passado por parâmetro está presente na string.
	 */

	public static int countOf(String str, char c)
	{
		int count = 0;

		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) == c)
				count++;

		return count;
	}

	/**
	 * Faz a contagem de quantas vezes um determinado caracter está presente em uma string.
	 * @param str string que será usada para verificar a quantidade de repetição do caracter.
	 * @param c caracter que deverá ser considerado na contagem quando iterar a string.
	 * @return quantidade de vezes que o caracter passado por parâmetro está presente na string.
	 */

	public static int countOf(String str, String sequence)
	{
		int count = 0;

		for (int i = 0; i < str.length(); i++)
		{
			int repeated = 0;

			for (int j = 0; j < sequence.length(); j++, repeated++)
				if (!(i + j < str.length() && str.charAt(i + j) == sequence.charAt(j)))
				{
					repeated = 0;
					break;
				}

			if (repeated == sequence.length())
				count++;
		}

		return count;
	}

	/**
	 * Substitui diversas sequências de caracteres para uma outra determinada sequência de caracteres.
	 * @param str referência da string que terá o valor de caracteres alterado conforme abaixo.
	 * @param replacement qual será o valor usado para substituir as sequências a seguir.
	 * @param targets sequência de caracteres alvos que serão substituídas por <b>replacement</b>.
	 * @return aquisição de uma nova string contendo os caracteres atualizados conforme os parâmetros.
	 */

	public static String replace(String str, String replacement, String... targets)
	{
		for (String target : targets)
			str = str.replace(target, replacement);

		return str;
	}

	/**
	 * Procura em uma determinada string uma sequência de caracteres após n vezes encontrado.
	 * Por exemplo: saber quando aparecerá "0" pela segunda vez na string "012345<b>0</b>12345".
	 * @param str string contendo o conjunto de caracteres que será verificado no processo.
	 * @param sequence conjunto de caracteres contendo a sequência que deverá se repetir.
	 * @param position número da vez que será considerado quando a sequência aparecer.
	 * @return índice da sequência na vez especificada, -1 se não encontrar ou -2 caso
	 * haja um parâmetro inválido, número de vezes negativa ou strings nulas.
	 */

	public static int indexOf(String str, String sequence, int time)
	{
		if (time < 0 || str == null || sequence == null)
			return -2;

		for (int i = 0, j = 0, k = 0; i < str.length(); i++)
		{
			if (j == sequence.length())
			{
				k++;

				if (k == time)
					return i;

				j = 0;
			}

			if (str.charAt(i) == sequence.charAt(j))
				j++;
			else
				j = 0;
		}

		return -1;
	}
}
