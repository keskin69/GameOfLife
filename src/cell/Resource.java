package cell;

public class Resource {
	String min;
	String max;
	private long current;
	String intake;
	String consume;

	public Resource() {

	}

	public Resource(String min, String max, String intake, String consume) {
		this.min = min;
		this.max = max;
		this.intake = intake;
		this.consume = consume;
	}

	public long getCurrent() {
		return current;
	}

	public void setCurrent(long value) {
		current = value;
	}
}
