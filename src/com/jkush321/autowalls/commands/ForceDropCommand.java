/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:26:18 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: ForceDropCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class ForceDropCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();

	@Command(command = "forcedrop", permission = "walls.forcedrop")
	public void forceDrop(CommandArgs info) {
		if (plugin.getWallDropTimer().time <= 5) {
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatString("%s: <red>The Walls have already fallen!"));
		} else if (plugin.getHandler().isGameInProgress()) {
			plugin.getWallDropTimer().time = 5;
		} else if (!plugin.getHandler().isGameInProgress()) {
			info.getSender().sendMessage(
					ColorUtil.formatColors("<red>Game is not in progress!"));
		}
	}
}
