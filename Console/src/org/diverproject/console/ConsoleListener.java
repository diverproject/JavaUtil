package org.diverproject.console;

/**
 * <h1>Escuta para Console</h1>
 *
 * <p>Esse tipo de escuta é chamado a todo momento em que algo for digitado e confirmado no Console.
 * Irá chamar o único método que a escuta implementa e para este será passado a entrada do console.
 * Nesse método cada aplicação deverá especificar como os comandos serão identificados e aplicados.</p>
 *
 * @author Andrew
 *
 */

public interface ConsoleListener
{
	/**
	 * Procedimento único da escuta que será desencadeado no momento em que o console receber o enter.
	 * @param text texto que foi entrado no campo de texto do console para executar comandos.
	 * @param actions referência da interface que permite interagir com o console.
	 */

	void trigger(String text, ConsoleActions actions);
}
