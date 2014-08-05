/*
 * Author: 598Johnn897
 * 
 * Date: Aug 5, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Aug 5, 2014 <br>
 * Time: 11:55:28 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: BlockHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class BlockHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getPlayer().hasPermission("walls.place.block"))
			return;
		if (!handler.playing.contains(e.getPlayer()))
			e.setCancelled(true);
		if (!handler.isGameInProgress())
			e.setCancelled(true);
		if (handler.getNextMap() == 1) {
			if (e.getBlock().getX() == 347)
				e.setCancelled(true);
			if (e.getBlock().getZ() == -793)
				e.setCancelled(true);
			if (e.getBlock().getX() > 408)
				e.setCancelled(true);
			if (e.getBlock().getZ() < -853)
				e.setCancelled(true);
			if (e.getBlock().getX() < 286)
				e.setCancelled(true);
			if (e.getBlock().getZ() > -731)
				e.setCancelled(true);
			if (e.getBlock().getY() > 138) {
				e.setCancelled(true);
				e.getPlayer()
						.sendMessage(
								ColorUtil
										.formatColors("<red>No building over walls. :P"));
			}
		} else {
			if (e.getBlock().getZ() == -182)
				e.setCancelled(true);
			if (e.getBlock().getZ() == -164)
				e.setCancelled(true);
			if (e.getBlock().getX() == -785)
				e.setCancelled(true);
			if (e.getBlock().getX() == -803)
				e.setCancelled(true);
			if (e.getBlock().getZ() > -103)
				e.setCancelled(true);
			if (e.getBlock().getX() < -863)
				e.setCancelled(true);
			if (e.getBlock().getX() > -725)
				e.setCancelled(true);
			if (e.getBlock().getZ() < -243)
				e.setCancelled(true);
			if (e.getBlock().getY() > 94) {
				e.setCancelled(true);
				e.getPlayer()
				.sendMessage(
						ColorUtil
								.formatColors("<red>No building over walls. :P"));
			}
		}
	}

}
