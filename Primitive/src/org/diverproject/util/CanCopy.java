package org.diverproject.util;

/**
 * <p><h1>Pode Copiar</h1></p>
 *
 * Classes que implementam essa interface determinam que seus objetos podem copiar os dados
 * de um outro objeto. Espera-se que essa cópia não faça uma referência dos objetos do mesmo,
 * e sim uma cópia, gerando os mesmos dados porém com referências diferentes em memória.
 *
 * @param <E> tipo de 
 */

public interface CanCopy<E>
{
	/**
	 * Copia os dados de um outro objeto esperado ser do mesmo tipo.
	 * @param e objeto do qual deve ser copiados os dados.
	 */

	void copyFrom(E e);
}
