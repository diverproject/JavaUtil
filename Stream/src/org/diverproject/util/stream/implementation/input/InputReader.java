package org.diverproject.util.stream.implementation.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.diverproject.util.stream.StreamRuntimeException;

/**
 * <h1>Entrada com Reader</h1>
 *
 * <p>Esse tipo de stream irá usar como base um Reader|BufferedReader que pode ser especificado diretamente.
 * Podendo ainda ser especificado através de um objeto File representando um arquivo ou o path dele.
 * Caso seja por um arquivo irá criar esse InputStream para que possa ser usado internamente.</p>
 *
 * @see GenericInput
 * @see InputReader
 *
 * @author Andrew
 */

public class InputReader extends GenericInput
{
	/**
	 * Stream para entrada de dados quando bytes forem lidos.
	 */
	private Reader reader;

	/**
	 * Cria uma nova stream através de uma stream de entrada de dados a partir de um arquivo especifico.
	 * @param path caminho completo ou parcial do arquivo em disco que será usado para leitura.
	 * @throws FileNotFoundException apenas se o arquivo não for encontrado.
	 */

	public InputReader(String path) throws FileNotFoundException
	{
		this(new File(path));
	}

	/**
	 * Cria uma nova stream através de uma stream de entrada de dados a partir de um arquivo especifico.
	 * @param file referência do objeto que identifica um arquivo em disco que será usado.
	 * @throws FileNotFoundException apenas se o arquivo não for encontrado.
	 */

	public InputReader(File file) throws FileNotFoundException
	{
		this(new FileReader(file));
	}

	/**
	 * Cria uma nova stream através de uma stream de entrada de dados pré-especificada.
	 * Para esse caso não será considerado qualquer limite de dados para se ler.
	 * @param fr referência da stream que será usada para escrever os dados.
	 */

	public InputReader(FileReader fr)
	{
		this(new BufferedReader(fr));
	}

	/**
	 * Cria uma nova stream através de uma stream de entrada de dados pré-especificada.
	 * @param is referência da stream que será usada para escrever os dados.
	 * @param length quantos bytes podem ser lidos nessa stream (limite),
	 * caso não haja limite deve ser especificado com o valor 0 (zero).
	 */

	public InputReader(BufferedReader br)
	{
		this.reader = br;
	}

	@Override
	public byte getByte()
	{
		return read();
	}

	@Override
	public byte read()
	{
		try {
			return (byte) reader.read();
		} catch (IOException e) {
			throw new StreamRuntimeException(e);
		}
	}

	@Override
	public int offset()
	{
		return 0;
	}

	@Override
	public int length()
	{
		return 0;
	}

	@Override
	public boolean isClosed()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void close()
	{
		try {
			reader.close();
		} catch (IOException e) {
			throw new StreamRuntimeException(e);
		}
	}

	@Override
	public void skipe(int bytes)
	{
		try {
			reader.skip(bytes);
		} catch (IOException e) {
			throw new StreamRuntimeException(e);
		}
	}

	@Override
	public void reset()
	{
		throw new UnsupportedOperationException();
	}
}
