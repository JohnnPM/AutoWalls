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

import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:24:03 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TellCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class TellCommand implements CommandListener {

	@SuppressWarnings("deprecation")
	@Command(command = "tell", permission = "walls.tell", aliases = { "pm",
			"msg" })
	public void tell(CommandArgs info) {
		if (info.isPlayer()) {
			if (info.getArgs().length == 0)
				info.getPlayer()
						.sendMessage(
								ColorUtil
										.formatColors("<red>Who to send it to? Invalid Arguments."));
			else if (info.getArgs().length == 1)
				info.getPlayer()
						.sendMessage(
								ColorUtil
										.formatColors("<red>What, no message? Supply a message."));
			else if (info.getArgs().length > 1) {
				Player fromPlayer = info.getPlayer();
				String to = info.getArgs()[0];
				Player toPlayer = null;
				String msg = info.getFinalArgs(1);
				try {
					toPlayer = Bukkit.getPlayer(to);
				} catch (NullPointerException e) {
					fromPlayer.sendMessage(ColorUtil.formatString(
							"<red>Player %s is not online!", to));
				}
				fromPlayer
						.sendMessage(ColorUtil.formatString(
								"<aqua>To <gray>%s: %s",
								toPlayer.getDisplayName(), msg));
				toPlayer.sendMessage(ColorUtil.formatString(
						"<aqua>From <gray>%s: %s", fromPlayer.getDisplayName(),
						msg));
			}
		} else {
			if (info.getArgs().length == 0)
				info.getSender()
						.sendMessage(
								ColorUtil
										.formatColors("<red>Who to send it to? Invalid Arguments."));
			else if (info.getArgs().length == 1)
				info.getSender()
						.sendMessage(
								ColorUtil
										.formatColors("<red>What, no message? Supply a message."));
			else if (info.getArgs().length > 1) {
				String to = info.getArgs()[0];
				Player toPlayer = null;
				String msg = info.getFinalArgs(1);
				try {
					toPlayer = Bukkit.getPlayer(to);
				} catch (NullPointerException e) {
					info.getSender().sendMessage(ColorUtil.formatString(
							"<red>Player %s is not online!", to));
				}
				info.getSender()
						.sendMessage(ColorUtil.formatString(
								"<aqua>To <gray>%s: %s",
								toPlayer.getDisplayName(), msg));
				toPlayer.sendMessage(ColorUtil.formatString(
						"<aqua>From <gray>%s: %s", "Console",
						msg));
			}
		}
	}
}
