/*
 * Author: 598Johnn897
 * 
 * Date: Aug 5, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.Grenades;

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
	private GameHandler handler = plugin.getHandler();

	public HashMap<Player, Long> lastEvent = new HashMap<Player, Long>();

	public void setLastEvent(Player p, long millis) {
		if (lastEvent.containsKey(p))
			lastEvent.remove(p);
		lastEvent.put(p, millis);
	}

	public void setLastEventToNow(Player p) {
		if (lastEvent.containsKey(p))
			lastEvent.remove(p);
		lastEvent.put(p, System.currentTimeMillis());
	}

	public long getLastEvent(Player p) {
		if (lastEvent.containsKey(p))
			return lastEvent.get(p);
		return 0;
	}

	public int getTicksFromLastEvent(Player p) {
		if (lastEvent.containsKey(p))
			return (int) Math
					.floor((double) ((System.currentTimeMillis() - lastEvent
							.get(p)) / 50));
		return Integer.MAX_VALUE;
	}

	@EventHandler
	public void onTp(PlayerTeleportEvent e) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			for (Player p2 : handler.playing) {
				if (p != p2 && !handler.playing.contains(p)) {
					p2.hidePlayer(p);
				} else if (p != p2 && handler.playing.contains(p)) {
					p2.showPlayer(p);
				}
			}
		}
	}

	@EventHandler
	public void onProjectileLand(ProjectileHitEvent e) {
		if (e.getEntity().getType() == EntityType.SNOWBALL) {
			if (e.getEntity().hasMetadata("grenade-type")) {
				Grenades.handleLanding(e, e.getEntity());
			}
		}
	}

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if (!plugin.getHandler().playing.contains(e.getPlayer())
				|| !e.getPlayer().hasPermission("walls.op"))
			e.setCancelled(true);
	}

	@EventHandler
	public void onDroppedItem(PlayerDropItemEvent e) {
		setLastEventToNow(e.getPlayer());
		if (!plugin.getHandler().playing.contains(e.getPlayer())
				|| !e.getPlayer().hasPermission("walls.op"))
			e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		setLastEventToNow(e.getPlayer());
	}

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
