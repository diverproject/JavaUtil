package org.diverproject.util.stream.implementation;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.diverproject.util.stream.Output;
import org.diverproject.util.stream.implementation.output.OutputByteArray;
import org.diverproject.util.stream.implementation.output.OutputMapped;

/**
 * <h1>Construtor de Saída</h1>
 *
 * <p>Criado para funcionar como meio intermediário entre a criação da comunicação e a fábrica.
 * Possuindo apenas os métodos que por nome a parâmetros é possível entender sua funcionalidade.
 * Assim, será possível "esconder" a real origem das comunicações que foram usadas por ele.</p>
 *
 * <p>Interface que possui todos os métodos possíveis para a criação de comunicações para saída de dados.
 * As possíveis alternativas oferecidas é de um arquivo, OutputStream ou então em um vetor de bytes.
 * A forma como é feito a escrita dos dados será respectiva de acordo com a fábrica que gerou este.</p>
 *
 * @see Output
 *
 * @author Andrew Mello
 */

public class OutputBuilder
{
	/**
	 * Cria uma nova comunicação de saída com um arquivo de acordo com o caminho especificado abaixo.
	 * @param path caminho parcial em relação a aplicação ou completo do arquivo a ser escrito.
	 * @return aquisição de uma nova comunicação de saída com o arquivo especificado arquivo.
	 * @throws IOException apenas se houver algum problema para iniciar a stream do arquivo.
	 */

	public Output newOutput(String path) throws IOException
	{
		return newOutput(new File(path));
	}

	/***
	 * Cria uma nova comunicação de saída com um arquivo de acordo com a especificação abaixo do arquivo.
	 * @param file referência do objeto que contém as informações do caminho do arquivo a ser escrito.
	 * @return aquisição de uma nova comunicação de saída com o arquivo especificado arquivo.
	 * @throws IOException apenas se houver algum problema para iniciar a stream do arquivo.
	 */

	public Output newOutput(File file) throws IOException
	{
		return new OutputMapped(file);
	}

	/**
	 * Cria uma nova comunicação de saída com uma comunicação de saída padrão do java existente.
	 * @param os referência da comunicação de saída padrão do java que será usada como saída.
	 * @return aquisição de uma nova comunicação de saída avançada com base no padrão do java.
	 * @throws IOException apenas se houver algum problema para iniciar a stream do arquivo.
	 */

	public Output newOutput(OutputStream os)
	{
		return new org.diverproject.util.stream.implementation.output.OutputStream(os);
	}

	/**
	 * Cria uma nova comunicação de saída onde os dados que forem escritos serão repassados a um vetor.
	 * @param data vetor de bytes que será usada como saída interna dos dados que forem escritos.
	 * @return aquisição de uma nova comunicação de saída para um vetor de bytes.
	 */

	public Output newOutput(byte[] data)
	{
		return new OutputByteArray(data);
	}
}
