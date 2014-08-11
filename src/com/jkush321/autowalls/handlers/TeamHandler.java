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
import com.jkush321.autowalls.team.TeamList;
import com.jkush321.autowalls.util.TagUtil;

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

	private AutoWalls plugin = AutoWalls.get();

	public HashMap<Team, ArrayList<Player>> teams = new HashMap<Team, ArrayList<Player>>();
	public HashMap<Player, Team> playerPerTeam = new HashMap<Player, Team>();
	public ArrayList<Team> teamList = new ArrayList<Team>();

	public int maxTeamSize;

	/**
	 * @return the maxTeamSize
	 */
	public int getMaxTeamSize() {
		return maxTeamSize;
	}

	/**
	 * @param maxTeamSize
	 *            the maxTeamSize to set
	 */
	public void setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
	}

	private void registerTeam(Team team) {
		teams.put(team, team.getPlayers());
		teamList.add(team);
	}

	public Team getTeam(TeamList team) {
		return team.getTeam();
	}

	public void updateTeams() {
		for (Team t : teamList) {
			teams.remove(t);
			teams.put(t, t.getPlayers());
		}
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
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

	public void addPlayerToTeam(Player player, TeamList team) {
		playerPerTeam.put(player, team.getTeam());
		team.getTeam().getPlayers().add(player);
		TagUtil.setTagPrefix(player, team.getTeam().getColor().toString());
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
	}

	/**
	 * Removes player from the team he/her is on
	 * 
	 * @param player
	 */
	public void removePlayerFromTeam(Player player) {
		if (!plugin.getHandler().playing.contains(player))
			return;
		try {
			playerPerTeam.get(player).getPlayers().remove(player);
			plugin.getHandler().playing.remove(player);
			playerPerTeam.remove(player);
			TagUtil.removeTagPrefix(player);
			if (plugin.getHandler().tabAPI)
				plugin.getTabHandler().updateTabAll();
		} catch (NullPointerException e) {
		}
	}

	public ArrayList<Player> getPlayersOnTeam(TeamList red) {
		return red.getTeam().getPlayers();
	}

	public ArrayList<Player> getPlayersOnTeams() {
		ArrayList<Player> totalPlayers = new ArrayList<Player>();
		for (Team t : teamList) {
			for (Player p : t.getPlayers()) {
				totalPlayers.add(p);
			}
		}
		return totalPlayers;
	}

	public TeamList getTeamWithMostPlayers() {
		int h = 0;
		TeamList highest = null;

		for (int i = 0; i < teamList.size(); i++) {
			Team t = teamList.get(i);
			if (teamList.get(i).getPlayers().size() > h) {
				highest = teamToList(t);
				h = teamList.get(i).getPlayers().size();
			}
		}
		return highest;
	}

	private TeamList teamToList(Team team) {
		switch (team.getColor().toString()) {
		case "BLUE":
			return TeamList.BLUE;
		case "GREEN":
			return TeamList.GREEN;
		case "RED":
			return TeamList.RED;
		case "YELLOW":
			return TeamList.YELLOW;
		default:
			return null;
		}
	}

	public void teleportPlayers(int mapNumber, TeamList team) {
		for (Player p : team.getTeam().getPlayers())
			if (mapNumber == 1)
				p.teleport(team.getTeam().getTeamSpawnMap1());
			else if (mapNumber == 2)
				p.teleport(team.getTeam().getTeamSpawnMap2());
			else
				throw new IllegalArgumentException("Invalid Map Number!");
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
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
						&& !c.isEnum() && !c.isAnnotation()
						&& !c.equals(Team.class)) {
					plugin.getLogger().log(Level.INFO,
							"Searching class: " + c.getSimpleName());
					registerTeam((Team) c.newInstance());
				}
			} catch (IllegalAccessException | InstantiationException e) {
				plugin.getLogger().log(
						Level.INFO,
						c.getSimpleName()
								+ " does not use the default constructor");
				//e.printStackTrace();
			}
		}
		plugin.getAWLogger().log(Level.INFO, "Ended registration of teams.");
	}

}
