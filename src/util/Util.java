package util;

import java.awt.Color;

public class Util {
	public static Color normalizeColor(double r, double g, double b) {
		double tot = 255D/(r + b + g) ;
		r *= tot;
		b *= tot;
		g *= tot;

		return new Color((int) r, (int) g, (int) b);
	}
}
