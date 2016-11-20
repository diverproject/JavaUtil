package org.diverproject.log;

import java.util.Locale;

/**
 * <h1>Registro em Console</h1>
 *
 * <p>Classe que contém procedimentos que permite que a biblioteca de registro interaja com o console.
 * Inicialmente deverá possuir apenas um método que é de imprimir as mensagens de log no mesmo.</p>
 *
 * @author Andrew
 */

public class LogConsole
{
	/**
	 * Deve imprimir um determinado registro no console natural da linguagem java.
	 * @param log referência do objeto contendo as informações do registro.
	 */

	public static void print(Log log)
	{
		System.out.printf(Locale.US, log.toString());
	}
}
