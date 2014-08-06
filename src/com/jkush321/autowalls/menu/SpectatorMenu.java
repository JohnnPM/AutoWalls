/*
 * Author: 598Johnn897
 * 
 * Date: Aug 6, 2014
 * Package: com.jkush321.autowalls.menu
 *
 */
package com.jkush321.autowalls.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.handlers.GameHandler;

/**
 * Created: Aug 6, 2014 <br>
 * Time: 1:08:27 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: SpectatorMenu.java <br>
 * Package: com.jkush321.autowalls.menu
 * <p>
 * 
 * @author 598Johnn897
 */
public class SpectatorMenu implements Listener {

	private Inventory spectate;
	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();
	
	public void openSpectateGUI(Player p, Player[] players, int rows,
			ItemStack item) {
		ItemStack i = item;
		ItemMeta m = i.getItemMeta();
		spectate = Bukkit.createInventory(p, rows * 9, "Click To Spectate!");
		for (int z = 0; z < players.length; z++) {
			m.setDisplayName(players[z].getName());
			i.setItemMeta(m);
			spectate.setItem(z, i);				
		}
		p.openInventory(spectate);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getTitle().equalsIgnoreCase("Click To Spectate!")) {
			if (e.getCurrentItem() != null
					&& e.getCurrentItem().getType() != null) {
				e.setCancelled(true);
				Player p = (Player) e.getWhoClicked();
				if (e.getCurrentItem().hasItemMeta()
						&& e.getCurrentItem().getItemMeta().hasDisplayName()) {
					@SuppressWarnings("deprecation")
					Player c = Bukkit.getPlayer(e.getCurrentItem()
							.getItemMeta().getDisplayName().trim());
					if (c != null) {
						p.teleport(c);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onItemClick(PlayerInteractEvent event) {
		if (handler.isGameInProgress()) return;
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR)
				|| event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getItem().equals(new ItemStack(Material.COMPASS))) {
				if (handler.spectators.contains(event.getPlayer())) {
					openSpectateGUI(event.getPlayer(), (Player[]) handler.playing.toArray(), 5, new ItemStack(Material.SKULL_ITEM, 1, (byte) 3));
				}
			}
		}
	}

}
