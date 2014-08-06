/*
 * Author: 598Johnn897
 * 
 * Date: Aug 6, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.ColorCycler;
import com.jkush321.autowalls.sign.SignUI;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Aug 6, 2014 <br>
 * Time: 1:45:37 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: InteractHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class InteractHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();

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
		if (handler.playing.contains(e.getPlayer())
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (e.getPlayer().getItemInHand() != null) {
				if (e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR) {
					if (ColorCycler.colorTime.containsKey(e.getPlayer())) {
						if (ColorCycler.colorTime.get(e.getPlayer()) == 0) {
							e.getPlayer()
									.sendMessage(
											ColorUtil
													.formatColors("<red>You no longer have the ability to do that anymore!"));
						} else {
							ColorCycler.cycle(e.getPlayer());
						}
					} else {
						ColorCycler.cycle(e.getPlayer());
					}
				} else if (e.getPlayer().getItemInHand().getType() == Material.SNOW_BALL) {
					if (e.getPlayer().getItemInHand().getItemMeta()
							.hasDisplayName()) {
						if (e.getPlayer().getItemInHand().getItemMeta()
								.getDisplayName().contains("Basic")) {
							e.getPlayer().setMetadata("last-grenade",
									new FixedMetadataValue(plugin, "basic"));
							System.out.println("Meep");
						}
					} else {
						if (e.getPlayer().hasMetadata("last-grenade"))
							e.getPlayer()
									.removeMetadata("last-grenade", plugin);
					}
				} else if (e.getPlayer().getItemInHand().getType() == Material.ENDER_PEARL
						&& plugin.getWallDropTimer().time > 0) {
					e.getPlayer()
							.sendMessage(
									ColorUtil
											.formatColors("<red>You cannot do that until the walls fall!"));
					e.setCancelled(true);
				}
			}
		}
		if (e.getPlayer().hasPermission("walls.op")) {
			e.setCancelled(false);
			return;
		}
		if ((e.getPlayer().getLocation().getBlockY() > 139 && handler.mapNumber == 1)
				|| (e.getPlayer().getLocation().getBlockY() > 125 && handler.mapNumber == 2)) {
			e.setCancelled(false);
			return;
		} else {
			if (handler.playing.contains(e.getPlayer())) {
				plugin.getEventsHandler().setLastEventToNow(e.getPlayer());
				if ((e.getPlayer().getItemInHand() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK)
						|| e.getAction() == Action.LEFT_CLICK_BLOCK) {
					if (e.getPlayer().getItemInHand().getType() != Material.AIR) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (!handler.playing.contains(p)) {
								if (p.getLocation().distance(
										e.getClickedBlock().getLocation()) <= 2) {
									p.teleport(p.getLocation()
											.add(new Location(p.getWorld(), 0,
													2, 0)));
									p.sendMessage(ColorUtil
											.formatString(
													"<gold>You have been moved to allow %s to place a block!",
													e.getPlayer()
															.getDisplayName()));
								}
							}
						}
					}
					if (e.getPlayer().getItemInHand().getType() != Material.FLINT_AND_STEEL
							&& e.getPlayer().getItemInHand().getType() == Material.FIREBALL
							&& plugin.getWallDropTimer().time > 0
							&& plugin.getAWConfig().getboolean(
									"AutoWalls Settings.fire")) {
						e.getPlayer()
								.sendMessage(
										ColorUtil
												.formatColors("<red>No placing fire before the walls fall!"));
						e.setCancelled(true);
					}
				}
			}
		}
		if (!handler.gameInProgress)
			e.setCancelled(true);
		if (!handler.playing.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
}
