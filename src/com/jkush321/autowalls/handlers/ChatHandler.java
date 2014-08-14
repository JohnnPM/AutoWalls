/*
 * Author: 598Johnn897
 * 
 * Date: Aug 3, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.config.Config;
import com.jkush321.autowalls.team.Team;
import com.jkush321.autowalls.util.ColorUtil;

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
	private Config config = plugin.getAWConfig();

	public ArrayList<Player> teamChatting = new ArrayList<Player>();
	public ArrayList<Player> specChatting = new ArrayList<Player>();
	public ArrayList<Player> globalChatting = new ArrayList<Player>();

	public String teamChatFormat = ColorUtil.formatColors(config.getString("Chat.team"));

	public void chatTeam(Player player, String message, Team team) {
		for (Player p : team.getPlayers()) {
			p.sendMessage(teamChatFormat
					.replaceAll("%teamcolor%", team.getColor().toString())
					.replaceAll("%team%", team.getName())
					.replaceAll("%player%", player.getDisplayName())
					.replaceAll("%message%", message.trim()));
		}
		plugin.getAWLogger().log(
				Level.INFO,
				ChatColor.stripColor(teamChatFormat
						.replaceAll("%teamcolor%", team.getColor().toString())
						.replaceAll("%team%", team.getName())
						.replaceAll("%player%", player.getDisplayName())
						.replaceAll("%message%", message.trim())));
	}

	public String specChatFormat = ColorUtil.formatColors(config.getString("Chat.spectator"));

	public void chatSpec(Player player, String message) {
		for (Player p : plugin.getHandler().spectators) {
			p.sendMessage(specChatFormat
					.replaceAll(
							"%kills%",
							""
									+ plugin.getAWConfig().getint(
											"player.stats.kills"))
					.replaceAll("%player%", player.getDisplayName())
					.replaceAll("%message%", message.trim()));
		}
		plugin.getAWLogger().log(
				Level.INFO,
				ChatColor.stripColor(specChatFormat
						.replaceAll(
								"%kills%",
								""
										+ plugin.getAWConfig().getint(
												"player.stats.kills"))
						.replaceAll("%player%", player.getDisplayName())
						.replaceAll("%message%", message.trim())));
	}

	public String globalChatFormat = ColorUtil.formatColors(config.getString("Chat.global"));

	public void chatGlobal(Player player, String message) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(globalChatFormat
					.replaceAll(
							"%kills%",
							""
									+ plugin.getAWConfig().getint(
											"player.stats.kills"))
					.replaceAll("%player%", player.getDisplayName())
					.replaceAll("%message%", message.trim()));
		}
		plugin.getAWLogger().log(
				Level.INFO,
				ChatColor.stripColor(globalChatFormat
						.replaceAll(
								"%kills%",
								""
										+ plugin.getAWConfig().getint(
												"player.stats.kills"))
						.replaceAll("%player%", player.getDisplayName())
						.replaceAll("%message%", message.trim())));
	}

	public String yellChatFormat = ColorUtil.formatColors(config.getString("Chat.yell"));

	public void yell(Player player, String message) {
		ChatColor color = ChatColor.GRAY;
		if (plugin.getHandler().playing.contains(player))
			color = teamHandler.getPlayerTeam(player).getColor();
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(yellChatFormat
					.replaceAll("%color%", color.toString())
					.replaceAll("%player%", player.getDisplayName())
					.replaceAll("%message%", message.trim()));
		}
		plugin.getAWLogger().log(
				Level.INFO,
				ChatColor.stripColor(yellChatFormat
						.replaceAll("%color%", color.toString())
						.replaceAll("%player%", player.getDisplayName())
						.replaceAll("%message%", message.trim())));
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		if (teamChatting.contains(player)) {
			chatTeam(player, event.getMessage(),
					teamHandler.getPlayerTeam(player));
		} else if (specChatting.contains(player)) {
			chatSpec(player, event.getMessage());
		} else {
			chatGlobal(player, event.getMessage());
		}
	}
}
