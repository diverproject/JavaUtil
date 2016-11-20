package org.diverproject.util.collection;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;

/**
 * <p><h1>Nó</h1></p>
 *
 * <p>Classe que permite a construção de uma estrutura de dados simples conhecida como nó.
 * Essas estruturas na verdade são pedaços usados em um verdadeira estrutura de dados.
 * Onde cada elemento nesta inserido será armazenado dentro de um nó para ordenar.</p>
 *
 * <p>São conhecidos por não determinarem uma capacidade limite pois são ligados entre si.
 * Cada nó possui a referência do seu elemento raíz, que será o nó deste mesmo na estrutura.
 * Além disso permite adicionar referência como elemento anterior/próximo, criando o nó.</p>
 *
 * <p>Nada impede que dois nós sejam ligados sequencialmente usando anterior e próximo.
 * Como também permite o uso de uma ligação em loop infinito onde o nó em questão aponta para
 * o nó anterior e este nó anterior aponta para o nó seguinte (primeiro nó mencionado).</p>
 *
 * <p>Essas referências para qualquer um dos lados que seja (anterior ou próximo),
 * podem ser definidos através dos setters ou obtidos através dos getters do mesmo.
 * Isso também vale para o elemento do qual está sendo armazenado pelo nó.</p>
 *
 * @see Collection
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public class Node<E> implements Iterable<E>
{
	/**
	 * Referência do valor que este nó está armazenado.
	 */
	private E value;

	/**
	 * Referência do nó que irá seguir este, nó seguinte.
	 */
	private Node<E> next;

	/**
	 * Referência do nó que irá anteceder este, nó anterior.
	 */
	private Node<E> prev;

	/**
	 * Constrói um novo nó onde deve ser definido o seu valor inicial.
	 * Espera-se que todo nó construído tenha um valor inicial para tal.
	 * Pois a utilização deste não faz sentido sem possuir um valor.
	 * @param value referência do valor que será armazenado neste nó.
	 */

	public Node(E value)
	{
		set(value);
	}

	/**
	 * Procedimento que permite obter o valor armazenado dentro deste nó.
	 * @return aquisição do objeto valor que foi atribuído a esse nó.
	 */

	public E get()
	{
		return value;
	}

	/**
	 * Permite definir qual será o objeto de valor atribuído a esse nó.
	 * @param element referência do novo objeto que será definido como valor.
	 */

	public void set(E element)
	{
		value = element;
	}

	/**
	 * Nó seguinte é um dos lados do nó que visualmente consideramos como lado direito.
	 * @return aquisição da referência do nó seguinte a este na estrutura.
	 */

	public Node<E> getNext()
	{
		return next;
	}

	/**
	 * Permite definir qual será o nó que será considerado como seguinte a este.
	 * @param next referência do novo nó que será definido como posterior.
	 */

	public void setNext(Node<E> next)
	{
		this.next = next;
	}

	/**
	 * Nó anterior é um dos lados do nó que visualmente consideramos como lado esquerdo.
	 * @return aquisição da referência do nó anterior a este na estrutura.
	 */

	public Node<E> getPrev()
	{
		return prev;
	}

	/**
	 * Permite definir qual será o nó que será considerado como anterior a este.
	 * @param next referência do novo nó que será definido como antecedente.
	 */

	public void setPrev(Node<E> prev)
	{
		this.prev = prev;
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		description.append("value", value);
		description.append("prev", prev == null ? null : prev.get());
		description.append("next", next == null ? null : next.get());

		return description.toString();
	}

	/**
	 * Procedimento que deve fazer o anexo de dois nós previamente definidos.
	 * Onde o primeiro terá como seguinte o segundo e o segundo terá como
	 * anterior o primeiro nó, torando-os assim sequenciais como esperado.
	 * @param first referência do primeiro nó que será o lado esquerdo.
	 * @param second referência do segundo nó que será o lado direito.
	 * @return true se conseguir anexar ou false caso um deles seja nulo.
	 */

	@SuppressWarnings("unchecked")
	public static boolean attach(Node<?> first, Node<?> second)
	{
		if (first == null || second == null)
			return false;

		Node<Object> left = (Node<Object>) first;
		Node<Object> right = (Node<Object>) second;

		left.setNext(right);
		right.setPrev(left);

		return false;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			private Node<E> node = new Node<E>(get());

			@Override
			public boolean hasNext()
			{
				return node != null && node.get() != null;
			}

			@Override
			public E next()
			{
				E e = node.get();
				node = node.getNext();

				return e;
			}
		};
	}
}
