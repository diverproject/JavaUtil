package org.diverproject.util.stream.implementation.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.diverproject.util.lang.IntUtil;
import org.diverproject.util.stream.StreamRuntimeException;

/**
 * <h1>Saída com Writer</h1>
 *
 * <p>Esse tipo de stream irá usar como base um Writer|BufferedWriter que pode ser especificado diretamente.
 * Podendo ainda ser especificado através de um objeto File representando um arquivo ou o path dele.
 * Caso seja por um arquivo irá criar esse OutputStream para que possa ser usado internamente.</p>
 *
 * @see GenericOutput
 * @see File
 * @see BufferedWriter
 *
 * @author Andrew
 */

public class OutputWriter extends GenericOutput
{
	/**
	 * Quantidade de bytes já escritos.
	 */
	private int offset;

	/**
	 * Stream para saída de dados quando bytes forem escritos.
	 */
	private BufferedWriter writer;

	/**
	 * Cria uma nova stream através de uma stream de saída de dados em um arquivo especifico.
	 * @param path caminho completo ou parcial do arquivo em disco que será usado para escrita.
	 * @throws IOException quando não for possível estabelecer a stream de escrita.
	 */

	public OutputWriter(String path) throws IOException
	{
		this(new File(path));
	}

	/**
	 * Cria uma nova stream através de uma stream de saída de dados em um arquivo especifico.
	 * @param file referência do objeto que identifica um arquivo em disco que será usado.
	 * @throws IOException quando não for possível estabelecer a stream de escrita.
	 */

	public OutputWriter(File file) throws IOException
	{
		this(new FileWriter(file));
	}

	/**
	 * Cria uma nova stream através de uma stream de saída de dados pré-especificada.
	 * Para esse caso não será considerado qualquer limite de dados para se escrever.
	 * @param fw referência da stream que será usada para escrever os dados.
	 */

	public OutputWriter(FileWriter fw)
	{
		this(new BufferedWriter(fw));
	}

	/**
	 * Cria uma nova stream através de uma stream de saída de dados pré-especificada.
	 * @param bw referência da stream que será usada para escrever os dados.
	 * caso não haja limite deve ser especificado com o valor 0 (zero).
	 */

	public OutputWriter(BufferedWriter bw)
	{
		this.writer = bw;
	}

	@Override
	public void putByte(byte b)
	{
		write(b);
	}

	@Override
	public void write(byte b)
	{
		try {

			offset++;
			writer.write(IntUtil.parseByte(b));

		} catch (IOException e) {
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
		return offset;
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
			writer.close();
			writer = null;
		} catch (IOException e) {
			throw new StreamRuntimeException(e);
		}
	}

	@Override
	public void skipe(int bytes)
	{
		try {
			writer.write(new char[bytes]);
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
	public void flush()
	{
		try {
			writer.flush();
		} catch (IOException e) {
			throw new StreamRuntimeException(e);
		}
	}
}
