package org.diverproject.util;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * <p><h1>Utilitário para Exibir Mensagem</h1></p>
 *
 * <p>Métodos que permitem exibir caixas de diálogos da biblioteca JOptionPane.
 * Estruturado apenas para definir título e mensagem, o ícone que será exibido
 * depende do método do qual está sendo chamado, aviso, informativo ou outro.</p>
 *
 * <p>Também permite definir uma variável pública e estática para determinar qual
 * o componente que deve ser utilizado para ser anexado ao diálogo que deve ser
 * exibido. Quando alterado todos diálogos seguintes irão utilizar este.</p>
 *
 * @see JOptionPane
 */

public class MessageUtil
{
	/**
	 * Componente que deve ser anexado aos diálogos seguintes.
	 */
	public static Component MESSAGE_UTIL_COMPONENT;

	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private MessageUtil()
	{
		
	}

	/**
	 * Exibe uma caixa d diálogo do JOptionPane com o ícone "erro"
	 * exibindo o tipo de exceção ocorrido e a mensagem da exceção.
	 * @param e exceção que deverá ser exibida no diálogo.
	 */

	public static void showException(Exception e)
	{
		e.printStackTrace();
		JOptionPane.showMessageDialog(MESSAGE_UTIL_COMPONENT, e.getMessage(), e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Exibe uma caixa de diálogo do JOptionPane com um ícone "informação".
	 * @param title título do diálogo que será aberto.
	 * @param message mensagem que será exibida no diálogo.
	 * @param args argumento referenciados pela formatação especificada no formato de message.
	 */

	public static void showInfo(String title, String message, Object... args)
	{
		JOptionPane.showMessageDialog(MESSAGE_UTIL_COMPONENT, String.format(message, args), title, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Exibe uma caixa de diálogo do JOptionPane com um ícone "aviso".
	 * @param title título do diálogo que será aberto.
	 * @param message mensagem que será exibida no diálogo.
	 * @param args argumento referenciados pela formatação especificada no formato de message.
	 */

	public static void showWarning(String title, String message, Object... o)
	{
		JOptionPane.showMessageDialog(MESSAGE_UTIL_COMPONENT, String.format(message, o), title, JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Exibe uma caixa de diálogo do JOptionPane com um ícone "erro".
	 * @param title título do diálogo que será aberto.
	 * @param message mensagem que será exibida no diálogo.
	 * @param args argumento referenciados pela formatação especificada no formato de message.
	 */

	public static void showError(String title, String message, Object... o)
	{
		JOptionPane.showMessageDialog(MESSAGE_UTIL_COMPONENT, String.format(message, o), title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Exibe uma caixa de diálogo do JOptionPane com duas opções ('ok' e 'cancelar').
	 * @param title título do diálogo que será aberto.
	 * @param message mensagem que será exibida no diálogo.
	 * @param args argumento referenciados pela formatação especificada no formato de message.
	 * @return true caso seja pressionado OK ou false se pressionado CANCELAR.
	 */

	public static boolean showYesNo(String title, String message, Object... o)
	{
		return JOptionPane.showConfirmDialog(MESSAGE_UTIL_COMPONENT, String.format(message, o), title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}
	/**
	 * Exibe uma caixa de diálogo do JOptionPane para entrada de dados.
	 * @param title título do diálogo que será aberto.
	 * @param message mensagem que será exibida no diálogo.
	 * @param args argumento referenciados pela formatação especificada no formato de message.
	 * @return valor inserido no campo de texto exibido.
	 */

	public static String showInput(String title, String message, Object... o)
	{
		return JOptionPane.showInputDialog(MESSAGE_UTIL_COMPONENT, String.format(message, o), title, JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Exibe uma caixa de diálogo do JOptionPane para exibir uma exceção fatal.
	 * Nesse caso após exibir a mensagem o sistema será encerrado automaticamente.
	 * @param e exceção do qual geral o término da aplicação.
	 */

	public static void die(Exception e)
	{
		showException(e);
		System.exit(0);
	}
}
