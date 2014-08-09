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
import com.jkush321.autowalls.handlers.GameHandler;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:20:44 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TimeCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class TimeCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();

	@Command(command = "time", permission = "walls.time", aliases = { "timetillfall, timeleft" })
	public void time(CommandArgs info) {
		int minutes = 0;
		int seconds = 0;
		if (!handler.isGameInProgress()) {
			info.getSender().sendMessage(
					ColorUtil.formatColors("<red>The game hasn't started!"));
			return;
		}
		minutes = plugin.getWallDropTimer().time / 60;
		seconds = plugin.getWallDropTimer().time % 60;

		if (minutes == 0 && seconds == 0) {
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatColors("<gold>The Walls Have Already Fallen!"));
			return;
		}
		info.getSender()
				.sendMessage(
						ColorUtil
								.formatString(
										"<gold>The Walls will fall in %02d minutes and %02d seconds!",
										minutes, seconds));
	}
}
