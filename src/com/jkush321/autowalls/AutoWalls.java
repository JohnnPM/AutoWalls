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
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.jkush321.autowalls.commands.CommandFramework;
import com.jkush321.autowalls.config.Config;
import com.jkush321.autowalls.handlers.EventsHandler;
import com.jkush321.autowalls.handlers.GameHandler;
import com.jkush321.autowalls.handlers.GraveHandler;
import com.jkush321.autowalls.handlers.KitHandler;
import com.jkush321.autowalls.handlers.TeamHandler;
import com.jkush321.autowalls.handlers.TeleportHandler;
import com.jkush321.autowalls.handlers.VoteHandler;
import com.jkush321.autowalls.kits.Kit;
import com.jkush321.autowalls.lib.References;
import com.jkush321.autowalls.timers.JoinTimer;
import com.jkush321.autowalls.timers.WallDropTimer;
import com.jkush321.autowalls.util.ColorUtil;
import com.jkush321.autowalls.util.FileUtil;

public class AutoWalls extends JavaPlugin implements Listener {

	private static AutoWalls plugin;
	public static AutoWalls get() { return plugin; }
	
	private CommandFramework framework;
	
	private GameHandler handler;
	public GameHandler getHandler() { return handler; }

	private TeamHandler teamHandler;
	public TeamHandler getTeamHandler() { return teamHandler; }

	private KitHandler kitHandler;
	public KitHandler getKitHandler() { return kitHandler; }
	
	private JoinTimer joinTimer;
	public JoinTimer getJoinTimer() { return joinTimer; }
	
	private WallDropTimer wallDropTimer;
	public WallDropTimer getWallDropTimer() { return wallDropTimer; }

	private Thread wallDropThread;
	public Thread getWallDropThread() { return wallDropThread; }
	
	private Logger logger;
	public Logger getAWLogger() { return logger; }

	private File configFile;
	private Config config;
	public Config getAWConfig() { return config; }

	private String prefix;
	public String getPrefix() {
		return ColorUtil.formatColors(this.getAWConfig().getString("AutoWalls Names.prefix"));
	}
	
	private GraveHandler graveHandler;
	public GraveHandler getGraveHandler() { return graveHandler; }
	
	private Announcer announcer;
	public Announcer getAnnouncer() { return announcer; }

	private Thread voteHandler;
	public Thread getVoteHandler() { return voteHandler; }
	
	private EventsHandler eventsHandler;
	public EventsHandler getEventsHandler() { return eventsHandler; }
	
	private TeleportHandler teleportHandler;
	public TeleportHandler getTeleportHandler() { return teleportHandler; }
	
	@Override
	public void onLoad() {
		plugin          = this;
		framework       = new CommandFramework(this);
		handler         = new GameHandler(this);
		teamHandler     = new TeamHandler(this);
		announcer       = new Announcer();
		kitHandler      = new KitHandler();
		joinTimer       = new JoinTimer();
		wallDropTimer   = new WallDropTimer();
		graveHandler    = new GraveHandler();
		voteHandler     = new Thread(new VoteHandler());
		eventsHandler   = new EventsHandler();
		teleportHandler = new TeleportHandler();
		wallDropThread  = new Thread(new WallDropTimer());
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
		if (!folder.exists())
			folder.mkdir();

		configFile = new File(folder, "config.txt");
		if (!configFile.exists())
			this.saveResource("config.txt", false);
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
		
		BukkitScheduler scheduler = get().getServer().getScheduler();
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

	@Override
	public void onDisable() {
		getAnnouncer().cancel();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// return framework.handleCommand(sender, label, cmd, args);
		if (cmd.getLabel().equalsIgnoreCase("join")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				boolean allowed = false;
				if (config.getInt("votes.players." + p.getName()) >= earlyJoinPriority
						&& !gameInProgress) {
					allowed = true;
				}
				if (canJoin && !gameInProgress) {
					allowed = true;
				}
				if (playing.size() < teamSize * 4
						&& config.getInt("votes.players." + p.getName()) >= lateJoinPriority
						&& WallDropper.time > 0) {
					allowed = true;
				}
				if (!allowed) {
					sender.sendMessage(ChatColor.DARK_RED
							+ "You can not join the game at this time!");
					return true;
				}
				if (args.length == 0) // Add to random team
				{
					if (redTeam.size() < teamSize)
						joinTeam(p, "red");
					else if (blueTeam.size() < teamSize)
						joinTeam(p, "blue");
					else if (greenTeam.size() < teamSize)
						joinTeam(p, "green");
					else if (orangeTeam.size() < teamSize)
						joinTeam(p, "orange");
					else
						p.sendMessage(ChatColor.RED + "Every team is full!");
				} else if (args.length == 1) // Add to specified team
				{
					if (args[0].equalsIgnoreCase("red"))
						joinTeam(p, "red");
					else if (args[0].equalsIgnoreCase("blue"))
						joinTeam(p, "blue");
					else if (args[0].equalsIgnoreCase("green"))
						joinTeam(p, "green");
					else if (args[0].equalsIgnoreCase("orange"))
						joinTeam(p, "orange");
					else
						p.sendMessage(ChatColor.DARK_RED + "The Team "
								+ args[0] + " Is Invalid!");
				} else
					p.sendMessage(ChatColor.RED
							+ "Too Many Arguments. /join <red|blue|green|orange>");
			} else
				sender.sendMessage("You can't join a team, console :P");
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("leave")) {
			if (sender instanceof Player) {
				if (playing.contains((Player) sender)) {
					Bukkit.broadcastMessage(ChatColor.YELLOW + sender.getName()
							+ ChatColor.DARK_RED + " has left the game!");
					((Player) sender).setHealth(0);
					leaveTeam((Player) sender);
				} else
					sender.sendMessage(ChatColor.DARK_RED
							+ "You aren't on a team");
			}
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("time")) {
			int minutes = 0;
			int seconds = 0;
			if (!gameInProgress) {
				sender.sendMessage(ChatColor.GRAY
						+ "The game hasn't started yet!");
				return true;
			}
			minutes = WallDropper.time / 60;
			seconds = WallDropper.time % 60;

			if (minutes == 0 && seconds == 0) {
				sender.sendMessage(ChatColor.GRAY
						+ "The Walls Already Dropped!");
				return true;
			}
			sender.sendMessage(ChatColor.GRAY + "The walls will drop in "
					+ minutes + " minutes and " + seconds + " seconds!");
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("tp")) {
			if (args.length == 1) {
				Player p = (Player) sender;
				Player p2 = Bukkit.getPlayer(args[0]);
				if (p2 != null && p2.isOnline()) {
					if (!playing.contains(p) || p.hasPermission("walls.op"))
						p.teleport(p2);
					else {
						if (teamTeleports && secondsBeforeTeleport > 0) {
							if (redTeam.contains(p) && redTeam.contains(p2))
								TeleportHandler.createTpRunnable(p, p2);
							else if (blueTeam.contains(p)
									&& blueTeam.contains(p2))
								TeleportHandler.createTpRunnable(p, p2);
							else if (greenTeam.contains(p)
									&& greenTeam.contains(p2))
								TeleportHandler.createTpRunnable(p, p2);
							else if (orangeTeam.contains(p)
									&& orangeTeam.contains(p2))
								TeleportHandler.createTpRunnable(p, p2);
							else {
								p.sendMessage(ChatColor.YELLOW + p2.getName()
										+ " is not on your team!");
								return true;
							}
							p.sendMessage(ChatColor.YELLOW
									+ "You will be teleported to "
									+ ChatColor.DARK_GREEN + p2.getName()
									+ ChatColor.YELLOW
									+ "if you do not move for "
									+ ChatColor.YELLOW + secondsBeforeTeleport
									+ ChatColor.YELLOW + " seconds");
						} else if (teamTeleports) {
							if (redTeam.contains(p) && redTeam.contains(p2))
								p.teleport(p2);
							else if (blueTeam.contains(p)
									&& blueTeam.contains(p2))
								p.teleport(p2);
							else if (greenTeam.contains(p)
									&& greenTeam.contains(p2))
								p.teleport(p2);
							else if (orangeTeam.contains(p)
									&& orangeTeam.contains(p2))
								p.teleport(p2);
							else
								p.sendMessage(ChatColor.YELLOW + p2.getName()
										+ " is not on your team!");
						} else
							p.sendMessage(ChatColor.DARK_AQUA
									+ "This server has team teleporting disabled!");
					}
				} else
					p.sendMessage(ChatColor.GRAY + "That player is not online!");
			} else if (args.length == 4) {
				Player p = Bukkit.getPlayer(args[0]);
				if (p.isOnline() && p != null) {
					double x, y, z;
					try {
						x = Double.parseDouble(args[1]);
						y = Double.parseDouble(args[2]);
						z = Double.parseDouble(args[3]);
					} catch (Exception e) {
						sender.sendMessage(ChatColor.DARK_RED
								+ "Invalid coordinates");
						return true;
					}
					p.teleport(new Location(p.getWorld(), x, y, z));
				} else
					sender.sendMessage(ChatColor.DARK_RED + "Player not found.");
			} else
				sender.sendMessage("Invalid Arguments. /tp playername");

			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("pri")) {
			if (args.length != 2 && args.length != 3)
				return false;
			if (!sender.hasPermission("walls.op"))
				return false;
			Player pl = Bukkit.getPlayer(args[0]);
			int a = Integer.parseInt(args[1]);
			if (config.isSet("votes.players." + pl.getName())) {
				config.set("votes.players." + pl.getName(),
						config.getInt("votes.players." + pl.getName()) + a);
			} else
				config.set("votes.players." + pl.getName(), a);
			if (Bukkit.getPlayer(pl.getName()) != null
					&& Bukkit.getPlayer(pl.getName()).isOnline())
				Bukkit.getPlayer(pl.getName())
						.sendMessage(
								ChatColor.YELLOW
										+ "Your priority is now "
										+ config.getInt("votes.players."
												+ pl.getName()));
			if (args.length == 3)
				Bukkit.broadcastMessage(ChatColor.AQUA + pl.getName()
						+ " Donated To Us And Now Has Login Priority of "
						+ config.getInt("votes.players." + pl.getName())
						+ "! :D Thank you very much, " + pl.getName());
			saveConfig();
			if (!pl.isOnline()) {
				sender.sendMessage("Done!");
				return true;
			}
			pl.setDisplayName(pl.getName());
			if (config.isSet("votes.players." + pl.getName())
					&& config.getInt("votes.players." + pl.getName()) >= 20) {
				pl.setDisplayName(ChatColor.DARK_AQUA + pl.getName()
						+ ChatColor.WHITE);
			}
			if (config.isSet("votes.players." + pl.getName())
					&& config.getInt("votes.players." + pl.getName()) >= 250) {
				pl.setDisplayName(ChatColor.DARK_RED + pl.getName()
						+ ChatColor.WHITE);
			}
			if (config.getBoolean("priorities") == true) {
				if (config.isSet("votes.players." + pl.getName())) {
					pl.setDisplayName(ChatColor.YELLOW + "["
							+ config.getInt("votes.players." + pl.getName())
							+ "]" + ChatColor.GRAY + pl.getDisplayName()
							+ ChatColor.WHITE);
				} else
					pl.setDisplayName(ChatColor.GRAY + "[0]"
							+ pl.getDisplayName() + ChatColor.WHITE);
			}
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("tpplayers")) {
			if (!sender.hasPermission("walls.op"))
				return false;
			for (Player p : playing) {
				if (p != (Player) sender)
					p.teleport((Player) sender);
			}
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("tpspecs")) {
			if (!sender.hasPermission("walls.op"))
				return false;
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!playing.contains(p) && p != (Player) sender)
					p.teleport((Player) sender);
			}
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("tphere")) {
			if (!sender.hasPermission("walls.op"))
				return false;
			if (args.length != 1) {
				sender.sendMessage(ChatColor.RED + "Invalid arguments");
				return true;
			}
			Player pl = Bukkit.getPlayer(args[0]);
			if (pl != null && pl.isOnline()) {
				pl.teleport((Player) sender);
			} else
				sender.sendMessage(ChatColor.RED + "Player is not online");
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("tc")) {
			Player p = (Player) sender;
			if (!playing.contains(p)) {
				p.sendMessage(ChatColor.RED
						+ "You have to be on a team to teamchat!");
				return true;
			}
			if (!TeamChat.teamChatting.contains(p)) {
				TeamChat.teamChatting.add(p);
				p.sendMessage(ChatColor.YELLOW + "You are now team chatting!");
				return true;
			}
			if (TeamChat.teamChatting.contains(p)) {
				TeamChat.teamChatting.remove(p);
				p.sendMessage(ChatColor.YELLOW
						+ "You have disabled team chatting!");
				return true;
			}
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("forcestart")) {
			if (playing.size() >= 2 && !gameInProgress
					&& JoinTimer.timeleft <= 0) {
				Bukkit.broadcastMessage(ChatColor.DARK_RED
						+ "FORCE STARTING GAME");
				startGame();
			} else {
				sender.sendMessage(ChatColor.GRAY
						+ "There have to be at least 2 players, and the game can not be started yet, and the join timer must be over!");
			}
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("yell")) {
			if (config.getInt("votes.players." + sender.getName()) >= 20
					|| !(sender instanceof Player)
					|| sender.hasPermission("walls.op")) {
				String message = "";
				for (String s : args) {
					message += s + " ";
				}
				message = message.trim();
				if (args.length != 0) {
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Yell] "
							+ ChatColor.AQUA + sender.getName() + ": "
							+ ChatColor.WHITE + message);
				} else
					sender.sendMessage(ChatColor.AQUA
							+ "Usage... /yell [message]");
			} else
				sender.sendMessage(ChatColor.AQUA
						+ "You need at least 20 priority to do that.");
		} else if (cmd.getLabel().equalsIgnoreCase("forcedrop")) {
			if (WallDropper.time <= 5) {
				sender.sendMessage(ChatColor.AQUA
						+ "The walls have already dropped!");
			} else {
				WallDropper.time = 5;
			}
		} else if (cmd.getLabel().equalsIgnoreCase("forceend")) {
			endGame("ADMINS", "No one.");
		} else if (cmd.getLabel().equalsIgnoreCase("kit")) {
			if (args.length == 1) {
				if (JoinTimer.timeleft > 0 || !gameInProgress) {

					if (KitHandler.findKit(args[0]) != null) {
						Kit k = KitHandler.findKit(args[0]);
						int p = 0;
						if (config.isSet("votes.players." + sender.getName()))
							p = config.getInt("votes.players."
									+ sender.getName());
						if (k.getRequiredPriority() <= p) {
							KitHandler.setKit((Player) sender, k);
							sender.sendMessage(ChatColor.DARK_AQUA
									+ "Selected kit " + k.getName());
						} else {
							sender.sendMessage(ChatColor.DARK_RED
									+ "That kit is not available to you! You can get "
									+ priorityPerDollar
									+ " priority for every $1 donated");
						}
					} else {
						sender.sendMessage(ChatColor.DARK_RED
								+ "That kit was not found.");
					}
				} else
					sender.sendMessage(ChatColor.DARK_RED
							+ "It is too late to choose a kit!");
			} else if (args.length == 0) {
				int p = 0;
				if (config.isSet("votes.players." + sender.getName()))
					p = config.getInt("votes.players." + sender.getName());
				String m1 = (ChatColor.GRAY + "Available Kits: " + ChatColor.WHITE);
				for (Kit k : KitHandler.kitList) {
					if (k.getRequiredPriority() <= p)
						m1 += "(" + k.getRequiredPriority() + ")" + k.getName()
								+ ", ";
				}
				String m2 = (ChatColor.GRAY + "Unavailable Kits: " + ChatColor.WHITE);
				for (Kit k : KitHandler.kitList) {
					if (k.getRequiredPriority() > p)
						m2 += "(" + k.getRequiredPriority() + ")" + k.getName()
								+ ", ";
				}
				m1 = m1.substring(0, m1.length() - 2) + ".";
				m2 = m2.substring(0, m2.length() - 2) + ".";
				sender.sendMessage(m1);
				sender.sendMessage(m2);
				sender.sendMessage(ChatColor.DARK_AQUA
						+ "To unlock the unavaible kits you can donate for priority. You get "
						+ priorityPerDollar + " priority for $1");
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "/kit [name]");
			}
		} else 
			return false;

		return true;
	}
	
	public void joinTeam(Player p, String team) {
		if (playing.contains(p)) {
			p.sendMessage(ChatColor.RED + "You are already on a team!");
		} else {
			if (team == "red") {
				if (redTeam.size() == teamSize) {
					p.sendMessage(ChatColor.RED + "That team is full!");
					return;
				}
				redTeam.add(p);
			}
			if (team == "blue") {
				if (blueTeam.size() == teamSize) {
					p.sendMessage(ChatColor.RED + "That team is full!");
					return;
				}
				blueTeam.add(p);
			}
			if (team == "green") {
				if (greenTeam.size() == teamSize) {
					p.sendMessage(ChatColor.RED + "That team is full!");
					return;
				}
				greenTeam.add(p);
			}
			if (team == "orange") {
				if (orangeTeam.size() == teamSize) {
					p.sendMessage(ChatColor.RED + "That team is full!");
					return;
				}
				orangeTeam.add(p);
			}
			playing.add(p);
			p.setAllowFlight(false);
			p.setGameMode(GameMode.SURVIVAL);
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (p != pl && !playing.contains(p))
					p.hidePlayer(pl);
			}
			removeDeadPlayer(p.getName());
			Bukkit.broadcastMessage(ChatColor.RED + p.getName()
					+ " has joined the " + team + " team!");
			int remaining = (teamSize * 4) - playing.size();
			String s = "s";
			if (remaining == 1)
				s = "";
			Bukkit.broadcastMessage(ChatColor.AQUA + "There is room for "
					+ remaining + " more player" + s + "!");
			if (remaining == 0 && !gameInProgress) {
				Bukkit.broadcastMessage(ChatColor.GREEN
						+ "It is time for the game to start! " + ChatColor.RED
						+ "Go be the best you can be now!");
				startGame();
			}
			if (gameInProgress && lateJoins) {
				if (team.equals("red")) {
					p.teleport(new Location(p.getWorld(), redSpawn[0],
							redSpawn[1], redSpawn[2]));
				} else if (team.equals("blue")) {
					p.teleport(new Location(p.getWorld(), blueSpawn[0],
							blueSpawn[1], blueSpawn[2]));
				} else if (team.equals("orange")) {
					p.teleport(new Location(p.getWorld(), orangeSpawn[0],
							orangeSpawn[1], orangeSpawn[2]));
				} else if (team.equals("green")) {
					p.teleport(new Location(p.getWorld(), greenSpawn[0],
							greenSpawn[1], greenSpawn[2]));
				}
				p.sendMessage(ChatColor.YELLOW
						+ "It is too late to receive a kit!");

				p.sendMessage(ChatColor.YELLOW + "Good Luck!");
			}
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setExp(0);
			p.setLevel(0);
			p.setNoDamageTicks(60);
		}
	}

	public void leaveTeam(Player p) {
		if (playing.contains(p))
			playing.remove(p);
		if (redTeam.contains(p))
			redTeam.remove(p);
		if (blueTeam.contains(p))
			blueTeam.remove(p);
		if (greenTeam.contains(p))
			greenTeam.remove(p);
		if (orangeTeam.contains(p))
			orangeTeam.remove(p);
		if (TeamChat.teamChatting.contains(p))
			TeamChat.teamChatting.remove(p);
		if (WallDropper.time > 0 && gameInProgress && lateJoins) {
			Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "A player with "
					+ lateJoinPriority + "+ priority may " + ChatColor.YELLOW
					+ "/join and take " + p.getName() + "'s place!");
		}
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (pl != p) {
				if (!p.canSee(pl))
					p.showPlayer(pl);
			}
		}
		checkStats();
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		try {
			if (!playing.contains(e.getEntity())) {
				e.setDeathMessage("");
				if (e.getEntity().getInventory().getSize() > 0) {
					while (e.getDrops().size() > 0)
						e.getDrops().remove(0);
				}
				return;
			}
			if (gameInProgress && playing.contains(e.getEntity())) {
				playing.remove(e.getEntity());
				if (redTeam.contains(e.getEntity()))
					redTeam.remove(e.getEntity());
				if (blueTeam.contains(e.getEntity()))
					blueTeam.remove(e.getEntity());
				if (greenTeam.contains(e.getEntity()))
					greenTeam.remove(e.getEntity());
				if (orangeTeam.contains(e.getEntity()))
					orangeTeam.remove(e.getEntity());
				if (TeamChat.teamChatting.contains(e.getEntity()))
					TeamChat.teamChatting.remove(e.getEntity());
				if (playing.size() > 1)
					e.setDeathMessage(ChatColor.YELLOW
							+ e.getEntity().getName()
							+ ChatColor.DARK_RED
							+ " "
							+ e.getDeathMessage().split(
									e.getEntity().getName() + " ")[1]
							+ ChatColor.DARK_GREEN + " " + playing.size()
							+ " Players Remain");
				createGrave(e.getEntity().getLocation(), e.getEntity()
						.getName());
				checkStats();
				addDeadPlayer(e.getEntity().getName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void spectate(Player p) {
		p.setAllowFlight(true);
		p.sendMessage(ChatColor.YELLOW + "You are now spectating!");
		p.sendMessage(ChatColor.YELLOW + "You can enable flying with /fly");
		p.setGameMode(GameMode.ADVENTURE);
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
