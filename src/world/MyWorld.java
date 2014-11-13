package world;

import java.awt.Dimension;

import util.Parameters;
import cell.ResourceList;
import cell.Sample1;
import cell.Sample2;

public class MyWorld {

	public static void main(String[] args) {
		Environment env = new Environment();

		// init resources
		for (int x = 0; x < Parameters.XSize; x++) {
			for (int y = 0; y < Parameters.YSize; y++) {
				Environment.env[x][y] = new EnvCell();
				Environment.env[x][y].resourceList = new ResourceList();
				Environment.env[x][y].resourceList.init("R", (x * 5000));
				Environment.env[x][y].resourceList.init("G", (y * 5000));
				Environment.env[x][y].resourceList.init("B", (x + y) * 10000);
				Environment.env[x][y].resourceList.init("X", 0);
			}
		}

		for (int x = 30; x < 40; x++) {
			for (int y = 30; y < 40; y++) {
				Environment.env[x][y].resourceList.init("R", 0);
				Environment.env[x][y].resourceList.init("G", 0);
				Environment.env[x][y].resourceList.init("B", 0);
			}
		}

		Environment.checkSum();
		(new Thread(env)).start();

		Sample1 cell1 = new Sample1();
		cell1.location = new Dimension(Parameters.XSize / 2,
				Parameters.YSize / 2);
		Environment.addCell(cell1);

		Sample2 cell2 = new Sample2();
		cell2.location = new Dimension(Parameters.XSize - 1,
				Parameters.YSize - 1);
		Environment.addCell(cell2);

	}
}
