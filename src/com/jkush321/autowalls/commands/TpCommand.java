/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
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

	@Command(command = "tp", permission = "walls.tp", aliases = { "teleport" })
	public void tp(CommandArgs info) {
		if (info.isPlayer()) {
			Player player = info.getPlayer();
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
					info.getSender().sendMessage(
							ColorUtil.formatColors("<red>Invalid Player(s)!"));
				}
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
