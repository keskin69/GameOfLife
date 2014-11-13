package world;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import util.Parameters;
import util.Util;
import cell.ACell;
import cell.ResourceList;

public class AEnvironment {
	protected static EnvCell env[][] = null;
	protected static int idx = 0;
	protected static HashMap<String, Integer> population = null;
	protected static boolean active = true;

	protected static ConcurrentLinkedQueue<ACell> cellList = null;
	public static Viewer view = null;

	public static Color getColor(int x, int y) {
		ResourceList res = env[x][y].resourceList;

		double r = Math.rint(res.getCurrent("R"));
		double g = Math.rint(res.getCurrent("G"));
		double b = Math.rint(res.getCurrent("B"));

		return Util.normalizeColor(r, g, b);
	}

	public static Dimension getFreeCell(ACell cell) {
		int x = (int) cell.location.getWidth();
		int y = (int) cell.location.getHeight();
		int newX = 0;
		int newY = 0;

		Dimension d = null;
		boolean checked[] = new boolean[8];
		boolean loop = true;

		while (loop) {
			Random rand = new Random();
			int r = rand.nextInt(8);
			checked[r] = true;

			switch (r) {
			case 0:
				newX = x;
				newY = y + 1;
				break;

			case 1:
				newX = x + 1;
				newY = y + 1;
				break;

			case 2:
				newX = x + 1;
				newY = y;
				break;

			case 3:
				newX = x - 1;
				newY = y;
				break;

			case 4:
				newX = x;
				newY = y - 1;
				break;

			case 5:
				newX = x - 1;
				newY = y - 1;
				break;

			case 6:
				newX = x + 1;
				newY = y - 1;
				break;

			case 7:
				newX = x - 1;
				newY = y + 1;
				break;
			}

			if (newX > 0 && newY > 0 && newX < Parameters.XSize
					&& newY < Parameters.YSize) {
				if (env[newX][newY].cell == null) {
					d = new Dimension(newX, newY);

					return d;
				}
			}

			loop = false;
			for (boolean b : checked) {
				if (!b) {
					loop = true;
					break;
				}
			}
		}

		return null;
	}

	public static void reportCell(int x, int y) {
		ACell cell = env[x][y].cell;

		if (cell != null) {
			cell.report();
		} else {
			// report env
			String str = "Environment\n";
			for (String key : env[x][y].resourceList.keySet()) {
				long d = env[x][y].resourceList.getCurrent(key);

				str += key + ":" + d + "\n";
			}

			view.showDetail(str);
		}
	}

	protected void getStat() {
		String str = "Population:\n";
		for (String key : population.keySet()) {
			str += key + ": " + population.get(key).shortValue() + "\n";
		}

		str += "\n";
		view.txaInfo.setText(str);
	}

	public static void checkSum() {
		HashMap<String, Long> total = new HashMap<String, Long>();

		for (EnvCell[] row : env) {
			for (EnvCell envCell : row) {
				for (String key : envCell.resourceList.keySet()) {
					long d = 0;

					try {
						d = total.get(key).longValue();
					} catch (NullPointerException ex) {
						total.put(key, 0L);
					}

					d += envCell.resourceList.getCurrent(key);

					if (envCell.cell != null) {
						try {
							d += envCell.cell.resourceList.getCurrent(key);
						} catch (NullPointerException ex) {

						}
					}

					total.put(key, d);
				}
			}
		}

		for (String key : total.keySet()) {
			System.out.println(key + ":" + total.get(key).longValue());
		}
	}
}
