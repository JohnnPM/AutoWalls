/*
 * Author: 598Johnn897
 * 
 * Date: Jul 30, 2014
 * Package: com.jkush321.autowalls.team
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.ClassEnumerator;
import com.jkush321.autowalls.team.Team;

/**
 * Created: Jul 30, 2014 <br>
 * Time: 9:29:56 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TeamHandler.java <br>
 * Package: com.jkush321.autowalls.team
 * <p>
 * 
 * @author 598Johnn897
 */
public class TeamHandler {

	private AutoWalls plugin;
	
	public HashMap<Team, ArrayList<Player>> teams = new HashMap<Team, ArrayList<Player>>();
	public HashMap<Player, Team> playerPerTeam = new HashMap<Player, Team>();
	public ArrayList<Team> teamList = new ArrayList<Team>();
	public int maxTeamSize;
	
	public TeamHandler(AutoWalls autoWalls) {
		plugin = autoWalls;
		
		maxTeamSize = plugin.getAWConfig().getint("AutoWalls Settings.maxTeamSize");
	}
	
	private void registerTeam(Team team) {
		teams.put(team, team.getPlayers());
		teamList.add(team);
	}

	public void updateTeams() {
		for (Team t : teamList) {
			teams.remove(t);
			teams.put(t, t.getPlayers());
		}
	}
	
	public void unregisterTeam(Team team) {
		teams.remove(team);
		teamList.remove(team);
	}
	
	/**
	 * Gets player's team else returns null
	 * 
	 * @return player's team
	 */
	public Team getPlayerTeam(Player player) {
		try {
			return playerPerTeam.get(player);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public void addPlayerToTeam(Player player, Team team) {
		playerPerTeam.put(player, team);
		team.getPlayers().add(player);
	}
	
	/**
	 * Removes player from the team he/her is on
	 * 
	 * @param player
	 */
	public void removePlayerFromTeam(Player player) {
		try {
			playerPerTeam.remove(player);
		} catch (NullPointerException e) {
		}
	}
	
	public ArrayList<Player> getPlayersOnTeam(Team team) {
		return team.getPlayers();
	}
	
	public ArrayList<Player> getPlayersOnTeams() {
		ArrayList<Player> totalPlayers = new ArrayList<Player>();
		for (Team t : teamList) {
			for(Player p : t.getPlayers()) {
				totalPlayers.add(p);
			}
		}
		return totalPlayers;
	}
	
	public void registerTeams() {
		Class<?>[] classes = ClassEnumerator.getInstance()
				.getClassesFromThisJar(plugin);
		if (classes == null || classes.length == 0) {
			return;
		}
		plugin.getAWLogger().log(Level.INFO, "Starting registration of teams:");
		for (Class<?> c : classes) {
			try {
				if (Team.class.isAssignableFrom(c) && !c.isInterface()
						&& !c.isEnum() && !c.isAnnotation()) {
					plugin.getLogger().log(Level.INFO,
							"Searching class: " + c.getSimpleName());
					registerTeam((Team) c.newInstance());
				}
			} catch (IllegalAccessException | InstantiationException e) {
				plugin.getLogger().log(
						Level.INFO,
						c.getSimpleName()
								+ " does not use the default constructor");
				e.printStackTrace();
			}
		}
		plugin.getAWLogger().log(Level.INFO, "Ended registration of teams.");
	}

}
