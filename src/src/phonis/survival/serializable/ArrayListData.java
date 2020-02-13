package src.phonis.survival.serializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArrayListData<T extends Serializable> {
	public List<T> data = new ArrayList<>();
	public String filename;

	public ArrayListData(String filename) {
		this.filename = filename;
	}
}
