package src.phonis.survival.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import src.phonis.survival.serializable.ArrayListData;
import src.phonis.survival.serializable.HashMapData;

public class SurvivalSerializationUtil {
	public static <T extends Serializable> void serialize(ArrayListData<T> gd, Logger log) {
		try {
			FileOutputStream file = new FileOutputStream(gd.filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(gd.data);
			
			out.close();
			file.close();
		} catch (IOException e) {
			log.warning("IOException during serialization of object");
		}
	}
	
	public static <K, V extends Serializable> void serialize(HashMapData<K, V> pd, Logger log) {
		try {
			FileOutputStream file = new FileOutputStream(pd.filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(pd.data);

			out.close();
			file.close();
		} catch (IOException e) {
			log.warning("IOException during serialization of object");
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> void deserialize(ArrayListData<T> gd, Logger log) {
		try {
			FileInputStream file = new FileInputStream(gd.filename);
			ObjectInputStream in = new ObjectInputStream(file);
			
			try {
				List<T> listT = (ArrayList<T>) in.readObject();
				
				gd.data = listT;
			} catch (ClassNotFoundException e) {
				log.warning("Error during deserialization of object");
			}
			
			in.close();
			file.close();
		} catch (IOException e) {
			log.warning("IOException during deserialization of objects");
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <K, V extends Serializable> void deserialize(HashMapData<K, V> pd, Logger log) {
		try {
			FileInputStream file = new FileInputStream(pd.filename);
			ObjectInputStream in = new ObjectInputStream(file);
			
			try {
				HashMap<K, V> hashV = (HashMap<K, V>) in.readObject();
				
				pd.data = hashV;
			} catch (ClassNotFoundException e) {
				log.warning("Error during deserialization of object");
			}
			
			in.close();
			file.close();
		} catch (IOException e) {
			log.warning("IOException during deserialization of objects");
		}
	}
}
