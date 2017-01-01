package org.diverproject.console;

/**
 * <h1>Ações do Console</h1>
 *
 * <p>Interface usada pelo console para permitir que uma escuta do console possa acessá-lo.
 * Esse acesso é limitado a algumas operações aqui especificadas que o console implementa.
 * As ações consistem em permitir limpar a tela do console e trabalhar as mensagens dele.</p>
 *
 * @author Andrew
 */

public interface ConsoleActions
{
	/**
	 * Limpa a tela do console removendo todos os caracteres que já foram exibidos no mesmo.
	 */

	void clear();

	/**
	 * Permite habilitar a utilização de trace durante a exibição das mensagens no console.
	 * Trace irá exibir o nome da classe que está solicitando a exibição da mensagem.
	 * @param enable true para habilitar o uso do trace ou false caso contrário.
	 */

	void setTrace(boolean enable);

	/**
	 * Define uma mensagem para que esta seja exibida no console após o fim da chamada.
	 * @param format formato do qual deve possuir a mensagem a ser exibida.
	 * @param args argumentos referentes a formatação se assim houver.
	 */

	void setMessage(String format, Object... args);

	/**
	 * Usar esse método fará com que o console use uma quebra de linha ao final da mensagem.
	 */

	void breakLine();
}
