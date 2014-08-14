/*
 * Author: 598Johnn897
 * 
 * Date: Aug 4, 2014
 * Package: com.jkush321.autowalls.timers
 *
 */
package com.jkush321.autowalls.timers;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Aug 4, 2014 <br>
 * Time: 4:00:36 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: JoinTimer.java <br>
 * Package: com.jkush321.autowalls.timers
 * <p>
 * 
 * @author 598Johnn897
 */
public class JoinTimer extends BukkitRunnable {

	private AutoWalls plugin = AutoWalls.get();

	public int timeLeft;

	@Override
	public void run() {
		try {
			timeLeft--;

			if ((timeLeft >= 10 && timeLeft % 10 == 0)
					|| (timeLeft <= 5 && timeLeft > 0)) {
				Bukkit.broadcastMessage(ColorUtil.formatString(
						"%s: <gray>You can join in <aqua>%d <gray>seconds!",
						plugin.getPrefix(), timeLeft));
			} else if (timeLeft == 0) {
				Bukkit.broadcastMessage(ColorUtil
						.formatString(
								"%s: <white>You can now <aqua>join<white>! <dark_gray>(<gray>/join<dark_gray>)",
								plugin.getPrefix()));
				plugin.getHandler().canJoin = true;
			}
		} catch (Exception e) {
			plugin.getAWLogger().log(Level.SEVERE, "Join timer error:");
			e.printStackTrace();
			this.cancel();
		}
	}

}
