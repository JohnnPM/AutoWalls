/*
 * Author: 598Johnn897
 * 
 * Date: Aug 5, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Aug 5, 2014 <br>
 * Time: 6:42:42 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: DeathHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class DeathHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();
	private TeamHandler teamHandler = plugin.getTeamHandler();

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		try {
			if (!handler.playing.contains(e.getEntity())) {
				e.setDeathMessage(null);
				if (e.getEntity().getInventory().getSize() > 0) {
					while (e.getDrops().size() > 0)
						e.getDrops().remove(0);
				}
				return;
			}
			if (handler.isGameInProgress()
					&& handler.playing.contains(e.getEntity())) {
				teamHandler.removePlayerFromTeam(e.getEntity());
				if (plugin.getChatHandler().teamChatting
						.contains(e.getEntity()))
					plugin.getChatHandler().teamChatting.remove(e.getEntity());
				if (handler.playing.size() > 1)
					e.setDeathMessage(ColorUtil
							.formatString(
									"%s%s <gray>%s! <blue>%d Players Remain!",
									teamHandler.getPlayerTeam(e.getEntity())
											.getColor(),
									e.getEntity().getName(),
									e.getDeathMessage()
											.replace(e.getEntity().getName(),
													"").trim(), handler.playing
											.size()));
				handler.checkStats();
				handler.addDeadPlayer(e.getEntity());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if (handler.isGameInProgress())
			handler.spectate(e.getPlayer());
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();	}
}
