package org.diverproject.util.collection;

/**
 * <p><h1>Fila</h1></p>
 *
 * <p>Fias são utilizadas sempre que os primeiros elementos adicionados,
 * serão os primeiros a serem selecionados também como de fato é uma fila.</p>
 *
 * <p>Conforme os elementos forem sendo adicionados vão sendo posicionados ao
 * fim da fila, sendo necessário esperar que os primeiros sejam selecionados.
 * Quando estes forem selecionados os últimos começam a passar para frente.</p>
 *
 * <p>Como forma de pensamento para a utilização de uma fila, devemos pensar que os
 * elementos que forem adicionados a esta deverão esperar uma sequência para que
 * possam ser selecionado, tendo de esperar os elementos a frente serem chamados.</p>
 *
 * @see Collection
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public interface Queue<E> extends Collection<E>
{
	/**
	 * Deve enfileirar um novo elemento na fila de acordo com suas especificações.
	 * @param element referência do elemento que será enfileirado.
	 * @return true se consegui enfileirar ou false caso contrário.
	 */

	boolean offer(E element);

	/**
	 * Irá analisar a fila e buscar o elemento mais adequado para ser retirado.
	 * @return aquisição do elemento mais antigo na fila.
	 */

	E poll();
}
