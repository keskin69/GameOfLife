package cell;

import java.awt.Color;

import util.Util;

public class Sample2 extends RGBCell {
	public Sample2() {
		super();
		gene.add("BConsume", 10);
		gene.add("MaxB", 300);
	}

	public Color getColor() {
		double r = 120 + Math.rint(resourceList.getCurrent("R"));
		double g = Math.rint(resourceList.getCurrent("G"));
		double b = Math.rint(resourceList.getCurrent("B"));

		return Util.normalizeColor(r, g, b);
	}
}
