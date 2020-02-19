package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import src.phonis.survival.completers.EnchantmentCompleter;
import src.phonis.survival.util.DirectionUtil;

import java.util.List;
import java.util.Map;

public class CommandTradeFind extends SubCommand {
    private EnchantmentCompleter completer;

    public CommandTradeFind() {
        super("find", "(Material)");
        this.addAlias("f");
        this.completer = new EnchantmentCompleter(0);
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return this.completer.onTabComplete(args);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        throw new CommandException(CommandException.consoleError);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(this.getCommandString(0));

            return;
        }

        Material material = Material.matchMaterial(args[0]);
        Location playerLocation = player.getLocation();
        double closest = Double.MAX_VALUE;
        Merchant closestMerchant = null;

        for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
            if (entity instanceof Merchant && entity instanceof LivingEntity) {
                Merchant merchant = (Merchant) entity;

                for (MerchantRecipe mr : merchant.getRecipes()) {
                    ItemStack result = mr.getResult();
                    Material resMat = result.getType();

                    if (material != null && material.equals(resMat)) {
                        double distance = playerLocation.distance(((LivingEntity) merchant).getLocation());

                        if (distance < closest) {
                            closest = distance;
                            closestMerchant = merchant;

                            break;
                        }
                    } else if (resMat == Material.ENCHANTED_BOOK) {
                        ItemMeta meta = result.getItemMeta();

                        if (meta instanceof EnchantmentStorageMeta) {
                            EnchantmentStorageMeta esm = (EnchantmentStorageMeta) meta;
                            Map<Enchantment, Integer> enchantmentMap = esm.getStoredEnchants();

                            for (Enchantment enchant : enchantmentMap.keySet()) {
                                if (enchant.getKey().getKey().equals(args[0])) {
                                    double distance = playerLocation.distance(((LivingEntity) merchant).getLocation());

                                    if (distance < closest) {
                                        closest = distance;
                                        closestMerchant = merchant;

                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (closestMerchant != null) {
            DirectionUtil.faceDirection(player, ((LivingEntity) closestMerchant).getEyeLocation());
        } else {
            player.sendMessage(ChatColor.RED + "No " + args[0] + " trade found within 100 blocks");
        }
    }
}
