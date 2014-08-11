/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import org.bukkit.entity.Player;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:25:52 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: YellCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class YellCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();

	@Command(command = "yell", permission = "walls.yell")
	public void yell(CommandArgs info) {
		if (info.isPlayer()) {
			Player player = info.getPlayer();
			if (plugin.getHandler().getPlayerConfig(player)
					.isSet("player.unlocked.perks")
					&& plugin.getHandler().getPlayerConfig(player)
							.getStringList("player.unlocked.perks")
							.contains("Yell")) {
				if (info.getArgs().length == 0) {
					player.sendMessage(ColorUtil
							.formatColors("<red>Usage: /yell (message...)"));
				} else {
					String message = info.getFinalArgs(0).trim();
					plugin.getChatHandler().yell(player, message);
				}
			} else {
				player.sendMessage(ColorUtil
						.formatString(
								"%s: <red>You have not unlocked this perk in the shop yet!",
								plugin.getPrefix()));
			}
		} else {
			info.getSender().sendMessage(
					ColorUtil.formatColors("<red>Player only command!"));
		}
	}
}
