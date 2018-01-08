package org.diverproject.util;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Method;

import javax.swing.JComponent;

public class PaintUtil
{
	public static void background(Graphics g, Color color, int x, int y, int width, int height)
	{
		g.setColor(color);
		g.fillRect(x, y, width, height);		
	}

	public static void drawString(JComponent component, Graphics g, String text, int x, int y)
	{
		try {

			Class<?> swingUtilities2 = Class.forName("sun.swing.SwingUtilities2");
			Class<?> parameters[] = { JComponent.class, Graphics.class, String.class, Integer.TYPE, Integer.TYPE };
			Method method = swingUtilities2.getMethod("drawString", parameters);
			method.invoke(null, new Object[] { component, g, text, x, y });

		} catch (Exception ex) {
			g.drawString(text, x, y);
		}
	}
}
