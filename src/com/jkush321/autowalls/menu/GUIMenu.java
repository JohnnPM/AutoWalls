package com.jkush321.autowalls.menu;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.jkush321.autowalls.AutoWalls;

public class GUIMenu implements Listener {
	private String name;
	private int size;
	private Player player;
	private boolean willClose = false;
	
	private HashMap<Integer, GUIButton> buttons = new HashMap<Integer, GUIButton>();

	private HashMap<Integer, ItemStack> options = new HashMap<Integer, ItemStack>();

	public GUIMenu(Plugin plugin, String name, int size, Player player) {
		this.name = name;
		this.size = size;
		this.player = player;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public static GUIMenu createMenu(Plugin plugin, String name, int size, Player player) {
		return new GUIMenu(plugin, name, size, player);
	}

	public GUIMenu setOption(int postition, ItemStack stack, String name,
			List<String> description, GUIButton button) {
		buttons.put(postition, button);
		options.put(postition, setNameAndLore(stack, name, description));

		return this;
	}

	public void setWillClose(boolean willClose) {
		this.willClose = willClose;
	}

	public void open() {
		Inventory inv = Bukkit.getServer().createInventory(player, size, name);

		for (int i = 0; i < buttons.size(); i++) {
			if (options.get(i) != null) {
				inv.setItem(i, options.get(i));
			}
		}

		player.openInventory(inv);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(name)) {
			event.setCancelled(true);
			int slot = event.getRawSlot();
			if (slot >= 0 && slot < size) {
				for (Integer ig : buttons.keySet()) {
					if (ig == slot) {
						buttons.get(ig).onClick(player,
								options.get(ig).getItemMeta().getDisplayName());
					}
				}
			}

			if (willClose) {
				final Player clicker = (Player) event.getWhoClicked();

				Bukkit.getServer()
						.getScheduler()
						.scheduleSyncDelayedTask(AutoWalls.get(),
								new Runnable() {
									public void run() {
										clicker.closeInventory();
									}
								}, 1L);

				unload();
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if (event.getInventory().getTitle().equals(name)) {
			unload();
		}
	}

	private void unload() {
		HandlerList.unregisterAll(this);

		options = null;
		buttons = null;

		name = null;
		player = null;
	}

	private ItemStack setNameAndLore(ItemStack item, String name,
			List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return (item);
	}
}