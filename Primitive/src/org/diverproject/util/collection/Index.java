package org.diverproject.util.collection;

/**
 * <p><h1>Índice</h1></p>
 *
 * <p>As estruturas de dados do tipo vetor são bastante semelhantes a mapas.
 * Pois posiciona os seus elementos de acordo com o índice especificado.
 * De modo que seja um vetor como outro qualquer porém com um controle.</p>
 *
 * <p>Os novos elementos adicionados sempre irão precisar de um índice para alocar.
 * Quando um determinado índice estiver ocupado esse elemento não poderá ser inserido.
 * A alocação dos elementos no vetor pode ser feito de diferentes formas.</p>
 *
 * <p>Podendo utilizar tabelas espalhadas quando houver uma variação grande porém
 * poucos elementos para serem inseridos ou então a utilização de nós duplos.
 * Mas o comum e a utilização de vetores com ordenação por índice ou o próprio índice.</p>
 *
 * @see Collection
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public interface Index<E> extends Collection<E>
{
	/**
	 * Adiciona um novo elemento especificado posicionando-o ao final da lista.
	 * @param index posicionamento do elemento na lista por prioridade.
	 * @param element referência do elemento que será adicionado à lista.
	 * @return true se conseguir adicionar ou false se estiver cheia.
	 */

	boolean add(int index, E element);

	/**
	 * Remove um elemento através da especificação da sua referência como objeto.
	 * Quanto um elemento é removido todos os seguintes passam para a esquerda.
	 * @param element referência do objeto elemento que será removido da lista.
	 * @return true se encontrar e portanto remover ou false se não encontrar.
	 */

	boolean remove(E element);

	/**
	 * Remove um elemento através da sua prioridade de posicionamento na lista.
	 * Quando um elemento é removido todos os seguintes passam para a esquerda.
	 * @param index número do índice do elemento que será removido da lista.
	 * @return true se conseguir remover ou false caso o índice seja inválido.
	 */

	boolean remove(int index);

	/**
	 * Atualiza um determinado índice da lista forçando a sobrescrita do elemento.
	 * Ou seja, o elemento no índice passado será substituído pelo elemento passado.
	 * @param index número do índice da posição que terá o seu valor substituído.
	 * @param element referência do elemento que será inserido nesse índice.
	 * @return true se conseguir atualizar ou false caso o índice seja inválido.
	 */

	boolean update(int index, E element);

	/**
	 * Troca dois elementos em determinadas prioridades especificadas.
	 * @param first índice respectiva ao primeiro elemento da troca.
	 * @param second índice respectiva ao segundo elemento da troca.
	 * @return true quando for trocado ou false caso haja falha,
	 * falha pode ser prioridade inválida ou então não usada.
	 */

	boolean change(int first, int second);

	/**
	 * Obtém um determinado item de dentro da lista a partir do seu índice.
	 * @param index número do índice do elemento que será obtido da lista.
	 * @return aquisição do elemento respectivo no índice especificado.
	 */

	E get(int index);
}
