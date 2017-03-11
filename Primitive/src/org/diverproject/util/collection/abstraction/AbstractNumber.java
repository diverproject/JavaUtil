package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.Map;
import org.diverproject.util.collection.Node;

/**
 * <p><h1>Mapa de Integer</h1></p>
 *
 * <p>Esse tipo de mapa é recomendável ser utilizado com chaves do tipo Integer.
 * Porém é viável apenas se houver muitos elementos para armazenar no mesmo.
 * Utiliza o conceito de tabela espelhada para armazenar seus elementos.</p>
 *
 * <p>Esse conceito considera que cada chave terá um ponto para armazená-lo.
 * Ponto este que é utilizado também para remover, obter ou atualizar.
 * Cada ponto no mapa é respectivo a um determinado índice de um vetor.
 * Esses vetores irão armazenar nós que irão conter elementos daquele ponto.</p>
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public abstract class AbstractNumber<E> extends AbstractCollection<E> implements Map<Integer, E>
{
	/**
	 * Quantos elementos podem ser armazenados nesse mapa.
	 */
	private int length;

	/**
	 * Constrói um novo mapeamento simples para armazenar elementos de key Integer.
	 * Nesse construtor irá definir o número máximo de elementos para armazenar.
	 */

	public AbstractNumber()
	{
		this(Integer.MAX_VALUE);
	}

	/**
	 * Constrói um novo mapeamento simples para armazenar elementos de key Integer.
	 * @param length quantos elementos podem ser armazenados.
	 */

	public AbstractNumber(int length)
	{
		this.length = length;

		clear();
	}

	@Override
	public int length()
	{
		return length;
	}

	@Override
	public boolean contains(E element)
	{
		Iterator<E> iterator = iterator();

		while (iterator.hasNext())
		{
			E iterate = iterator.next();

			if (iterate.equals(element))
				return true;
		}

		return false;
	}

	@Override
	public boolean add(Integer key, E element)
	{
		if (key == null || element == null)
			return false;

		int hash[] = hash(key);

		return add(getNodeFrom(hash), key, element);
	}

	@Override
	public boolean removeKey(Integer key)
	{
		if (key == null)
			return false;

		int hash[] = hash(key);

		return removeKey(getNodeFrom(hash), key);
	}

	@Override
	public boolean renameKey(Integer oldKey, Integer newKey)
	{
		if (oldKey == null || newKey == null || containsKey(newKey))
			return false;

		E element = get(oldKey);

		return removeKey(oldKey) && add(newKey, element);
	}

	@Override
	public boolean update(Integer key, E value)
	{
		if (key == null || value == null)
			return false;

		int hash[] = hash(key);

		return update(getNodeFrom(hash), key, value);
	}

	@Override
	public E get(Integer key)
	{
		if (key == null)
			return null;

		int hash[] = hash(key);

		return get(getNodeFrom(hash), key);
	}

	@Override
	public Iterator<Integer> iteratorKey()
	{
		return new Iterator<Integer>()
				{
			private int iteration;
			private Iterator<MapItem<Integer, E>> items = iteratorItems();

			@Override
			public boolean hasNext()
			{
				return items.hasNext();
			}

			@Override
			public Integer next()
			{
				iteration++;
				return items.next().key;
			}

			@Override
			public String toString()
			{
				ObjectDescription description = new ObjectDescription(getClass());

				description.append("iteration", iteration);
				description.append("size", size);

				return description.toString();
			}
		};
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			private int iteration;
			private Iterator<MapItem<Integer, E>> items = iteratorItems();

			@Override
			public boolean hasNext()
			{
				return items.hasNext();
			}

			@Override
			public E next()
			{
				iteration++;
				return items.next().value;
			}

			@Override
			public String toString()
			{
				ObjectDescription description = new ObjectDescription(getClass());

				description.append("iteration", iteration);
				description.append("size", size);

				return description.toString();
			}
		};
	}

	/**
	 * Chamado para verificar se um determinado nó de itens contém uma chave.
	 * @param node referência do nó que irá verificar se possui uma chave.
	 * @param key qual será a chave que deve ser verificada nos nós.
	 * @return true se conter essa chave seja em qual nó for ou false caso contrário.
	 */

	protected boolean containsKey(Node<MapElement<Integer, E>> node, int key)
	{
		while (node != null)
		{
			if (node.get().key == key)
				return true;

			node = node.getNext();
		}

		return false;
	}

	/**
	 * Procedimento que deve fazer a inserção de um elemento a um nó de elemento mapeado.
	 * Assim é possível se criar diversos AbstractNumber com vetores, matriz ou maiores.
	 * @param node referência do nó do qual deverá ser inserido o elemento em questão.
	 * @param key chave que foi atribuído ao elemento em questão para ser adicionado.
	 * @param element referência do elemento que deverá ser inserido no nó.
	 * @return true se conseguir adicionar ou false caso contrário.
	 */

	protected boolean add(Node<MapElement<Integer, E>> node, Integer key, E element)
	{
		if (node.get() == null)
			node.set(new MapElement<Integer, E>(key, element));

		else if (node.getNext() == null)
		{
			int compare = 0;
			int lkey = Integer.toString(key).length();
			int lnode = Integer.toString(node.get().key).length();

			if (lkey == lnode)
				compare = key.compareTo(node.get().key);
			else
				compare = lkey - lnode;

			MapElement<Integer, E> old = node.get();
			MapElement<Integer, E> map = new MapElement<Integer, E>(key, element);
			Node<MapElement<Integer, E>> next = new Node<MapElement<Integer,E>>(null);

			if (compare == 0)
				return false;

			Node.attach(node, next);

			if (compare > 0)
				next.set(map);

			else
			{
				next.set(old);
				node.set(map);
			}
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

			MapElement<Integer, E> map = new MapElement<Integer, E>(key, element);
			Node<MapElement<Integer, E>> next;

			if (node.get().key.compareTo(key) < 0)
				next = new Node<MapElement<Integer,E>>(map);

			else
			{
				next = new Node<MapElement<Integer,E>>(node.get());
				node.set(map);
			}

			Node.attach(next, node.getNext());
			Node.attach(node, next);
		}

		size++;

		return true;
	}

	/**
	 * Procedimento que deve fazer a obtenção de um elemento a um nó de elemento mapeado.
	 * Assim é possível se criar diversos AbstractNumber com vetores, matriz ou maiores.
	 * @param node nó do qual deverá ser percorrido para encontrar o elemento na chave.
	 * @param key chave atribuído ao elemento do qual deseja obter do mapeador.
	 * @return referência do elemento com a chave passada atribuído ou null se não encontrar.
	 */

	protected E get(Node<MapElement<Integer, E>> node, int key)
	{
		while (node != null && node.get() != null)
		{
			int compare = node.get().key.compareTo(key);

			if (compare == 0)
				return node.get().value;

			node = node.getNext();
		}

		return null;
	}

	/**
	 * Procedimento que deve fazer a remoção de um elemento através da sua chave.
	 * @param node referência do nó que será verificado se é possível remover.
	 * @param element referência do elemento que deverá ser removido no nó.
	 * @return true se conseguir encontrar e remover ou false caso contrário.
	 */

	protected boolean remove(Node<MapElement<Integer, E>> node, E element)
	{
		while (node != null && node.get() != null)
		{
			if (node.get().value.equals(element))
				break;

			node = node.getNext();
		}

		if (node == null)
			return false;

		size--;
		removeNode(node);

		return true;
	}

	/**
	 * Procedimento que deve fazer a remoção de um elemento através da sua chave.
	 * @param node referência do nó do qual será verificado se contém a chave.
	 * @param key chave do elemento do qual deverá ser removido do mapeamento.
	 * @return true se conseguir encontrar e remover ou false caso contrário.
	 */

	protected boolean removeKey(Node<MapElement<Integer, E>> node, int key)
	{
		while (node != null && node.get() != null)
		{
			int compare = node.get().key.compareTo(key);

			if (compare == 0)
				break;

			node = node.getNext();
		}

		if (node == null)
			return false;

		size--;
		removeNode(node);

		return true;
	}

	/**
	 * Remove um determinado nó do mapeamento, chamado quando o nó para ser removido,
	 * tiver sido encontrado, irá ligar o nó anterior com o próximo conforme necessário.
	 * @param node referência do nó do qual deve ser removido do mapeamento.
	 */

	private void removeNode(Node<MapElement<Integer, E>> node)
	{
		Node<MapElement<Integer, E>> prev = node.getPrev();
		Node<MapElement<Integer, E>> next = node.getNext();

		if (prev == null && next == null)
			node.set(null);

		else if (prev == null)
		{
			node.set(next.get());
			next = next.getNext();

			node.setNext(next);

			if (next == null)
				node.setNext(null);
			else
				next.setPrev(node);
		}

		else if (next == null)
			prev.setNext(null);

		else
			Node.attach(prev, next);		
	}

	/**
	 * Chamado para atualizar um determinado elemento de acordo com sua chave.
	 * Percorre um nó especificado tentando encontrar o item com tal chave.
	 * Quando encontrar o item em questão irá atualizar o seu elemento.
	 * @param node referência do nó do qual será procurado e atualizado.
	 * @param key qual é a chave do item que está guardado o elemento.
	 * @param value referÊncia do novo elemento que o item irá armazenar.
	 * @return true se encontrar e fazer a atualização ou false caso contrário.
	 */

	private boolean update(Node<MapElement<Integer, E>> node, int key, E value)
	{
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

	/**
	 * Procedimento que irá calcular qual o ponto para uma determinada chave.
	 * @param key nome da chave que será usada para fazer o calculo do ponto.
	 * @return aquisição do ponto para determinar a localização de uma chave.
	 */

	protected abstract int[] hash(Integer key);

	/**
	 * Procedimento que deve obter o nó respectivo a um determinado conjunto de hash.
	 * Utilizado pelos métodos que devem adicionar, remover, selecionar ou atualizar.
	 * Caso o hash não seja encontrado, este deverá ser criado para garantir existência.
	 * @param hash vetor contendo os códigos hash para localizar o nó desejado.
	 * @return referência do nó respectivo ao hash passado.
	 */

	protected abstract Node<MapElement<Integer, E>> getNodeFrom(int hash[]);

	/**
	 * Procedimento chamado pela iteração de itens, elementos e chaves.
	 * Deve listar todos os nós raízes existentes no mapeador.
	 * @return aquisição de um vetor contendo os nós raíz.
	 */

	protected abstract Node<MapElement<Integer, E>>[] toArrayNode();

	@Override
	public Iterator<MapItem<Integer, E>> iteratorItems()
	{
		return new Iterator<MapItem<Integer, E>>()
		{
			private Node<MapElement<Integer, E>> nodes[] = toArrayNode();
			private MapItem<Integer, E> node = getNext();
			private int index;

			private MapItem<Integer, E> getNext()
			{
				Node<MapElement<Integer, E>> node = nodes[index];

				while ((index < nodes.length -1 && node == null) || (node != null && node.get() == null))
					node = nodes[++index];

				if (node != null)
				{
					nodes[index] = node.getNext();
					MapItem<Integer, E> item = new MapItem<Integer, E>(node.get().key, node.get().value);

					return item;
				}

				return null;
			}

			@Override
			public boolean hasNext()
			{
				return node != null;
			}

			@Override
			public MapItem<Integer, E> next()
			{
				MapItem<Integer, E> current = node;
				node = getNext();

				return current;
			}

			@Override
			public String toString()
			{
				ObjectDescription description = new ObjectDescription(getClass());

				description.append("nodeIndex", index);

				return description.toString();
			}
		};
	}

	@Override
	public Iterable<Integer> iterateKey()
	{
		return new Iterable<Integer>()
		{
			@Override
			public Iterator<Integer> iterator()
			{
				return iteratorKey();
			}
		};
	}

	@Override
	public Iterable<MapItem<Integer, E>> iterateItems()
	{
		return new Iterable<Map.MapItem<Integer, E>>()
		{
			@Override
			public Iterator<MapItem<Integer, E>> iterator()
			{
				return iteratorItems();
			}
		};
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		Iterator<MapItem<Integer, E>> iterator = iteratorItems();

		while (iterator.hasNext())
		{
			MapItem<Integer, E> map = iterator.next();
			description.append(Integer.toString(map.key), map.value);
		}

		return description.toString();
	}
}
