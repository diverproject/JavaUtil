package org.diverproject.util.stream.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.diverproject.util.stream.Input;
import org.diverproject.util.stream.Output;
import org.diverproject.util.stream.StreamException;
import org.diverproject.util.stream.StreamRuntimeException;
import org.diverproject.util.stream.implementation.input.InputStreamNamed;
import org.diverproject.util.stream.implementation.output.OutputPacket;
import org.diverproject.util.stream.implementation.output.OutputStreamNamed;
import org.diverproject.util.stream.implementation.input.InputByteArrayNamed;

/**
 * <h1>Criador de Pacotes</h1>
 *
 * <p>Permite criar objetos de Input e Output conforme suas preferências para um Socket.
 * Tanto Input e Output poderão criar pacotes de tamanho estáticos ou dinâmicos.
 * Quando estático define um tamanho fixo e dinâmico pode ler/escrever o quanto quiser.</p>
 *
 * @see Socket
 * @see Input
 * @see Output
 *
 * @author Andrew Mello
 */

public class PacketBuilder
{
	/**
	 * Referência da conexão socket que será usada.
	 */
	private Socket socket;

	/**
	 * Inicia o Criador de Pacotes através de uma conexão socket estabelecida.
	 * Através dessa conexão será possível criar os Input e Output necessários.
	 * @param socket referência da conexão socket que irá originar os pacotes.
	 */

	public PacketBuilder(Socket socket)
	{
		if (socket == null)
			throw new StreamRuntimeException("socket nulo");

		if (!socket.isConnected())
			throw new StreamRuntimeException("socket desconectado");

		this.socket = socket;
	}

	/**
	 * Para este caso irá instanciar um novo pacote com tamanho dinâmico.
	 * Em quanto houver dados para ler será possível obter dados do pacote.
	 * @param name nome que será dado ao pacote para reconhecê-lo.
	 * @return aquisição do objeto que irá receber os dados desse pacote.
	 * @throws StreamException conexão foi fechada inesperadamente.
	 */

	public Input newInputPacket(String name) throws StreamException
	{
		try {

			while (isConnected())
			{
				InputStream stream = socket.getInputStream();

				if (stream.available() > 0)
					return new InputStreamNamed(name, socket.getInputStream());
			}

			throw new StreamException("socket encerrado inesperadamente");

		} catch (IOException e) {
			throw new StreamException(e.getMessage());
		}
	}

	/**
	 * Para este caso irá instanciar um novo pacote com tamanho fixo.
	 * Irá carregar todos os bytes do socket para um buffer temporário.
	 * Os dados a serem lidos serão obtidos desse buffer temporário.
	 * @param name nome que será dado ao pacote para reconhecê-lo.
	 * @param length quantos bytes o pacote possui para serem lidos.
	 * @return aquisição do objeto que irá receber os dados desse pacote.
	 * @throws StreamException conexão foi fechada inesperadamente.
	 */

	public Input newInputPacket(String name, int length) throws StreamException
	{
		if (length < 0)
			throw new StreamException("tamanho inválido");

		try {

			while (isConnected())
			{
				InputStream stream = socket.getInputStream();

				if (stream.available() > 0)
				{
					if (length == -1)
						length = stream.available();

					if (stream.available() >= length)
					{
						byte data[] = new byte[length];

						if (stream.read(data) != length)
							throw new StreamException("falta de dados inesperada");

						return new InputByteArrayNamed(name, data);
					}
				}
			}

			throw new StreamException("socket encerrado inesperadamente");

		} catch (IOException e) {
			throw new StreamException(e.getMessage());
		}
	}

	/**
	 * Para este caso cria um novo pacote com tamanho dinâmico.
	 * Em quanto os dados não forem enviados novos dados poderão ser escritos.
	 * @param name nome que será dado ao pacote para reconhecê-lo.
	 * @return aquisição do objeto que irá enviar os dados desse pacote.
	 * @throws StreamException conexão foi fechada inesperadamente.
	 */

	public Output newOutputPacket(String name) throws StreamException
	{
		try {

			if (isConnected())
				return new OutputStreamNamed(name, socket.getOutputStream());

			throw new StreamException("socket encerrado inesperadamente");

		} catch (IOException e) {
			throw new StreamException(e.getMessage());
		}
	}

	/**
	 * Para este caso cria um novo pacote com tamanho fixo.
	 * Irá criar um buffer temporário interno para alocar os dados.
	 * Os dados em buffer serão enviados quando assim for solicitado.
	 * @param name nome que será dado ao pacote para reconhecê-lo.
	 * @param length tamanho do buffer interno para alocar os dados.
	 * @return aquisição do objeto que irá enviar os dados desse pacote.
	 * @throws StreamException conexão foi fechada inesperadamente.
	 */

	public Output newOutputPacket(String name, int length) throws StreamException
	{
		if (length < 0)
			throw new StreamException("tamanho inválido");

		if (length == 0)
			length = 1024;

		byte data[] = new byte[length];

		try {

			OutputPacket output = new OutputPacket(name, data);
			output.setFlushTarget(socket.getOutputStream());

			return output;

		} catch (IOException e) {
			throw new StreamException(e.getMessage());
		}
	}

	/**
	 * Procedimento que permite verificar se ainda há uma conexão socket.
	 * @return true se houver ou false caso contrário.
	 */

	public boolean isConnected()
	{
		return socket != null && socket.isConnected();
	}
}
