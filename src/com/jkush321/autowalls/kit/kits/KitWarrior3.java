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

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.jkush321.autowalls.kit.Kit;

public class KitWarrior3 extends Kit {

	ItemStack[] contents = new ItemStack[] {
			new ItemStack(Material.DIAMOND_SWORD, 1),
			new ItemStack(Material.COOKED_BEEF, 4),
			new ItemStack(Material.IRON_HELMET),
			new ItemStack(Material.IRON_CHESTPLATE, 1),
			new ItemStack(Material.IRON_LEGGINGS, 1),
			new ItemStack(Material.IRON_BOOTS, 1) };

	public KitWarrior3() {
		ItemStack bow = new ItemStack(Material.BOW);
		bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
		bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);

		contents = new ItemStack[] { new ItemStack(Material.DIAMOND_SWORD, 1),
				new ItemStack(Material.COOKED_BEEF, 4),
				new ItemStack(Material.IRON_HELMET),
				new ItemStack(Material.IRON_CHESTPLATE, 1),
				new ItemStack(Material.IRON_LEGGINGS, 1),
				new ItemStack(Material.IRON_BOOTS, 1), bow,
				new ItemStack(Material.ARROW, 1) };
	}

	@Override
	public ItemStack[] getItemStack() {
		return contents;
	}

	@Override
	public int getCoinsToBuy() {
		return 3000;
	}

}
