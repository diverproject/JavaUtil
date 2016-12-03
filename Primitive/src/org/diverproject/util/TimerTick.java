package org.diverproject.util;


/**
 * <p><h1>Temporizador por Tick</h1></p>
 *
 * <p>Com um temporizador é possível determinar um intervalo em milissegundos para cada tick.
 * Utiliza um método que permite obter quantos ticks podem ser obtidos deste a última tentativa.
 * Possuindo dois modos de ticks, onde um determina um limite de ticks enquanto o outro usa o máximo.</p>
 *
 * <p>Isso é utilizado quando é necessário fazer uma contagem de tempos em tempos (ticks).
 * Assim sendo, os dois construtores permitem definir o intervalo em milissegundos para cada tick.
 * Quando o método for chamado irá atualizar o último tick obtido para um cálculo seguinte.
 * Além disso, não permite que esse possa obter mais ticks do que o definido no limite.</p>
 *
 * @author Andrew
 */

public class TimerTick
{
	/**
	 * Contagem de quantos ticks já foram obtidos.
	 */
	private long count;

	/**
	 * Última vez que foi possível obter-se um ou mais ticks.
	 */
	private long last;

	/**
	 * Limite de ticks que podem ser obtidos desse temporizador.
	 */
	private final long limit;

	/**
	 * Duração do intervalo para cada tick em milissegundos.
	 */
	private final long interval;

	/**
	 * Constrói um novo temporizador para ticks onde define o limite máximo permitido.
	 * @param interval duração que será considerado por cada tick em milissegundos.
	 */

	public TimerTick(long interval)
	{
		this(interval, Integer.MAX_VALUE);
	}

	/**
	 * Constrói um novo temporizador para ticks onde permite definir um limite de ticks.
	 * @param interval duração que será considerado por cada tick em milissegundos.
	 * @param limit quantos ticks poderão ser obtidos desse temporizador.
	 */

	public TimerTick(long interval, long limit)
	{
		this.last = System.currentTimeMillis();
		this.interval = interval;
		this.limit = limit;
		this.count = 0;
	}

	/**
	 * Principal método utilizado pelo temporizador, feito para obter os ticks.
	 * Quando nenhum tick é obtido não atualiza o tempo de último tick (last).
	 * @return aquisição de quantos ticks foram feitos desde a última tentativa.
	 */

	public long getTicks()
	{
		long delay = System.currentTimeMillis() - last;
		long count = delay / interval;

		if (count == 0)
			return 0;

		last = System.currentTimeMillis();

		if (count > howMany())
			count = howMany();

		this.count += count;

		return count;
	}

	/**
	 * Para que o temporizador não passe do limite permitido, é feito uma contagem de ticks obtidos.
	 * @return aquisição de quantos ticks já foram obtidos desse temporizador.
	 */

	public long getTicksCount()
	{
		return count;
	}

	/**
	 * Método usado por getTicks() para que a contagem de ticks não ultrapasse o limite permitido.
	 * @return quantos ticks ainda podem ser obtidos desse temporizador.
	 */

	public long howMany()
	{
		return limit - count;
	}

	/**
	 * Verifica se o temporizador ainda pode ou não obter ticks.
	 * @return true se for possível ou false caso contrário.
	 */

	public boolean has()
	{
		return howMany() > 0;
	}

	@Override
	public String toString()
	{
		return String.format("TimerTick[interval: %dms, count: %d/%d, last: %s]", interval, count, limit, new Time(last));
	}
}
