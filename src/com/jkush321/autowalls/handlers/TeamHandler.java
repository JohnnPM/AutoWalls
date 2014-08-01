/*
 * Author: 598Johnn897
 * 
 * Date: Jul 30, 2014
 * Package: com.jkush321.autowalls.team
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.ClassEnumerator;
import com.jkush321.autowalls.team.Team;

/**
 * Created: Jul 30, 2014 <br>
 * Time: 9:29:56 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TeamHandler.java <br>
 * Package: com.jkush321.autowalls.team
 * <p>
 * 
 * @author 598Johnn897
 */
public class TeamHandler {

	private AutoWalls plugin;
	public TeamHandler(AutoWalls autoWalls) {
		plugin = autoWalls;
	}

	public List<Team> teams = new ArrayList<Team>();

	private void registerTeam(Team team) {
		teams.add(team);
	}

	public void unregisterTeam(Team team) {
		teams.remove(team);
	}
	
	public void registerTeams() {
		Class<?>[] classes = ClassEnumerator.getInstance()
				.getClassesFromThisJar(plugin);
		if (classes == null || classes.length == 0) {
			return;
		}
		plugin.getAWLogger().log(Level.INFO, "Starting registration of teams:");
		for (Class<?> c : classes) {
			try {
				if (Team.class.isAssignableFrom(c) && !c.isInterface()
						&& !c.isEnum() && !c.isAnnotation()) {
					plugin.getLogger().log(Level.INFO,
							"Searching class: " + c.getSimpleName());
					registerTeam((Team) c.newInstance());
				}
			} catch (IllegalAccessException | InstantiationException e) {
				plugin.getLogger().log(
						Level.INFO,
						c.getSimpleName()
								+ " does not use the default constructor");
				e.printStackTrace();
			}
		}
		plugin.getAWLogger().log(Level.INFO, "Ended registration of teams.");
	}

}
