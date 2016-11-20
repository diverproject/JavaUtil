package org.diverproject.log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JOptionPane;

/**
 * <h1>Registro em Arquivo</h1>
 *
 * <p>Classe que contém procedimentos que permite que a biblioteca registre mensagens em arquivo.
 * Inicialmente deverá possuir apenas um método que é de imprimir as mensagens de log no mesmo.</p>
 *
 * @author Andrew
 */

public class LogFile
{
	/**
	 * Deve imprimir um determinado registro no arquivo especificado nas preferências.
	 * Se por algum motivo o writer não for obtido com êxito o método não fará nada.
	 * @param log referência do objeto contendo as informações do registro.
	 */

	public static void print(Log log)
	{
		String formated = String.format(Locale.US, log.toString());
		BufferedWriter writer = LogSystem.getBufferedWrite();

		if (writer == null)
			return;

		try {

			writer.write(formated);
			writer.flush();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "LogFile", JOptionPane.ERROR_MESSAGE);
		}
	}
}
