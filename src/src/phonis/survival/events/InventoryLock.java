package src.phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.block.DoubleChest;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftChest;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import src.phonis.survival.Survival;

import java.util.Objects;

public class InventoryLock implements Listener {
	private final Survival survival;

	public InventoryLock(Survival survival) {
		this.survival = survival;

		Bukkit.getServer().getPluginManager().registerEvents(this, this.survival);
	}

	@EventHandler
	public void LockInventory(InventoryClickEvent event) {
		if (event.getInventory().getMaxStackSize() == Integer.MAX_VALUE) {
			if (!event.isCancelled()) {
//				if (event.getWhoClicked() != null && event.getWhoClicked() instanceof Player) {
//					if (event.getWhoClicked().isOp())
//						return;
//				}

				event.setCancelled(true);
			}
		} else if (!event.isCancelled()) {
			if (event.getClickedInventory() instanceof CraftInventoryDoubleChest) {
				CraftInventoryDoubleChest craftInv = (CraftInventoryDoubleChest) event.getClickedInventory();

				if (craftInv.getHolder() != null) {
					DoubleChest dChest = craftInv.getHolder();

					this.queueChest((CraftChest) Objects.requireNonNull(dChest.getLeftSide()));
					this.queueChest((CraftChest) Objects.requireNonNull(dChest.getRightSide()));
				}
			} else if (event.getInventory() instanceof CraftInventoryDoubleChest) {
				CraftInventoryDoubleChest craftInv = (CraftInventoryDoubleChest) event.getInventory();

				if (craftInv.getHolder() != null) {
					DoubleChest dChest = craftInv.getHolder();

					this.queueChest((CraftChest) Objects.requireNonNull(dChest.getLeftSide()));
					this.queueChest((CraftChest) Objects.requireNonNull(dChest.getRightSide()));
				}
			} else if (event.getClickedInventory() instanceof CraftInventory) {
				CraftInventory craftInv = (CraftInventory) event.getClickedInventory();
				CraftInventory toInv = (CraftInventory) event.getInventory();

				if (craftInv.getHolder() instanceof CraftChest) {
					this.queueChest((CraftChest) craftInv.getHolder());
				} else if (toInv.getHolder() instanceof CraftChest) {
					this.queueChest((CraftChest) toInv.getHolder());
				}
			}
		}
	}

	private void queueChest(CraftChest cChest) {
		this.survival.updateQueue.add(cChest.getBlock().getLocation());
	}

//  if (event.getClickedInventory() instanceof CraftInventoryDoubleChest) {
//		Bukkit.broadcastMessage(event.getAction().name());
//		Bukkit.broadcastMessage("1");
//	} else if (event.getInventory() instanceof CraftInventoryDoubleChest) {
//		Bukkit.broadcastMessage(event.getAction().name());
//		Bukkit.broadcastMessage("2");
//	} else if (event.getClickedInventory() instanceof CraftInventory) {
//		CraftInventory craftInv = (CraftInventory) event.getClickedInventory();
//
//		if (craftInv.getHolder() instanceof CraftChest) {
//			CraftChest cChest = (CraftChest) craftInv.getHolder();
//
//			if (event.getAction().equals(InventoryAction.PICKUP_ALL) || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
//				Bukkit.broadcastMessage(
//					"Taking out " +
//						event.getClickedInventory().getItem(event.getSlot()).getAmount() + " " +
//						event.getClickedInventory().getItem(event.getSlot()).getType() + " from single chest at " +
//						cChest.getBlock().getLocation()
//				);
//			} else if (event.getAction().equals(InventoryAction.PLACE_ALL)) {
//				ItemStack stack = event.getWhoClicked().getItemOnCursor();
//
//				Bukkit.broadcastMessage(
//					"Putting in " + stack.getAmount() + " " + stack.getType() + " to single chest at " +
//						cChest.getBlock().getLocation()
//				);
//			} else if (event.getAction().equals(InventoryAction.PLACE_ONE)) {
//				ItemStack stack = event.getWhoClicked().getItemOnCursor();
//
//				Bukkit.broadcastMessage(
//					"Putting in 1 " + stack.getType() + " to single chest at " +
//						cChest.getBlock().getLocation()
//				);
//			} else if (event.getAction().equals(InventoryAction.PICKUP_HALF)) {
//
//			}
//		} else if (craftInv.getHolder() instanceof CraftPlayer) {
//			if (event.getInventory() instanceof CraftInventory) {
//				CraftInventory toInv = (CraftInventory) event.getInventory();
//
//				if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && toInv.getHolder() instanceof CraftChest) {
//					CraftChest cChest = (CraftChest) toInv.getHolder();
//
//					Bukkit.broadcastMessage(
//						"Putting in " +
//							event.getClickedInventory().getItem(event.getSlot()).getAmount() + " " +
//							event.getClickedInventory().getItem(event.getSlot()).getType() + " into single chest at " +
//							cChest.getBlock().getLocation()
//					);
//				}
//			}
//		}
//	} else {
//		Bukkit.broadcastMessage(event.getClickedInventory().getClass().getName());
//	}
}