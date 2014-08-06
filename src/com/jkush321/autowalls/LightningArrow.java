/*
 * Author: 598Johnn897
 * 
 * Date: Aug 5, 2014
 * Package: com.jkush321.autowalls
 *
 */
package com.jkush321.autowalls;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Aug 5, 2014 <br>
 * Time: 6:50:32 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: LightningArrow.java <br>
 * Package: com.jkush321.autowalls
 * <p>
 * 
 * @author 598Johnn897
 */
public class LightningArrow implements Listener {

	private AutoWalls plugin = AutoWalls.get();

	@EventHandler
	public void onProjectileLand(ProjectileHitEvent e) {
		if (e.getEntityType() == EntityType.ARROW
				&& plugin.getAWConfig().getboolean(
						"AutoWalls Settings.lightingArrow")) {
			if (e.getEntity().getShooter() != null) {
				if (e.getEntity().getShooter() instanceof Player) {
					Player shooter = (Player) e.getEntity().getShooter();
					if (plugin.getHandler().playing.contains(shooter)) {
						if (plugin.getWallDropTimer().time <= 0) {
							Random r = new Random();
							int rand = r.nextInt(plugin.getAWConfig().getint(
									"AutoWalls Settings.lightingArrowChance"));
							if (rand == 0) {
								Bukkit.broadcastMessage(ColorUtil
										.formatString(
												"<aqua><bold>%s Has Shot A Rare Lightning Arrow!",
												shooter.getName()));
								e.getEntity()
										.getWorld()
										.strikeLightning(
												e.getEntity().getLocation());
							}
						}
					}
				}
			}
		}
	}
}
