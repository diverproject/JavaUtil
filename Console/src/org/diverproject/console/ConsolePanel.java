package org.diverproject.console;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import org.diverproject.util.UtilRuntimeException;
import org.diverproject.util.collection.StringExtend;

@SuppressWarnings("serial")
public class ConsolePanel extends JTextPane implements ConsoleActions
{
	/**
	 * Cor padrão que será usada para exibição dos textos no console.
	 */
	public static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;


	/**
	 * Documento referente ao painel de texto para estilizar (colorir).
	 */
	private Document document;

	/**
	 * Estilização do painel de texto que foi usado.
	 */
	private Style style;

	/**
	 * Utilizar quebra de linha após exibir a mensagem do comando.
	 */
	private boolean breakLine;

	/**
	 * Mostrar o nome da classe que executou as operações do comando.
	 */
	private boolean trace;

	/**
	 * Mensagem que será exibida após o final da execução do comando.
	 */
	private String message;

	public ConsolePanel()
	{
		style = addStyle("default", style);
		document = getDocument();
	}

	@Override
	public void clear()
	{
		try {
			document.remove(0, document.getLength());
		} catch (BadLocationException e) {
			throw new UtilRuntimeException("falha ao limpar console");
		}
	}

	@Override
	public void setTrace(boolean enable)
	{
		trace = enable;
	}

	@Override
	public void breakLine()
	{
		breakLine = true;
	}

	@Override
	public void setMessage(String format, Object... args)
	{
		String caller = getClass().getSimpleName();
		String message = String.format(format, args);

		if (trace)
		{
			Throwable throwable = new Throwable();
			StackTraceElement elements[] = throwable.getStackTrace();

			caller = elements[1].getClassName();
			caller = caller.substring(caller.lastIndexOf('.') + 1, caller.length());

			this.message = String.format("%s: %s", caller, message);
		}

		else
			this.message = message;
	}

	/**
	 * Procedimento interno usado para imprimir todos os caracteres da mensagem no console.
	 * Irá processar caracter por caracter afim de identificar possíveis comandos.
	 * Caso seja definido o uso de quebra de linhas será quebrada a linha ao final.
	 */

	public void callPrintMessage()
	{
		StringExtend se = new StringExtend(message);

		if (se.length() == 0)
			return;

		do {

			processCharacter(se);

		} while (se.next() && !se.finish());
		
		if (breakLine)
			printCharacter('\n');

		message = "";
	}

	/**
	 * Procedimento interno usado para processar um único caracter exibindo-o no console.
	 * Verifica se é uma caracter de escape para acionar uma função ou não.
	 * @param se referência do objeto contendo a iteração da string.
	 */

	private void processCharacter(StringExtend se)
	{
		switch (se.get())
		{
			case '\f':
				processFunction(se);
				break;

			default:
				printCharacter(se.get());
		}
	}

	/**
	 * Procedimento interno chamado quando o caracter processado for de escape para função.
	 * @param se referência do objeto contendo a iteração da string.
	 */

	private void processFunction(StringExtend se)
	{
		se.next();

		switch(se.get())
		{
			case 'c':
				processChangeColor(se);
				break;
		}
	}

	/**
	 * Procedimento interno chamado para realizar a função de alteração na cor do console.
	 * @param se referência do objeto contendo a iteração da string.
	 */

	private void processChangeColor(StringExtend se)
	{
		se.next();

		switch (se.get())
		{
			case 'b': setColor(Color.BLUE); break;
			case 'n': setColor(Color.CYAN); break;
			case 'k': setColor(Color.DARK_GRAY); break;
			case 'a': setColor(Color.GRAY); break;
			case 'g': setColor(Color.GREEN); break;
			case 'l': setColor(Color.LIGHT_GRAY); break;
			case 'm': setColor(Color.MAGENTA); break;
			case 'o': setColor(Color.ORANGE); break;
			case 'p': setColor(Color.PINK); break;
			case 'r': setColor(Color.RED); break;
			case 'w': setColor(Color.WHITE); break;
			case 'y': setColor(Color.YELLOW); break;
			default: setColor(null);
		}
	}

	/**
	 * Procedimento interno usado para definir qual será a cor usada para exibir o texto.
	 * @param color referência do objeto contendo os valores da cor em RGB.
	 */

	private void setColor(Color color)
	{
		if (color != null)
			StyleConstants.setForeground(style, color);
		else
			StyleConstants.setForeground(style, DEFAULT_COLOR);
	}

	/**
	 * Procedimento interno usado para imprimir um único caracter no console.
	 * @param c caracter do qual deverá ser imprimido na área do console.
	 */

	private void printCharacter(char c)
	{
		try {
			document.insertString(document.getLength(), Character.toString(c), style);
		} catch (BadLocationException e) {
			throw new UtilRuntimeException("falha ao escrever no console");
		}
	}

	/**
	 * Procedimento interno usado para restabelecer as configurações do console ao padrão.
	 * Esse padrão inclui em usar DEFAULT_COLOR na cor e quebra de linhas e trace false.
	 */

	public void callResetPreferences()
	{
		StyleConstants.setForeground(style, DEFAULT_COLOR);
		breakLine = false;
	}
}
