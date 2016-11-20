package org.diverproject.util;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 * <p><h1>Barra de Progresso</h1></p>
 *
 * Exibe uma nova janela (JFrame) apenas uma barra de progresso.
 * A barra de progresso pode ser controlado pelos métodos nela definido.
 * - Iniciar: basta instanciar que ela será exibida automaticamente.
 * - Fechar: chamar o método dispose() irá remover a tela e liberar memória.
 * - Progresso: através de setRate() é possível determinar a taxa completada.
 *
 * @see JFrame
 * @see JProgressBar
 */

public class ProgressUtil
{
	/**
	 * Janela da barra de progresso.
	 */
	private JFrame frame;

	/**
	 * Barra de progresso que será exibida.
	 */
	private JProgressBar progress;

	/**
	 * Inicia a criação da janela definindo o tamanho padrão.
	 * Não permite redimensionar a janela com o mouse e deixa centralizada.
	 * Também não deixa fechar a janela através do fechar da janela.
	 * Exibe a janela automaticamente com a barra de progresso junto.
	 */

	public ProgressUtil()
	{
		frame = new JFrame();
		frame.setSize(520, 60);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		progress = new JProgressBar(0, 100);
		frame.setContentPane(progress);

		frame.setVisible(true);
	}

	/**
	 * Limpa a tela removendo a janela do mesmo.
	 * @return true se existir uma aberta ou false caso contrário.
	 */

	public boolean dispose()
	{
		if (frame == null)
			return false;

		frame.dispose();
		return true;
	}

	/**
	 * Define a taxa de progresso que a barra atingiu.
	 * Por padrão vai de 0 a 100 (%), se definido com um valor
	 * inválido, o valor de progresso completo não irá mudar.
	 * @param rate taxa de progresso.
	 * @return true se o valor for válido ou false caso contrário..
	 */

	public boolean setComplete(int rate)
	{
		if (rate < 0 || rate > 100)
			return false;

		progress.setValue(rate+1);
		return true;
	}

	/**
	 * Por padrão a taxa de progresso completo pode variar de 0 a 100 (%).
	 * @return taxa de progresso completa.
	 */

	public int getComplete()
	{
		return frame == null ? 0 : progress.getValue();
	}

	/**
	 * Permite definir/redefinir a barra de títulos da janela que será exibida.
	 * @param title título que será exibido na barra da janela.
	 * @return true se conseguir ou false caso não haja nenhuma janela criada.
	 */

	public boolean setTitle(String title)
	{
		if (frame == null)
			return false;

		frame.setTitle(title);
		return true;
	}
}
