package cell;

import java.util.HashMap;

public class ResourceList extends HashMap<String, Resource> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6928180643635762705L;

	public void put(String key, String min, String max, String intake,
			String consume) {
		Resource res = new Resource(min, max, intake, consume);
		put(key, res);
	}

	public long getCurrent(String key) {
		return get(key).getCurrent();
	}

	public void setCurrent(String key, long value) {
		Resource res = get(key);
		res.setCurrent(value);
	}

	public void init(String key, long value) {
		put(key, new Resource());
		setCurrent(key, value);
	}

}
