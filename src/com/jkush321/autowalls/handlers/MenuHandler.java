/*
 * Author: 598Johnn897
 * 
 * Date: Aug 2, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.kits.KitClass;
import com.jkush321.autowalls.menu.GUIButton;
import com.jkush321.autowalls.menu.GUIMenu;
import com.jkush321.autowalls.util.StringUtil;

/**
 * Created: Aug 2, 2014 <br>
 * Time: 8:19:59 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: MenuHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class MenuHandler implements Listener {

	public GUIMenu kitMenu;

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();
	
	@EventHandler
	public void onItemClick(PlayerInteractEvent event) {
		if (handler.isGameInProgress()) return;
		if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)
				|| !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getItem().equals(new ItemStack(Material.IRON_AXE))) {
				KitClass[] kitClasses = KitClass.values();
				kitMenu = GUIMenu.createMenu("Select your kit...",
						(int) Math.ceil(kitClasses.length / 9.0),
						event.getPlayer());
				for (int i = 0; i < kitClasses.length; i++) {
					final KitClass kitClass = kitClasses[i];
					kitMenu.setOption(i, new ItemStack(kitClass.getIcon()), kitClass.getDisplayName(),
							StringUtil.wrapWords(kitClass.getDescription(), 40), new GUIButton() {

								@Override
								public boolean onClick(Player player,
										String displayName) {
									return false;
								}

							});
				}
			}
		}
	}
}
