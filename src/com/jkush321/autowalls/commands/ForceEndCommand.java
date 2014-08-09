/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import org.bukkit.Bukkit;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.handlers.GameHandler;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:26:34 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: ForceEndCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class ForceEndCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();

	@Command(command = "forceend", permission = "walls.forceend")
	public void forceEnd(CommandArgs info) {
		if (handler.isGameInProgress()) {
			Bukkit.broadcastMessage(ColorUtil.formatString(
					"%s: <red>Game has been ended by %s!", plugin.getPrefix(),
					info.getSender().getName()));
			handler.endGame(plugin.getTeamHandler().getTeamWithMostPlayers(),
					plugin.getTeamHandler().getTeamWithMostPlayers().getTeam()
							.getPlayers().toString().replaceAll("[", "")
							.replaceAll("]", ""));
		} else {
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatColors("<red>Game is not even in progress!"));
		}
	}
}
