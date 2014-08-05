/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 11:06:39 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: PlayerHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class PlayerHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();
	private TeamHandler teamHandler = plugin.getTeamHandler();

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			handler.createPlayerFile(player);
			FileConfiguration playerFile = handler.getPlayerConfig(player);
			HashMap<String, Object> defaults = new HashMap<String, Object>();
			{
				defaults.put("player.username", player.getName());
				defaults.put("player.nickname", player.getDisplayName());
				
				defaults.put("player.unlocked.kits", "");
				defaults.put("player.unlocked.powers", "");
				defaults.put("player.unlocked.cosmetics", "");
				
				defaults.put("player.stats.logins", 1);

				defaults.put("player.stats.kills", 0);
				defaults.put("player.stats.coins", 0);
				defaults.put("player.stats.wins", 0);
			}
			playerFile.addDefaults(defaults);
			playerFile.options().copyDefaults(true);
			handler.savePlayerConfig(player);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		plugin.getHandler().playersOnline.add(player.getName());

		event.setJoinMessage(ChatColor.translateAlternateColorCodes(
				'&',
				ColorUtil.formatColors(
						plugin.getAWConfig().getString(
								"AutoWalls Messages.join")).replaceAll(
						"%player%", player.getDisplayName())));

		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		board.registerNewObjective("showhealth", "health");
		Objective objective = board.getObjective("showhealth");
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objective.setDisplayName(ColorUtil.formatColors("<red>♥︎"));

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(board);
			p.setHealth(p.getHealth());
		}

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (handler.playing.contains(player)) {
			player.setHealth(0);
			teamHandler.removePlayerFromTeam(player);
			teamHandler.updateTeams();
		}
		plugin.getHandler().playersOnline.remove(player.getName());

		event.setQuitMessage(ChatColor.translateAlternateColorCodes(
				'&',
				ColorUtil.formatColors(
						plugin.getAWConfig().getString(
								"AutoWalls Messages.leave")).replaceAll(
						"%player%", player.getDisplayName())));
		
		if (handler.playing.contains(player) && handler.isGameInProgress())
			player.setHealth(0);
		else if (handler.playing.contains(player) && !handler.isGameInProgress())
			plugin.getTeamHandler().removePlayerFromTeam(player);
		if (plugin.getEventsHandler().getLastEvent(player) != 0)
			plugin.getEventsHandler().lastEvent.remove(player);
	}
}
