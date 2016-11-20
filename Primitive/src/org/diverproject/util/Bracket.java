package org.diverproject.util;

/**
 * Bracket
 *
 * Classe que permite trabalhar na contagem para de parêntese, colchete e chave.
 * Possuindo métodos que irão fazer a contagem desses quando forem abertos ou fechados.
 * Essa contagem é salva internamente para que outros métodos possam processá-los.
 *
 * Esses métodos permitem verificar se há ou não algum bracket que foi aberto e não fechou.
 * Além disso há outros que verificam se foi verificado mais fechamento do que abertura.
 * Como também faz o oposto deste que é verificar se há algum em aberto sem fechar.
 *
 * @author Andrew
 */

public class Bracket
{
	/**
	 * Contagem de chaves.
	 */
	int braces;

	/**
	 * Contagem de colchetes.
	 */
	int brackets;

	/**
	 * Contagem de parênteses.
	 */
	int parenthesis;

	/**
	 * Verifica se um caracter é um tipo de abertura seja lá qual for esse.
	 * @param c caracter do qual será verificado se é uma abertura.
	 */

	public void parseOpen(char c)
	{
		if (c == '(')
			parenthesis++;

		else if (c == '[')
			brackets++;

		else if (c == '{')
			braces++;
	}

	/**
	 * Verifica se um caracter é um tipo de fechamento seja lá qual for esse.
	 * @param c caracter do qual será verificado se é um fechamento.
	 */

	public void parseClose(char c)
	{
		if (c == '}')
			braces--;

		else if (c == ']')
			brackets--;

		else if (c == ')')
			parenthesis--;
	}

	/**
	 * Faz a verificação para garantir se há algum que foi aberto e não foi fechado.
	 * @return true se houver um seja lá qual for esse ou false caso não haja nenhum.
	 */

	public boolean has()
	{
		return braces != 0 || brackets != 0 || parenthesis != 0;
	}

	/**
	 * Verifica se há mais fechamentos do que aberturas, isso nunca pode ocorrer.
	 * @throws UtilException mensagem respectiva a qual tipo de fechamento que tem muito.
	 */

	public void isMuch() throws UtilException
	{
		if (parenthesis < 0)
			throw new UtilException("muitos parenteses");

		if (brackets < 0)
			throw new UtilException("muitos conchetes");

		if (braces < 0)
			throw new UtilException("muitas chaves");
	}

	/**
	 * Verifica se há mais aberturas do que fechamentos, isso não pode acontecer no final.
	 * @throws UtilException mensagem respectiva a qual tipo de abertura que tem muito.
	 */

	public void isLittle() throws UtilException
	{
		if (parenthesis > 0)
			throw new UtilException("falta parenteses");

		if (brackets > 0)
			throw new UtilException("falta conchetes");

		if (braces > 0)
			throw new UtilException("falta chaves");			
	}

	@Override
	public String toString()
	{
		return String.format("Bracket[braces: %d, bracket: %d, parenthesis: %d]", braces, brackets, parenthesis);
	}
}
