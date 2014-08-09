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
 * Time: 10:25:02 AM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: ForceStartCommand.java <br>
 * Package: com.jkush321.autowalls.commands <p>
 * 
 * @author 598Johnn897
 */
public class ForceStartCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();

	@Command(command = "forcestart", permission = "walls.forcedrop")
	public void forceStart(CommandArgs info) {
		if (!(handler.playing.size() >= 2) || handler.isGameInProgress()
				|| !(plugin.getJoinTimer().timeLeft <= 0)) {
			info.getSender().sendMessage(
					ColorUtil.formatString(
							"%s: <red>Cannot force start game at this time!",
							plugin.getPrefix()));
		} else {
			handler.startGame();
			Bukkit.broadcastMessage(ColorUtil.formatString(
					"%s: <red>Game Force Started By %s!", plugin.getPrefix(),
					info.getSender().getName()));
		}
	}
}
