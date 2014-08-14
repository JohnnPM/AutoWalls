/*
 * Author: 598Johnn897
 * 
 * Date: Aug 11, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.kit.Kit;
import com.jkush321.autowalls.kit.KitClass;
import com.jkush321.autowalls.menu.GUIButton;
import com.jkush321.autowalls.menu.GUIMenu;
import com.jkush321.autowalls.util.ColorUtil;
import com.jkush321.autowalls.util.StringUtil;

/**
 * Created: Aug 11, 2014 <br>
 * Time: 5:59:03 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: KitMenuCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * <b> Temp: Test Class </b>
 * 
 * @author 598Johnn897
 */
public class KitMenuCommand implements CommandListener {

	public GUIMenu kitMenu;

	private AutoWalls plugin = AutoWalls.get();

	@Command(command = "kitmenu")
	public void kitMenu(CommandArgs info) {
		if (!info.isPlayer())
			return;
		List<String> kits = null;
		if (plugin.getHandler().getPlayerConfig(info.getPlayer())
				.isSet("player.unlocked.kits"))
			kits = plugin.getHandler().getPlayerConfig(info.getPlayer())
					.getStringList("player.unlocked.kits");

		List<String> avaliable = new CopyOnWriteArrayList<String>();
		List<String> locked = new CopyOnWriteArrayList<String>();

		for (int i = 0; i < plugin.getKitHandler().kitList.size(); i++) {
			Validate.notNull(kits);
			if (kits.contains(plugin.getKitHandler().kitList.get(i).getName())
					|| plugin.getKitHandler().kitList.get(i).isDefault()) {
				avaliable.add(plugin.getKitHandler().kitList.get(i).getName());
			} else {
				locked.add(plugin.getKitHandler().kitList.get(i).getName());
			}
		}
		KitClass[] kitClasses = KitClass.values();
		kitMenu = GUIMenu.createMenu(plugin, "Select your kit...",
				(int) Math.ceil(kitClasses.length / 9.0) * 9, info.getPlayer());
		for (int i = 0; i < kitClasses.length; i++) {
			final KitClass kitClass = kitClasses[i];
			String name = "";
			List<String> desc = StringUtil.wrapWords(kitClass.getDescription(),
					40);
			if (avaliable.contains(ChatColor.stripColor(kitClass
					.getDisplayName()))) {
				name = ColorUtil.formatString("<green>%s",
						ChatColor.stripColor(kitClass.getDisplayName()));
				desc.add(ChatColor.GREEN.toString());
				desc.add(ColorUtil.formatColors("<green>UNLOCKED"));
			} else if (locked.contains(ChatColor.stripColor(kitClass
					.getDisplayName()))) {
				name = ColorUtil.formatString("<red>%s",
						ChatColor.stripColor(kitClass.getDisplayName()));
				desc.add(ChatColor.RED.toString());
				desc.add(ColorUtil.formatString("<gray>Price: <gold>%d",
						kitClass.getCoinsToBuy()));
			} else {
				name = ColorUtil.formatColors(kitClass.getDisplayName());
				desc = StringUtil.wrapWords(
						ColorUtil.formatColors(kitClass.getDescription()), 40);
			}
			Validate.notNull(name);
			Validate.notNull(desc);
			Validate.notNull(kitClass.getIcon());
			kitMenu.setOption(i, new ItemStack(kitClass.getIcon()), name, desc,
					new GUIButton() {

						@Override
						public void onClick(Player player, String displayName) {
							if (plugin.getJoinTimer().timeLeft > 0
									|| !plugin.getHandler().isGameInProgress()) {
								if (plugin.getKitHandler().findKit(
										ChatColor.stripColor(displayName)) != null) {
									Kit k = plugin.getKitHandler().findKit(
											ChatColor.stripColor(displayName));
									List<String> kitsUnlocked = null;
									if (plugin.getHandler()
											.getPlayerConfig(player)
											.isSet("player.unlocked.kits"))
										kitsUnlocked = plugin
												.getHandler()
												.getPlayerConfig(player)
												.getStringList(
														"player.unlocked.kits");
									if (kitsUnlocked.contains(k.getName())) {
										plugin.getKitHandler()
												.setKit(player, k);
										player.sendMessage(ColorUtil
												.formatString(
														"%s: <gold>Selected ",
														k.getName()));
									} else {
										player.sendMessage(ColorUtil
												.formatString(
														"%s: <red>You have not unlocked kit \"%s\" in the shop yet!",
														plugin.getPrefix(),
														k.getName()));
									}
								} else {
									player.sendMessage(ColorUtil
											.formatColors("<red>That Kit Was Not Found!"));

								}
							}
						}

					});
			kitMenu.open();
		}

	}
}
