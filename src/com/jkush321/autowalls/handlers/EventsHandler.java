/*
 * Author: 598Johnn897
 * 
 * Date: Aug 5, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.jkush321.autowalls.AutoWalls;

/**
 * Created: Aug 5, 2014 <br>
 * Time: 10:36:27 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: EventHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class EventsHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();

	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent e) {
		for (Block b : e.getBlocks()) {
			if (b.getType() == Material.SAND || b.getType() == Material.GRAVEL)
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPistonRetract(BlockPistonRetractEvent e) {
		if (e.getRetractLocation().getBlock().getType() == Material.SAND
				|| e.getRetractLocation().getBlock().getType() == Material.GRAVEL)
			e.setCancelled(true);
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		List<Block> newList = new ArrayList<Block>();
		newList.addAll(e.blockList());

		for (Block b : newList) {
			if (b.getType() == Material.SAND || b.getType() == Material.GRAVEL) {
				e.blockList().remove(b);
			}
		}
	}

	/**
	 * Gets rid of the rain
	 * 
	 * @param e
	 */
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntitySpawn(CreatureSpawnEvent e) {
		if (!plugin.getAWConfig().getboolean("AutoWalls Options.hostileMobs"))
			return;
		if (e.getEntity().getType().equals(EntityType.CREEPER)
				|| e.getEntity().getType().equals(EntityType.ENDERMAN)
				|| e.getEntity().getType().equals(EntityType.SLIME)
				|| e.getEntity().getType().equals(EntityType.SKELETON)
				|| e.getEntity().getType().equals(EntityType.SPIDER)
				|| e.getEntity().getType().equals(EntityType.ZOMBIE))
			e.setCancelled(true);
	}

}
