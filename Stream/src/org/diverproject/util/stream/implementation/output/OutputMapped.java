package org.diverproject.util.stream.implementation.output;

import static org.diverproject.util.Util.format;
import static org.diverproject.util.Util.i;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.stream.StreamRuntimeException;

/**
 * <h1>Saída Mapeada</h1>
 *
 * <p>A entrada de dados mapeada é feito através de um MappedByteBuffer onde os dados são obtidos.
 * Objetos desse tipo poderão ser obtidos através da especificação de um arquivo ou stream.
 * Com a stream do arquivo será possível obter o canal do arquivo e deste criar o mapa.</p>
 *
 * @see GenericOutput
 * @see File
 * @see FileOutputStream
 *
 * @author Andrew Mello
 */

public class OutputMapped extends GenericOutput
{
	/**
	 * Tamanho interno mínimo do buffer que será aceito para escrita de dados.
	 */
	public static final int MIN_BUFFER_SIZE = 32;

	/**
	 * Tamanho interno padrão do buffer que será utilizado para escrita de dados.
	 */
	public static final int DEFAULT_BUFFEER_SIZE = 1024;

	/**
	 * Buffer com bytes mapeados do arquivo que será escrito.
	 */
	private byte[] buffer;

	/**
	 * Stream para saída de dados de um arquivo em disco.
	 */
	private FileOutputStream fos;

	/**
	 * Até quantos bytes podem ser escritos neste arquivo.
	 */
	private int length;

	/**
	 * Ponteiro de escrita dos dados no buffer interno.
	 */
	private int bufferOffset;

	/**
	 * Se habilitado escreve os dados sempre houver um flush nos dados.
	 */
	private boolean partialFlush;

	/**
	 * Cria um novo escritor de dados em vetor a partir de um arquivo os os dados serão escritos.
	 * @param path caminho completo ou parcial do arquivo do qual será escrito por essa saída.
	 * @throws IOException apenas se houver algum problema para iniciar a stream do arquivo.
	 */

	public OutputMapped(String path) throws IOException
	{
		this(new File(path));
	}

	/**
	 * Cria um novo escritor de dados em vetor a partir de um arquivo os os dados serão escritos.
	 * @param file referência de um objeto que represente um arquivo em disco no Java.
	 * @throws IOException apenas se houver algum problema para iniciar a stream do arquivo.
	 */

	public OutputMapped(File file) throws IOException
	{
		fos = new FileOutputStream(file);
		buffer = new byte[DEFAULT_BUFFEER_SIZE];
		length = Integer.MAX_VALUE;
	}

	@Override
	public void flush()
	{
		forceFlush(partialFlush);
	}

	@Override
	public void write(byte b)
	{
		if (space() == 0)
			throw new StreamRuntimeException("espaço máximo alcançado");

		buffer[bufferOffset++] = b;

		if (bufferOffset == buffer.length)
			flush();
	}

	@Override
	public int offset()
	{
		try {
			return fos == null ? 0 : i(fos.getChannel().position());
		} catch (IOException e) {
			return 0;
		}
	}

	@Override
	public int length()
	{
		return length;
	}

	@Override
	public boolean isClosed()
	{
		return fos == null || buffer == null || !fos.getChannel().isOpen();
	}

	@Override
	public void close()
	{
		if (fos == null || buffer == null)
			return;

		try {

			forceFlush(true);

			fos.close();
			buffer = null;
			fos = null;

		} catch (IOException e) {
			throw new StreamRuntimeException(e.getMessage());
		}
	}

	@Override
	public void skipe(int bytes)
	{
		try {

			if (bytes > 0)
				putBytes(new byte[bytes]);
			else
			{
				forceFlush(true);
				fos.getChannel().position(fos.getChannel().position() + bytes);
			}

		} catch (IOException e) {
			throw new StreamRuntimeException(e.getMessage());
		}
	}

	@Override
	public void reset()
	{
		try {

			forceFlush(true);
			fos.getChannel().position(0);

		} catch (IOException e) {
			throw new StreamRuntimeException(e.getMessage());
		}
	}

	public void forceFlush(boolean fosForceFlush)
	{
		if (buffer == null)
			throw new StreamRuntimeException("buffer fechado");

		if (fos == null || !fos.getChannel().isOpen())
			throw new StreamRuntimeException("stream fechada");

		try {

			fos.write(buffer, 0, bufferOffset);
			bufferOffset = 0;

			if (fosForceFlush)
				fos.flush();

		} catch (IOException e) {
			throw new StreamRuntimeException(e.getMessage());
		}
	}

	/**
	 * Através deste método é possível limitar a quantidade de bytes que poderão ser mapeados na stream.
	 * @param length quantidade limite de bytes que podem ser escritos nesta stream para saída de dados.
	 */

	public void setLength(int length)
	{
		this.length = length;
	}

	/**
	 * Libera os dados em buffer (por garantia) e redimensiona o buffer interno para escrita dos dados.
	 * Existe um tamanho mínimo do buffer porém não há limite para o tamanho máximo do mesmo.
	 * @param size quantidade de bytes que o buffer interno da stream irá utilizar.
	 */

	public void setBufferSize(int size)
	{
		if (size < MIN_BUFFER_SIZE)
			throw new StreamRuntimeException("buffer mínimo é de %d bytes", MIN_BUFFER_SIZE);

		flush();

		buffer = new byte[size];
	}

	@Override
	protected void toString(ObjectDescription description)
	{
		if (isClosed())
		{
			description.append("closed");
			return;
		}

		if (buffer != null)
			description.append("buffer", format("%d/%d", bufferOffset, buffer.length));

		description.append("offset", offset());
		description.append("length", length());
		description.append("space", space());
		description.append("closed", isClosed());
		description.append("inverted", isInverted());
	}
}
