package org.diverproject.util;

import java.net.Socket;
import java.nio.ByteBuffer;

import org.diverproject.util.lang.ByteUtil;
import org.diverproject.util.lang.IntUtil;

/**
 * <p><h1>Utilitário para Socket</h1></p>
 *
 * Classe utilitária para trabalhar com objetos do tipo socket.
 *
 * @see Socket
 */

public class SocketUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private SocketUtil()
	{
		
	}

	/**
	 * Verifica se o socket existe e se há um endereço de internet no mesmo.
	 * @param socket conexão socket do qual será obtido o endereço de IP.
	 * @return endereço de IP da conexão socket se bem sucedido ou conexão
	 * não definida caso contrário "0.0.0.0".
	 */

	public static String socketIP(Socket socket)
	{
		if (socket == null || socket.getInetAddress() == null)
			return "0.0.0.0";

		return socket.getInetAddress().getHostAddress();
	}

	/**
	 * Converte um endereço IP salvo em um número inteiro para um valor em string.
	 * @param ip endereço de ip que foi salvo em número inteiro (esperasse).
	 * @return string correspondendo ao valor do IP sendo legível como "x.x.x.x".
	 */

	public static String socketIP(int ip)
	{
		byte[] buffer = ByteBuffer.allocate(4).putInt(ip).array();

		return String.format("%d.%d.%d.%d", ByteUtil.putInt(buffer[0]), ByteUtil.putInt(buffer[1]), ByteUtil.putInt(buffer[3]), ByteUtil.putInt(buffer[3]));
	}

	/**
	 * Verifica se o socket existe e se há um endereço de internet no mesmo.
	 * Um IP possui 4 dígitos que vão de 0 a 255, sendo possível serem salvos
	 * em um valor numérico inteiro onde cada valor corresponde a um byte.
	 * @param socket conexão socket do qual será obtido o endereço de IP.
	 * @return valor numérico do IP respectivo ao socket ou 0 se inválido.
	 */

	public static int socketIPInt(Socket socket)
	{
		if (socket == null || socket.getInetAddress() == null)
			return 0;

		String ip = socketIP(socket);
		String[] split = ip.split("\\.");

		byte[] bytes = new byte[]
		{
				(byte) Integer.parseInt(split[0]),
				(byte) Integer.parseInt(split[1]),
				(byte) Integer.parseInt(split[2]),
				(byte) Integer.parseInt(split[3])
		};

		return ByteBuffer.wrap(bytes).getInt();
	}

	/**
	 * Um IP possui 4 dígitos que vão de 0 a 255, sendo possível serem salvos
	 * em um valor numérico inteiro onde cada valor corresponde a um byte.
	 * @param ipaddress endereço do ip que deve ser convertido para int.
	 * @return valor numérico do IP respectivo ao endereço passado.
	 */

	public static int socketIPInt(String ipaddress)
	{
		String[] split = ipaddress.split("\\.");

		byte[] bytes = new byte[]
		{
				(byte) Integer.parseInt(split[0]),
				(byte) Integer.parseInt(split[1]),
				(byte) Integer.parseInt(split[2]),
				(byte) Integer.parseInt(split[3])
		};

		return ByteBuffer.wrap(bytes).getInt();
	}

	/**
	 * Procedimento usado para verificar se uma determinada string é um endereço de IP.
	 * @param ipaddress referência da string contendo o endereço de IP que será verificado.
	 * @return true se for válido como endereço de IP ou false caso contrário.
	 */

	public static boolean isIP(String ipaddress)
	{
		String[] split = ipaddress.split("\\.");

		if (split.length != 4)
			return false;

		if (!IntUtil.isInteger(split[0]) || !IntUtil.isInteger(split[1]) ||
			!IntUtil.isInteger(split[2]) || !IntUtil.isInteger(split[3]))
			return false;

		return true;
	}
}
