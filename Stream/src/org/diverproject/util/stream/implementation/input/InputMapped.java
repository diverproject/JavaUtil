package org.diverproject.util.stream.implementation.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.diverproject.util.stream.StreamRuntimeException;

/**
 * <h1>Entrada Mapeada</h1>
 *
 * <p>A entrada de dados mapeada é feito através de um MappedByteBuffer onde os dados são obtidos.
 * Objetos desse tipo poderão ser obtidos através da especificação de um arquivo ou stream.
 * Com a stream do arquivo será possível obter o canal do arquivo e deste criar o mapa.</p>
 *
 * @see GenericInput
 * @see File
 * @see InputStream
 * @see FileChannel
 * @see MappedByteBuffer
 *
 * @author Andrew Mello
 */

public class InputMapped extends GenericInput
{
	/**
	 * Stream da entrada de dados para fechar se houver.
	 */
	private FileInputStream fis;

	/**
	 * Referência do mapa contendo os bytes que poderão ser lidos nesse input.
	 */
	private MappedByteBuffer map;

	/**
	 * Cria uma nova estrutura de stream para entrada de dados no sistema através de um arquivo.
	 * @param path string contendo o caminho parcial ou completo do arquivo que será lido.
	 * @throws IOException ocorre apenas se houver algum problema durante a leitura.
	 */

	public InputMapped(String path) throws IOException
	{
		this(new File(path));
	}

	/**
	 * Cria uma nova estrutura de stream para entrada de dados no sistema através de um arquivo.
	 * @param file referência do objeto contendo o caminho parcial ou completo do arquivo.
	 * @throws IOException ocorre apenas se houver algum problema durante a leitura.
	 */

	public InputMapped(File file) throws IOException
	{
		this(new FileInputStream(file));
	}

	/**
	 * Cria uma nova estrutura de stream para entrada de dados no sistema através de um FileInputStream.
	 * @param fis referência da stream de entrada de dados gerados a partir de um arquivo em disco.
	 * @throws IOException ocorre apenas se houver algum problema durante a leitura.
	 */

	public InputMapped(FileInputStream fis) throws IOException
	{
		this(fis.getChannel());

		this.fis = fis;
	}

	/**
	 * Cria uma nova estrutura de stream para entrada de dados no sistema através de um canal de arquivo.
	 * Esse canal pode ser obtido quando um InputStream é instanciado a partir de um arquivo.
	 * @param channel referência do canal do arquivo que contém os bytes para o input.
	 * @throws IOException ocorre apenas se houver algum problema durante a leitura.
	 */

	public InputMapped(FileChannel channel) throws IOException
	{
		this(channel.map(FileChannel.MapMode.READ_ONLY, channel.position(), channel.size()));
	}

	/**
	 * Cria uma nova estrutura de stream para entrada de dados no sistema través de um mapa.
	 * Esse mapa pode ser obtido através de um FileChannel de um InputStream criado.
	 * @param map referência do mapa que contém os bytes que poderão ser usados como input.
	 */

	public InputMapped(MappedByteBuffer map)
	{
		this.map = map;
	}

	@Override
	public byte getByte()
	{
		return map.get();
	}

	@Override
	public byte read()
	{
		return map.get();
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
		return !map.hasRemaining();
	}

	@Override
	public void close()
	{
		if (fis != null)
		{
			try {
				fis.close();
				fis = null;
			} catch (IOException e) {
				throw new StreamRuntimeException(e);
			}
		}

		map.position(length());
	}

	@Override
	public void skipe(int bytes)
	{
		map.position(offset() + bytes);
	}

	@Override
	public void reset()
	{
		map.position(0);
	}
}
