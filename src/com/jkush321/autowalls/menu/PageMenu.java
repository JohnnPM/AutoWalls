/*
 * Author: 598Johnn897
 * 
 * Date: Aug 6, 2014
 * Package: com.jkush321.autowalls.menu
 *
 */
package com.jkush321.autowalls.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created: Aug 6, 2014 <br>
 * Time: 5:07:48 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: PageMenu.java <br>
 * Package: com.jkush321.autowalls.menu <p>
 * 
 * @author 598Johnn897
 */
public class PageMenu implements Listener {

	private List<Inventory> invPages = new ArrayList<Inventory>();
	
	public PageMenu(Player player, String name, Player[] players, int rows, int pages) {
		for (int z = 0; z < pages; z++) 
			invPages.add(Bukkit.createInventory(player, rows * 9)); 
		
		ArrayList<Player> playersArray = (ArrayList<Player>) Arrays.asList(players);
		
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		ItemMeta skullMeta = head.getItemMeta();
		
		int number = 0;
		for (int z = 0; z < playersArray.size(); z++) {
			if (z != invPages.get(number).getSize() - 1) {
				skullMeta.setDisplayName(playersArray.get(z).getDisplayName());
				head.setItemMeta(skullMeta);
				invPages.get(number).setItem(z, head);
			} else {
				invPages.get(number).setItem(z, new ItemStack(Material.ARROW));
				number++;
			}
		}
	}
}
