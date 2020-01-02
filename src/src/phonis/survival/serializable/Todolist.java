package src.phonis.survival.serializable;

import java.io.Serializable;

public class Todolist implements Serializable {
	private static final long serialVersionUID = 3956145446424494880L;
	public static ArrayListData<Todolist> gd = new ArrayListData<Todolist>("plugins/Survival/Todolist.txt");
	
	private String item;
	
	public Todolist(String item) {
		this.item = item;
	}

	public static void addTodo(String item) {
		Todolist.gd.data.add(new Todolist(item));
	}
	
	public static String getTodoList() {
		String ret = "";
		int i = 1;
		int size = Todolist.gd.data.size();
		
		for (Todolist todo : Todolist.gd.data) {
			ret += Integer.toString(i);
			ret += ". " + todo.getItem() + "\n";
			
			if (i != size) {
				ret += "\n";
			}
			
			i++;
		}
		
		return ret;
	}
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
