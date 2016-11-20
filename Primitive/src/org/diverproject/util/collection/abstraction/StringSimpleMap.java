package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.Map;
import org.diverproject.util.collection.Node;

/**
 * <p><h1>Mapa de String</h1></p>
 *
 * <p>Esse tipo de mapa é recomendável ser utilizado com chaves do tipo String.
 * Porém é viável apenas se houver muitos elementos para armazenar no mesmo.
 * Utiliza o conceito de tabela espelhada para armazenar seus elementos.</p>
 *
 * <p>Esse conceito considera que cada chave terá um ponto para armazená-lo.
 * Ponto este que é utilizado também para remover, obter ou atualizar.
 * Cada ponto no mapa é respectivo a um determinado índice de um vetor.
 * Esses vetores irão armazenar nós que irão conter elementos daquele ponto.</p>
 *
 * <p>Para esse tipo de mapeamento será considerado as chaves de letras e números.
 * Sendo assim, 26 casas para letras (A-Z) e 10 casas para números (0-9).
 * Essas casas serão consideradas apenas do primeiro caracter ignorando case.</p>
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public class StringSimpleMap<E> extends AbstractCollection<E> implements Map<String, E>
{
	/**
	 * Referência mapeador dos elementos que podem ou já foram armazenados.
	 */
	protected Node<MapElement<String, E>> table[];

	/**
	 * Quantos elementos podem ser armazenados nesse mapa.
	 */
	private int length;

	/**
	 * Constrói um novo mapeamento simples para armazenar elementos de key String.
	 * Nesse construtor irá definir o número máximo de elementos para armazenar.
	 */

	public StringSimpleMap()
	{
		this(Integer.MAX_VALUE);
	}

	/**
	 * Constrói um novo mapeamento simples para armazenar elementos de key String.
	 * @param length quantos elementos podem ser armazenados.
	 */

	@SuppressWarnings("unchecked")
	public StringSimpleMap(int length)
	{
		this.length = length;
		this.table = new Node[37];
	}

	@Override
	@SuppressWarnings("unchecked")
	public void clear()
	{
		size = 0;
		table = new Node[table.length];
	}

	@Override
	public int length()
	{
		return length;
	}

	@Override
	public boolean containsKey(String key)
	{
		for (Node<MapElement<String, E>> node : table)
			while (node != null)
				if (node.get().key.equals(key))
					return true;
				else
					node = node.getNext();

		return false;
	}

	@Override
	public boolean contains(E element)
	{
		for (Node<MapElement<String, E>> node : table)
			while (node != null)
				if (node.get().value.equals(element))
					return true;
				else
					node = node.getNext();

		return false;
	}

	@Override
	public boolean add(String key, E element)
	{
		if (key == null || element == null)
			return false;

		int letter = map(key);

		if (table[letter] == null)
		{
			MapElement<String, E> map = new MapElement<String, E>(key, element);
			Node<MapElement<String, E>> node = new Node<MapElement<String,E>>(map);

			table[letter] = node;
		}

		else
		{
			Node<MapElement<String, E>> node = table[letter];

			if (node.getNext() == null)
			{
				MapElement<String, E> map = new MapElement<String, E>(key, element);
				Node<MapElement<String, E>> newNode = new Node<MapElement<String,E>>(map);

				int compare = node.get().key.compareTo(key);

				if (compare == 0)
					return false;

				if (compare > 0)
				{
					Node.attach(newNode, node);
					table[letter] = newNode;
				}

				else
					Node.attach(node, newNode);
			}

			else if (node.get().key.compareTo(key) > 0)
			{
				MapElement<String, E> map = new MapElement<String, E>(key, element);
				Node<MapElement<String, E>> newNode = new Node<MapElement<String,E>>(map);

				Node.attach(newNode, node);
				table[letter] = newNode;
			}

			else
			{
				while (node != null && node.getNext() != null)
				{
					int compare = node.get().key.compareTo(key);

					if (compare == 0)
						return false;

					if (compare > 0)
						break;

					node = node.getNext();
				}

				MapElement<String, E> map = new MapElement<String, E>(key, element);
				Node<MapElement<String, E>> next;

				if (node.get().key.compareTo(key) < 0)
					next = new Node<MapElement<String,E>>(map);

				else
				{
					next = new Node<MapElement<String,E>>(node.get());
					node.set(map);
				}

				Node.attach(next, node.getNext());
				Node.attach(node, next);
			}
		}

		size++;

		return true;
	}

	@Override
	public boolean remove(E element)
	{
		if (element == null)
			return false;

		for (Node<MapElement<String, E>> node : table)
		{
			while (node != null)
			{
				if (node.get().value.equals(element))
					break;
			}

			if (node == null)
				return false;

			Node<MapElement<String, E>> prev = node.getPrev();
			Node<MapElement<String, E>> next = node.getNext();

			size--;

			return Node.attach(prev, next);
		}

		return false;
	}

	@Override
	public boolean removeKey(String key)
	{
		if (key == null)
			return false;

		int letter = map(key);

		if (table[letter] == null)
			return false;

		Node<MapElement<String, E>> node = table[letter];

		if (node.get().key.equals(key))
		{
			table[letter] = node.getNext();

			if (table[letter] != null)
				table[letter].setPrev(null);

			node.setNext(null);
			node = null;

			size--;

			return true;
		}

		while (node != null && !node.get().key.equals(key))
			node = node.getNext();

		if (node == null)
			return false;

		Node<MapElement<String, E>> prev = node.getPrev();
		Node<MapElement<String, E>> next = node.getNext();

		size--;

		return Node.attach(prev, next);
	}

	@Override
	public boolean renameKey(String oldKey, String newKey)
	{
		if (oldKey == null || newKey == null || containsKey(newKey))
			return false;

		E element = get(oldKey);

		return removeKey(oldKey) && add(newKey, element);
	}

	@Override
	public boolean update(String key, E value)
	{
		if (key == null || value == null)
			return false;

		int letter = map(key);

		if (table[letter] == null)
			return false;

		Node<MapElement<String, E>> node = table[letter];

		while (node != null)
		{
			int compare = node.get().key.compareTo(key);

			if (compare == 0)
			{
				node.get().value = value;
				return true;
			}

			node = node.getNext();
		}

		return false;
	}

	@Override
	public E get(String key)
	{
		if (key == null)
			return null;

		int letter = map(key);

		if (table[letter] == null)
			return null;

		Node<MapElement<String, E>> node = table[letter];

		while (node != null)
		{
			int compare = node.get().key.compareTo(key);

			if (compare == 0)
				return node.get().value;

			node = node.getNext();
		}

		return null;
	}

	@Override
	public Iterator<String> iteratorKey()
	{
		return new Iterator<String>()
		{
			private int iterate;
			private int letter;
			private Node<MapElement<String, E>> node = table[0];

			@Override
			public boolean hasNext()
			{
				return iterate < size();
			}

			@Override
			public String next()
			{
				if (node != null)
				{
					String key = node.get().key;
					node = node.getNext();
					iterate++;

					return key;
				}

				while (letter < table.length)
				{
					if (table[letter] != null)
					{
						node = table[letter++];
						break;
					}

					letter++;
				}

				return next();
			}

			@Override
			public String toString()
			{
				ObjectDescription description = new ObjectDescription(getClass());

				description.append("generic", getGeneric());
				description.append("letter", (char) (letter + 'a'));
				description.append("iterate", iterate);

				return description.toString();
			}
		};
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			private int iterate;
			private int letter;
			private Node<MapElement<String, E>> node;

			@Override
			public boolean hasNext()
			{
				return iterate < size();
			}

			@Override
			public E next()
			{
				if (node != null)
				{
					E value = node.get().value;
					node = node.getNext();
					iterate++;

					return value;
				}

				while (letter < table.length)
				{
					node = table[letter++];

					if (node != null)
						return next();
				}

				return null;
			}

			@Override
			public String toString()
			{
				ObjectDescription description = new ObjectDescription(getClass());

				description.append("generic", getGeneric());
				description.append("letter", (char) (letter + 'a'));
				description.append("iterate", iterate);

				return description.toString();
			}
		};
	}

	/**
	 * Procedimento que irá calcular qual o ponto para uma determinada chave.
	 * @param key nome da chave que será usada para fazer o calculo do ponto.
	 * @return aquisição do ponto para determinar a localização de uma chave.
	 */

	protected int map(String key)
	{
		char c = key.charAt(0);
		c = Character.toLowerCase(c);

		return c >= 'a' && c <= 'z' ? c - 'a' : c >= '0' && c <= '9' ? c - '0' + 26 : 36;
	}

	/**
	 * Procedimento que deve informar os dados contidos dentro desse objeto.
	 * Mostrando o nome das variáveis e em seguida os seus valores respectivos.
	 * @param description referência da descrição do objeto que será usada.
	 */

	protected void toString(ObjectDescription description)
	{
		
	}

	/**
	 * Procedimento que deve informar os dados contidos dentro desse objeto.
	 * Chamado sempre que um nó for encontrado com dados incluídos no mesmo.
	 * @param description referência da descrição do objeto que será usada.
	 * @param node referência do nó contendo as informações do elemento.
	 */

	protected void toString(ObjectDescription description, Node<MapElement<String, E>> node)
	{
		description.append(node.get().key, node.get().value);
	}

	@Override
	public Iterable<String> iterateKey()
	{
		return new Iterable<String>()
		{
			@Override
			public Iterator<String> iterator()
			{
				return iteratorKey();
			}
		};
	}

	@Override
	public Iterable<MapItem<String, E>> iterateItems()
	{
		return new Iterable<Map.MapItem<String, E>>()
		{
			@Override
			public Iterator<MapItem<String, E>> iterator()
			{
				return iteratorItems();
			}
		};
	}

	@Override
	public Iterator<MapItem<String, E>> iteratorItems()
	{
		return new Iterator<MapItem<String,E>>()
		{
			private int iterate;
			private int letter;
			private Node<MapElement<String, E>> node;

			@Override
			public boolean hasNext()
			{
				return iterate < size();
			}

			@Override
			public MapItem<String, E> next()
			{
				if (node != null)
				{
					iterate++;

					String key = node.get().key;
					E value = node.get().value;
					node = node.getNext();

					return new MapItem<String, E>(key, value);
				}

				while (node == null && letter < table.length)
					node = table[letter++];

				return next();
			}

			@Override
			public String toString()
			{
				ObjectDescription description = new ObjectDescription(getClass());

				description.append("generic", getGeneric());
				description.append("letter", (char) (letter + 'a'));
				description.append("iterate", iterate);

				return description.toString();
			}
		};
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		toString(description);

		for (int i = 0; i < table.length; i++)
			if (table[i] != null)
			{
				Node<MapElement<String, E>> node = table[i];

				while (node != null)
				{
					toString(description, node);
					node = node.getNext();
				}
			}

		return description.toString();
	}
}
