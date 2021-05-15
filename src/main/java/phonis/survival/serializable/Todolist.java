package src.phonis.survival.serializable;

import java.io.Serializable;

public class Todolist implements Serializable {
	private static final long serialVersionUID = 3956145446424494880L;
	public static ArrayListData<Todolist> gd = new ArrayListData<>("plugins/Survival/Todolist.txt");
	
	private String item;

	public Todolist(String item) {
		this.item = item;
	}

	public static void addTodo(String item) {
		Todolist.gd.data.add(new Todolist(item));
	}

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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
