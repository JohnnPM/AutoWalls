/*
 * Author: 598Johnn897
 * 
 * Date: Aug 9, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mcsg.double0negative.tabapi.TabAPI;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.team.TeamList;

/**
 * Created: Aug 9, 2014 <br>
 * Time: 11:40:29 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TabListHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class TabListHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!plugin.getHandler().tabAPI)
			return;

		Player p = event.getPlayer();
		TabAPI.setPriority(plugin, p, 2);
		TabAPI.updatePlayer(p);

		updateTabAll();
	}

	public void updateTabAll() {
		if (!plugin.getHandler().tabAPI)
			return;

		for (Player p : Bukkit.getOnlinePlayers()) {
			updateTab(p);
		}
	}

	public void updateTab(Player p) {
		if (!plugin.getHandler().tabAPI)
			return;
		try {
			TabAPI.clearTab(p);

			TabAPI.setTabString(plugin, p, 0, 0, ChatColor.GREEN + "Alive");
			TabAPI.setTabString(plugin, p, 0, 1, ChatColor.RED + "Dead");
			TabAPI.setTabString(plugin, p, 0, 2, ChatColor.GRAY + "Spectators");

			for (int i = 2; i < plugin.getHandler().playing.size() + 2; i++) {
				if (plugin.getTeamHandler().getTeam(TeamList.RED).getPlayers()
						.contains(plugin.getHandler().playing.get(i - 2)))
					TabAPI.setTabString(
							plugin,
							p,
							i,
							0,
							ChatColor.RED
									+ plugin.getHandler().playing.get(i - 2)
											.getName());
				if (plugin.getTeamHandler().getTeam(TeamList.BLUE).getPlayers()
						.contains(plugin.getHandler().playing.get(i - 2)))
					TabAPI.setTabString(plugin, p, i, 0, ChatColor.BLUE
							+ plugin.getHandler().playing.get(i - 2).getName());
				if (plugin.getTeamHandler().getTeam(TeamList.GREEN)
						.getPlayers()
						.contains(plugin.getHandler().playing.get(i - 2)))
					TabAPI.setTabString(plugin, p, i, 0, ChatColor.GREEN
							+ plugin.getHandler().playing.get(i - 2).getName());
				if (plugin.getTeamHandler().getTeam(TeamList.YELLOW)
						.getPlayers()
						.contains(plugin.getHandler().playing.get(i - 2)))
					TabAPI.setTabString(plugin, p, i, 0, ChatColor.YELLOW
							+ plugin.getHandler().playing.get(i - 2).getName());
			}
			for (int i = 2; i < plugin.getHandler().dead.size() + 2; i++) {
				TabAPI.setTabString(plugin, p, i, 1,
						ChatColor.BLACK
								+ plugin.getHandler().dead.get(i - 2).getName());
			}
			int i = 2;
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!plugin.getHandler().playing.contains(player)
						&& !plugin.getHandler().dead.contains(player.getName())) {
					TabAPI.setTabString(plugin, p, i, 2, player.getName());
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TabAPI.updatePlayer(p);

	}

	public void removePlayer(Player p) {
		if (!plugin.getHandler().tabAPI)
			return;
		TabAPI.setPriority(plugin, p, -2);
		TabAPI.updatePlayer(p);
	}

}
