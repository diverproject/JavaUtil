package org.diverproject.util.collection.abstraction;

import org.diverproject.util.collection.Node;

public class IntegerLittleMap<E> extends AbstractNumber<E>
{
	/**
	 * Referência mapeador dos elementos que podem ou já foram armazenados.
	 */
	protected Node<MapElement<Integer, E>> nodes[];

	@Override
	public boolean containsKey(Integer key)
	{
		for (Node<MapElement<Integer, E>> node : nodes)
			if (node != null)
				return containsKey(node, key);

		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void clear()
	{
		size = 0;
		nodes = new Node[10];
	}

	@Override
	protected int[] hash(Integer key)
	{
		return new int[]
		{
			Integer.toString(key).charAt(0) - '0',
		};
	}

	@Override
	public boolean remove(E element)
	{
		for (Node<MapElement<Integer, E>> node : nodes)
			while (node != null)
			{
				if (remove(node, element))
					return true;
				else
					node = node.getNext();
			}

		return false;
	}

	@Override
	protected Node<MapElement<Integer, E>> getNodeFrom(int[] hash)
	{
		int i = hash[0];

		if (nodes[i] == null)
			nodes[i] = new Node<MapElement<Integer,E>>(null);

		return nodes[i];
	}

	@Override
	protected Node<MapElement<Integer, E>>[] toArrayNode()
	{
		return nodes.clone();
	}
}
