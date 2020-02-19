package src.phonis.survival.completers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentCompleter {
    private int argSize;
    private List<String> enchants = new ArrayList<>();
    private List<String> mats = new ArrayList<>();

    public EnchantmentCompleter(int argSize) {
        this.argSize = argSize;

        for (Enchantment enchantment : Enchantment.values()) {
            enchants.add(enchantment.getKey().getKey());
        }

        for (Material material : Material.values()) {
            mats.add(material.name());
        }
    }

    public List<String> onTabComplete(String[] args) {
        List<String> ret = new ArrayList<>();

        if (args.length <= this.argSize) {
            for (String enchant : this.enchants) {
                if (enchant.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                    ret.add(enchant);
                }
            }

            for (String mat : mats) {
                if (mat.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                    ret.add(mat);
                }
            }
        }

        return ret;
    }
}
