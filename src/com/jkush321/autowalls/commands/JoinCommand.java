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
import com.jkush321.autowalls.handlers.GameHandler;
import com.jkush321.autowalls.handlers.TeamHandler;
import com.jkush321.autowalls.team.TeamList;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:19:26 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: JoinCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class JoinCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();
	private TeamHandler teamHandler = plugin.getTeamHandler();

	@Command(command = "join", aliases = { "team" }, permission = "walls.player")
	public void join(CommandArgs info) {
		if (info.isPlayer()) {
			Player p = info.getPlayer();
			if (handler.isGameInProgress()) {
				p.sendMessage(ColorUtil
						.formatColors("<red>You cannot join a game at this time!"));
				return;
			}
			if (info.getArgs().length == 0) {
				if (teamHandler.getTeam(TeamList.RED).getPlayers().size() < teamHandler.maxTeamSize)
					handler.joinTeam(p, TeamList.RED);
				else if (teamHandler.getTeam(TeamList.BLUE).getPlayers().size() < teamHandler.maxTeamSize)
					handler.joinTeam(p, TeamList.BLUE);
				else if (teamHandler.getTeam(TeamList.GREEN).getPlayers()
						.size() < teamHandler.maxTeamSize)
					handler.joinTeam(p, TeamList.GREEN);
				else if (teamHandler.getTeam(TeamList.YELLOW).getPlayers()
						.size() < teamHandler.maxTeamSize)
					handler.joinTeam(p, TeamList.YELLOW);
				else
					p.sendMessage(ColorUtil
							.formatColors("<red>Every team is full!"));
			} else if (info.getArgs().length == 1) {
				switch (info.getArgs()[0].toUpperCase()) {
				case "RED":
					handler.joinTeam(p, TeamList.RED);
				case "BLUE":
					handler.joinTeam(p, TeamList.BLUE);
				case "GREEN":
					handler.joinTeam(p, TeamList.GREEN);
				case "YELLOW":
					handler.joinTeam(p, TeamList.YELLOW);
				case "1":
					handler.joinTeam(p, TeamList.RED);
				case "2":
					handler.joinTeam(p, TeamList.BLUE);
				case "3":
					handler.joinTeam(p, TeamList.GREEN);
				case "4":
					handler.joinTeam(p, TeamList.YELLOW);
				default:
					p.sendMessage(ColorUtil.formatString(
							"<red>Team \"%s\" is not a valid team!",
							info.getArgs()[0]));
				}

			} else
				p.sendMessage(ColorUtil
						.formatColors("<red>Too many arguments! /join [red, blue, green, yellow]"));
		} else
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatColors("<red>Go home console, your drunk."));
	}
}
