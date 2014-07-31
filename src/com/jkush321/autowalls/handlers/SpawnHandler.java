/*
 * Author: Laddawan
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.event.Listener;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.team.Team;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 4:42:07 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: SpawnHandler.java <br>
 * Package: com.jkush321.autowalls.handlers <p>
 * 
 * @author 598Johnn897
 */
public class SpawnHandler implements Listener {
	
	private AutoWalls plugin;
	public HashMap<Team, Location> locations = new HashMap<Team, Location>();
	
	public SpawnHandler(AutoWalls autoWalls) {
		plugin = autoWalls;
	}
	
	/**
	 * Teleports all players to their team spawn
	 */
	public void tpPlayers(){
		
	}

}
