/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.jkush321.autowalls.AutoWalls;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 11:06:39 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: PlayerHandler.java <br>
 * Package: com.jkush321.autowalls.handlers <p>
 * 
 * @author 598Johnn897
 */
public class PlayerHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		
	}
}
