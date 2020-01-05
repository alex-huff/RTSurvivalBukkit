package src.phonis.survival.serializable;

import java.io.Serializable;

/**
 * Serializable data representing to-do list entry;
 */
public class Todolist implements Serializable {
	private static final long serialVersionUID = 3956145446424494880L;
	public static ArrayListData<Todolist> gd = new ArrayListData<>("plugins/Survival/Todolist.txt");
	
	private String item;

	/**
	 * To-doList constructor taking in String item
	 * @param item String to-do item
	 */
	public Todolist(String item) {
		this.item = item;
	}

	/**
	 * Adds to-do item to ArrayListData
	 * @param item String to-do item
	 */
	public static void addTodo(String item) {
		Todolist.gd.data.add(new Todolist(item));
	}

	/**
	 * Gets to-do list represented by String
	 * @return String
	 */
	public static String getTodoList() {
		StringBuilder ret = new StringBuilder();
		int i = 1;
		int size = Todolist.gd.data.size();
		
		for (Todolist todo : Todolist.gd.data) {
			ret.append(i);
			ret.append(". ").append(todo.getItem()).append("\n");
			
			if (i != size) {
				ret.append("\n");
			}
			
			i++;
		}

		return ret.toString();
	}

	/**
	 * Gets to-do item
	 *
	 * @return String
	 */
	public String getItem() {
		return item;
	}

	/**
	 * Sets to-do item
	 *
	 * @param item String item to replace current
	 */
	public void setItem(String item) {
		this.item = item;
	}
}
