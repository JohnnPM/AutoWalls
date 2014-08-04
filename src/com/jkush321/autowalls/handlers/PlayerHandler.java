/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			handler.createPlayerFile(player);
			FileConfiguration playerFile = handler.getPlayerConfig(player);
			HashMap<String, Object> defaults = new HashMap<String, Object>();
			{
				defaults.put("player.logins", 1);
				defaults.put("player.username", player.getName());
				defaults.put("player.nickname", player.getDisplayName());
				defaults.put("player.unlocked.kits", "");
				defaults.put("player.unlocked.powers", "");
				defaults.put("player.unlocked.cosmetics", "");
				defaults.put("player.stats.kills", 0);
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
								"AutoWalls Messages.join")).replace("%player%",
						player.getDisplayName())));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		plugin.getHandler().playersOnline.remove(player.getName());
	}
}
