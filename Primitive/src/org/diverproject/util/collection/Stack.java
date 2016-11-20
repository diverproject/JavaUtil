package org.diverproject.util.collection;

/**
 * <p><h1>Pilha</h1></p>
 *
 * <p>Pilhas são utilizadas quando os elementos interagidos sempre são o do topo.
 * Utilizando o procedimento de empilhar elementos ou desempilhar elementos.</p>
 *
 * <p>Quanto utilizado a empilhagem será o de inserir um novo elemento na pilha.
 * Em quanto a desempilhagem será o de remover o elemento no topo da pilha.
 * De modo que seja seja sempre trabalhado a questão dos primeiros elementos.</p>
 *
 * <p>Outra característica da pilha é que o procedimento de remover (desempilhar),
 * também é ao mesmo tempo o procedimento para seleção de um elemento na pilha.
 * Assim sendo apenas o último elemento dessa pilha poderá ser selecionado.</p>
 *
 * <p>Mesmo que ele trabalhe apenas com o último elemento da pilha (adicionar/remover),
 * ao mesmo tempo ele também é uma coleção (estrutura de dados) como outra qualquer.
 * Portanto é possível utilizar a iteração para percorrer os elementos desta.</p>
 *
 * @see Collection
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public interface Stack<E> extends Collection<E>
{
	/**
	 * Empilha um determinado elemento especificado no topo dessa pilha.
	 * @param element referência do objeto elemento que será empilhado.
	 * @return true se conseguir empilhar ou false se estiver cheio.
	 */

	boolean push(E element);

	/**
	 * Desempilha o último elemento empilhado na pilha (elemento do topo).
	 * Ao fazer isso também irá remover o mesmo da pilha se assim houver.
	 * @return aquisição do elemento no topo da pilha ou null se vazia.
	 */

	E pop();
}
