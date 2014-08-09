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
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.handlers.GameHandler;
import com.jkush321.autowalls.handlers.TeamHandler;
import com.jkush321.autowalls.team.TeamList;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:21:49 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TpCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class TpCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();
	private TeamHandler teamHandler = plugin.getTeamHandler();

	@Command(command = "tp", aliases = { "teleport" })
	public void tp(CommandArgs info) {
		if (info.isPlayer()) {
			Player player = info.getPlayer();
			if (player.hasPermission("walls.tp.all")) {
				if (info.getArgs().length == 0) {
					player.sendMessage(ColorUtil
							.formatColors("<red>Invalid Arguments! /tp <player> [player2]"));
				} else if (info.getArgs().length == 1) {
					try {
						@SuppressWarnings("deprecation")
						Player playerTo = Bukkit.getPlayer(info.getArgs()[0]);

						player.teleport(playerTo.getLocation(),
								TeleportCause.COMMAND);
					} catch (NullPointerException e) {
						player.sendMessage(ColorUtil
								.formatColors("<red>Invalid Player(s)!"));
					}
				} else if (info.getArgs().length == 2) {
					try {
						@SuppressWarnings("deprecation")
						Player playerFrom = Bukkit.getPlayer(info.getArgs()[0]);
						@SuppressWarnings("deprecation")
						Player playerTo = Bukkit.getPlayer(info.getArgs()[1]);

						playerFrom.teleport(playerTo.getLocation(),
								TeleportCause.COMMAND);

						player.sendMessage(ColorUtil.formatString(
								"<gray>You teleported %s to %s!",
								info.getArgs()[0], info.getArgs()[1]));
					} catch (NullPointerException e) {
						info.getSender()
								.sendMessage(
										ColorUtil
												.formatColors("<red>Invalid Player(s)!"));
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
					player.teleport(loc);
				} else if (info.getArgs().length == 4) {
					@SuppressWarnings("deprecation")
					Player playerTo = Bukkit.getPlayer(info.getArgs()[0]);
					double x;
					double y;
					double z;
					try {
						x = Double.parseDouble(info.getArgs()[1]);
						y = Double.parseDouble(info.getArgs()[2]);
						z = Double.parseDouble(info.getArgs()[3]);
					} catch (Exception e) {
						player.sendMessage(ColorUtil
								.formatColors("<red>Invalid Coords!"));
						return;
					}
					Location loc = new Location(player.getWorld(), x, y, z);
					if (playerTo != null) {
						playerTo.teleport(loc);
						playerTo.sendMessage(ColorUtil.formatString(
								"<gold>You have been teleport to %d, %d, %d!",
								x, y, z));
					} else {
						player.sendMessage(ColorUtil
								.formatColors("<red>Invalid Player!"));
					}
				} else {
					info.getSender().sendMessage(
							ColorUtil.formatColors("<red>Invalid Arguments!"));
				}
				return;
			} else if (player.hasPermission("walls.tp.game")) {
				if (info.getArgs().length == 1) {
					@SuppressWarnings("deprecation")
					Player p2 = Bukkit.getPlayer(info.getArgs()[0]);
					if (p2 != null && p2.isOnline()) {
						if (!handler.playing.contains(player))
							player.teleport(p2);
						else {
							if (plugin.getAWConfig().getboolean(
									"AutoWalls Settings.teamTeleport")
									&& plugin.getAWConfig().getint(
											"AutoWalls Settings.teleportDelay") > 0) {
								if (teamHandler.getTeam(TeamList.RED)
										.getPlayers().contains(player)
										&& teamHandler.getTeam(TeamList.RED)
												.getPlayers().contains(p2))
									plugin.getTeleportHandler()
											.createTpRunnable(player, p2);
								else if (teamHandler.getTeam(TeamList.BLUE)
										.getPlayers().contains(player)
										&& teamHandler.getTeam(TeamList.BLUE)
												.getPlayers().contains(p2))
									plugin.getTeleportHandler()
											.createTpRunnable(player, p2);
								else if (teamHandler.getTeam(TeamList.GREEN)
										.getPlayers().contains(player)
										&& teamHandler.getTeam(TeamList.GREEN)
												.getPlayers().contains(p2))
									plugin.getTeleportHandler()
											.createTpRunnable(player, p2);
								else if (teamHandler.getTeam(TeamList.YELLOW)
										.getPlayers().contains(player)
										&& teamHandler.getTeam(TeamList.YELLOW)
												.getPlayers().contains(p2))
									plugin.getTeleportHandler()
											.createTpRunnable(player, p2);
								else {
									player.sendMessage(ColorUtil.formatString(
											"<red>%s is not on your team!",
											p2.getDisplayName()));
								}
								player.sendMessage(ColorUtil
										.formatString(
												"<gold>You will be teleport to <aqua>%s <gold>if you do not move for <aqua>%d <gold>seconds!",
												p2.getDisplayName(),
												plugin.getAWConfig()
														.getint("AutoWalls Settings.teleportDelay")));
							} else if (plugin.getAWConfig().getboolean(
									"AutoWalls Settings.teamTeleport")) {
								if (teamHandler.getTeam(TeamList.RED)
										.getPlayers().contains(player)
										&& teamHandler.getTeam(TeamList.RED)
												.getPlayers().contains(p2))
									player.teleport(p2);
								else if (teamHandler.getTeam(TeamList.BLUE)
										.getPlayers().contains(player)
										&& teamHandler.getTeam(TeamList.BLUE)
												.getPlayers().contains(p2))
									player.teleport(p2);
								else if (teamHandler.getTeam(TeamList.GREEN)
										.getPlayers().contains(player)
										&& teamHandler.getTeam(TeamList.GREEN)
												.getPlayers().contains(p2))
									player.teleport(p2);
								else if (teamHandler.getTeam(TeamList.YELLOW)
										.getPlayers().contains(player)
										&& teamHandler.getTeam(TeamList.YELLOW)
												.getPlayers().contains(p2))
									player.teleport(p2);
								else
									player.sendMessage(ColorUtil.formatString(
											"<red>%s is not on your team!",
											p2.getDisplayName()));
							} else
								player.sendMessage(ColorUtil
										.formatColors("<red>Teleporting is disabled!"));
						}
					} else
						player.sendMessage(ColorUtil
								.formatColors("That player is not online!"));
				} else
					info.getPlayer()
							.sendMessage(
									ColorUtil
											.formatColors("<red>Invalid Arguments! /tp [player]"));
			} else {
				player.sendMessage(ColorUtil
						.formatColors("<red>Much Deny. So Wow."));
			}
		} else {
			if (info.getArgs().length == 0) {
				info.getSender()
						.sendMessage(
								ColorUtil
										.formatColors("<red>Invalid Arguments! /tp <player> <player2>"));
			} else if (info.getArgs().length == 1) {
				info.getSender()
						.sendMessage(
								ColorUtil
										.formatColors("<red>Invalid Arguments! /tp <player> <player2>"));
			} else if (info.getArgs().length == 2) {
				try {
					@SuppressWarnings("deprecation")
					Player playerFrom = Bukkit.getPlayer(info.getArgs()[0]);
					@SuppressWarnings("deprecation")
					Player playerTo = Bukkit.getPlayer(info.getArgs()[1]);

					playerFrom.teleport(playerTo.getLocation(),
							TeleportCause.COMMAND);

					info.getSender().sendMessage(
							ColorUtil.formatString(
									"<gray>You teleported %s to %s!",
									info.getArgs()[0], info.getArgs()[1]));
				} catch (NullPointerException e) {
					info.getSender().sendMessage(
							ColorUtil.formatColors("<red>Invalid Player(s)!"));
				}
			} else {
				info.getSender().sendMessage(
						ColorUtil.formatColors("<red>Too many arguments!"));
			}
		}
	}
}
