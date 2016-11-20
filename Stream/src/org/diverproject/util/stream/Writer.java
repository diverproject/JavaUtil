package org.diverproject.util.stream;

/**
 * <h1>Escritor</h1>
 *
 * <p>Interface com o objetivo de permitir que uma classe possa escrever dados em uma comunicação.
 * Os dados podem ser escritos em string como uma linha de um arquivo de texto simples ou então
 * em bytes que normalmente é usado por arquivos "codificados", armazenar dados primitivos.</p>
 *
 * <p>Com essa interface, será possível facilitar a utilização da ideia de herança múltipla.
 * No caso, será usado a delegação em alguns casos, onde essa interface será usada como fronteira.
 * Assim, será possível fazer a escrita de dados na comunicação sem saber como ele será feita.</p>
 *
 * @see Stream
 *
 * @author Andrew Mello
 */

public interface Writer extends Stream
{
	/**
	 * Permite escrever um único byte na comunicação estabelecida de acordo com o ponteiro de escrita.
	 * No caso, o ponteiro de escrita sempre será o tamanho em que a comunicação se encontra.
	 * Para a escrita, o ponteiro sempre será contínuo, nunca poderá retroceder na linha.
	 * @param b byte do qual deverá ser escrito no local do ponteiro de escrita.
	 */

	void write(byte b);

	/**
	 * Permite escrever uma o conteúdo de uma string inteira na atual linha em que se encontra.
	 * Irá escrever os bytes dessa string iniciando onde o ponteiro de escrita indica na comunicação.
	 * Após fazer a escrita da string irá adicionar uma quebra de linha padrão ao final.
	 * @param line referência da string contendo os bytes do qual serão escritos na linha.
	 */

	void writeLine(String line);

	/**
	 * Ao ser chamado deverá fazer uma quebra de linha na comunicação que é denominada por '\n'.
	 */

	void writeBreakLine();
}
