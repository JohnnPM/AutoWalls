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
 * Time: 10:23:31 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TpHereCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class TpHereCommand implements CommandListener {

	@Command(command = "tphere", permission = "walls.tphere")
	public void tpHere(CommandArgs info) {
		if (info.isPlayer()) {
			if (info.getArgs().length != 1) {
				info.getPlayer().sendMessage(
						ColorUtil.formatColors("<red>Invalid Arguments!"));
			}
			@SuppressWarnings("deprecation")
			Player pl = Bukkit.getPlayer(info.getArgs()[0]);
			if (pl != null && pl.isOnline()) {
				pl.teleport(info.getPlayer());
			} else
				info.getPlayer().sendMessage(
						ColorUtil.formatColors("<red>Player is not online!"));
		} else {
			info.getSender().sendMessage(
					ColorUtil.formatColors("<red>Player only command!"));
		}
	}
}
