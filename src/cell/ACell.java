package cell;

import java.awt.Dimension;

import util.Parameters;
import world.Environment;

public abstract class ACell implements ICell {
	public boolean display = false;
	public int age = 0;
	//public UUID id = null;
	public Dimension location = null;
	public ResourceList resourceList = null;
	public Gene gene = null;

	public int idx = 0;

	public ACell() {
		resourceList = new ResourceList();
		gene = new Gene();
	}

	public void addResource(String key, String min, String max, String intake,
			String consume) {
		resourceList.put(key, min, max, intake, consume);
	}

	public void die() {
		Environment.removeCell(this);
	}

	public void split() {
		for (String key : resourceList.keySet()) {
			Resource res = resourceList.get(key);

			if (res.getCurrent() < 2 * gene.getValue(res.min)) {
				// not enough resource for splitting
				return;
			}
		}

		// check for an available location
		Dimension location = Environment.getFreeCell(this);
		if (location != null) {
			ACell newCell = CellFactory.getCell(this);

			// clone genes with mutation
			for (String key : gene.keySet()) {
				long val = gene.get(key).longValue();
				int sign = (Math.random() > 0.5 ? 1 : -1);
				val += Math.random() * sign * Parameters.mutationRatio;

				if (val < 0) {
					val = 0;
				}

				newCell.gene.put(key, val);
			}

			// split resources
			for (String key : resourceList.keySet()) {
				Resource res = resourceList.get(key);
				long d = res.getCurrent() / 2;
				res.setCurrent(d);

				newCell.resourceList.setCurrent(key, d);
			}

			// add cell to world
			newCell.location = location;
			Environment.addCell(newCell);
		}
	}

	public final void live() {
		consume();

		if (splitCondition()) {
			split();
		}

		if (dieCondition()) {
			die();
		}

		// increment age of the cell
		age++;
	}

	public void report() {
		String rep = this.getClass().getName() + idx + "\n";
		rep += " Age:" + age + " ";
		rep += "X:" + location.width;
		rep += " Y:" + location.height;

		rep += "\nContent\n";
		for (String key : resourceList.keySet()) {
			Resource res = resourceList.get(key);
			rep += key + ":" + res.getCurrent() + "\n";
		}

		rep += "\nGenes\n";
		for (String key : gene.keySet()) {
			rep += key + ":" + gene.getValue(key) + "\n";
		}

		Environment.view.showDetail(rep);
	}

	public void consume() {
		int x = (int) location.getWidth();
		int y = (int) location.getHeight();

		for (String key : resourceList.keySet()) {
			Resource res = resourceList.get(key);

			// get resource from environment
			if (res.getCurrent() < (gene.getValue(res.max) + gene
					.getValue(res.intake))) {

				long intake = Environment.useResource(x, y, key,
						gene.getValue(res.intake));
				res.setCurrent(res.getCurrent() + intake);
			}

			// consume resource
			if (res.getCurrent() > gene.getValue(res.consume)) {
				Environment.returnBack(x, y, key, gene.getValue(res.consume));
				res.setCurrent(res.getCurrent() - gene.getValue(res.consume));
			} else {
				Environment.returnBack(x, y, key, res.getCurrent());
				res.setCurrent(0);
			}

			// check healthy level of resource
			if (res.getCurrent() < gene.getValue(res.min)) {
				die();
			}
		}
	}
}
