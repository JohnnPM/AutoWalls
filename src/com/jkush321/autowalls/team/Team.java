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
	
	public Team(String name, ChatColor color) {
		this.name = name;
		this.color = color;
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
}
