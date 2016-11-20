package org.diverproject.util.collection;

/**
 * <p><h1>Pasta</h1></p>
 *
 * <p>Classes que utilizam essa interface devem implementar métodos que permite torná-la uma pasta virtual.
 * Uma pasta virtual não apenas irá trabalhar com os elementos como também com caminho de diretório como chave.
 * Para que um elemento seja considerado um arquivo virtual deverá utilizar da interface adequada para tal.</p>
 *
 * <p>Espera-se que a alocação dos elementos sejam feitos de modo que a utilização de caminhos facilite a busca.
 * Caso esse requisito não seja atendido na implementação, a utilização desta interface irá perder seu real valor.</p>
 *
 * @see Collection
 *
 * @author Andrew Mello
 *
 * @param <E> tipo de elemento do qual será armazenado.
 */

public interface Folder<E> extends Collection<E>
{
	/**
	 * Adiciona um novo elemento a essa pasta virtual, deve verificar o caminho do mesmo.
	 * O arquivo deve ser alocado de acordo com o seu caminho especificado no mesmo.
	 * @param element referência do elemento (arquivo) do qual deve ser inserido.
	 * @return true se conseguir adicionar ou false caso contrário.
	 */

	boolean add(E element);

	/**
	 * Remove um elemento (arquivo) da pasta virtual a partir do seu caminho no mesmo.
	 * @param path caminho onde está alocado o arquivo do qual deve ser removido.
	 * @return true se conseguir remover ou false caso contrário.
	 */

	boolean remove(String path);

	/**
	 * Verifica se um determinado caminho existe dentro dessa pasta virtual.
	 * @param path caminho respectivo ao arquivo do qual deseja verificar.
	 * @return true se existir o caminho ou false caso contrário.
	 */

	boolean contains(String path);

	/**
	 * Obtém um determinado arquivo de acordo com o caminho especificado do mesmo.
	 * @param path caminho do qual se encontra o arquivo na pasta virtual.
	 * @return aquisição do arquivo no caminho especificado ou null se não encontrar.
	 */

	E get(String path);

	/**
	 * O nome da pasta é o que indica como deve ser o inicio de todos os caminhos.
	 * @return aquisição do nome que foi definido a pasta raíz principal.
	 */

	String getName();
}
