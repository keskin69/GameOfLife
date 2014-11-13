package cell;

import java.awt.Color;

public interface ICell {
	abstract void split();

	abstract Color getColor();

	abstract boolean splitCondition();

	abstract boolean dieCondition();

	abstract void consume();
}
