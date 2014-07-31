/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:24:28 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: MeCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class MeCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();

	@Command(command = "me")
	public void me(CommandArgs info) {
		if (info.isPlayer()) {
			if (info.getArgs().length == 0)
				info.getPlayer().sendMessage(
						ColorUtil.formatColors("<red>Usage... /me <message>"));
			else if (info.getArgs().length > 0) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.sendMessage(ColorUtil.formatString("<gray>* %s %s", info
							.getPlayer().getDisplayName(), info.getFinalArgs(0)));
				}
				plugin.getAWLogger().log(
						Level.INFO,
						String.format("* %s %s", info.getPlayer()
								.getDisplayName(), info.getFinalArgs(0)));
			}
		} else {
			if (info.getArgs().length == 0)
				info.getSender().sendMessage(
						ColorUtil.formatColors("<red>Usage... /me <message>"));
			else if (info.getArgs().length > 0) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.sendMessage(ColorUtil.formatString("<gray>* %s %s",
							"Console", info.getFinalArgs(0)));
				}
				plugin.getAWLogger().log(
						Level.INFO,
						String.format("* %s %s", "Console",
								info.getFinalArgs(0)));
			}
		}
	}
}
