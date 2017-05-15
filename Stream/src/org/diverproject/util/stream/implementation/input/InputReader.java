package org.diverproject.util.stream.implementation.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.diverproject.util.ObjectDescription;
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
	private BufferedReader reader;

	/**
	 * Quantidade de bytes que já foram lidos.
	 */
	private int offset;

	/**
	 * Quantidade limite de bytes que podem ser lidos.
	 */
	private int length;

	/**
	 * Cria uma nova stream através de uma stream de entrada de dados a partir de um arquivo especifico.
	 * @param path caminho completo ou parcial do arquivo em disco que será usado para leitura.
	 * @throws IOException apenas se houver problema em acessar o arquivo para escrita.
	 */

	public InputReader(String path) throws IOException
	{
		this(new File(path));
	}

	/**
	 * Cria uma nova stream através de uma stream de entrada de dados a partir de um arquivo especifico.
	 * @param file referência do objeto que identifica um arquivo em disco que será usado.
	 * @throws IOException apenas se houver problema em acessar o arquivo para escrita.
	 */

	public InputReader(File file) throws IOException
	{
		this(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));

		this.length = (int) file.length();
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
		this.length = -1;
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
			offset++;
			return (byte) reader.read();
		} catch (IOException e) {
			offset--;
			throw new StreamRuntimeException(e);
		}
	}

	@Override
	public int offset()
	{
		return offset;
	}

	@Override
	public int length()
	{
		return length == -1 ? offset : length;
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
			reader = null;
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

	@Override
	protected void toString(ObjectDescription description)
	{
		description.append("offset", offset());
		description.append("length", length());
		description.append("space", space());
		description.append("inverted", isInverted());
	}
}
