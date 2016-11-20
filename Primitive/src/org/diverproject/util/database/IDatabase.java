package org.diverproject.util.database;

/**
 * <p><h1>Database</h1></p>
 *
 * <p>Interface que possui alguns métodos que permitem trabalhar como forma de banco de dados.
 * Esses métodos devem ser implementados para fazer o gerenciamento dos elementos no banco.
 * Assim sendo permitindo adicionar, atualizar, remover e selecionar elementos no mesmo.
 * Também permite obter o número de elementos inseridos e tamanho limite do banco de dados.</p>
 *
 * @author Andrew
 *
 * @param <E> tipo de da dado que será armazenado no banco de dados.
 */

interface IDatabase<E>
{
	/**
	 * Verifica se o banco de dados está ou não vazio.
	 * @return true se estiver ou false caso contrário.
	 */

	boolean isEmpety();

	/**
	 * Verifica se o banco de dados está ou não cheio.
	 * @return true se estiver ou false caso contrário.
	 */

	boolean isFully();

	/**
	 * Permite atualizar um determinado índice do banco de dados com um novo elemento.
	 * Caso o índice esteja ocupado será substituido pelo elemento passado.
	 * @param index índice do elemento no banco para ser substituído.
	 * @param element referência do elemento do qual deverá substituir.
	 * @return true se atualizar ou false se índice inválido ou em branco.
	 */

	boolean update(int index, E element);

	/**
	 * Permite remover um determinado elemento em um determinado índice.
	 * @param index índice do elemento do qual deseja remover do banco.
	 * @return true se conseguir remover ou false caso não exista
	 */

	boolean remove(int index);

	/**
	 * Permite selecionar um único elemento do banco de dados de acordo com o seu índice.
	 * @param index índice do elemento que deseja obter de dentro do banco de dados.
	 * @return aquisição do elemento no índice ou null com índice inválido ou livre para elemento.
	 */

	E select(int index);

	/**
	 * Constrói um vetor com os elementos selecionados dos índices respectivos.
	 * Caso se um índice estiver em branco ou inválido será preenchido com null.
	 * @param index índices separados por vírgula dos elementos desejados.
	 * @return vetor contendo os elementos respectivos dos índices.
	 */

	E[] select(int... index);

	/**
	 * Tamanho do banco de dados determina o seu espaço interno ocupado.
	 * @return aquisição do número de elementos inseridos no banco.
	 */

	int size();

	/**
	 * Comprimento do banco de dados determina o seu espaço interno máximo.
	 * @return aquisição do número máximo de elementos permitidos.
	 */

	int length();
}
