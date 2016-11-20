package org.diverproject.util.collection.abstraction;

import org.diverproject.util.collection.Node;
import org.diverproject.util.lang.StringUtil;

public class IntegerLargeMap<E> extends AbstractNumber<E>
{
	/**
	 * Referência mapeador dos elementos que podem ou já foram armazenados.
	 */
	protected Node<MapElement<Integer, E>> nodes[][][];

	@Override
	public boolean containsKey(Integer key)
	{
		int hash[] = hash(key);
		Node<MapElement<Integer, E>> node = getNodeFrom(hash);

		return get(node, key) != null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void clear()
	{
		size = 0;
		nodes = new Node[10][10][10];
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
			str.charAt(2) - '0',
		};
	}

	@Override
	public boolean remove(E element)
	{
		for (Node<MapElement<Integer, E>> matrix[][] : nodes)
			for (Node<MapElement<Integer, E>> array[] : matrix)
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
		int k = hash[2];

		if (nodes[i][j][k] == null)
			nodes[i][j][k] = new Node<MapElement<Integer,E>>(null);

		return nodes[i][j][k];
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Node<MapElement<Integer, E>>[] toArrayNode()
	{
		Node<MapElement<Integer, E>> array[] = new Node[size];

		int i = 0;

		for (int j = 0; j < nodes.length; j++)
			for (int k = 0; k < nodes[j].length; k++)
				for (int l = 0; l < nodes[j][k].length; l++)
					if (nodes[j][k][l] != null)
						array[i++] = nodes[j][k][l];

		return array;
	}
}
