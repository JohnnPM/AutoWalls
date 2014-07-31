/*
 * Author: 598Johnn897
 * 
 * Date: Jul 31, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;
import com.jkush321.autowalls.util.ColorUtil;

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:26:47 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: FlyCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class FlyCommand implements CommandListener {

	@Command(command = "fly", permission = "walls.fly", aliases = {"flight"})
	public void fly(CommandArgs info) {
		if (info.isPlayer()) {
			info.getPlayer().setAllowFlight(true);
			info.getPlayer().setFlying(true);
		} else {
			info.getSender().sendMessage(
					ColorUtil.formatColors("<red>You can't fly!"));
		}
	}
}
