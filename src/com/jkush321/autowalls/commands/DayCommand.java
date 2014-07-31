/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import org.bukkit.Bukkit;

import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:25:21 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: DayCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class DayCommand implements CommandListener {

	@Command(command = "day", permission = "walls.time.day", aliases = { "sunrise" })
	public void day(CommandArgs info) {
		if (info.getArgs().length == 0) {
			if (info.isPlayer())
				info.getPlayer().getWorld().setTime(0);
			else
				info.getSender().sendMessage(
						ColorUtil.formatColors("<red>Usage: /day <world>"));
		} else if (info.getArgs().length == 1) {
			if (info.isPlayer())
				try {
					Bukkit.getWorld(info.getArgs()[0]).setTime(0);
				} catch (NullPointerException e) {
					info.getPlayer().sendMessage(
							ColorUtil.formatColors("<red>Invalid World!"));
				}
			else
				try {
					Bukkit.getWorld(info.getArgs()[0]).setTime(0);
				} catch (NullPointerException e) {
					info.getPlayer().sendMessage(
							ColorUtil.formatColors("<red>Invalid World!"));
				}
		} else if (info.getArgs().length > 1) {
			info.getSender().sendMessage(
					ColorUtil.formatColors("Too many Arguments!"));
		}
	}
}
