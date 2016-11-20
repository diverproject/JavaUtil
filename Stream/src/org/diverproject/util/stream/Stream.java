package org.diverproject.util.stream;

/**
 * <h1>Stream</h1>
 *
 * <p>Usado para determinar algumas funcionalidades padrões para todos os meios de comunicações.
 * Suas funcionalidades devem ser de permitir obter algumas informações em relação a essa comunicação.
 * Ponteiro indicando onde está a comunicação, comprimento máximo, tamanho já percorrido e espaço sobrando.</p>
 *
 * <p>Além disso algumas outras funções também são de responsabilidade de uma stream saber como lidar.
 * Verificar se a stream está vazia (provável falha na comunicação) ou ainda então se ela foi fechada.
 * Uma vez que tenha sido fechada, as streams não devem permitir qualquer comunicação com estas.</p>
 *
 * <p>Por último, em alguns casos, os dados primitivos são armazenados com seus bytes invertidos.
 * Para isso, é de responsabilidade da stream permitir essa alternação entre comunicar-se invertido.
 * Ela deverá ainda permitir verificar se a atual comunicação está ou não sendo feito invertido.</p>
 *
 * @author Andrew Mello
 */

public interface Stream
{
	/**
	 * Offset é a margem do inicio da comunicação até onde ela está sendo feita no momento.
	 * Para facilitar, será denominado como ponteiro durante a utilização da comunicação.
	 * Esse ponteiro aponta para onde será feito a leitura dos próximos dados solicitados.
	 * @return aquisição do índice do ponteiro de leitura, quantidade de bytes lidos.
	 */

	int offset();

	/**
	 * Comprimento é usado para garantir que mais dados do que o existente sejam lidos.
	 * Indicando assim, o espaço exato de quantos bytes será possível fazer a comunicação.
	 * Podendo ainda também em caso de saída de dados, indicar o limite de dados para este.
	 * @return aquisição do limite de dados que poderão <i>trafegar</i> durante a comunicação.
	 */

	int length();

	/**
	 * Espaço é uma função muito importante, é usado para verificar se há dados para serem lidos.
	 * Ou no caso de escrita verificar se há espaço para fazer o armazenamento dos dados escritos.
	 * Isso também pode ajudar como um indicador de que não há mais nada a fazer na comunicação.
	 * @return aquisição da quantidade de bytes que ainda podem ser lidos ou escritos na stream.
	 */

	int space();

	/**
	 * Essa função tem como objetivo indireto indicar que há um problema na comunicação.
	 * Quando uma comunicação está vazia significa que está não possui dados para ler ou escrever.
	 * Por tanto é bem provável que houve uma falha na comunicação, a não ser que esta esteja em branco.
	 * @return true se a comunicação estiver vazia ou false caso contrário.
	 */

	boolean isEmpty();

	/**
	 * Uma comunicação fechada não irá permitir fazer a leitura ou escrita de dados na comunicação.
	 * No caso da leitura, qualquer dado seguinte lido será sempre respectivo a null ou em código ASCII: 0.
	 * @return true se a comunicação estiver fechada ou false caso contrário.
	 */

	boolean isClosed();

	/**
	 * Deve ser chamado apenas se houve alguma falha durante a comunicação ou então quando ela acabou.
	 * Isso irá garantir que a comunicação seja fechada, assim sendo, não poderá mais ler ou escrever.
	 */

	void close();

	/**
	 * Comunicações que estejam invertidas, sempre irão ler ou escrever os dados primitivos ao contrário.
	 * Esse <i>ao contrário</i> é referente a ordem em que seus bytes serão lidos ou então escritos.
	 * @return true se a comunicação estiver sendo invertida ou false caso contrário.
	 */

	boolean isInverted();

	/**
	 * Permite definir a comunicação como invertida, assim os bytes dos dados primitivos serão invertidos.
	 * Alguns arquivos precisam de uma leitura de dados padrões porém em alguns casos eles são invertidos.
	 * @param enable true para habilitar a comunicação de dados invertida ou false para desabilitar.
	 */

	void setInvert(boolean enable);

	/**
	 * Permite pular uma determinada quantidade de bytes do fluxo pré-estabelecido.
	 * @param bytes quantidade de bytes que serão pulados no fluxo.
	 */

	void skipe(int bytes);

	/**
	 * Restabelece a stream como se nenhum dado tivesse lido, movendo ponteiros para o inicio.
	 */

	void reset();
}
