package org.diverproject.util.stream.implementation.buffer;

import org.diverproject.util.stream.implementation.input.InputByteArray;
import org.diverproject.util.stream.implementation.output.OutputByteArray;

/**
 * <h1>Buffer com Dados Padrão</h1>
 *
 * <p>Implementação é uma extensão para um Buffer Padrão, implementando as funcionalidades restantes.
 * Essas funcionalidades são implementadas a parte de modo que seja possível usufruir de seus atributos.
 * Isso também permite que outros tipos de buffers futuros possam ser criados tendo a mesma ideia.</p>
 *
 * <p>As funcionalidades de responsabilidade dessa classe é determinar como é feito a leitura e escrita de
 * um único byte (principal procedimento das comunicações), permitindo atribuir adequadamente os seus
 * atributos que irão fazer o controle, também é aqui que é guardado o vetor com os dados da comunicação.</p>
 *
 * @see GenericBuffer
 *
 * @author Andrew Mello
 */

public class BufferArrayData extends GenericBuffer
{
	/**
	 * Referência dos dados (vetor de bytes) usado no buffer.
	 */
	private byte internalBuffer[];

	/**
	 * Cria um novo buffer padrão sendo necessário definir qual será o vetor de bytes usado para tal.
	 * O vetor não será copiado e sim ligado ao buffer estabelecendo assim a comunicação de dados.
	 * @param data referência do vetor que será usado na comunicação para saída e entrada de dados.
	 */

	public BufferArrayData(byte[] data)
	{
		super(new InputByteArray(data), new OutputByteArray(data));

		internalBuffer = data;
	}

	@Override
	public byte[] getArrayBuffer()
	{
		return internalBuffer.clone();
	}

	@Override
	public void moveTo(int offset)
	{
		int difOffset = offset - offset();

		input.skipe(difOffset);
		output.skipe(difOffset);
	}

	@Override
	public byte read()
	{
		output.skipe(1);

		return input.getByte();
	}

	@Override
	public void write(byte b)
	{
		output.write(b);
		input.skipe(1);
	}

	@Override
	public boolean isClosed()
	{
		return input.isClosed() || output.isClosed();
	}

	@Override
	public void close()
	{
		input.close();
		output.close();

		internalBuffer = null;
		System.gc();
	}

	@Override
	public void skipe(int bytes)
	{
		input.skipe(bytes);
		output.skipe(bytes);
	}

	@Override
	public void reset()
	{
		input.reset();
		output.reset();
	}
}
