package src.phonis.survival.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftBlastingRecipe;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftComplexRecipe;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftFurnaceRecipe;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftSmokingRecipe;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftGetter implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0) {
			Player player = (Player) sender;
			Material mat = Material.matchMaterial(args[0]);
			
			if (mat != null) {
				List<Recipe> recipes = Bukkit.getServer().getRecipesFor(new ItemStack(mat));
				
				if(recipes.size() == 0) {
					player.sendMessage(ChatColor.RED + "No recipes for material");
					return true;
				}
				
				found: {
					for(Recipe recipe : recipes) {
						if(recipe instanceof ShapedRecipe) {
					        ShapedRecipe shaped = (ShapedRecipe) recipe;
							Inventory inventory = Bukkit.createInventory(null, InventoryType.WORKBENCH);
							inventory.setMaxStackSize(Integer.MAX_VALUE);
							Map<Character, ItemStack> map = shaped.getIngredientMap();
							String[] shape = shaped.getShape();

							for(int w = 0; w < shape.length; w++) {
								for(int i = 0; i < shape[w].length(); i++) {
									inventory.setItem(i + w * 3 + 1, map.get(new Character(shape[w].charAt(i))));
								}
							}
							
							inventory.setItem(0, shaped.getResult());
							
							player.openInventory(inventory);
							break found;
					    }else if(recipe instanceof ShapelessRecipe) {
					        ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
					        List<ItemStack> ls = shapeless.getIngredientList();
					        Inventory inventory = Bukkit.createInventory(null, InventoryType.WORKBENCH);
					        inventory.setMaxStackSize(Integer.MAX_VALUE);
					        int count = 1;
					        
					        for(ItemStack is : ls) {
					        	inventory.setItem(count, is);
					        	count++;
					        }
					        
					        inventory.setItem(0, shapeless.getResult());
					        
							player.openInventory(inventory);
							break found;
					    }else if(recipe instanceof CraftFurnaceRecipe) {
					    	CraftFurnaceRecipe furnace = (CraftFurnaceRecipe) recipe;
					        ItemStack is = furnace.getInput();
					        Inventory inventory = Bukkit.createInventory(null, InventoryType.FURNACE);
					        inventory.setMaxStackSize(Integer.MAX_VALUE);
					        inventory.setItem(0, is);
					        inventory.setItem(1, new ItemStack(Material.COAL));
					        inventory.setItem(2, furnace.getResult());
							player.openInventory(inventory);
							break found;
					    }else if(recipe instanceof CraftSmokingRecipe) {
					    	CraftSmokingRecipe smoker = (CraftSmokingRecipe) recipe;
					        ItemStack is = smoker.getInput();
					        Inventory inventory = Bukkit.createInventory(null, InventoryType.SMOKER);
					        inventory.setMaxStackSize(Integer.MAX_VALUE);
					        inventory.setItem(0, is);
					        inventory.setItem(1, new ItemStack(Material.COAL));
					        inventory.setItem(2, smoker.getResult());
							player.openInventory(inventory);
							break found;
					    }else if(recipe instanceof CraftBlastingRecipe) {
					    	CraftBlastingRecipe blaster = (CraftBlastingRecipe) recipe;
					        ItemStack is = blaster.getInput();
					        Inventory inventory = Bukkit.createInventory(null, InventoryType.BLAST_FURNACE);
					        inventory.setMaxStackSize(Integer.MAX_VALUE);
					        inventory.setItem(0, is);
					        inventory.setItem(1, new ItemStack(Material.COAL));
					        inventory.setItem(2, blaster.getResult());
							player.openInventory(inventory);
							break found;
					    }else if(recipe instanceof CraftComplexRecipe) {
					    	sender.sendMessage(ChatColor.AQUA +
					    		"This material has a complex recipe. This means that its functionality, or appearance, is a result of its inputs, and it has many. Look it up."
					    	);
					    	
					    	break found;
					    }
						
						//player.sendMessage(ChatColor.DARK_GREEN + recipe.getClass().toString());
					}
					
					player.sendMessage(ChatColor.RED + "Recipe not found");
				}
			}else {
				player.sendMessage(ChatColor.RED + "Material not found");
			}
			
			return true;
		}
		
		return false;
	}
}
