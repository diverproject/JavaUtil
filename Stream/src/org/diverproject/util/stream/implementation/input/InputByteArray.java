package org.diverproject.util.stream.implementation.input;

import org.diverproject.util.stream.StreamRuntimeException;

/**
 * <h1>Entrada de Dados com Vetor</h1>
 *
 * <p>Implementação básica das funcionalidades de um leitor utilizando comunicação com <i>dados em vetor</i>.
 * Trabalhar com dados em vetor, é usar um vetor especificado no construtor como fonte da comunicação feita.
 * Por exemplo, comunicações com arquivos escreve seus bytes consecutivamente sem pulos como fluxo de dados.
 * Nesse caso a fonte de fluxo será esse vetor de bytes especificado e este será usado diretamente ou copiado.</p>
 *
 * @see GenericInput
 *
 * @author Andrew Mello
 */

public class InputByteArray extends GenericInput
{
	/**
	 * Posição do ponteiro para leitura de dados.
	 */
	private int offset;

	/**
	 * Quantidade de bytes que compõe essa entrada de dados.
	 */
	private int length;

	/**
	 * Vetor de dados (bytes) do qual será feito a leitura.
	 */
	private byte[] data;

	/**
	 * Cria um novo leitor de dados em vetor a partir de um vetor de dados para ser feito a leitura.
	 * Para esse caso os bytes serão vinculados a entrada de dados e não serão clonados (padrão).
	 * @param data referência do vetor de bytes que será considerado como dados para leitura.
	 */

	public InputByteArray(byte[] data)
	{
		this(data, false);
	}

	/**
	 * Cria um novo leitor de dados padrão a partir de um vetor de dados para ser feito a leitura.
	 * @param data referência do vetor de bytes que será considerado como dados para leitura.
	 * @param copy permite definir se o vetor será clonado dentro da stream.
	 */

	public InputByteArray(byte[] data, boolean copy)
	{
		if (copy)
			this.data = data.clone();
		else
			this.data = data;

		this.length = data.length;
	}

	@Override
	public byte getByte()
	{
		if (space() > 0)
			return data[offset++];

		throw new StreamRuntimeException("fim do buffer");
	}

	@Override
	public byte read()
	{
		return getByte();
	}

	@Override
	public int offset()
	{
		return offset;
	}

	@Override
	public int length()
	{
		return length;
	}

	@Override
	public boolean isClosed()
	{
		return data == null;
	}

	@Override
	public void close()
	{
		data = null;
		System.gc();
	}

	@Override
	public void skipe(int bytes)
	{
		if (space() >= bytes)
			offset += bytes;
	}

	@Override
	public void reset()
	{
		offset = 0;
	}
}
