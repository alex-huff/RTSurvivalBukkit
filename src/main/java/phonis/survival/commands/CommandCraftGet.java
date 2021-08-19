package phonis.survival.commands;

import com.google.common.base.Preconditions;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.IInventory;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.Containers;
import net.minecraft.world.level.block.entity.TileEntity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_17_R1.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_17_R1.inventory.*;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;
import phonis.survival.completers.ItemTabCompleter;

import java.util.Arrays;
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
                if (recipe instanceof ShapedRecipe shaped) {
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
                } else if (recipe instanceof ShapelessRecipe shapeless) {
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
                } else if (recipe instanceof CraftFurnaceRecipe furnace) {
                    ItemStack is = furnace.getInput();
                    Inventory inventory = Bukkit.createInventory(null, InventoryType.FURNACE);

                    inventory.setMaxStackSize(Integer.MAX_VALUE);
                    inventory.setItem(0, is);
                    inventory.setItem(1, new ItemStack(Material.COAL));
                    inventory.setItem(2, furnace.getResult());
                    player.openInventory(inventory);

                    break found;
                } else if (recipe instanceof CraftSmokingRecipe smoker) {
                    ItemStack is = smoker.getInput();
                    Inventory inventory = Bukkit.createInventory(null, InventoryType.SMOKER);

                    inventory.setMaxStackSize(Integer.MAX_VALUE);
                    inventory.setItem(0, is);
                    inventory.setItem(1, new ItemStack(Material.COAL));
                    inventory.setItem(2, smoker.getResult());
                    player.openInventory(inventory);

                    break found;
                } else if (recipe instanceof CraftBlastingRecipe blaster) {
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
////                    Inventory inventory = Bukkit.createInventory(null, InventoryType.SMITHING);
////                    IInventory iInventory = ((CraftInventoryCustom) inventory).getInventory();
////
////                    System.out.println(iInventory.getClass().getName());
////                    iInventory.getContents().forEach(itemStack -> System.out.println(itemStack.toString()));
////                    iInventory.getContents().forEach(itemStack -> System.out.println(itemStack.toString()));
//
//                    Inventory inventory = Bukkit.createInventory(null, InventoryType.SMITHING);
//                    EntityPlayer playerN = (EntityPlayer) ((CraftHumanEntity) player).getHandle();
//                    Container formerContainer = ((CraftHumanEntity) player).getHandle().bV;
//                    ITileInventory iinventory = null;
//                    if (inventory instanceof CraftInventoryDoubleChest) {
//                        iinventory = ((CraftInventoryDoubleChest)inventory).tile;
//                    } else if (inventory instanceof CraftInventoryLectern) {
//                        iinventory = ((CraftInventoryLectern)inventory).tile;
//                    } else if (inventory instanceof CraftInventory) {
//                        CraftInventory craft = (CraftInventory)inventory;
//                        if (craft.getInventory() instanceof ITileInventory) {
//                            iinventory = (ITileInventory)craft.getInventory();
//                        }
//                    }
//
//                    if (iinventory instanceof ITileInventory && iinventory instanceof TileEntity) {
//                        TileEntity te = (TileEntity)iinventory;
//                        if (!te.hasWorld()) {
//                            te.setWorld(((CraftHumanEntity) player).getHandle().t);
//                        }
//                    }
//
//                    inventory.setItem(2, result);
//                    inventory.setItem(0, base.getItemStack());
//                    inventory.setItem(1, addition.getItemStack());
//
//                    Containers<?> container = CraftContainer.getNotchInventoryType(inventory);
//                    if (iinventory instanceof ITileInventory) {
//                        ((CraftHumanEntity) player).getHandle().openContainer(iinventory);
//                    } else {
//                        if (playerN.b != null) {
//                            Preconditions.checkArgument(container != null, "Unknown windowType");
//                            Container containerN = new CraftContainer(inventory, playerN, playerN.nextContainerCounter());
//                            containerN = CraftEventFactory.callInventoryOpenEvent(playerN, containerN);
//                            System.out.println(containerN.getBukkitView().getItem(0));
//                            if (containerN != null) {
//                                String title = containerN.getBukkitView().getTitle();
//                                playerN.b.sendPacket(new PacketPlayOutOpenWindow(containerN.j, container, CraftChatMessage.fromString(title)[0]));
//                                playerN.bV = containerN;
//                                playerN.initMenu(containerN);
//                            }
//                        }
//                    }
//
//                    if (((CraftHumanEntity) player).getHandle().bV == formerContainer) {
//                    } else {
//                        ((CraftHumanEntity) player).getHandle().bV.checkReachable = false;
//                        ((CraftHumanEntity) player).getHandle().bV.getBukkitView();
//                    }
//
//                    // player.openInventory(inventory);
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
