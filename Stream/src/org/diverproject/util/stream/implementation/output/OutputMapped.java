package org.diverproject.util.stream.implementation.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

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
 * @see OutputStream
 * @see FileChannel
 * @see MappedByteBuffer
 *
 * @author Andrew Mello
 */

public class OutputMapped extends GenericOutput
{
	/**
	 * Stream da saída de dados para liberação dos dados.
	 */
	private FileOutputStream fos;

	/**
	 * Buffer com bytes mapeados do arquivo que será escrito.
	 */
	private MappedByteBuffer map;

	/**
	 * Cria um novo escritor de dados em vetor a partir de um arquivo os os dados serão escritos.
	 * @param path caminho completo ou parcial do arquivo do qual será escrito por essa saída.
	 * @param length quantos bytes deverão ser possíveis escrever no mapa para alocar espaço.
	 * @throws IOException apenas se houver algum problema para iniciar a stream do arquivo.
	 */

	public OutputMapped(String path, long length) throws IOException
	{
		this(new File(path), length);
	}

	/**
	 * Cria um novo escritor de dados em vetor a partir de um arquivo os os dados serão escritos.
	 * @param file referência de um objeto que represente um arquivo em disco no Java.
	 * @param length quantos bytes deverão ser possíveis escrever no mapa para alocar espaço.
	 * @throws IOException apenas se houver algum problema para iniciar a stream do arquivo.
	 */

	public OutputMapped(File file, long length) throws IOException
	{
		this(new FileOutputStream(file), length);
	}

	/**
	 * Cria um novo escritor de dados em vetor a partir de um arquivo os os dados serão escritos.
	 * @param fos referência do objeto que possui a stream para saída de dados em um arquivo.
	 * @param length quantos bytes deverão ser possíveis escrever no mapa para alocar espaço.
	 * @throws IOException apenas se houver algum problema para iniciar a stream do arquivo.
	 */

	public OutputMapped(FileOutputStream fos, long length) throws IOException
	{
		this(fos.getChannel().map(FileChannel.MapMode.PRIVATE, 0, length));

		this.fos = fos;
	}

	/**
	 * Cria um novo escritor de dados em vetor a partir de um arquivo os os dados serão escritos.
	 * @param mapa referência do buffer de bytes mapeados que será usado para escrever os dados.
	 * @throws IOException apenas se houver algum problema para iniciar a stream do arquivo.
	 */

	public OutputMapped(MappedByteBuffer map)
	{
		this.map = map;
	}

	@Override
	public void flush()
	{
		try {
			fos.flush();
		} catch (IOException e) {
			throw new StreamRuntimeException(e);
		}
	}

	@Override
	public void write(byte b)
	{
		map.put(b);
	}

	@Override
	public int offset()
	{
		return map.position();
	}

	@Override
	public int length()
	{
		return map.capacity();
	}

	@Override
	public boolean isClosed()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void close()
	{
		if (fos != null)
			try {
				fos.close();
			} catch (IOException e) {
				throw new StreamRuntimeException(e);
			}

		throw new UnsupportedOperationException("não usou um FileOutputStream");
	}

	@Override
	public void skipe(int bytes)
	{
		map.position(map.position() + bytes);
	}

	@Override
	public void reset()
	{
		map.position(0);
	}
}
