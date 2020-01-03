package src.phonis.survival.serializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializable collection for storing List like plugin data for serialization;
 * Example: to-do list
 *
 * @param <T> Serializable type stored in the ArrayList
 */
public class ArrayListData<T extends Serializable> {
	public List<T> data = new ArrayList<>();
	public String filename;

	/**
	 * ArrayListData constructor that sets filename for serialization
	 * @param filename String filename of out file
	 */
	public ArrayListData(String filename) {
		this.filename = filename;
	}
}
