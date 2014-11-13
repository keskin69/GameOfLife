package world;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import util.Parameters;
import cell.ACell;

public class Environment extends AEnvironment implements Runnable {

	public Environment() {
		cellList = new ConcurrentLinkedQueue<ACell>();
		env = new EnvCell[Parameters.XSize][Parameters.YSize];
		population = new HashMap<String, Integer>();
		view = new Viewer();
	}

	public static long useResource(int x, int y, String key, long consume) {
		long available = env[x][y].resourceList.getCurrent(key);
		if (consume >= available) {
			env[x][y].resourceList.setCurrent(key, 0);
			return available;
		} else {
			env[x][y].resourceList.setCurrent(key, available - consume);
			return consume;
		}
	}

	public static void returnBack(int x, int y, String key, long value) {
		long val = env[x][y].resourceList.getCurrent(key);
		env[x][y].resourceList.setCurrent(key, (val + value));
	}

	public static void addCell(ACell cell) {
		int cnt = 0;

		try {
			cnt = population.get(cell.getClass().getName()).shortValue();
		} catch (NullPointerException ex) {

		}

		population.put(cell.getClass().getName(), new Integer(cnt + 1));

		int x = (int) cell.location.getWidth();
		int y = (int) cell.location.getHeight();

		env[x][y].cell = cell;
		cell.idx = idx++;

		cellList.add(cell);
	}

	public static void removeCell(ACell cell) {
		short cnt = population.get(cell.getClass().getName()).shortValue();
		population.put(cell.getClass().getName(), new Integer(cnt - 1));

		int x = (int) cell.location.getWidth();
		int y = (int) cell.location.getHeight();

		env[x][y].cell = null;

		// return cell content to environment
		for (String key : cell.resourceList.keySet()) {
			returnBack(x, y, key, cell.resourceList.getCurrent(key));
		}

		cellList.remove(cell);
		cell = null;
	}

	public static void killAll() {
		for (EnvCell[] row : env) {
			for (EnvCell envCell : row) {
				try {
					envCell.cell.die();
					view.repaint();
				} catch (NullPointerException ex) {

				}
			}
		}
	}

	public void run() {
		while (true) {
			if (active) {
				for (ACell cell : cellList) {
					if (cell.display) {
						cell.display = false;
						cell.live();
					}
				}

				view.repaint();

				for (ACell cell : cellList) {
					cell.display = true;
				}

				try {
					Thread.sleep(Parameters.DELAY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				getStat();

			} else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
