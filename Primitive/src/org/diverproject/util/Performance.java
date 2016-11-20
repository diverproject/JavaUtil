package org.diverproject.util;

import org.diverproject.util.lang.StringUtil;

public class Performance
{
	private long init;
	private long duration;

	private void init()
	{
		init = System.nanoTime();
	}

	private void print()
	{
		stop();

		System.out.printf("[DEBUG] Performance: %sns\n", StringUtil.money(duration));
	}

	private void stop()
	{
		if (init > 0)
			duration += System.nanoTime() - init;

		init = 0;
	}

	private static final Performance[] performances = new Performance[10];

	public static void create(int testid)
	{
		Performance performance = new Performance();
		performances[testid] = performance;
	}

	public static void delete(int testid)
	{
		performances[testid] = null;
	}

	public static void init(int testid)
	{
		performances[testid].init();
	}

	public static void print(int testid)
	{
		performances[testid].print();
	}

	public static void stop(int testid)
	{
		performances[testid].stop();
	}

	public static void delay(int a, int b)
	{
		Performance pa = performances[a];
		Performance pb = performances[b];

		long difference = pa.duration - pb.duration;

		System.out.printf("[DEBUG] Performance: A: %sns [%s] %sns :B\n",
				StringUtil.money(pa.duration),
				StringUtil.money(difference),
				StringUtil.money(pb.duration));
	}
}
