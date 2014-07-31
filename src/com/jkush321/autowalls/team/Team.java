/*
 * Author: 598Johnn897
 * 
 * Date: Jul 30, 2014
 * Package: com.jkush321.autowalls.team
 *
 */
package com.jkush321.autowalls.team;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created: Jul 30, 2014 <br>
 * Time: 9:29:42 PM <br>
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

	private ArrayList<Player> players = new ArrayList<Player>();
	
	public Team(String name, ChatColor color) {
		
	}

	/**
	 * Adds a player to this team
	 */
	public void addPlayer(Player player) {
		
	}
	
	/**
	 * Removes a player from this team
	 */
	public void removePlayer(Player player) {
		
	}
	
	/**
	 * @return true if player is on this team
	 */
	public boolean isPlayerOnTeam(Player player) {
		if (players.contains(player.getName()))
			return true;
		else
			return false;
	}
	
	/**
	 * Gets all the players on this team
	 * 
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

}
