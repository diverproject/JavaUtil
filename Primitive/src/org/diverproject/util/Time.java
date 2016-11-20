package org.diverproject.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p><h1>Tempo</h1></p>
 *
 * <p>Possui um atributo simples para considerar qual o horário definido a este tempo.
 * Além disso irá possuir diversos métodos que permitem trabalhar com este horário.
 * Como obter uma string formatada ou valores como dia, mês ou até mesmo o ano.</p>
 *
 * <p>Um tempo pode ser construído por uma Date (java.util | java.sql) ou horário (long).
 * Também pode criar ambos os tipos de Date de acordo com o horário usado no tempo.</p>
 *
 * @author Andrew
 */

public class Time
{
	/**
	 * Valor do horário deste tempo.
	 */
	private long time;

	/**
	 * Constrói um novo tempo onde o horário é definido como 0.
	 */

	public Time()
	{
		this(0);
	}

	/**
	 * Constrói um novo tempo onde pode-se definir o horário.
	 * @param time valor do horário que será considerado.
	 */

	public Time(long time)
	{
		set(time);
	}

	/**
	 * Constrói um novo tempo definindo o horário com um objeto Date Util.
	 * @param date referência do objeto Date que será considerado.
	 */

	public Time(Date date)
	{
		set(date.getTime());
	}

	/**
	 * Constrói um novo tempo definindo o horário com um objeto Date SQL.
	 * @param date referência do objeto Date que será considerado.
	 */

	public Time(java.sql.Date date)
	{
		set(date.getTime());
	}

	/**
	 * @return aquisição do horário definido a este tempo.
	 */

	public long get()
	{
		return time;
	}

	/**
	 * Permite redefinir o tempo que será usado por este horário.
	 * @param time valor do novo horário que será considerado.
	 */

	public void set(long time)
	{
		this.time = time;
	}

	/**
	 * Permite aumentar o valor desse tempo em uma quantidade de milissegundos.
	 * @param milliseconds quantos milissegundos devem ser adicionados.
	 */

	public void addMilliseconds(int milliseconds)
	{
		time += milliseconds;
	}

	/**
	 * Permite aumentar o valor desse tempo em uma quantidade de segundos.
	 * @param seconds quantos segundos devem ser adicionados.
	 */

	public void addSeconds(int seconds)
	{
		addMilliseconds(seconds * 1000);
	}

	/**
	 * Permite aumentar o valor desse tempo em uma quantidade de minutos.
	 * @param minutes quantos minutos devem ser adicionados.
	 */

	public void addMinutes(int minutes)
	{
		addSeconds(minutes * 60);
	}

	/**
	 * Permite aumentar o valor desse tempo em uma quantidade de horas.
	 * @param hours quantas horas devem ser adicionadas.
	 */

	public void addHours(int hours)
	{
		addMinutes(hours * 60);
	}

	/**
	 * Permite aumentar o valor desse tempo em uma quantidade de dias.
	 * @param days quantos dias devem ser adicionados.
	 */

	public void addDays(int days)
	{
		addHours(days * 24);
	}

	/**
	 * Permite aumentar o valor desse tempo em uma quantidade de semanas.
	 * @param weeks quantas semanas devem ser adicionadas.
	 */

	public void addWeeks(int weeks)
	{
		addDays(weeks * 7);
	}

	/**
	 * Permite aumentar o valor desse tempo em uma quantidade de meses.
	 * Cada mês irá considerar um valor de 30 dias independente do mês.
	 * @param months quantos meses devem ser adicionados.
	 */

	public void addMonths(int months)
	{
		addDays(months * 30);
	}

	/**
	 * @return requisição dos milissegundos do tempo definido.
	 */

	public int getMilisseconds()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(toDate());

		return c.get(Calendar.MILLISECOND);
	}

	/**
	 * @return requisição dos minutos do tempo definido.
	 */

	public int getMinutes()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(toDate());

		return c.get(Calendar.MINUTE);
	}

	/**
	 * @return requisição das horas do tempo definido.
	 */

	public int getHours()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(toDate());

		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @return aquisição do dia respectivo ao tempo definido.
	 */

	public int getDay()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(toDate());

		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return requisição do mês respectivo ao tempo definido.
	 */

	public int getMonth()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(toDate());

		return c.get(Calendar.MONTH);
	}

	/**
	 * @return aquisição do ano respectivo ao tempo definido.
	 */

	public int getYear()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(toDate());

		return c.get(Calendar.YEAR);
	}

	/**
	 * Verifica se este horário já foi passado, ou seja, se ele já passou.
	 * @return true se já tiver passado ou false caso contrário.
	 */

	public boolean isOver()
	{
		return System.currentTimeMillis() > time;
	}

	/**
	 * Permite criar um Date para ser usado em Java (java.util.Date).
	 * @return novo objeto da library Java SDK.
	 */

	public Date toDate()
	{
		return new Date(time);
	}

	/**
	 * Permite criar um Date para ser usado em SQL (java.sql.date).
	 * @return novo objeto date da library SQL.
	 */

	public java.sql.Date toDateSQL()
	{
		return new java.sql.Date(time);
	}

	/**
	 * Permite criar um Timestamp para ser usado em SQL (java.sql.timestamp).
	 * @return novo objeto timestamp da library SQL.
	 */

	public Timestamp toTimestamp()
	{
		return new Timestamp(time);
	}

	/**
	 * Converte o valor definido a este tempo em uma string legível.
	 * @param str formato do qual a string deve formatar a data.
	 * @return string com a data formatada para ser legível.
	 */

	public String toStringFormat(String str)
	{
		DateFormat df = new SimpleDateFormat(str);

		return df.format(toDate());
	}

	/**
	 * Verifica se o horário está definido como horário inicial UNIX (1970/01/01 00:00:00).
	 * @return true se for o horário inicial ou false caso contrário.
	 */

	public boolean isNull()
	{
		return toString().equals("1970/01/01 00:00:00");
	}

	@Override
	public String toString()
	{
		return toStringFormat("yyyy/MM/dd HH:mm:ss");
	}
}
