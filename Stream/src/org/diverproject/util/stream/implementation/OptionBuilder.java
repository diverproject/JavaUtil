package org.diverproject.util.stream.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.diverproject.util.stream.implementation.input.InputMapped;
import org.diverproject.util.stream.implementation.option.OptionInput;
import org.diverproject.util.stream.implementation.option.OptionListInput;
import org.diverproject.util.stream.implementation.option.OptionListOutput;
import org.diverproject.util.stream.implementation.option.OptionOutput;
import org.diverproject.util.stream.implementation.output.OutputStream;

/**
 * <h1>Construtor de Entrada</h1>
 *
 * <p>Criado para funcionar como meio intermediário entre a criação da comunicação e a fábrica.
 * Possuindo apenas os métodos que por nome a parâmetros é possível entender sua funcionalidade.
 * Assim, será possível "esconder" a real origem das comunicações que foram usadas por ele.</p>
 *
 * <p>Interface que possui todos os métodos possíveis para a criação de comunicações para entrada de dados.
 * As possíveis alternativas oferecidas é de um arquivo, InputOptionStream ou então em um vetor de bytes.
 * A forma como é feito a leitura dos dados será respectiva de acordo com a fábrica que gerou este.</p>
 *
 * @see OptionInput
 *
 * @author Andrew Mello
 */

public class OptionBuilder
{
	/**
	 * Cria uma nova comunicação com um arquivo para comunicação de dados através do caminho especificado.
	 * @param path caminho parcial respectivo a aplicação ou completo do arquivo que será lido.
	 * @return aquisição de uma nova comunicação para entrada de dados a partir de um arquivo.
	 * @throws IOException ocorre apenas se houver algum problema durante a leitura.
	 */

	public OptionInput newInputOption(String path) throws IOException
	{
		return new OptionListInput(new InputMapped(path));
	}

	/**
	 * Cria uma nova comunicação com um arquivo para comunicação de dados através do caminho especificado.
	 * @param file referência do objeto que contém as informações do caminho do arquivo a ser lido.
	 * @return aquisição de uma nova comunicação para entrada de dados a partir de um arquivo.
	 * @throws IOException ocorre apenas se houver algum problema durante a leitura.
	 */

	public OptionInput newInputOption(File file) throws IOException
	{
		return new OptionListInput(new InputMapped(file));
	}

	/**
	 * Cria uma nova comunicação de saída com um arquivo de acordo com o caminho especificado abaixo.
	 * @param path caminho parcial em relação a aplicação ou completo do arquivo a ser escrito.
	 * @return aquisição de uma nova comunicação de saída com o arquivo especificado arquivo.
	 * @throws FileNotFoundException ocorre apenas se não for possível encontrar o arquivo.
	 */

	public OptionOutput newOutputOption(String path) throws FileNotFoundException
	{
		return new OptionListOutput(new OutputStream(path));
	}

	/***
	 * Cria uma nova comunicação de saída com um arquivo de acordo com a especificação abaixo do arquivo.
	 * @param file referência do objeto que contém as informações do caminho do arquivo a ser escrito.
	 * @return aquisição de uma nova comunicação de saída com o arquivo especificado arquivo.
	 * @throws FileNotFoundException ocorre apenas se não for possível encontrar o arquivo.
	 */

	public OptionOutput newOutputOption(File file) throws FileNotFoundException
	{
		return new OptionListOutput(new OutputStream(file));
	}
}
