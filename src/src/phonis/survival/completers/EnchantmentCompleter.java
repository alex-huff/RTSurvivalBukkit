package src.phonis.survival.completers;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EnchantmentCompleter implements TabCompleter {
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

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String alias, @Nonnull String[] args) {
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
