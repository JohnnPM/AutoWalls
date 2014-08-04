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
 * Time: 10:23:11 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TpAllCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class TpAllCommand implements CommandListener {

	@Command(command = "tpall", permission = "walls.tpall", aliases = { "teleportall" })
	public void tpAll(CommandArgs info) {
		if (info.getArgs().length == 0)
			info.getSender().sendMessage(
					ColorUtil.formatColors("<red>Invalid Arguments!"));
		else if (info.getArgs().length == 1) {
			try {
				@SuppressWarnings("deprecation")
				Player playerTo = Bukkit.getPlayer(info.getArgs()[0]);
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (info.isPlayer()) {
						if (p != (Player) info) {
							p.teleport(playerTo.getLocation());
							p.sendMessage(ColorUtil.formatString(
									"<green>You have been teleported to %s",
									playerTo.getDisplayName()));
						}
					} else {
						p.teleport(playerTo.getLocation());
						p.sendMessage(ColorUtil.formatString(
								"<green>You have been teleported to %s",
								playerTo.getDisplayName()));
					}
				}
			} catch (NullPointerException e) {
				info.getSender().sendMessage(
						ColorUtil.formatColors("<red>Invalid Player!"));
			}
		} else {
			info.getSender().sendMessage(
					ColorUtil.formatColors("<red>Too Many Arguments!"));
		}
	}
}
