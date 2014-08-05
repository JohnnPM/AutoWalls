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

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.handlers.GameHandler;
import com.jkush321.autowalls.handlers.TeamHandler;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:20:02 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: LeaveCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class LeaveCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();
	private TeamHandler teamHandler = plugin.getTeamHandler();

	@Command(command = "leave", permission = "walls.leave", aliases = { "quit" })
	public void leave(CommandArgs info) {
		if (info.isPlayer()) {
			if (info.getArgs().length == 0) {
				Player player = info.getPlayer();
				if (handler.playing.contains(player)) {
					teamHandler.removePlayerFromTeam(player);
					Bukkit.broadcastMessage(ColorUtil.formatString(
							"%s <red>has left the game!", plugin.getPrefix()));
				} else {
					player.sendMessage(ColorUtil
							.formatColors("<red>You aren't on a team!"));
				}
			} else {
				info.getPlayer().sendMessage(
						ColorUtil.formatColors("<red>Too many arguments!"));
			}
		} else {
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatColors("<red>Go home console. Your drunk."));
		}
	}

}
