package org.diverproject.util.stream.implementation.output;

import static org.diverproject.util.Util.i;
import static org.diverproject.util.lang.IntUtil.limit;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
	 * 
	 */
	private FileChannel channel;

	/**
	 * Buffer com bytes mapeados do arquivo que será escrito.
	 */
	private MappedByteBuffer map;

	/**
	 * Quantidade de bytes que será aumentado quando necessário redimensionar.
	 */
	private int bufferSize;

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

	@SuppressWarnings("resource")
	public OutputMapped(File file, long length) throws IOException
	{
		bufferSize = limit(i(length), 1024, 1024 * 16);

		RandomAccessFile raf = new RandomAccessFile(file.getAbsolutePath(), "rw");

		channel = raf.getChannel();
		map = channel.map(FileChannel.MapMode.READ_WRITE, 0, bufferSize);
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
		if (channel != null)
		{
			try {

				Class<?> cls = channel.getClass();
				Method method = cls.getDeclaredMethod("unmap", new Class[]{ MappedByteBuffer.class });
				method.setAccessible(true);
				method.invoke(null, new Object[]{ map });

				int length = length();

				map = channel.map(FileChannel.MapMode.READ_WRITE, 0, length + bufferSize);
				map.position(length);

			} catch (IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new StreamRuntimeException(e);
			}
		}
	}

	@Override
	public void write(byte b)
	{
		if (!map.hasRemaining())
			flush();

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
		return !(channel != null && channel.isOpen()) || map == null;
	}

	@Override
	public void close()
	{
		if (channel == null && map == null)
			throw new UnsupportedOperationException("não usou um FileOutputStream");

		try {

			this.channel.close();
			this.channel = null;
			this.map = null;

		} catch (IOException e) {
			throw new StreamRuntimeException(e);
		}
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
