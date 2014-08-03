/*
 * Author: 598Johnn897
 * 
 * Date: Jul 30, 2014
 * Package: com.jkush321.autowalls.lib
 *
 */
package com.jkush321.autowalls.lib;

import com.jkush321.autowalls.AutoWalls;

/**
 * Created: Jul 30, 2014 <br>
 * Time: 9:52:14 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: References.java <br>
 * Package: com.jkush321.autowalls.lib <p>
 * 
 * @author 598Johnn897
 */
public class References {
	
	private static AutoWalls plugin = AutoWalls.get();
	
	public static final String NAME = plugin.getDescription().getName();
	public static final String VERSION = plugin.getDescription().getVersion();
	
	public static final String PLAYER_FILE_FORMAT = ".txt";
	public static final String PLAYER_FILE_EXT = "/players";

}
