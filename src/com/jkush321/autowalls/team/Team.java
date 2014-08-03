/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.team
 *
 */
package com.jkush321.autowalls.team;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 7:21:41 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: Team.java <br>
 * Package: com.jkush321.autowalls.team <p>
 * 
 * @author 598Johnn897
 */
public class Team {
	
	private String name;
	private ChatColor color;
	private Location teamSpawnMap1;
	private Location teamSpawnMap2;
	
	public Team(String name, ChatColor color, Location map1, Location map2) {
		this.name = name;
		this.color = color;
		this.teamSpawnMap1 = map1;
		this.teamSpawnMap2 = map2;
	}
	
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public String getName() {
		return name.toUpperCase();
	}
	
	public ChatColor getColor() {
		return color;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setColor(ChatColor color) {
		this.color = color;
	}
	
	/**
	 * @return the teamSpawnMap1
	 */
	public Location getTeamSpawnMap1() {
		return teamSpawnMap1;
	}

	/**
	 * @param teamSpawnMap1 the teamSpawnMap1 to set
	 */
	public void setTeamSpawnMap1(Location teamSpawnMap1) {
		this.teamSpawnMap1 = teamSpawnMap1;
	}

	/**
	 * @return the teamSpawnMap2
	 */
	public Location getTeamSpawnMap2() {
		return teamSpawnMap2;
	}

	/**
	 * @param teamSpawnMap2 the teamSpawnMap2 to set
	 */
	public void setTeamSpawnMap2(Location teamSpawnMap2) {
		this.teamSpawnMap2 = teamSpawnMap2;
	}
}
