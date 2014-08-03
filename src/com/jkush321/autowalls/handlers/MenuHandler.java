/*
 * Author: 598Johnn897
 * 
 * Date: Aug 2, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.menu.GUIMenu;

/**
 * Created: Aug 2, 2014 <br>
 * Time: 8:19:59 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: MenuHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class MenuHandler implements Listener {

	public GUIMenu kitMenu = new GUIMenu("", 4, null);
	
	private AutoWalls plugin = AutoWalls.get();

	@EventHandler
	public void onItemClick(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)
				|| !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(event.getItem().equals(null)) {
				
			}

		}
	}
}
