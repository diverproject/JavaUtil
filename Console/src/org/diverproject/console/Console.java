package org.diverproject.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.diverproject.util.collection.List;
import org.diverproject.util.collection.abstraction.DynamicList;
import javax.swing.JMenuBar;

/**
 * <h1>Console</h1>
 *
 * <p>A utilização do console permite exibir uma janela que possui um campo de texto e área de texto.
 * Para que seja possível usá-lo será necessário implementado por outra classe através de herança.
 * Assim todos os métodos estarão sendo implementados e poderão ser utilizados sem problemas.</p>
 *
 * <p>O campo de texto irá permitir executar comandos que devem ser especificado pelo desenvolvedor.
 * Os comandos podem ser interpretados de diversas formas, onde todo o conteúdo digitado no campo
 * pode ser obtido através da utilização da escuta do console, que irá repassar o texto a um método.</p>
 *
 * <p>A área de texto pode ser usada através de interface de ações do console na escuta para console.
 * Essas ações irá permitir que nessa área de texto seja exibido mensagens relativas aos comandos.
 * podendo ainda limpar o mesmo ou definir cores para tornar as mensagens mais dinâmicas e nítidas.</p>
 *
 * @see ConsoleListener
 *
 * @author Andrew
 */

@SuppressWarnings("serial")
public abstract class Console extends JFrame
{
	/**
	 * Painel para exibição de textos resultantes dos comandos do console.
	 */
	private ConsolePanel consolePanel;

	/**
	 * Campo de texto para receber as entradas referentes aos comandos.
	 */
	private JTextField input;

	/**
	 * Painel para rolagem do painel de texto que será usado.
	 */
	private JScrollPane scrollPane;

	/**
	 * Lista contendo as escutas que o console deverá considerar.
	 */
	private List<ConsoleListener> listeners;

	/**
	 * Usar trim para remover espaços no começo e fim do comando.
	 */
	private boolean useTrimInput;

	/**
	 * Limpar o conteúdo da entrada (comando) após ser executado.
	 */
	private boolean clearInput;

	/**
	 * Cria um novo console e deve inicializar seus componentes mínimos.
	 * Os componentes instanciados são de janela, campo de texto e outros.
	 * Deverá ainda definir por padrão que a entrada será limpa após o comando.
	 */

	public Console()
	{
		initConsole();
	}

	/**
	 * Procedimento interno chamado pelo construtor que garante a existência do console.
	 * Inicializa a janela definindo algumas preferências de utilização para o console.
	 * Como também cria o painel de texto, campo de texto painel de rolagem e afins.
	 */

	protected void initConsole()
	{
		setTitle("Console");
		setSize(1024, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setFocusable(true);

		input = new JTextField();
		input.setFont(ConsolePanel.DEFAULT_FONT);
		input.setOpaque(false);
		input.setForeground(ConsolePanel.DEFAULT_COLOR);
		input.setCaretColor(Color.WHITE);
		input.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					enterInput();
			}
		});
		getContentPane().add(input, BorderLayout.SOUTH);

		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				if (consolePanel != null && consolePanel.wasPrinted())
					e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		/*scrollPane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});*/
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().setBackground(Color.BLACK);

		setJMenuBar(createMenuBar());
	}

	protected abstract JMenuBar createMenuBar();

	/**
	 * Adiciona uma nova escuta ao console para que seja possível executar os comandos.
	 * Sempre que um comando for detectado no console, irá passar para esses escudas.
	 * @param listener referência do objeto que possui a implementação da escuta.
	 */

	public void addListener(ConsoleListener listener)
	{
		if (listeners == null)
			listeners = new DynamicList<ConsoleListener>();

		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	/**
	 * Remove um objeto que possui a implementação de escuta do console do mesmo.
	 * Uma vez removido os comandos aqui executados não serão repassado para este.
	 * @param listener referência do objeto que possui a implementação da escuta.
	 */

	public void removeListener(ConsoleListener listener)
	{
		if (listeners != null && listeners.contains(listener))
		{
			listeners.remove(listener);

			if (listeners.size() == 0)
				listeners = null;
		}
	}

	/**
	 * Ao ser chamado torna o console visível (caso não esteja) como o trás para frente.
	 * Ao trazer para frente também solicita que o campo de texto do comando seja focado.
	 */

	@Override
	@SuppressWarnings("deprecation")
	public void show()
	{
		super.show();

		toFront();
		requestFocus();
		input.requestFocus();
	}

	/**
	 * Ao chamar o método para esconder, irá ocultar a janela do console.
	 * Quando oculto, não será possível digitar comandos, já que não estará visível.
	 */

	@Override
	@SuppressWarnings("deprecation")
	public void hide()
	{
		super.hide();
	}

	/**
	 * Procedimento chamado automaticamente sempre que a tecla enter for pressionada na caixa de texto.
	 * Assim será possível que o console execute as operações especificadas conforme o conteúdo digitado.
	 */

	public void enterInput()
	{
		callListeners();

		if (consolePanel != null)
		{
			consolePanel.callPrintMessage();
			consolePanel.callResetPreferences();
		}

		callDefaultCommands();
		callClearInput();				
	}

	/**
	 * Permite definir se a entrada (campo de texto) que recebe os comandos será limpa.
	 * Se habilitado, após executar o comando o campo de texto deverá ser limpo.
	 * @param enable true para habilitar ou false para desativar.
	 */

	public void setClearInput(boolean enable)
	{
		clearInput = enable;
	}

	/**
	 * Define que o console deverá usar a operação de trim após a execução do comando.
	 * A operação trim deverá remover espaços que estejam no começo e fim do comando.
	 * @param enable true para habilitar ou false para desativar.
	 */

	public void useTrimInput(boolean enable)
	{
		useTrimInput = enable;
	}

	/**
	 * Procedimento interno que irá chamar todos os listeners para processar a mensagem.
	 */

	public void callListeners()
	{
		String text = input.getText();

		if (text.length() > 0)
		{
			if (useTrimInput)
				text = text.trim();

			if (listeners != null)
				for (ConsoleListener listener : listeners)
					listener.trigger(text, consolePanel);
		}
	}

	/**
	 * Procedimento interno usado para verificar se algum comando do console foi usado.
	 * Os comandos disponíveis são de <b>exit</b> para fechar e <b>clear</b> para limpar.
	 */

	public void callDefaultCommands()
	{
		switch (input.getText())
		{
			case "exit":
				hide();
				break;

			case "clear":
				consolePanel.clear();
				break;
		}
	}

	/**
	 * Procedimento interno usado para limpar todos os caracteres exibidos no console.
	 */

	public void callClearInput()
	{
		if (clearInput)
			input.setText("");
	}

	/**
	 * Envia uma mensagem ao painel de console para que esta seja exibido no mesmo.
	 * O painel a ser considerado será aquele que estiver sendo usado no momento.
	 * @param message mensagem do qual deseja que o painel venha a exibir no console.
	 */

	public void print(String message)
	{
		consolePanel.setMessage(message);
		consolePanel.callPrintMessage();
	}

	/**
	 * Permite definir um painel de console para ser exibido na janela do console.
	 * No momento em que este for definido para ser utilizado já será exibido.
	 * @param consolePanel novo painel de console que deve ser exibido.
	 */

	public void setConsolePanel(ConsolePanel consolePanel)
	{
		this.consolePanel = consolePanel;
		this.scrollPane.setViewportView(consolePanel);
		this.repaint();
	}
}
