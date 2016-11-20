package org.diverproject.util.stream;

/**
 * <h1>Buffer</h1>
 *
 * <p>Buffer nada mais é do que uma comunicação de entrada e saída em uma massa de dados especificada.
 * Essa massa de dados só será aceita como um vetor de bytes, sendo usado o mesmo para entrada e saída.
 * Irá possuir tanto as funções de stream para entrada de dados quanto a stream para saída de dados.</p>
 *
 * <p>No buffer, os dois ponteiros (offset, size) serão separados para cada uma de suas funcionalidades.
 * Somente nesse tipo de buffer ambos serão de fato utilizados de acordo com o que foi especificado.</p>
 *
 * <p>Ainda adiciona algumas funções extras como movimentar o ponteiro de escrita e salvar dados.
 * A movimentação do ponteiro pode ser feita as vezes para ignorar alguns bytes ou ir direto a um ponto.
 * Em quanto o salvamento de dados irá dizer que os bytes seguintes lidos serão salvos internamente.
 * Esses dados lidos que foram salvos podem ser obtidos posteriormente quando for desejado.</p>
 *
 * <p>Os buffers também, não irão fazer uma cópia do vetor de bytes, e sim utilizado diretamente.
 * Por tanto, o mesmo vetor passado para o buffer, terá os seus dados alterados de acordo com o buffer.</p>
 *
 * @see Input
 * @see Output
 *
 * @author Andrew Mello
 */

public interface Buffer extends Input, Output
{
	/**
	 * Cria um vetor contendo todos os bytes que foram lidos a partir da chamada de salvamento.
	 * Uma vez que este tenha sido chamado, esse salvamento será esquecido sendo necessário um novo.
	 */

	byte[] getArrayBuffer();

	/**
	 * Permite fazer a movimentação do ponteiro para uma posição especifica na comunicação.
	 * Caso seja movido para um ponteiro inválido não poderá ser feito a leitura de dados corretamente.
	 * @param offset novo índice do qual o ponteiro de leitura deverá indicar na comunicação.
	 */

	void moveTo(int offset);
}
