package org.diverproject.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.UIManager;

import org.diverproject.util.collection.ArrayUtil;

/**
 * <p><h1>Utilitários do Sistema</h1></p>
 *
 * @author Andrew
 */

public class SystemUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private SystemUtil()
	{
		
	}

	/**
	 * Deve definir toda a interface das aplicações java como o mesmo estilo do Windows.
	 * Isso tem efeito sobre componentes como JButton, JTextField, JScrollBar e outros.
	 */

	public static void setWindowsInterface()
	{
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			MessageUtil.showException(e);
		}
	}

	/**
	 * Deve definir toda a interface das aplicações java como o mesmo estilo do Nimbus.
	 * Isso tem efeito sobre componentes como JButton, JTextField, JScrollBar e outros.
	 */

	public static void setNimbusInterface()
	{
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			MessageUtil.showException(e);
		}
	}

	/**
	 * Memória usada é o quanto da memória RAM está sendo utilizado pela aplicação.
	 * @return aquisição da quantidade em bytes total de memória usada.
	 */

	public static long getUsageMemory()
	{
		return getAllocatedMemory() - getFreeMemory();
	}

	/**
	 * Memória livre é o quanto da memória RAM reservado para aplicação ainda está livre.
	 * @return aquisição da quantidade em bytes total de memória livre.
	 */

	public static long getFreeMemory()
	{
		return Runtime.getRuntime().freeMemory();
	}

	/**
	 * Memória alocada é o quanto de memória RAM reservado par aplicação foi definido.
	 * @return aquisição da quantidade em bytes total de memória alocada.
	 */

	public static long getAllocatedMemory()
	{
		return Runtime.getRuntime().totalMemory();
	}

	/**
	 * Memória máxima é o quanto a JVM permite que a aplicação use da memória RAM.
	 * @return aquisição da quantidade em bytes total de memória máxima.
	 */

	public static long getMaxMemory()
	{
		return Runtime.getRuntime().maxMemory();
	}

	/**
	 * Memória livre é o quanto ainda pode ser usado da memória RAM para essa aplicação.
	 * @return aquisição da quantidade em bytes total de memória disponível.
	 */

	public static long getTotalFreeMemory()
	{
		return getFreeMemory() + (getMaxMemory() - getAllocatedMemory());
	}

	/**
	 * Calcula a quantidade de memória usada e formata para o maior byte possível.
	 * @return aquisição da quantidade em bytes total de memória usada.
	 */

	public static String getUsageMemoryString()
	{
		return SizeUtil.toString(getUsageMemory());
	}

	/**
	 * Calcula a quantidade de memória livre e formata para o maior byte possível.
	 * @return aquisição da quantidade em bytes total de memória livre.
	 */

	public static String getFreeMemoryString()
	{
		return SizeUtil.toString(getFreeMemory());
	}

	/**
	 * Calcula a quantidade de memória alocada e formata para o maior byte possível.
	 * @return aquisição da quantidade em bytes total de memória alocada.
	 */

	public static String getAllocatedMemoryString()
	{
		return SizeUtil.toString(getAllocatedMemory());
	}

	/**
	 * Calcula a quantidade de memória máxima e formata para o maior byte possível.
	 * @return aquisição da quantidade em bytes total de memória máxima.
	 */

	public static String getMaxMemoryString()
	{
		return SizeUtil.toString(getMaxMemory());
	}

	/**
	 * Calcula a quantidade de memória disponível e formata para o maior byte possível.
	 * @return aquisição da quantidade em bytes total de memória disponível.
	 */

	public static String getTotalFreeMemoryString()
	{
		return SizeUtil.toString(getTotalFreeMemory());
	}

	/**
	 * Imprimi no console em um total de quatro linhas os estados atuais de uso de memória.
	 */

	public static void printMemoryUsage()
	{
		System.out.printf("Memória Usada: %s de %s\n", getUsageMemoryString(), getAllocatedMemoryString());
	}

	/**
	 * Imprimi no console em um total de quatro linhas os estados atuais de uso de memória.
	 */

	public static void printMemoryAvaiable()
	{
		System.out.printf("Memória Disponível: %s de %s\n", getTotalFreeMemoryString(), getMaxMemoryString());
	}

	/**
	 * Imprimi no console em um total de quatro linhas os estados atuais de uso de memória.
	 */

	public static void printMemoryStatus()
	{
		printMemoryUsage();
		printMemoryAvaiable();
	}

	/**
	 * Permite obter o endereço MAC da placa de rede utilizada no localhost, considerando o seguinte formato:
	 * <b>FF-FF-FF-FF-FF-FF</b>, o outro getMacAddress() permite determinar o separador que aqui é <b>-</b>.
	 * @return aquisição do endereço MAC do localhost no formato especificado acima.
	 * @throws SocketException ocorre se não conseguir criar uma conexão com o localhost.
	 * @throws UnknownHostException ocorre se o localhost não for encontrado.
	 */

	public static String getMacAddress() throws SocketException, UnknownHostException
	{
		return getMacAddress("-");
	}

	/**
	 * Permite obter o endereço MAC da placa de rede utilizada no localhost, considerando o seguinte formato:
	 * <b>FF-FF-FF-FF-FF-FF</b>, isso considerando que o separador seja definido como <b>-</b> (padrão).
	 * @param separetor string com o separador que irá ser incrementado entre os valores hexadecimais.
	 * @return aquisição do endereço MAC do localhost no formato especificado acima.
	 * @throws SocketException ocorre se não conseguir criar uma conexão com o localhost.
	 * @throws UnknownHostException ocorre se o localhost não for encontrado.
	 */

	public static String getMacAddress(String separetor) throws SocketException, UnknownHostException
	{
		NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

		return ArrayUtil.join(ni.getHardwareAddress(), "-", true);
	}
}
