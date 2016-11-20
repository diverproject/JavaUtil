package org.diverproject.util.collection;

/**
 * <p><h1>Lista</h1></p>
 *
 * <p>As estruturas de dados do tipo lista são as mais comuns de serem encontradas.
 * Pois a sua forma de manipular os dados é a que mais encaixa na maioria dos casos.
 * Possuindo um protocolo bem simples para os procedimentos de adicionar e remover.</p>
 *
 * <p>Os novos elementos adicionados em uma lista sempre serão adicionados ao fim desta.
 * Há um caso em que é possível adicionar um no meio da lista, porém é especificado.
 * No caso da remoção de um item todos os elementos seguintes são movidos para trás.</p>
 *
 * <p>Uma diferença bem importante para se ressaltar de diferença dentre essa estrutura,
 * e as outras estruturas, é que esta permite atualizar determinados índices da lista.
 * Talvez este seja o ponto mais importante dos mecanismos dispostos em comparação.</p>
 *
 * @see Collection
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public interface List<E> extends Collection<E>
{
	/**
	 * Adiciona um novo elemento especificado posicionando-o ao final da lista.
	 * @param element referência do elemento que será adicionado à lista.
	 * @return true se conseguir adicionar ou false se estiver cheia.
	 */

	boolean add(E element);

	/**
	 * Remove um elemento através da especificação da sua referência como objeto.
	 * Quando um elemento é removido todos os seguintes passam para a esquerda.
	 * @param element referência do objeto elemento que será removido da lista.
	 * @return true se encontrar e portanto remover ou false se não encontrar.
	 */

	boolean remove(E element);

	/**
	 * Remove um elemento através do seu índice de posicionamento na lista.
	 * Quanto um elemento é removido todos os seguintes passam para a esquerda.
	 * @param index número do índice do elemento que será removido da lista.
	 * @return true se conseguir remover ou false caso o índice seja inválido.
	 */

	boolean remove(int index);

	/**
	 * Atualiza um determinado índice da lista forçando a sobrescrita do elemento.
	 * Ou seja, o elemento no índice passado será substituído pelo elemento passado.
	 * @param index número do índice na lista que terá o seu valor substituído.
	 * @param element referência do elemento que será inserido nesse índice.
	 * @return true se conseguir atualizar ou false caso o índice seja inválido.
	 */

	boolean update(int index, E element);

	/**
	 * Obtém um determinado item de dentro da lista a partir do seu índice.
	 * @param index número do índice do elemento que será obtido da lista.
	 * @return aquisição do elemento respectivo no índice especificado.
	 */

	E get(int index);
}
