package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.Node;
import org.diverproject.util.collection.Queue;

/**
 * <p><h1>Fila Dinâmica</h1></p>
 *
 * <p>Neste caso a fila dinâmica utiliza o conceito de nós duplamente encadeados.
 * De modo que cada elemento seja armazenado em um nó especificando quem é que
 * está a frente deste se houver e quem está atrás se assim houver um.</p>
 *
 * <p>Trabalha com dois nós duplamente encadeados especificando quem é o primeiro
 * elemento na fila e quem é o último elemento do mesmo, facilitando o processo para
 * pegar o elemento que está ao inicio da fila e inserir um novo ao final desta..</p>
 *
 * <p>Outra característica importante para se definir é que esta não possui um
 * "tamanho limite", onde o limite e Integer.MAX_VALUE para dimensionar o tamanho
 * dessa estrutura através do atributo size ou método size() que funciona como get.</p>
 *
 * @see AbstractCollection
 * @see Queue
 *
 * @author Andrew qual será o tipo de dado que será armazenado na coleção.
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public class DynamicQueue<E> extends AbstractCollection<E> implements Queue<E>
{
	/**
	 * Referência do nó que representa o inicio da fila (antigos elementos).
	 */
	private Node<E> first;

	/**
	 * Referência do nó que representa o final da fila (novos elementos).
	 */
	private Node<E> last;

	/**
	 * Constrói uma nova fila com tamanho dinâmico através de nós encadeados.
	 * Além disso também define do tipo de objeto que será usado em toArray.
	 */

	public DynamicQueue()
	{
		this(null);
	}

	/**
	 * Constrói uma nova fila com tamanho dinâmico através de nós encadeados.
	 * Além disso também define do tipo de objeto que será usado em toArray.
	 * @param generic classe respectiva ao tipo de dado armazenado.
	 */

	public DynamicQueue(Class<?> generic)
	{
		super();

		setGeneric(generic);
	}

	@Override
	public void clear()
	{
		first = null;
		last = null;
		size = 0;
	}

	@Override
	public int length()
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean contains(E element)
	{
		Node<E> node = first;

		while (node != null)
		{
			if (node.get().equals(element))
				return true;

			node = node.getNext();
		}

		return false;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			private Node<E> node = first;

			@Override
			public boolean hasNext()
			{
				return node != null;
			}

			@Override
			public E next()
			{
				Node<E> last = node;
				node = last.getNext();

				return last.get();
			}
		};
	}

	@Override
	public boolean offer(E element)
	{
		if (isFull())
			return false;

		Node<E> node = new Node<E>(element);

		if (isEmpty())
		{
			first = node;
			last = node;
		}

		else if (size == 1)
		{
			Node.attach(first, node);
			last = node;
		}

		else
		{
			Node.attach(last, node);
			last = node;
		}

		size++;

		return true;
	}

	@Override
	public E poll()
	{
		if (isEmpty())
			return null;

		Node<E> poll = first;
		first = first.getNext();
		size--;

		return poll.get();
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		description.append("generic", getGeneric().getSimpleName());

		Node<E> node = first;

		while (node != null)
		{
			description.append(node.get());
			node = node.getNext();
		}

		return description.toString();
	}
}
