/*
 * Author: 598Johnn897
 * 
 * Date: Aug 4, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.jkush321.autowalls.AutoWalls;

/**
 * Created: Aug 4, 2014 <br>
 * Time: 9:02:03 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: GraveHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class GraveHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();
	public List<String> graveMessages = new CopyOnWriteArrayList<String>();

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {

	}
}
