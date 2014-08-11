/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.handlers.GameHandler;
import com.jkush321.autowalls.handlers.TeamHandler;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:20:19 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: PlayingCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class PlayingCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();
	private TeamHandler teamHandler = plugin.getTeamHandler();

	@Command(command = "playing", permission = "walls.playing", aliases = {
			"who", "list" })
	public void playing(CommandArgs info) {
		if (!handler.isGameInProgress()) {
			info.getSender().sendMessage(
					ColorUtil.formatColors("<red>Game is not in progress!"));
			return;
		}
		info.getSender().sendMessage(
				ColorUtil.formatString(
						"<gray>There are <aqua>%d <gray>players playing:",
						handler.playing.size()));
		for (int i = 0; i < teamHandler.teamList.size(); i++) {
			String s = ColorUtil.formatString("%s%s: <gray>",
					teamHandler.teamList.get(i).getColor(),
					teamHandler.teamList.get(i).getName());
			for (Player p : teamHandler.teamList.get(i).getPlayers()) {
				s += p.getDisplayName() + ChatColor.GRAY + ", ";
			}
			info.getSender().sendMessage(s.substring(0, s.length() - 4) + ".");
		}
	}
}
