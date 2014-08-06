/*
 * Author: 598Johnn897
 * 
 * Date: Aug 6, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.sign;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created: Aug 6, 2014 <br>
 * Time: 1:51:59 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: SignHandler.java <br>
 * Package: com.jkush321.autowalls.handlers <p>
 * 
 * @author 598Johnn897
 */
public class SignHandler implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.SIGN
					|| e.getClickedBlock().getType() == Material.SIGN_POST) {
				Sign s = (Sign) e.getClickedBlock().getState();
				SignUI.onClick(e.getPlayer(), s.getLine(0), s.getLine(1),
						s.getLine(2), s.getLine(3));
			}
		}
	}
	

	@EventHandler
	public void onSignUpdate(SignChangeEvent e) {
		if (ChatColor.stripColor(e.getLine(0).trim())
				.equalsIgnoreCase("[Join]")
				&& !e.getPlayer().hasPermission("walls.op")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(
					ChatColor.RED + "No placing special signs!");
		}
		if (ChatColor.stripColor(e.getLine(0).trim()).equalsIgnoreCase("[Kit]")
				&& !e.getPlayer().hasPermission("walls.op")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(
					ChatColor.RED + "No placing special signs!");
		}
	}
}
