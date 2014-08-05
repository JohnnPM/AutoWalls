/*
 * Author: 598Johnn897
 * 
 * Date: Aug 4, 2014
 * Package: com.jkush321.autowalls.timers
 *
 */
package com.jkush321.autowalls.timers;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.handlers.GameHandler;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Aug 4, 2014 <br>
 * Time: 3:59:58 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: WallDropTimer.java <br>
 * Package: com.jkush321.autowalls.timers
 * <p>
 * 
 * @author 598Johnn897
 */
public class WallDropTimer implements Runnable {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();

	public int time; // In seconds
	public int timeContinued;

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		while (true) {
			if (handler.isGameInProgress()) {
				time--;
				timeContinued--;

				if (time > 0)
					timeContinued = time;

				if (time == -1)
					time = 0;

				if ((time >= 60 && time % 10 * 60 == 0))
					Bukkit.broadcastMessage(ColorUtil.formatString(
							"%s: The Walls will drop in %d minutes!",
							plugin.getPrefix(),
							TimeUnit.SECONDS.toMinutes(time)));
				else if ((time <= 60 && time % 10 == 0 && time >= 5))
					Bukkit.broadcastMessage(ColorUtil.formatString(
							"%s: The Walls will drop in %d seconds!",
							plugin.getPrefix(), time));
				else if (time == 5 * 60) {
					if (plugin.getAWConfig().getboolean(
							"AutoWalls Settings.heal")) {
						Bukkit.broadcastMessage(ColorUtil
								.formatString("%s: <gray>Everyone has been healed!"));
						for (Player p : handler.playing) {
							p.setHealth(20.0);
							p.setExhaustion(20.0F);
						}
					}
				} else if (time <= 5)
					Bukkit.broadcastMessage(ColorUtil.formatString(
							"%s: The Walls will drop in %d seconds!",
							plugin.getPrefix(), time));
				else if (timeContinued == 0) {
					handler.dropWalls();
					plugin.getWallDropThread().stop();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
