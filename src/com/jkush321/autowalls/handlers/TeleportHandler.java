/*
 * AutoWalls by jkush321 is licensed under the
 * Creative Commons Attribution-NonCommercial 3.0 Unported License
 * 
 * You are fully allowed to modify the source code for your own network
 * of servers, but you may not distribute the modified code outside of
 * your servers.
 * 
 * AutoWalls was originally a personal project that was standalone for
 * my own private server, and it slowly accumulated into a giant plugin.
 * 
 * AutoWalls is for dedicated servers that are willing to run just Walls.
 * 
 * The license requires attribution and you have to give credit to jkush321
 * no matter how many changes were made to the code. In some clearly stated
 * way everyone who goes on the server must be able to easily see and be aware
 * of the fact that this code originated from jkush321 and was modified by
 * you or your team.
 * 
 * For more information visit http://bit.ly/AutoWalls
 * 
 */

package com.jkush321.autowalls.handlers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.util.ColorUtil;

public class TeleportHandler {
	private AutoWalls plugin = AutoWalls.get();

	private Map<Player, Runnable> runnables = new HashMap<>();
	private Map<Runnable, Player> runnableOwnership = new HashMap<>();
	private Map<Player, Player> tpTargets = new HashMap<>();

	public Runnable getRunnable(Player p) {
		if (runnables.containsKey(p))
			return runnables.get(p);
		return null;
	}

	public Player getPlayer(Runnable r) {
		if (runnableOwnership.containsKey(r))
			return runnableOwnership.get(r);
		return null;
	}

	public Player getTarget(Player p) {
		if (tpTargets.containsKey(p)) {
			return tpTargets.get(p);
		}
		return null;
	}

	public void createTpRunnable(Player p, Player target) {
		Runnable runnable = new Runnable() {
			public void run() {
				if (plugin.getEventsHandler().getTicksFromLastEvent(
						plugin.getTeleportHandler().getPlayer(this)) * 20 >= plugin
						.getAWConfig().getint(
								"AutoWalls Settings.teleportDelay")) {
					plugin.getTeleportHandler()
							.getPlayer(this)
							.sendMessage(
									ColorUtil
											.formatString(
													"<green>You are being teleported to %s!",
													plugin.getTeleportHandler()
															.getTarget(
																	plugin.getTeleportHandler()
																			.getPlayer(
																					this))
															.getDisplayName()));
					plugin.getTeleportHandler()
							.getPlayer(this)
							.teleport(
									plugin.getTeleportHandler().getTarget(
											plugin.getTeleportHandler()
													.getPlayer(this)));
				} else {
					plugin.getTeleportHandler()
							.getPlayer(this)
							.sendMessage(
									ColorUtil
											.formatColors("<red>Your previous teleport was canceled!"));
				}
			};
		};
		Bukkit.getScheduler()
				.scheduleSyncDelayedTask(
						AutoWalls.get(),
						runnable,
						plugin.getAWConfig().getint(
								"AutoWalls Settings.teleportDelay") * 20);
		runnableOwnership.put(runnable, p);
		if (runnables.containsKey(p))
			runnables.remove(p);
		runnables.put(p, runnable);
		if (tpTargets.containsKey(p))
			tpTargets.remove(p);
		tpTargets.put(p, target);
	}
}
