/*
 * Author: 598Johnn897
 * 
 * Date: Jul 30, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.ClassEnumerator;

/**
 * Created: Jul 30, 2014 <br>
 * Time: 9:41:27 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: GameHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class GameHandler implements Listener {

	private AutoWalls plugin;

	public boolean gameInProgress = false;
	public boolean gameOver = false;
	public boolean canJoin = false;
	
	public List<String> playersOnline = new CopyOnWriteArrayList<String>();
	public List<Player> playing = new CopyOnWriteArrayList<Player>();
	public List<Player> dead = new CopyOnWriteArrayList<Player>();
	
	public GameHandler(AutoWalls autoWalls) {
		this.plugin = autoWalls;
	}

	/**
	 * @return if game is in progress
	 */
	public boolean isGameInProgress() {
		return gameInProgress;
	}
	
	/**
	 * Sets if game in progress or not
	 */
	public void setGameProgress(boolean progress) {
		this.gameInProgress = progress;
	}
	
	/**
	 * Gets rid of the rain
	 * 
	 * @param e
	 */
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	
	/**
	 * Dynamically registers all listeners/events in project.
	 */
	public void registerEvents() {
		Class<?>[] classes = ClassEnumerator.getInstance()
				.getClassesFromThisJar(plugin);
		if (classes == null || classes.length == 0) {
			return;
		}
		for (Class<?> c : classes) {
			try {
				if (Listener.class.isAssignableFrom(c) && !c.isInterface()
						&& !c.isEnum() && !c.isAnnotation()) {
					if (JavaPlugin.class.isAssignableFrom(c)) {
						if (plugin.getClass().equals(c)) {
							plugin.getLogger().log(Level.INFO,
									"Searching class: " + c.getSimpleName());
							Bukkit.getPluginManager().registerEvents(plugin,
									plugin);
						}
					} else {
						plugin.getLogger().log(Level.INFO,
								"Searching class: " + c.getSimpleName());
						Bukkit.getPluginManager().registerEvents(
								(Listener) c.newInstance(), plugin);
					}
				}
			} catch (IllegalAccessException | InstantiationException e) {
				plugin.getLogger().log(
						Level.INFO,
						c.getSimpleName()
								+ " does not use the default constructor");
				e.printStackTrace();
			}
		}
	}
}
