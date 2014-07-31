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

/**
 * Created: Jul 31, 2014 <br>
 * Time: 10:19:26 AM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: JoinCommand.java <br>
 * Package: com.jkush321.autowalls.commands <p>
 * 
 * @author 598Johnn897
 */
public class JoinCommand implements CommandListener {

	@Command(command = "join", aliases = {"team"}, permission = "walls.player")
	public void join(CommandArgs info) {
		
	}
}