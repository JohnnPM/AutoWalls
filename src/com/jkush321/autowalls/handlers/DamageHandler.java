/*
 * Author: 598Johnn897
 * 
 * Date: Aug 4, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.team.TeamList;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Aug 4, 2014 <br>
 * Time: 8:44:36 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: DamageHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class DamageHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();
	private TeamHandler teamHandler = plugin.getTeamHandler();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;

		// no spectators hitting animals
		if (!(e.getEntity() instanceof Player)) {
			if (e.getDamager() instanceof Player) {
				if (!handler.playing.contains((Player) e.getDamager()))
					e.setCancelled(true);
				return;
			}
		}

		// no arrows shot at spectators
		if (e.getEntity() instanceof Player) {
			if (!handler.playing.contains((Player) e.getEntity())
					&& e.getDamager().getType().equals(EntityType.ARROW)) {
				e.setCancelled(true);
				return;
			}
		}

		if (e.getDamager().getType().equals(EntityType.ARROW)
				&& e.getEntity() instanceof Player) {
			if (handler.playing.contains((Player) e.getEntity())) {
				Arrow arrow = (Arrow) e.getDamager();
				if (arrow.getShooter() instanceof Player) {
					Player d = (Player) arrow.getShooter();
					if ((teamHandler.getTeam(TeamList.RED).getPlayers()
							.contains((Player) e.getEntity()) && teamHandler
							.getTeam(TeamList.RED).getPlayers().contains(d))
							|| (teamHandler.getTeam(TeamList.BLUE).getPlayers()
									.contains((Player) e.getEntity()) && teamHandler
									.getTeam(TeamList.BLUE).getPlayers()
									.contains(d))
							|| (teamHandler.getTeam(TeamList.GREEN)
									.getPlayers()
									.contains((Player) e.getEntity()) && teamHandler
									.getTeam(TeamList.GREEN).getPlayers()
									.contains(d))
							|| (teamHandler.getTeam(TeamList.YELLOW)
									.getPlayers()
									.contains((Player) e.getEntity()) && teamHandler
									.getTeam(TeamList.YELLOW).getPlayers()
									.contains(d))) {
						d.sendMessage(ColorUtil
								.formatColors("<red>Don't team kill!"));
						e.setCancelled(true);
						return;
					}
				}
			}
		}
		if (!(e.getDamager() instanceof Player))
			return;
		if (!(e.getEntity() instanceof Player))
			return;

		Player p = (Player) e.getEntity();
		Player damager = (Player) e.getDamager();

		plugin.getEventsHandler().setLastEventToNow(p);

		if (!handler.playing.contains(p) && handler.playing.contains(damager)) {
			damager.sendMessage(ChatColor.RED
					+ "There is a spectator there, don't hurt it");
			e.setCancelled(true);
			return;
		}
		if (!handler.playing.contains(damager) && handler.playing.contains(p)) {
			e.setCancelled(true);
			damager.sendMessage(ChatColor.RED + "You Are Not In This Fight!");
			return;
		}

		if (!handler.playing.contains(p) && !handler.playing.contains(damager)) {
			e.setCancelled(true);
		}

		if ((teamHandler.getTeam(TeamList.RED).getPlayers()
				.contains((Player) e.getEntity()) && teamHandler
				.getTeam(TeamList.RED).getPlayers().contains(damager))
				|| (teamHandler.getTeam(TeamList.BLUE).getPlayers()
						.contains((Player) e.getEntity()) && teamHandler
						.getTeam(TeamList.BLUE).getPlayers().contains(damager))
				|| (teamHandler.getTeam(TeamList.GREEN).getPlayers()
						.contains((Player) e.getEntity()) && teamHandler
						.getTeam(TeamList.GREEN).getPlayers().contains(damager))
				|| (teamHandler.getTeam(TeamList.YELLOW).getPlayers()
						.contains((Player) e.getEntity()) && teamHandler
						.getTeam(TeamList.YELLOW).getPlayers()
						.contains(damager))) {
			damager.sendMessage(ColorUtil.formatColors("<red>Don't team kill!"));
			e.setCancelled(true);
			return;
		}
		if (plugin.getWallDropTimer().time > 0 && handler.playing.contains(p)
				&& handler.playing.contains(damager)) {
			damager.sendMessage(ColorUtil.formatString(
					"<red>The walls haven't fallen! Why are you hitting %s?",
					p.getDisplayName()));
			e.setCancelled(true);
			return;
		}
		if (e.getDamager().getType().equals(EntityType.ARROW)
				&& e.getEntity() instanceof Player
				&& plugin.getWallDropTimer().time > 0) {
			if (handler.playing.contains((Player) e.getEntity())) {
				Arrow arrow = (Arrow) e.getDamager();
				if (arrow.getShooter() instanceof Player) {
					Player d = (Player) arrow.getShooter();
					d.sendMessage(ColorUtil
							.formatString(
									"<red>The Walls haven't fallen yet! Why are you shooting %s?",
									((Player) e.getEntity()).getDisplayName()));
				}
			}
		}
	}
}
