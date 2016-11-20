package org.diverproject.util.collection.abstraction;

import java.lang.reflect.Array;

import org.diverproject.util.collection.Collection;

/**
 * <p><h1>Coleção Abstrata</h1></p>
 *
 * <p>Essa classe possui algumas propriedades que todas as coleções devem possuir.
 * No caso são os atributos que definem o seu tipo genérico e o tamanho da estrutura.
 * Além disso irá implementar alguns dos métodos que são comuns entre as estruturas.</p>
 *
 * @see Collection
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public abstract class AbstractCollection<E> implements Collection<E>
{
	protected int size;
	private Class<?> generic;

	public AbstractCollection()
	{
		size = 0;
		generic = Object.class;
	}

	@Override
	public Class<?> getGeneric()
	{
		return generic == null ? Object.class : generic;
	}

	@Override
	public void setGeneric(Class<?> generic)
	{
		this.generic = generic;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public boolean isFull()
	{
		return size == length();
	}

	@Override
	@SuppressWarnings("unchecked")
	public E[] toArray()
	{
		E[] array = (E[]) Array.newInstance(generic, size());
		int i = 0;

		for (E element : this)
			array[i++] = element;

		return array;
	}

}
