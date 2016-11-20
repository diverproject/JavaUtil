package org.diverproject.util.stream;

/**
 * <h1>Leitor</h1>
 *
 * <p>Interface com o objetivo de permitir que uma classe possa ler dados em uma comunicação.
 * Os dados podem ser lidos em string como uma linha de um arquivo de texto simples ou então
 * em bytes que normalmente é usado por arquivos "codificados", lendo dados primitivos.</p>
 *
 * <p>Com essa interface, será possível facilitar a utilização da ideia de herança múltipla.
 * No caso, será usado a delegação em alguns casos, onde essa interface será usada como fronteira.
 * Assim, será possível fazer a leitura de dados na comunicação sem saber como ele será feita.</p>
 *
 * @see Stream
 *
 * @author Andrew Mello
 */

public interface Reader extends Stream
{
	/**
	 * Chamado para que a comunicação faça a leitura do próximo dado (byte) existente nela.
	 * Irá utilizar o ponteiro de leitura para saber qual o próximo dado que deve ser lido.
	 * No caso de arquivos, isso é feito internamente pela stream padrão do java.
	 * @return aquisição do próximo byte disponível para leitura na comunicação.
	 */

	byte read();

	/**
	 * Permite fazer a leitura de dados na comunicação de uma forma um pouco mais dinâmica.
	 * Em quanto não encontrar uma sequência de bytes correspondentes irá continuar a ler.
	 * @param sequence string contendo a sequência de caracteres (bytes) que será o seu fim.
	 * @return aquisição dos bytes até o final da sequência passada acima.
	 */

	byte[] readAt(String sequence);

	/**
	 * Utiliza o procedimento de leitura até uma determinada sequência de caracteres.
	 * Irá fazer uma leitura até que se encontre a quebra de linha indicando o final desta.
	 * @return aquisição de uma string contendo todos os bytes lidos da atual linha.
	 */

	String readLine();
}
