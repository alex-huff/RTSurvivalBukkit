package src.phonis.survival.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import src.phonis.survival.serializable.ArrayListData;
import src.phonis.survival.serializable.HashMapData;

/**
 * Utility class for serializing various plugin data
 */
public class SurvivalSerializationUtil {
	/**
	 * Serializes ArrayList data
	 * @param gd ArrayListData to be serialized
	 * @param log Logger to log the process
	 * @param <T> Serializable object ArrayList contains
	 */
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

	/**
	 * Serializes HashMap data
	 * @param pd HashMapData to be serialized
	 * @param log Logger to log the process
	 * @param <K> Key type
	 * @param <V> Serializable type for value of HashMapData
	 */
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

	/**
	 * Deserializes ArrayList data
	 * @param gd ArrayListData to be deserialized
	 * @param log Logger to log the process
	 * @param <T> Serializable object ArrayList contains
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> void deserialize(ArrayListData<T> gd, Logger log) {
		try {
			FileInputStream file = new FileInputStream(gd.filename);
			ObjectInputStream in = new ObjectInputStream(file);
			
			try {
				gd.data = (ArrayList<T>) in.readObject();
			} catch (ClassNotFoundException e) {
				log.warning("Error during deserialization of object");
			}
			
			in.close();
			file.close();
		} catch (IOException e) {
			log.warning("IOException during deserialization of objects");
		}
	}

	/**
	 * Deserializes HashMap data
	 * @param pd HashMapData to be deserialized
	 * @param log Logger to log the process
	 * @param <K> Key type
	 * @param <V> Serializable type for value of HashMapData
	 */
	@SuppressWarnings("unchecked")
	public static <K, V extends Serializable> void deserialize(HashMapData<K, V> pd, Logger log) {
		try {
			FileInputStream file = new FileInputStream(pd.filename);
			ObjectInputStream in = new ObjectInputStream(file);
			
			try {
				pd.data = (HashMap<K, V>) in.readObject();
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
