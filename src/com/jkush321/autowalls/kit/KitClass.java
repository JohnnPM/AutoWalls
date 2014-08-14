/*
 * Author: 598Johnn897
 * 
 * Date: Aug 3, 2014
 * Package: com.jkush321.autowalls.kits
 *
 */
package com.jkush321.autowalls.kit;

import org.bukkit.Material;

import com.jkush321.autowalls.kit.kits.KitArcher;
import com.jkush321.autowalls.kit.kits.KitBuilder;
import com.jkush321.autowalls.kit.kits.KitBuilder2;
import com.jkush321.autowalls.kit.kits.KitCactus;
import com.jkush321.autowalls.kit.kits.KitDefault;
import com.jkush321.autowalls.kit.kits.KitDemo;
import com.jkush321.autowalls.kit.kits.KitElite;
import com.jkush321.autowalls.kit.kits.KitElite2;
import com.jkush321.autowalls.kit.kits.KitEnder;
import com.jkush321.autowalls.kit.kits.KitExcavator;
import com.jkush321.autowalls.kit.kits.KitFarmer;
import com.jkush321.autowalls.kit.kits.KitFirefighter;
import com.jkush321.autowalls.kit.kits.KitFood;
import com.jkush321.autowalls.kit.kits.KitFood2;
import com.jkush321.autowalls.kit.kits.KitGhost;
import com.jkush321.autowalls.kit.kits.KitGhost2;
import com.jkush321.autowalls.kit.kits.KitGhost3;
import com.jkush321.autowalls.kit.kits.KitMedic;
import com.jkush321.autowalls.kit.kits.KitMiner;
import com.jkush321.autowalls.kit.kits.KitParatrooper;
import com.jkush321.autowalls.kit.kits.KitPyro;
import com.jkush321.autowalls.kit.kits.KitPyro2;
import com.jkush321.autowalls.kit.kits.KitSmelter;
import com.jkush321.autowalls.kit.kits.KitSpy;
import com.jkush321.autowalls.kit.kits.KitTools;
import com.jkush321.autowalls.kit.kits.KitTrader;
import com.jkush321.autowalls.kit.kits.KitWarrior;
import com.jkush321.autowalls.kit.kits.KitWarrior2;
import com.jkush321.autowalls.kit.kits.KitWarrior3;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Aug 3, 2014 <br>
 * Time: 4:23:56 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: KitClass.java <br>
 * Package: com.jkush321.autowalls.kits <p>
 * 
 * <b>Temporary Until Better Kit System Is Implemented.</b>
 * 
 * @author 598Johnn897
 * 
 */
public enum KitClass {
	
	ARCHER("<blue>Archer", "<gray>Shoot em' skybasers to their death.", Material.BOW, new KitArcher()),
	BUILDER("<blue>Builder", "<gray>Building all day. Building all night.", Material.COBBLESTONE, new KitBuilder()),
	BUILDER2("<blue>Builder2", "<gray>Build all 2 dayz, building all 2 nightz.", Material.STONE, new KitBuilder2()),
	CACTUS("<blue>Cactus", "<gray>Cactus, never leave home without it.", Material.CACTUS, new KitCactus()),
	DEFAULT("<blue>Default", "<gray>Just in case you forget.", Material.STONE_PICKAXE, new KitDefault()),
	DEMO("<blue>Demo", "<gray>Blow teh stuff up.", Material.TNT, new KitDemo()),
	ELITE("<blue>Elite", "<gray>Come at me bro!", Material.IRON_SWORD, new KitElite()),
	ELITE2("<blue>Elite2", "<gray>2 Powerful.", Material.DIAMOND_SWORD, new KitElite2()),
	ENDER("<blue>Ender", "<gray>Teleportation at its best.", Material.ENDER_PEARL, new KitEnder()),
	EXCAVATOR("<blue>Excavator", "<gray>Dig all that dirt.", Material.DIAMOND_SPADE, new KitExcavator()),
	FARMER("<blue>Farmer", "<gray>Be the farmer you always were.", Material.DIAMOND_HOE, new KitFarmer()),
	FIREFIGHTER("<blue>Firefighter", "<gray>Fight that fire!", Material.WATER_BUCKET, new KitFirefighter()),
	FOOD("<blue>Food", "<gray>Food 4 Dayz!", Material.COOKED_BEEF, new KitFood()),
	FOOD2("<blue>Food2", "<gray>Enough food to last a lifetime!", Material.GOLDEN_APPLE, new KitFood2()),
	GHOST("<blue>Ghost", "<gray>They won't even see it coming.", Material.POTION, new KitGhost()),
	GHOST2("<blue>Ghost2", "<gray>BOOOoOOooOOO!!", Material.POTION, new KitGhost2()),
	GHOST3("<blue>Ghost3", "<gray>Spooky.", Material.POTION, new KitGhost3()),
	MEDIC("<blue>Medic", "<gray>Heal all the players who should've died.", Material.GLASS_BOTTLE, new KitMedic()),
	MINER("<blue>Miner", "<gray>Don't mine at night!", Material.DIAMOND_PICKAXE, new KitMiner()),
	PARATROOPER("<blue>Paratrooper", "<gray>Don't you lay one finger on my new shoes!", Material.GOLD_BOOTS, new KitParatrooper()),
	PYRO("<blue>Pyro", "<gray>MMFMMFMFMMMFMFF", Material.DIAMOND_HELMET, new KitPyro()),
	PYRO2("<blue>Pyro2", "<gray>MMF2MFMFMF2MFFMF2", Material.DIAMOND_CHESTPLATE, new KitPyro2()),
	SMELTER("<blue>Smelter", "<gray>Cooking food in the same furnace I smelt my iron... Just Lovely.", Material.FURNACE, new KitSmelter()),
	SPY("<blue>Spy", "<gray>Wait... Your not on our te--", Material.NETHER_STAR, new KitSpy()),
	TOOLS("<blue>Tools", "<gray>All the tool you could find. At least wooden ones.", Material.WOOD_AXE, new KitTools()),
	TRADER("<blue>Trader", "<gray>Villagers always have great trades. *sarcasm*", Material.EMERALD, new KitTrader()),
	WARRIOR("<blue>Warrior", "<gray>THIS! IS! SPARTA!!!", Material.GOLD_CHESTPLATE, new KitWarrior()),
	WARRIOR2("<blue>Warrior2", "<gray>This. Use it wisely.", Material.IRON_CHESTPLATE, new KitWarrior2()),
	WARRIOR3("<blue>Warrior3", "<gray>Power. Period.", Material.DIAMOND_CHESTPLATE, new KitWarrior3());

	private String displayName;
    private String description;
    private final Material icon;
    private final Kit kit;
    
	KitClass(String name, String description, Material icon, Kit kit) {
		this.displayName = ColorUtil.formatColors(name);
		this.description = ColorUtil.formatColors(description);
		this.icon = icon;
		this.kit = kit;
	}
	
    /**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the icon
	 */
	public Material getIcon() {
		return icon;
	}

	/**
	 * @return the kit
	 */
	public Kit getKit() {
		return kit;
	}
	
	/**
	 * @return the amount of coins to buy it
	 */
	public int getCoinsToBuy() {
		return getKit().getCoinsToBuy();
	}
	
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
