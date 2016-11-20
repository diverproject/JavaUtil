package org.diverproject.util;

import java.util.Locale;

/**
 * <p><h1>Teste de Duração</h1></p>
 *
 * <p>Possui métodos estáticos que permitem fazer o teste de duração entre procedimentos.
 * Primeiramente deve ser chamado init() para determinar o inicio do teste de duração.
 * Por fim deve ser chamado delay() para verificar o tempo de duração naquele momento.</p>
 *
 * @author Andrew
 */

public class TimerTest
{
	/**
	 * Duração do último momento que foi feito a inicialização.
	 */
	private static long init;

	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private TimerTest()
	{
		
	}

	/**
	 * Determina o inicio do cálculo do tempo em nanosegundos.
	 */

	public static void init()
	{
		init = System.nanoTime();
	}

	/**
	 * Permite obter a duração desde a inicialização feita por init().
	 * @return aquisição do teste de duração em milissegundos.
	 */

	public static double delay()
	{
		return (double) ((double) (System.nanoTime() - (double) init) / 1000000D);
	}

	/**
	 * Permite obter a duração desde a inicialização feita por init().
	 * @return aquisição do teste de duração em nanosegundos.
	 */

	public static long delayNano()
	{
		return System.nanoTime() - init;
	}

	/**
	 * Imprimi no console a duração desde a inicialização em milissegundos.
	 */

	public static void print()
	{
		System.out.printf(Locale.UK, "TimerTest: %.3f ms\n", delay());
	}

	/**
	 * Imprimi no console a duração desde a inicialização em nanosegundos.
	 */

	public static void printNano()
	{
		System.out.printf(Locale.UK, "TimerTest: %6dns\n", delayNano());
	}
}
