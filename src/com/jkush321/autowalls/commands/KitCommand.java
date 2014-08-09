/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.kit.Kit;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:27:02 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: KitCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class KitCommand implements CommandListener {

	private AutoWalls plugin = AutoWalls.get();

	@Command(command = "kit", permission = "walls.kit")
	public void kit(CommandArgs info) {
		if (info.isPlayer()) {
			if (info.getArgs().length == 1) {
				if (plugin.getJoinTimer().timeLeft > 0
						|| !plugin.getHandler().isGameInProgress()) {
					if (plugin.getKitHandler().findKit(info.getArgs()[0]) != null) {
						Kit k = plugin.getKitHandler().findKit(
								info.getArgs()[0]);
						List<String> kitsUnlocked = null;
						if (plugin.getHandler()
								.getPlayerConfig(info.getPlayer())
								.isSet("player.unlocked.kits"))
							kitsUnlocked = plugin.getHandler()
									.getPlayerConfig(info.getPlayer())
									.getStringList("player.unlocked.kits");
						if (kitsUnlocked.contains(k)) {
							plugin.getKitHandler().setKit(info.getPlayer(), k);
							info.getSender()
									.sendMessage(
											ColorUtil.formatString(
													"%s: <gold>Selected ",
													k.getName()));
						} else {
							info.getSender()
									.sendMessage(
											ColorUtil
													.formatString(
															"%s: <red>You have not unlocked kit %s in the shop yet!",
															plugin.getPrefix(),
															k.getName()));
						}
					} else {
						info.getSender()
								.sendMessage(
										ColorUtil
												.formatColors("<red>That Kit Was Not Found!"));

					}
				} else
					info.getSender()
							.sendMessage(
									ColorUtil
											.formatColors("<red>It is too late to choose a kit!"));
			} else if (info.getArgs().length == 0) {
				List<String> kits = null;
				if (plugin.getHandler().getPlayerConfig(info.getPlayer())
						.isSet("player.unlocked.kits"))
					kits = plugin.getHandler()
							.getPlayerConfig(info.getPlayer())
							.getStringList("player.unlocked.kits");
				String m1UnLocked = (ColorUtil
						.formatColors("<gray>Unlocked Kits: <white>"));
				String m2Locked = (ColorUtil
						.formatColors("<gray>Locked Kits: <white>"));
				List<String> avaliable = new CopyOnWriteArrayList<String>();
				List<String> locked = new CopyOnWriteArrayList<String>();

				for (int i = 0; i < plugin.getKitHandler().kitList.size(); i++) {
					if (kits.contains(plugin.getKitHandler().kitList.get(i)
							.getName())
							|| plugin.getKitHandler().kitList.get(i)
									.isDefault()) {
						avaliable.add(plugin.getKitHandler().kitList.get(i)
								.getName());
						m1UnLocked += plugin.getKitHandler().kitList.get(i)
								.getName() + ", ";
					} else {
						locked.add(plugin.getKitHandler().kitList.get(i)
								.getName());
						m2Locked += plugin.getKitHandler().kitList.get(i)
								.getName() + ", ";
					}
				}

				m1UnLocked = m1UnLocked.substring(0, m1UnLocked.length() - 2)
						+ ".";
				m2Locked = m2Locked.substring(0, m2Locked.length() - 2) + ".";
				info.getSender().sendMessage(m1UnLocked);
				info.getSender().sendMessage(m2Locked);
			} else {
				info.getSender().sendMessage(
						ColorUtil.formatColors("<red>Usage: /kit (name)"));
			}
		} else {
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatColors("<red>Console cannot choose a kit!"));
		}
	}
}
