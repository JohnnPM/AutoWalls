/*
 * Author: 598Johnn897
 * 
 * Date: Aug 1, 2014
 * Package: com.jkush321.autowalls.commands
 *
 */
package com.jkush321.autowalls.commands;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import com.jkush321.autowalls.commands.CommandFramework.Command;
import com.jkush321.autowalls.commands.CommandFramework.CommandArgs;
import com.jkush321.autowalls.commands.CommandFramework.CommandListener;

/**
 * Created: Aug 1, 2014 <br>
 * Time: 10:20:16 AM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: RestartCommand.java <br>
 * Package: com.jkush321.autowalls.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class RestartCommand implements CommandListener {

	@Command(command = "restart", permission = "server.restart")
	public void restart(CommandArgs info) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				List<String> args = ManagementFactory.getRuntimeMXBean()
						.getInputArguments();
				List<String> command = new ArrayList<String>();
				command.add(System.getProperty("java.home") + File.separator
						+ "bin" + File.separator + "java.exe");
				for (int i = 0; i < args.size(); i++) {
					command.add(args.get(i));
				}
				command.add("-jar");
				command.add(new File(Bukkit.class.getProtectionDomain()
						.getCodeSource().getLocation().getFile())
						.getAbsolutePath());
				try {
					new ProcessBuilder(command).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Bukkit.shutdown();
	}

}
