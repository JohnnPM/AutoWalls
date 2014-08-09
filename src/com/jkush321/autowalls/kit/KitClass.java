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
 * @author 598Johnn897
 */
public enum KitClass {
	
	ARCHER("<blue>Archer", "<gray>Shoot em' skybasers to their death.", Material.BOW, new KitArcher());

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
