package org.diverproject.util.collection;

/**
 * <p><h1>Elemento de Pasta</h1></p>
 *
 * <p>Interface usada para permitir que um objeto seja armazenado em uma pasta virtual.
 * Precisa implementar apenas um método do qual é usado para obter o caminho do objeto.
 * Esse caminho será usado na pasta virtual para indicar onde ele deve ser alocado.</p>
 *
 * @author Andrew Mello
 */

public interface FolderElement
{
	/**
	 * A formatação padrão do caminho do arquivo é a utilização de barras <b>/</b>.
	 * Cada barra indica um pasta e o último elemento desta o nome do arquivo.
	 * @return aquisição do caminho do objeto para alocar na pasta virtual.
	 */

	String getFilePath();
}
