package org.diverproject.util.collection.abstraction;

import org.diverproject.util.collection.Node;
import org.diverproject.util.lang.StringUtil;

public class IntegerAvarageMap<E> extends AbstractNumber<E>
{
	/**
	 * Referência mapeador dos elementos que podem ou já foram armazenados.
	 */
	protected Node<MapElement<Integer, E>> nodes[][];

	@Override
	public boolean containsKey(Integer key)
	{
		for (Node<MapElement<Integer, E>> array[] : nodes)
			for (Node<MapElement<Integer, E>> node : array)
				if (node != null)
					return containsKey(node, key);

		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void clear()
	{
		size = 0;
		nodes = new Node[10][10];
	}

	@Override
	protected int[] hash(Integer key)
	{
		String str = Integer.toString(key);
		str = StringUtil.addStartWhile(str, "0", 3);

		return new int[]
		{
			str.charAt(0) - '0',
			str.charAt(1) - '0',
		};
	}

	@Override
	public boolean remove(E element)
	{
		for (Node<MapElement<Integer, E>> array[] : nodes)
			for (Node<MapElement<Integer, E>> node : array)
				while (node != null)
					if (remove(node, element))
						return true;

		return false;
	}

	@Override
	protected Node<MapElement<Integer, E>> getNodeFrom(int[] hash)
	{
		int i = hash[0];
		int j = hash[1];

		if (nodes[i][j] == null)
			nodes[i][j] = new Node<MapElement<Integer,E>>(null);

		return nodes[i][j];
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Node<MapElement<Integer, E>>[] toArrayNode()
	{
		Node<MapElement<Integer, E>> array[] = new Node[100];

		for (int i = 0; i < nodes.length; i++)
			for (int j = 0; j < nodes[i].length; j++)
				array[(i * 10) + j] = nodes[i][j];

		return array;
	}
}
