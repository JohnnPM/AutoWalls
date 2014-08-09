/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:22:52 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TpSpectatorsCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class TpSpectatorsCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();

	@Command(command = "tpspecs", permission = "walls.tpspecs")
	public void tpSpecs(CommandArgs info) {
		if (info.isPlayer()) {
			Player player = info.getPlayer();
			if (info.getArgs().length == 0) {
				for (Player p : plugin.getHandler().spectators) {
					p.teleport(player.getLocation());
					p.sendMessage(ColorUtil.formatString(
							"<gold>You have been teleported to %s!",
							player.getDisplayName()));
				}
			} else if (info.getArgs().length == 1) {
				@SuppressWarnings("deprecation")
				Player playerTo = Bukkit.getPlayer(info.getArgs()[0]);
				if (playerTo != null) {
					for (Player p : plugin.getHandler().spectators) {
						p.teleport(playerTo.getLocation());
						p.sendMessage(ColorUtil.formatString(
								"<gold>You have been teleported to %s!",
								playerTo.getDisplayName()));
					}
				} else {
					player.sendMessage(ColorUtil
							.formatColors("<red>Player not online!"));
				}
			} else if (info.getArgs().length == 3) {
				double x;
				double y;
				double z;
				try {
					x = Double.parseDouble(info.getArgs()[0]);
					y = Double.parseDouble(info.getArgs()[1]);
					z = Double.parseDouble(info.getArgs()[2]);
				} catch (Exception e) {
					player.sendMessage(ColorUtil
							.formatColors("<red>Invalid Coords!"));
					return;
				}
				Location loc = new Location(player.getWorld(), x, y, z);
				for (Player p : plugin.getHandler().spectators) {
					p.teleport(loc);
					p.sendMessage(ColorUtil.formatString(
							"<gold>You have been teleported to %d, %d, %d!", x,
							y, z));
				}
			} else {
				player.sendMessage(ColorUtil
						.formatColors("<red>Invalid Arguments!"));
			}
		} else {
			if (info.getArgs().length == 0) {
				info.getSender()
						.sendMessage(
								ColorUtil
										.formatColors("<red>Usage: /tpspecs ([player] | [x, y, z])"));
			} else if (info.getArgs().length == 1) {
				@SuppressWarnings("deprecation")
				Player playerTo = Bukkit.getPlayer(info.getArgs()[0]);
				if (playerTo != null) {
					for (Player p : plugin.getHandler().spectators) {
						p.teleport(playerTo.getLocation());
						p.sendMessage(ColorUtil.formatString(
								"<gold>You have been teleported to %s!",
								playerTo.getDisplayName()));
					}
				} else {
					info.getSender().sendMessage(
							ColorUtil.formatColors("<red>Player not online!"));
				}
			} else if (info.getArgs().length == 3) {
				double x;
				double y;
				double z;
				try {
					x = Double.parseDouble(info.getArgs()[0]);
					y = Double.parseDouble(info.getArgs()[1]);
					z = Double.parseDouble(info.getArgs()[2]);
				} catch (Exception e) {
					info.getSender().sendMessage(
							ColorUtil.formatColors("<red>Invalid Coords!"));
					return;
				}
				Location loc = new Location(plugin.getHandler().spectators.get(
						0).getWorld(), x, y, z);
				for (Player p : plugin.getHandler().spectators) {
					p.teleport(loc);
					p.sendMessage(ColorUtil.formatString(
							"<gold>You have been teleported to %d, %d, %d!", x,
							y, z));
				}
			} else if (info.getArgs().length == 4) {
				World world;
				double x;
				double y;
				double z;
				try {
					world = Bukkit.getWorld(info.getArgs()[0]);
					x = Double.parseDouble(info.getArgs()[1]);
					y = Double.parseDouble(info.getArgs()[2]);
					z = Double.parseDouble(info.getArgs()[3]);
				} catch (Exception e) {
					info.getSender().sendMessage(
							ColorUtil.formatColors("<red>Invalid Coords!"));
					return;
				}
				Location loc = new Location(world, x, y, z);
				for (Player p : plugin.getHandler().spectators) {
					p.teleport(loc);
					p.sendMessage(ColorUtil.formatString(
							"<gold>You have been teleported to %d, %d, %d!", x,
							y, z));
				}
			} else {
				info.getSender().sendMessage(
						ColorUtil.formatColors("<red>Invalid Arguments!"));
			}

		}
	}
}
