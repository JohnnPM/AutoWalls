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

package com.jkush321.autowalls;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.java.JavaPlugin;

import com.jkush321.autowalls.commands.CommandFramework;
import com.jkush321.autowalls.config.Config;
import com.jkush321.autowalls.handlers.ChatHandler;
import com.jkush321.autowalls.handlers.EventsHandler;
import com.jkush321.autowalls.handlers.GameHandler;
import com.jkush321.autowalls.handlers.GraveHandler;
import com.jkush321.autowalls.handlers.KitHandler;
import com.jkush321.autowalls.handlers.TeamHandler;
import com.jkush321.autowalls.handlers.TeleportHandler;
import com.jkush321.autowalls.handlers.VoteHandler;
import com.jkush321.autowalls.lib.References;
import com.jkush321.autowalls.timers.JoinTimer;
import com.jkush321.autowalls.timers.WallDropTimer;
import com.jkush321.autowalls.util.ColorUtil;
import com.jkush321.autowalls.util.FileUtil;

public class AutoWalls extends JavaPlugin implements Listener {

	private static AutoWalls plugin;
	public static AutoWalls get() 					{ return plugin; }
		
	private GameHandler handler;
	public GameHandler getHandler() 				{ return handler; }

	private TeamHandler teamHandler;
	public TeamHandler getTeamHandler() 			{ return teamHandler; }

	private KitHandler kitHandler;
	public KitHandler getKitHandler() 				{ return kitHandler; }
	
	private JoinTimer joinTimer;
	public JoinTimer getJoinTimer() 				{ return joinTimer; }
	
	private WallDropTimer wallDropTimer;
	public WallDropTimer getWallDropTimer() 		{ return wallDropTimer; }

	private Thread wallDropThread;
	public Thread getWallDropThread() 				{ return wallDropThread; }
	
	private Logger logger;
	public Logger getAWLogger() 					{ return logger; }

	private File configFile;
	private Config config;
	public Config getAWConfig() 					{ return config; }
	
	private GraveHandler graveHandler;
	public GraveHandler getGraveHandler() 			{ return graveHandler; }
	
	private Announcer announcer;
	public Announcer getAnnouncer() 				{ return announcer; }

	private Thread voteHandler;
	public Thread getVoteHandler() 					{ return voteHandler; }
	
	private EventsHandler eventsHandler;
	public EventsHandler getEventsHandler() 		{ return eventsHandler; }
	
	private TeleportHandler teleportHandler;
	public TeleportHandler getTeleportHandler() 	{ return teleportHandler; }
	
	private ChatHandler chatHandler;
	public ChatHandler getChatHandler() 			{ return chatHandler; }
	
	public String getPrefix() 						{ return ColorUtil.formatColors(this.getAWConfig().getString("AutoWalls Names.prefix")); }
	
	private CommandFramework framework;
	
	@Override
	public void onLoad() {
		plugin          = this;
		framework       = new CommandFramework(this);
		handler         = new GameHandler();
		teamHandler     = new TeamHandler();
		announcer       = new Announcer();
		kitHandler      = new KitHandler();
		joinTimer       = new JoinTimer();
		wallDropTimer   = new WallDropTimer();
		graveHandler    = new GraveHandler();
		voteHandler     = new Thread(new VoteHandler());
		eventsHandler   = new EventsHandler();
		teleportHandler = new TeleportHandler();
		wallDropThread  = new Thread(new WallDropTimer());
		chatHandler     = new ChatHandler();
		logger          = Logger.getLogger("AutoWalls");
	}

	@Override
	public void onEnable() {
		if (plugin == null)
			plugin = this;

		framework.registerCommands();
		framework.registerHelp();

		handler.registerEvents();
		teamHandler.registerTeams();

		File folder = getDataFolder();
		if (!folder.exists()) folder.mkdir();

		configFile = new File(folder, "config.txt");
		if (!configFile.exists()) this.saveResource("config.txt", false);
		config = new Config(configFile);

		// My CC3.0 Attribution license requires you to leave this in some way
		// If you have forked it you can say...
		// "This server runs MyFork by Me based on AutoWalls by Jkush321" or
		// something similar
		String[] announcements = config.getString("announcements").split(";");
		getAnnouncer().messages.add(
				ColorUtil.formatString(
						"<gray>Running AutoWalls v<aqua>%s on <aqua>%s <gray>by <aqua>%s<gray>."
							,References.VERSION
							,References.GAME_VERISON
							,References.AUTHOR));
		for (String s : announcements) {
			getAnnouncer().messages.add(s);
		}
		
		String[] messages = plugin.getAWConfig().getString("Graves.messages")
				.split(";");
		for (String s : messages) {
			getGraveHandler().graveMessages.add(s);
		}
		
		if (getAWConfig().getboolean("Announcer.announcer"))
			getAnnouncer().runTaskTimer(this,
					getAWConfig().getint("Announcer.intervals") * 20,
					getAWConfig().getint("Announcer.intervals") * 20);

		File mapFile = new File(plugin.getDataFolder(), "next_map");
		if (!mapFile.exists()) {
			try {
				mapFile.createNewFile();
				FileUtil.writeTo(mapFile, "1");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		getHandler().mapNumber = getHandler().getNextMap();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				ColorCycler.tick();
			}
		}, 0L, 20L);

		Grenades.init();

		boolean tabAPI = getAWConfig().getboolean("AutoWalls Options.tabAPI");

		if (Bukkit.getPluginManager().getPlugin("TabAPI") != null && tabAPI) {
			getHandler().tabAPI = true;
			getAWLogger().log(Level.INFO, "Successfully hooked into TabAPI!");
		} else if (tabAPI) {
			getAWLogger().log(Level.INFO,
						"Error! TabAPI is not installed but it was set to be used in the config!");
			getHandler().tabAPI = false;
		}
		
		this.getJoinTimer().timeLeft = this.getAWConfig().getint("AutoWalls Settings.joinTime");
		this.getJoinTimer().runTaskTimer(this, 20, 20);
		
		this.getWallDropTimer().time = (int) TimeUnit.MINUTES.toSeconds(this.getAWConfig().getint("AutoWalls Settings.minutes"));
		getWallDropThread().start();
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		this.getAnnouncer().cancel();
		this.getWallDropThread().stop();
		this.getJoinTimer().cancel();
		this.getVoteHandler().stop();
		
		plugin = null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		 return framework.handleCommand(sender, label, cmd, args);
	}

	@EventHandler
	public void onProjLaunch(ProjectileLaunchEvent e) {
		if (((Metadatable) e.getEntity().getShooter())
				.hasMetadata("last-grenade")) {
			e.getEntity().setMetadata(
					"grenade-type",
					new FixedMetadataValue(this, ((Metadatable) e.getEntity()
							.getShooter()).getMetadata("last-grenade").get(0)
							.asString()));
		}
	}
}
