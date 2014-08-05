package com.jkush321.autowalls;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.jkush321.autowalls.handlers.TeamHandler;
import com.jkush321.autowalls.util.ColorUtil;

public class ColorCycler {

	private static AutoWalls plugin = AutoWalls.get();
	private static TeamHandler teamHandler = plugin.getTeamHandler();

	public static int MAX_COLOR_TIME;

	public static Map<Player, ChatColor> fakeColors = new HashMap<>();
	public static Map<Player, Integer> colorTime = new HashMap<>();

	public static ChatColor getFakeColor(Player p) {
		if (fakeColors.containsKey(p))
			return fakeColors.get(p);
		return null;
	}

	public static void setFakeColor(Player p, ChatColor c) {
		if (fakeColors.containsKey(p))
			fakeColors.remove(p);
		fakeColors.put(p, c);
	}

	public static void cycle(Player p) {
		ChatColor nextColor;
		if (getFakeColor(p) == null) {
			if (teamHandler.getPlayerTeam(p).getColor().equals(ChatColor.RED)) {
				nextColor = ChatColor.BLUE;
			} else if (teamHandler.getPlayerTeam(p).getColor()
					.equals(ChatColor.BLUE)) {
				nextColor = ChatColor.GREEN;
			} else if (teamHandler.getPlayerTeam(p).getColor()
					.equals(ChatColor.GREEN)) {
				nextColor = ChatColor.YELLOW;
			} else {
				nextColor = ChatColor.RED;
			}
		} else {
			if (getFakeColor(p) == ChatColor.RED) {
				nextColor = ChatColor.BLUE;
			} else if (getFakeColor(p) == ChatColor.BLUE) {
				nextColor = ChatColor.GREEN;
			} else if (getFakeColor(p) == ChatColor.GREEN) {
				nextColor = ChatColor.YELLOW;
			} else {
				nextColor = ChatColor.RED;
			}
		}
		if ((teamHandler.getPlayerTeam(p).getColor().equals(ChatColor.RED) && nextColor == ChatColor.RED)
				|| (teamHandler.getPlayerTeam(p).getColor()
						.equals(ChatColor.BLUE) && nextColor == ChatColor.BLUE)
				|| (teamHandler.getPlayerTeam(p).getColor()
						.equals(ChatColor.GREEN) && nextColor == ChatColor.GREEN)
				|| (teamHandler.getPlayerTeam(p).getColor()
						.equals(ChatColor.YELLOW) && nextColor == ChatColor.YELLOW)) {
			setFakeColor(p, null);
			p.sendMessage(ColorUtil.formatString(
					"%sYou now have your original nametag color.", teamHandler
							.getPlayerTeam(p).getColor()));
		} else {
			if (nextColor == ChatColor.RED) {
				p.sendMessage(ColorUtil
						.formatColors("<red>Your Nametag Is Now Red!"));
			} else if (nextColor == ChatColor.BLUE) {
				p.sendMessage(ColorUtil
						.formatColors("<blue>Your Nametag Is Now Red!"));
			} else if (nextColor == ChatColor.GREEN) {
				p.sendMessage(ColorUtil
						.formatColors("<green>Your Nametag Is Now Red!"));
			} else {
				p.sendMessage(ColorUtil
						.formatColors("<yellow>Your Nametag Is Now Red!"));
			}
			setFakeColor(p, nextColor);
		}
	}

	public static void tick() {
		if (fakeColors.size() > 0) {
			Set<Player> copiedSet = new HashSet<Player>();
			copiedSet.addAll(fakeColors.keySet());

			for (Player p : copiedSet) {
				if (fakeColors.get(p) != null) {
					if (colorTime.containsKey(p)) {
						int time;
						if ((time = colorTime.get(p)) > 0) {
							time = time - 1;
							colorTime.remove(p);
							colorTime.put(p, time);
						} else {
							fakeColors.remove(p);
							p.sendMessage(ColorUtil
									.formatColors("<red>Your Nameplate Color Has Worn Off!"));
						}
					} else {
						colorTime.put(p, MAX_COLOR_TIME);
					}
				}
			}
		}
	}

}
