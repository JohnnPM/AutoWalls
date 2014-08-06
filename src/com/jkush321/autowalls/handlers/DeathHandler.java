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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.jkush321.autowalls.AutoWalls;

/**
 * Created: Aug 5, 2014 <br>
 * Time: 6:42:42 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: DeathHandler.java <br>
 * Package: com.jkush321.autowalls.handlers <p>
 * 
 * @author 598Johnn897
 */
public class DeathHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if (handler.isGameInProgress())
			handler.spectate(e.getPlayer());
	}
}
