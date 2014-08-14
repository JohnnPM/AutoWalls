/*
 * Author: 598Johnn897
 * 
 * Date: Aug 4, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.util.ColorUtil;
import com.jkush321.autowalls.util.StringUtil;

/**
 * Created: Aug 4, 2014 <br>
 * Time: 9:02:03 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: GraveHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class GraveHandler implements Listener {

	private AutoWalls plugin = AutoWalls.get();

	public List<String> graveMessages = new CopyOnWriteArrayList<String>();
	public HashMap<Sign, String> graveToPlayer = new HashMap<Sign, String>();
	public List<Sign> graves = new CopyOnWriteArrayList<Sign>();

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (plugin.getHandler().playing.contains((Player) event.getEntity())) {
			if (plugin.getHandler().isGameInProgress())
				createGrave(event.getEntity().getLocation(),
						((Player) event.getEntity()).getDisplayName());
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock() instanceof Sign) {
			if (graves.contains((Sign) e.getBlock())) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						ColorUtil.formatString(
								"<red>Disrespectful! Don't break %s's grave!",
								graveToPlayer.get((Sign) e.getBlock()
										.getState())));
			}
		}
	}

	@SuppressWarnings("deprecation")
	public Sign createGrave(Location l, String playername) {
		Random r = new Random();
		l.getBlock().setType(Material.SIGN_POST);
		l.getBlock().setData((byte) r.nextInt(16));
		Sign s = (Sign) l.getBlock().getState();
		s.setLine(0, "R.I.P.");
		s.setLine(1, playername.substring(0, Math.min(playername.length(), 15)));
		int i = r.nextInt(graveMessages.size());
		String message = graveMessages.get(i);
		List<String> messageShortened = StringUtil.wrapWords(
				message.substring(0, Math.min(message.length(), 30)), 15);
		s.setLine(3, messageShortened.get(0));
		s.setLine(4, messageShortened.get(1));
		s.update();
		graves.add(s);
		graveToPlayer.put(s, playername);
		return s;
	}
}
