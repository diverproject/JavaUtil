package org.diverproject.util.stream.implementation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.diverproject.util.stream.Input;
import org.diverproject.util.stream.implementation.input.InputByteArray;
import org.diverproject.util.stream.implementation.input.InputMapped;

/**
 * <h1>Construtor de Entrada</h1>
 *
 * <p>Criado para funcionar como meio intermediário entre a criação da comunicação e a fábrica.
 * Possuindo apenas os métodos que por nome a parâmetros é possível entender sua funcionalidade.
 * Assim, será possível "esconder" a real origem das comunicações que foram usadas por ele.</p>
 *
 * <p>Interface que possui todos os métodos possíveis para a criação de comunicações para entrada de dados.
 * As possíveis alternativas oferecidas é de um arquivo, InputStream ou então em um vetor de bytes.
 * A forma como é feito a leitura dos dados será respectiva de acordo com a fábrica que gerou este.</p>
 *
 * @see Input
 *
 * @author Andrew Mello
 */

public class InputBuilder
{
	/**
	 * Cria uma nova comunicação com um arquivo para comunicação de dados através do caminho especificado.
	 * @param path caminho parcial respectivo a aplicação ou completo do arquivo que será lido.
	 * @return aquisição de uma nova comunicação para entrada de dados a partir de um arquivo.
	 * @throws IOException ocorre apenas se houver algum problema durante a leitura.
	 */

	public Input newInput(String path) throws IOException
	{
		return new InputMapped(path);
	}

	/**
	 * Cria uma nova comunicação com um arquivo para comunicação de dados através do caminho especificado.
	 * @param file referência do objeto que contém as informações do caminho do arquivo a ser lido.
	 * @return aquisição de uma nova comunicação para entrada de dados a partir de um arquivo.
	 * @throws IOException ocorre apenas se houver algum problema durante a leitura.
	 */

	public Input newInput(File file) throws IOException
	{
		return new InputMapped(file);
	}

	/**
	 * Cria uma nova comunicação com uma comunicação para entrada de dados padrão do java especificado.
	 * @param is referência da comunicação padrão do java pra entrada de dados que será usada.
	 * @return aquisição de uma nova comunicação avançada a partir de uma padrão do java.
	 */

	public Input newInput(InputStream is)
	{
		return new org.diverproject.util.stream.implementation.input.InputStream(is);
	}

	/**
	 * Constrói uma nova comunicação usando um vetor de bytes para fazer a entrada de dados.
	 * @param data referência do vetor que será usado para fazer a leitura dos dados.
	 * @return aquisição de uma nova comunicação para entrada de dados com vetor.
	 */

	public Input newInput(byte[] data)
	{
		return new InputByteArray(data);
	}
}
