package src.phonis.survival.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class TradeGetter implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        Player player = (Player) sender;
        Location location = player.getEyeLocation();
        Vector direction = location.getDirection();
        double closest = Double.MAX_VALUE;
        Villager villager = null;

        for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
            if (entity instanceof Villager) {
                BoundingBox bb = entity.getBoundingBox();
                RayTraceResult rtr = bb.rayTrace(location.toVector(), direction.normalize(), 100);

                if (rtr != null) {
                    double distance = location.distance(new Location(entity.getWorld(), rtr.getHitPosition().getX(), rtr.getHitPosition().getY(), rtr.getHitPosition().getZ()));

                    if (distance < closest) {
                        closest = distance;
                        villager = (Villager) entity;
                    }
                }
            }
        }

        if (villager != null) {
            for (MerchantRecipe mr : villager.getRecipes()) {
                ItemStack result = mr.getResult();
                Material resMat = result.getType();
                StringBuilder message = new StringBuilder();
                List<ItemStack> ingredients = mr.getIngredients();

                for (int i = 0; i < ingredients.size(); i++) {
                    ItemStack input = ingredients.get(i);
                    Material inMat = input.getType();

                    if (!(inMat == Material.AIR)) {
                        if (i != 0) {
                            message.append("+ ");
                        }

                        message.append(inMat.toString()).append(" (x").append(input.getAmount()).append(") ");
                    }
                }
                message.append("-> ");

                if (resMat == Material.ENCHANTED_BOOK) {
                    ItemMeta meta = result.getItemMeta();

                    if (meta instanceof EnchantmentStorageMeta) {
                        EnchantmentStorageMeta esm = (EnchantmentStorageMeta) meta;
                        Map<Enchantment, Integer> enchantmentMap = esm.getStoredEnchants();

                        for (Enchantment enchant : enchantmentMap.keySet()) {
                            message.append(
                                StringUtils.capitalize(
                                    enchant.getKey().getKey().replace(
                                        "_",
                                        " "
                                    )
                                )
                            ).append(" ").append(enchantmentMap.get(enchant)).append(" ");
                        }
                    }
                } else {
                    message.append(resMat.toString()).append(" (x").append(result.getAmount()).append(")");
                }

                player.sendMessage(message.toString());
            }
        }

        return true;
    }
}
