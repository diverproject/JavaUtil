package org.diverproject.util.stream.implementation.output;

import java.io.IOException;
import java.io.OutputStream;

import org.diverproject.util.stream.StreamRuntimeException;

/**
 * <h1>Pacote de Saída</h1>
 *
 * <p>Funciona da mesma forma que uma Saída de Dados com Vetor Nomeada.
 * Neste caso permite usar um OutputStream para enviar os dados por flush.</p>
 *
 * @see OutputByteArrayNamed
 *
 * @author Andrew Mello
 */

public class OutputPacket extends OutputByteArrayNamed
{
	/**
	 * Stream para saída de dados alvo.
	 */
	private OutputStream flushTarget;

	/**
	 * Cria um novo escritor de dados em vetor a partir de um vetor os os dados serão escritos.
	 * Para esse caso os bytes serão vinculados a saída de dados e não serão clonados (padrão).
	 * @param name nome que será vinculado a essa saída para um auxílio na identificação.
	 * @param data referência do vetor de bytes que será considerado como dados para escrita.
	 */

	public OutputPacket(String name, byte[] data)
	{
		super(name, data);
	}

	/**
	 * Cria um novo escritor de dados em vetor a partir de um vetor os os dados serão escritos.
	 * @param name nome que será vinculado a essa saída para um auxílio na identificação.
	 * @param data referência do vetor de bytes que será considerado como dados para escrita.
	 * @param copy permite definir se o vetor será clonado dentro da stream.
	 */

	public OutputPacket(String name, byte[] data, boolean copy)
	{
		super(name, data, copy);
	}

	/**
	 * Permite definir uma stream para saída de dados quando essa saída usar flush.
	 * O método flush irá funcionar de forma natural com ou sem essa definição.
	 * Essa funcionalidade é extra e permite que os dados daqui passe para o target.
	 * @param flushTarget stream target que irá receber os dados após o flush.
	 */

	public void setFlushTarget(OutputStream flushTarget)
	{
		this.flushTarget = flushTarget;
	}

	@Override
	public void flush()
	{
		super.flush();

		byte flushed[] = getFlushedArray();

		if (flushTarget != null)
		{
			try {

				flushTarget.write(flushed);
				flushTarget.flush();

			} catch (IOException e) {
				throw new StreamRuntimeException(e.getMessage());
			}
		}
	}
}
