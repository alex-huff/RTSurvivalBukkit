package src.phonis.survival.completers;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemTabCompleter {
    private final int argSize;
    private final List<String> mats = new ArrayList<>();

    public ItemTabCompleter(int argSize) {
        this.argSize = argSize;

        for (Material material : Material.values()) {
            mats.add(material.name());
        }
    }

    public List<String> onTabComplete(String[] args) {
        List<String> ret = new ArrayList<>();

        if (args.length <= this.argSize) {
            for (String mat : mats) {
                if (mat.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                    ret.add(mat);
                }
            }
        }
		
		return ret;
	}
}