package org.diverproject.util.collection;

/**
 * <p><h1>Coleção</h1></p>
 *
 * <p>Todos os tipos para construção das estruturas de dados dessa biblioteca são coleções.
 * Esse nome é dado pelo fato de colecionarem dados e estes serem de um tipo pré-definido.
 * Cada tipo de estrutura possui suas vantagens e desvantagens de acordo com o objetivo.</p>
 *
 * <p>Existem diversos tipos de estruturas de dados que podem ser criadas para cada situação.
 * Os mais comuns serão aplicados nessa biblioteca como coleções (listas, filas, pilhas).
 * Tendo em vista a possibilidade em que podem ser estáticas como também dinâmicas.</p>
 *
 * <p>Sua funcionalidade é a mesma em ambos os modos diferenciando apenas na sua capacidade.
 * Onde no caso das estáticas a sua capacidade é fixa no momento em que este for criado.
 * Em quanto a dinâmica permite um número "infinito" de elementos (Integer.MAX_VALUE).</p>
 *
 * <p>Além dessas funcionalidade em comum, uma coleção também pode sofrer ser iterável.
 * Quanto iterável permite que este possa passar pelo procedimento de <b>for each</b>.
 * Assim é possível "obter todos os elementos na adicionados na coleção de uma só vez".</p>
 *
 * @see Iterable
 *
 * @author Andrew
 *
 * @param <E> qual será o tipo de dado que será armazenado na coleção.
 */

public interface Collection<E> extends Iterable<E>
{
	/**
	 * Classe genérica de uma coleção é utilizada pelo toArray.
	 * Onde será usado pra criar uma instância de acordo com tal.
	 * @return aquisição do tipo de dado genérico dessa coleção.
	 */

	Class<?> getGeneric();

	/**
	 * Permite definir qual será o tipo de dado genérico obtido por toArray.
	 * Procedimento utilizado pelos construtores quando definido o mesmo.
	 * Deve ser definido sempre que toArray for usado e com o tipo de dado.
	 * @param generic tipo genérico dessa coleção de dados (respectivo a E).
	 */

	void setGeneric(Class<?> generic);

	/**
	 * Quanto esse procedimento for chamado irá dizer a estrutura de dados,
	 * que todos os elementos dentro desta podem ser descartados, removidos.
	 * Internamente esse procedimento é feito reconstruindo a fonte.
	 */

	void clear();

	/**
	 * Tamanho da coleção irá determinar quantos elementos ela possui.
	 * @return aquisição do número de elementos armazenados atualmente.
	 */

	int size();

	/**
	 * Comprimento da coleção irá determinar a sua capacidade máxima.
	 * @return aquisição do número de elementos que podem ser armazenados.
	 */

	int length();

	/**
	 * Procedimento que irá fazer a verificação do estado de vazio da coleção.
	 * @return true se estiver vazia ou false se houver ao menos um elemento.
	 */

	boolean isEmpty();

	/**
	 * Procedimento que irá fazer a verificação do estado de cheio da coleção.
	 * @return true se estiver cheia ou false se houver ao menos um espaço.
	 */

	boolean isFull();

	/**
	 * Procedimento que irá verificar se a coleção já possui um determinado elemento.
	 * @param element referência do objeto (elemento) que será verificado a existência.
	 * @return true se ele já tiver sido adicionado ou false caso não encontrado.
	 */

	boolean contains(E element);

	/**
	 * Constrói um novo vetor que irá armazenar todos os elementos da coleção.
	 * Utiliza o procedimento de iteração como padrão por todas coleções possuírem.
	 * Para que esse procedimento funcione, deve ser utilizando setGeneric antes.
	 * Caso já tenha sido definido uma vez ou pelo construtor não terá necessidade.
	 * @return aquisição de um vetor contendo todos os elementos da coleção.
	 */

	E[] toArray();
}
