package cell;

public class CellFactory {
	public static ACell getCell(ICell cell) {
		if (cell instanceof Sample1) {
			return new Sample1();
		} else if (cell instanceof Sample2) {
			return new Sample2();
		}

		return null;
	}
}
