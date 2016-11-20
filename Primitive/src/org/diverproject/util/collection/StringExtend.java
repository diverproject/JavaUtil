package org.diverproject.util.collection;

import org.diverproject.util.lang.CharUtil;
import org.diverproject.util.lang.IntUtil;

/**
 * <p><h1>String Estendida</h1></p>
 *
 * <p>Estrutura de dados que permite trabalhar com uma única string ou conteúdo de uma.
 * Permite iniciar o conteúdo dessa string e inserir dados no final dessa mesma.
 * Além disso permite percorrer a string com métodos next() e back() dentre outros.</p>
 *
 * @author Andrew
 */

public class StringExtend
{
	/**
	 * Índice do caracter do nó atual.
	 */
	private int offset;

	/**
	 * Conteúdo respectivo do nó.
	 */
	private String string;

	/**
	 * Constrói um novo nó de caracteres com conteúdo em branco.
	 */

	public StringExtend()
	{
		this("");
	}

	/**
	 * Constrói um novo nó de caracteres através de uma string.
	 * @param str string que será usada como conteúdo do nó.
	 */

	public StringExtend(String str)
	{
		string = str;
		offset = 0;
	}

	/**
	 * Todo nó de caracter deve possuir um caracter armazenado no mesmo.
	 * @return aquisição do caracter do qual esse nó armazena.
	 */

	public char get()
	{
		return get(offset);
	}

	/**
	 * Permite obter um determinado caracter de um determinado índice.
	 * @param index índice do posicionamento do caracter.
	 * @return caracter respectivo ou null se não encontrar.
	 */

	public char get(int index)
	{
		if (index < 0 || index >= string.length())
			return 0;

		return string.charAt(index);
	}

	/**
	 * Verifica se o nó possui um próximo caracter, se houver substitui os dados pelo próximo.
	 * @return true se houver um próximo caracter ou false caso contrário.
	 */

	public boolean next()
	{
		if (finish())
			return false;

		offset++;

		return true;
	}

	/**
	 * Verifica se o nó possui um caracter anterior, se houver substitui os dados pelo anterior.
	 * @return true se houver um caracter anterior ou false caso contrário.
	 */

	public boolean back()
	{
		if (start())
			return false;

		offset--;

		return true;
	}

	/**
	 * Faz com que o ponteiro avance na string se assim for possível
	 * @return caracter atual do qual o ponteiro está se referindo.
	 */

	public char nextChar()
	{
		char c = get();
		next();

		return c;
	}

	/**
	 * Faz com que o ponteiro retroceda na string se assim for possível
	 * @return caracter atual do qual o ponteiro está se referindo.
	 */

	public char backChar()
	{
		char c = get();
		back();

		return c;
	}

	/**
	 * Verifica se esse nó está atualmente no primeiro elemento do mesmo.
	 * Nesse caso significa que não há mais caracteres anteriores.
	 * @return true se chegou ao fim ou false caso contrário.
	 */

	public boolean start()
	{
		return offset < 0;
	}

	/**
	 * Verifica se esse nó está atualmente no último elemento do mesmo.
	 * Nesse caso significa que não há mais caracteres seguintes.
	 * @return true se chegou ao fim ou false caso contrário.
	 */

	public boolean finish()
	{
		return offset >= string.length();
	}

	/**
	 * Faz com que o ponteiro na string retorne ao inicio do mesmo.
	 */

	public void restart()
	{
		offset = -1;
	}

	/**
	 * Faz com que o ponteiro da string avance para o fim do mesmo.
	 */

	public void terminate()
	{
		offset = string.length();
	}

	/**
	 * Permite inserir um determinado caracter no último nó.
	 * @param c character do qual deve ser inserido no último nó.
	 */

	public void insert(char c)
	{
		string += c;
	}

	/**
	 * Verifica se um determinado caracter é igual ao que está armazenado no nó.
	 * @param character qual o caracter que será verificado a igualdade.
	 * @return true se forem iguais ou false caso contrário.
	 */

	public boolean is(char character)
	{
		return get() == character;
	}

	/**
	 * Índice determina o ponteiro de navegação dentro do nó de caracteres.
	 * @return aquisição do índice desse nó de caracteres.
	 */

	public int offset()
	{
		return offset;
	}

	/**
	 * Comprimento é obtido através da string conteúdo usada.
	 * @return aquisição do comprimento da string estendida.
	 */

	public int length()
	{
		return string.length();
	}

	/**
	 * Faz com que o ponteiro se mova para uma determinada posição na string.
	 * Quando definido não permite passar do inicio ou do fim da string.
	 * Quando -1 significa que não foi iterado e quando no limita determina
	 * que a iteração já chegou ao fim e não há mais nada para iterar.
	 * @param offset nova posição (índice) do ponteiro na string.
	 */

	public void to(int offset)
	{
		this.offset = IntUtil.limit(offset, -1, string.length());
	}

	/**
	 * Constrói uma nova string com um determinado conteúdo a partir desse nó de caracteres.
	 * @param offset índice do primeiro caracter que deve ser usado nessa string.
	 * @param length quantos caracteres essa nova string deverá possuir.
	 * @return string contendo o conteúdo de acordo com os parâmetros passados,
	 * caso o índice seja inválido retorna string em branco, no caso de o tamanho
	 * for além do que é possível não irá retornar caracteres amais ou exception.
	 */

	public String cut(int offset, int length)
	{
		return new String(CharUtil.subarray(string.toCharArray(), offset, length));
	}

	/**
	 * Constrói uma nova string com o conteúdo próximo ao offset.
	 * @param length quantos caracteres a esquerda e a direita.
	 * @return string contendo os valores próximos ao offset.
	 */

	public Object fear(int length)
	{
		return new String(CharUtil.subarray(string.toCharArray(), offset - length, length * 2));
	}

	/**
	 * Acrescenta um determinado conteúdo especificado em string (caracteres) ao final.
	 * @param string referência do objeto contendo o conjunto de caracteres (string).
	 */

	public void append(String string)
	{
		this.string += string;
	}

	/**
	 * Em alguns casos pode ser necessário se obter o conteúdo direto da string.
	 * Isso irá ocorrer principalmente quando o método deleteAt for utilizado.
	 * @return aquisição direta do conteúdo que está sendo usado internamente.
	 */

	public String raw()
	{
		return this.string;
	}

	/**
	 * Excluir todo o conteúdo inicial da string interna até um ponto delimitado.
	 * Essa exclusão irá incluir o caracter especificado índice por parâmetro.
	 * @param c até qual caracter que será excluído, será usado como índice.
	 */

	public void deleteAt(char c)
	{
		deleteAt(c, 1);
	}

	/**
	 * Excluir todo o conteúdo inicial da string interna até um ponto delimitado.
	 * Essa exclusão irá incluir o caracter especificado índice por parâmetro.
	 * Nesse caso irá procurar uma caracter específico repetido em x vezes.
	 * @param c caracter do qual deverá ser encontrado para ser o índice.
	 * @param time número de repetições que deverá ocorrer desse caracter.
	 */

	public void deleteAt(char c, int time)
	{
		for (int i = 0; i < string.length(); i++)
		{
			if (string.charAt(i) == c)
				time--;

			if (time == 0)
				deleteAt(i);
		}
	}

	/**
	 * Excluir todo o conteúdo inicial da string interna até um ponto delimitado.
	 * Essa exclusão irá incluir o caracter especificado índice por parâmetro.
	 * @param index índice do caracter que será recortado do inicio até ele.
	 */

	public void deleteAt(int index)
	{
		if (offset < index)
			offset = 0;

		string = string.substring(index);
	}

	/**
	 * Procedimento interno que irá formatar o conteúdo adicionando chaves ao caracter ponteiro.
	 * Utilizado somente e diretamente por toString afim de facilitar a utilização do mesmo.
	 * @return aquisição de uma string formatada conforma as especificações do método.
	 */

	private String getCotent()
	{
		String before = string.substring(0, IntUtil.min(offset, 0));
		String after = string.substring(IntUtil.max(offset + 1, string.length()), string.length());

		if (start())
			return String.format("[]%s", after);

		else if (finish())
			return String.format("%s[]", before);

		return String.format("%s[%s]%s", before, get(), after);
	}

	@Override
	public String toString()
	{
		return String.format("CharNode[prev: %s, char: %s, next: %s, content: %s]", get(offset - 1), get(), get(offset + 1), getCotent());
	}
}
