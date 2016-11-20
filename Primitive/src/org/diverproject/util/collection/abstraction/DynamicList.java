package org.diverproject.util.collection.abstraction;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.ArrayUtil;

/**
 * <p><h1>Lista Estática</h1><p>
 *
 * <p>As listas dinâmicas determinam que o seu tamanho para armazenamento dos elementos,
 * pode ser redimensionado, por tanto a sua capacidade pode ser aumentada ou reduzida.
 * De modo geral esse tipo de estrutura é quase como um simples vetor versátil.</p>
 *
 * <p>A questão da dinâmica em sua capacidade ocorre internamente ao adicionar ou remover.
 * Quando adicionado verifica se está cheio, e se estiver irá aumentar o tamanho deste.
 * No caso da remoção de um elemento verifica se há espaços demais sobrando e o corta.</p>
 *
 * @see AbstractList
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public class DynamicList<E> extends AbstractList<E>
{
	/**
	 * Constrói uma nova lista estática e inicializando o seu vetor interno.
	 */

	public DynamicList()
	{
		this(DEFAULT_SIZE);
	}

	/**
	 * Constrói uma nova lista estática e inicializando o seu vetor interno.
	 * @param size qual será o tamanho usado para inicialização da lista.
	 */

	public DynamicList(int size)
	{
		super(size);
	}

	/**
	 * Constrói uma nova lista estática e inicializando o seu vetor interno.
	 * Nesse construtor será permitido definir o tipo de dado em toArray.
	 * @param generic classe respectiva ao tipo de dado que será armazenado.
	 */

	public DynamicList(Class<?> generic)
	{
		super(DEFAULT_SIZE);

		setGeneric(generic);
	}

	@Override
	public boolean add(E element)
	{
		if (element == null)
			return false;

		if (isFull())
			elements = ArrayUtil.increseIn(elements, DEFAULT_SIZE);

		elements[size++] = element;

		return true;
	}

	@Override
	public boolean remove(E element)
	{
		if (super.remove(element))
			if (elements.length - size >= DEFAULT_SIZE)
			{
				elements = ArrayUtil.decreaseIn(elements, DEFAULT_SIZE);
				return true;
			}

		return false;
	}

	@Override
	public boolean remove(int index)
	{
		if (super.remove(index))
			if (elements.length - size >= DEFAULT_SIZE)
			{
				elements = ArrayUtil.decreaseIn(elements, DEFAULT_SIZE);
				return true;
			}

		return false;
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		for (Object element : this)
			description.append(element);

		return description.toString();
	}
}
