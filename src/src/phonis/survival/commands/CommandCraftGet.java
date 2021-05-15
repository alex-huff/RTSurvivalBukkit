package src.phonis.survival.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftBlastingRecipe;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftComplexRecipe;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftFurnaceRecipe;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftSmokingRecipe;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;
import src.phonis.survival.completers.ItemTabCompleter;

import java.util.List;
import java.util.Map;

public class CommandCraftGet extends SubCommand {
    private final ItemTabCompleter completer;

    public CommandCraftGet(JavaPlugin plugin) {
        super("getcraft", "(Material)");
        SubCommand.registerCommand(plugin, this);
        this.completer = new ItemTabCompleter(1);
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
    public void execute(Player player, String[] args) throws CommandException {
        if (args.length == 0) {
            player.sendMessage(this.getCommandString(0));

            return;
        }

        Material mat = Material.matchMaterial(args[0]);

        if (mat == null) {
            throw new CommandException("Invalid material");
        }

        List<Recipe> recipes = Bukkit.getServer().getRecipesFor(new ItemStack(mat));

        if (recipes.size() == 0) {
            player.sendMessage(ChatColor.RED + "No recipes for material");
        }

        found:
        {
            for (Recipe recipe : recipes) {
                if (recipe instanceof ShapedRecipe) {
                    ShapedRecipe shaped = (ShapedRecipe) recipe;
                    Inventory inventory = Bukkit.createInventory(null, InventoryType.WORKBENCH);

                    inventory.setMaxStackSize(Integer.MAX_VALUE);

                    Map<Character, ItemStack> map = shaped.getIngredientMap();
                    String[] shape = shaped.getShape();

                    for (int w = 0; w < shape.length; w++) {
                        for (int i = 0; i < shape[w].length(); i++) {
                            inventory.setItem(i + w * 3 + 1, map.get(shape[w].charAt(i)));
                        }
                    }

                    inventory.setItem(0, shaped.getResult());
                    player.openInventory(inventory);

                    break found;
                } else if (recipe instanceof ShapelessRecipe) {
                    ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
                    List<ItemStack> ls = shapeless.getIngredientList();
                    Inventory inventory = Bukkit.createInventory(null, InventoryType.WORKBENCH);

                    inventory.setMaxStackSize(Integer.MAX_VALUE);

                    int count = 1;

                    for (ItemStack is : ls) {
                        inventory.setItem(count, is);
                        count++;
                    }

                    inventory.setItem(0, shapeless.getResult());
                    player.openInventory(inventory);

                    break found;
                } else if (recipe instanceof CraftFurnaceRecipe) {
                    CraftFurnaceRecipe furnace = (CraftFurnaceRecipe) recipe;
                    ItemStack is = furnace.getInput();
                    Inventory inventory = Bukkit.createInventory(null, InventoryType.FURNACE);

                    inventory.setMaxStackSize(Integer.MAX_VALUE);
                    inventory.setItem(0, is);
                    inventory.setItem(1, new ItemStack(Material.COAL));
                    inventory.setItem(2, furnace.getResult());
                    player.openInventory(inventory);

                    break found;
                } else if (recipe instanceof CraftSmokingRecipe) {
                    CraftSmokingRecipe smoker = (CraftSmokingRecipe) recipe;
                    ItemStack is = smoker.getInput();
                    Inventory inventory = Bukkit.createInventory(null, InventoryType.SMOKER);

                    inventory.setMaxStackSize(Integer.MAX_VALUE);
                    inventory.setItem(0, is);
                    inventory.setItem(1, new ItemStack(Material.COAL));
                    inventory.setItem(2, smoker.getResult());
                    player.openInventory(inventory);

                    break found;
                } else if (recipe instanceof CraftBlastingRecipe) {
                    CraftBlastingRecipe blaster = (CraftBlastingRecipe) recipe;
                    ItemStack is = blaster.getInput();
                    Inventory inventory = Bukkit.createInventory(null, InventoryType.BLAST_FURNACE);

                    inventory.setMaxStackSize(Integer.MAX_VALUE);
                    inventory.setItem(0, is);
                    inventory.setItem(1, new ItemStack(Material.COAL));
                    inventory.setItem(2, blaster.getResult());
                    player.openInventory(inventory);

                    break found;
//                } else if (recipe instanceof CraftSmithingRecipe) {
//                    CraftSmithingRecipe smither = (CraftSmithingRecipe) recipe;
//                    ItemStack result = smither.getResult();
//                    RecipeChoice base = smither.getBase();
//                    RecipeChoice addition = smither.getAddition();
//                    Inventory inventory = Bukkit.createInventory(null, InventoryType.SMITHING);
//
//                    System.out.println(result);
//                    System.out.println(base);
//                    System.out.println(addition);
//
//                    CraftInventory cfi = (CraftInventory) inventory;
//                    CraftItemStack cis = (CraftItemStack) result;
//                    net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(cis);
//                    IInventory ii = cfi.getInventory();
//
//                    ii.setItem(0, nmsStack);
//                    ii.setItem(1, nmsStack);
//                    ii.setItem(2, nmsStack);
//
//                    System.out.println(ii.getSize());
//
//                    inventory.setMaxStackSize(Integer.MAX_VALUE);
//                    inventory.setItem(1, result);
//                    inventory.setItem(2, result);
//                    inventory.setItem(0, result);
//                    player.openInventory(inventory);
//
//                    break found;
                } else if (recipe instanceof CraftComplexRecipe) {
                    player.sendMessage(ChatColor.AQUA +
                            "This material has a complex recipe. This means that its functionality, or appearance, is a result of its inputs, and it has many. Look it up."
                    );

                    break found;
                }
            }

            player.sendMessage(ChatColor.RED + "Recipe not found");
        }
    }
}
