/*
 * AutoWalls by jkush321 is licensed under the
 * Creative Commons Attribution-NonCommercial 3.0 Unported License
 * 
 * You are fully allowed to modify the source code for your own network
 * of servers, but you may not distribute the modified code outside of
 * your servers.
 * 
 * AutoWalls was originally a personal project that was standalone for
 * my own private server, and it slowly accumulated into a giant plugin.
 * 
 * AutoWalls is for dedicated servers that are willing to run just Walls.
 * 
 * The license requires attribution and you have to give credit to jkush321
 * no matter how many changes were made to the code. In some clearly stated
 * way everyone who goes on the server must be able to easily see and be aware
 * of the fact that this code originated from jkush321 and was modified by
 * you or your team.
 * 
 * For more information visit http://bit.ly/AutoWalls
 * 
 */

package com.jkush321.autowalls.kit.kits;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.jkush321.autowalls.kit.Kit;

public class KitGhost2 extends Kit {

	ItemStack[] contents;

	public KitGhost2() {
		Potion potion = new Potion(PotionType.INVISIBILITY);
		potion.setSplash(true);
		potion.setHasExtendedDuration(true);
		contents = new ItemStack[] { potion.toItemStack(5) };
	}

	@Override
	public ItemStack[] getItemStack() {
		return contents;
	}

	@Override
	public int getCoinsToBuy() {
		return 350;
	}

}
