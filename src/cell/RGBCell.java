package cell;

import java.awt.Color;

import util.Util;

public class RGBCell extends ACell {
	public RGBCell() {
		gene.add("MaturityAge", 2);
		gene.add("MaxAge", 6);
		gene.add("RIntake", 100);
		gene.add("GIntake", 200);
		gene.add("BIntake", 200);
		gene.add("MinR", 50);
		gene.add("MinG", 20);
		gene.add("MinB", 10);
		gene.add("MaxR", 100);
		gene.add("MaxG", 100);
		gene.add("MaxB", 500);
		gene.add("RConsume", 30);
		gene.add("GConsume", 20);
		gene.add("BConsume", 30);

		addResource("R", "MinR", "MaxR", "RIntake", "RConsume");
		addResource("G", "MinG", "MaxG", "GIntake", "GConsume");
		addResource("B", "MinB", "MaxB", "BIntake", "BConsume");
	}

	public boolean splitCondition() {
		return age >= gene.getValue("MaturityAge");
	}

	public boolean dieCondition() {
		return age >= gene.getValue("MaxAge");
	}

	@Override
	public Color getColor() {
		double r = Math.rint(resourceList.getCurrent("R"));
		double g = Math.rint(resourceList.getCurrent("G"));
		double b = Math.rint(resourceList.getCurrent("B"));

		return Util.normalizeColor(r, g, b);
	}
}
