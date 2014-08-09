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
import com.jkush321.autowalls.handlers.GameHandler;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:23:49 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TeamChatCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class TeamChatCommand {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();

	@Command(command = "tc", permission = "walls.teamchat", aliases = { "teamchat" })
	public void teamChat(CommandArgs info) {
		if (info.isPlayer()) {
			Player player = info.getPlayer();
			if (info.getArgs().length == 0) {
				if (!handler.playing.contains(player)) {
					player.sendMessage(ColorUtil
							.formatColors("<red>You must be on a team to teamchat!"));
				}
				if (!plugin.getChatHandler().teamChatting.contains(player)) {
					plugin.getChatHandler().teamChatting.add(player);
					player.sendMessage(ColorUtil
							.formatColors("<gold>You are now team chatting!"));
				}
				if (plugin.getChatHandler().teamChatting.contains(player)) {
					plugin.getChatHandler().teamChatting.remove(player);
					player.sendMessage(ColorUtil
							.formatColors("<red>You have disabled team chatting!"));
				}
			} else if (info.getArgs().length > 0) {
				String message = info.getFinalArgs(0);
				plugin.getChatHandler().chatTeam(player, message,
						plugin.getTeamHandler().getPlayerTeam(player));
			}
		} else {
			info.getSender().sendMessage(
					ColorUtil.formatColors("<red>You cannnot join a team!"));
		}
	}
}
