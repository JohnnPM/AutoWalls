/*
 * Author: 598Johnn897
 * 
 * Date: Aug 3, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.team.Team;

/**
 * Created: Aug 3, 2014 <br>
 * Time: 6:32:34 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: ChatHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class ChatHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();
	private TeamHandler teamHandler = plugin.getTeamHandler();

	public ArrayList<Player> teamChatting = new ArrayList<Player>();
	public ArrayList<Player> specChatting = new ArrayList<Player>();
	public ArrayList<Player> globalChatting = new ArrayList<Player>();

	public void chatTeam(Player player, String message, Team team) {

	}

	public void chatSpec(Player player, String message) {

	}

	public void chatGlobal(Player player, String message) {

	}
	
	public void yell(Player player, String message) {
		
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (teamChatting.contains(player)) {
			chatTeam(player, event.getMessage(),
					teamHandler.getPlayerTeam(player));
		} else if (specChatting.contains(player)) {
			chatSpec(player, event.getMessage());
		} else if (globalChatting.contains(player)) {
			chatGlobal(player, event.getMessage());
		} else {
			event.setCancelled(false);
		}
	}
}
