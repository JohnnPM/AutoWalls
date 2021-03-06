/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.team
 *
 */
package com.jkush321.autowalls.team;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.config.Config;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 7:24:10 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: TeamBlue.java <br>
 * Package: com.jkush321.autowalls.team <p>
 * 
 * @author 598Johnn897
 */
public class TeamBlue extends Team {
		
	private static Location map1 = new Location(Bukkit.getWorld("map1"), 403, 118, -848);
	private static Location map2 = new Location(Bukkit.getWorld("map2"), -868, 74, -132);
	
	private static AutoWalls plugin = AutoWalls.get();
	private static Config config = plugin.getAWConfig();
	private static String name = config.getString("AutoWalls Names.blue");
	
	public TeamBlue() {
		super(name, ChatColor.BLUE, map1, map2);
	}
	
	private ArrayList<Player> players = new ArrayList<Player>();

	public ArrayList<Player> getPlayers() {
		return players;
	}

}
