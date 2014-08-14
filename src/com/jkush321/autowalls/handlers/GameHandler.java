/*
 * Author: 598Johnn897
 * 
 * Date: Jul 30, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.commands.CommandFramework.ClassEnumerator;
import com.jkush321.autowalls.lib.References;
import com.jkush321.autowalls.team.TeamList;
import com.jkush321.autowalls.util.ColorUtil;
import com.jkush321.autowalls.util.FileUtil;

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

	private AutoWalls plugin = AutoWalls.get();

	public int mapNumber;

	public boolean gameInProgress = false;
	public boolean gameOver = false;
	public boolean canJoin = false;
	public boolean voting = false;
	public boolean tabAPI = false;

	public List<String> playersOnline = new CopyOnWriteArrayList<String>();
	public List<Player> playing = new CopyOnWriteArrayList<Player>();
	public List<Player> spectators = new CopyOnWriteArrayList<Player>();
	public List<Player> dead = new CopyOnWriteArrayList<Player>();

	public void addDeadPlayer(Player player) {
		if (!dead.contains(player))
			dead.add(player);
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
	}

	public void removeDeadPlayer(Player player) {
		if (dead.contains(player))
			dead.remove(player);
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
	}

	public File getPlayerFile(Player player) {
		File file = null;
		try {
			file = new File(
					plugin.getDataFolder() + References.PLAYER_FILE_EXT,
					player.getUniqueId() + References.PLAYER_FILE_FORMAT);
			return file;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public File createPlayerFile(Player player) {
		File dir = new File(plugin.getDataFolder()
				+ References.PLAYER_FILE_EXT);
		File file = new File(plugin.getDataFolder()
				+ References.PLAYER_FILE_EXT, player.getUniqueId()
				+ References.PLAYER_FILE_FORMAT);
		if (!dir.exists()) {
			dir.mkdir();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public YamlConfiguration getPlayerConfig(Player player) {
		File pFile = getPlayerFile(player);
		try {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(pFile);
			return defConfig;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public void savePlayerConfig(Player player) {
		try {
			getPlayerConfig(player).save(getPlayerFile(player));
		} catch (IOException | NullPointerException e) {
			return;
		}
	}

	public int getNextMap() {
		try {
			File mapFile = new File(plugin.getDataFolder(), "next_map");
			if (!mapFile.exists()) {
				try {
					mapFile.createNewFile();
					FileUtil.writeTo(mapFile, "1");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			List<String> lines = new CopyOnWriteArrayList<String>();
			FileInputStream fis = new FileInputStream(mapFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();

			int number;
			try {
				number = Integer.parseInt(lines.get(0));
			} catch (Exception e) {
				return 0;
			}
			return number;
		} catch (IOException e) {
			return 0;
		}
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
	 * Dynamically registers all listeners/events in project.
	 */
	public void registerEvents() {
		Class<?>[] classes = ClassEnumerator.getInstance()
				.getClassesFromThisJar(plugin);
		if (classes == null || classes.length == 0) {
			return;
		}
		plugin.getLogger().log(Level.INFO, "Starting registration of events:");
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
				plugin.getLogger()
						.log(Level.INFO,
								c.getSimpleName()
										+ " does not use the default constructor. Ignoring.");
				// e.printStackTrace();
			}
		}
		plugin.getLogger().log(Level.INFO, "Finished registration of events.");
	}

	public void dropWalls() {
		if (mapNumber == 1) {
			new Location(playing.get(0).getWorld(), 409, 108, -787).getBlock()
					.setType(Material.BEDROCK);
			new Location(playing.get(0).getWorld(), 353, 108, -855).getBlock()
					.setType(Material.BEDROCK);
			new Location(playing.get(0).getWorld(), 285, 108, -799).getBlock()
					.setType(Material.BEDROCK);
			new Location(playing.get(0).getWorld(), 341, 108, -731).getBlock()
					.setType(Material.BEDROCK);
			Runnable drop = new Runnable() {
				@Override
				public void run() {
					new Location(playing.get(0).getWorld(), 409, 110, -787)
							.getBlock().setType(Material.BEDROCK);
					new Location(playing.get(0).getWorld(), 353, 110, -855)
							.getBlock().setType(Material.BEDROCK);
					new Location(playing.get(0).getWorld(), 285, 110, -799)
							.getBlock().setType(Material.BEDROCK);
					new Location(playing.get(0).getWorld(), 341, 110, -731)
							.getBlock().setType(Material.BEDROCK);
				}
			};
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, drop, 20);

			Bukkit.broadcastMessage(ColorUtil.formatString(
					"%s: %s",
					plugin.getPrefix(),
					plugin.getAWConfig().getString(
							"AutoWalls Messages.wallsFall")));
		} else {
			new Location(playing.get(0).getWorld(), -794, 20, -173).getBlock()
					.setType(Material.REDSTONE_TORCH_ON);
			Runnable drop = new Runnable() {
				@Override
				public void run() {
					new Location(playing.get(0).getWorld(), -794, 20, -173)
							.getBlock().setType(Material.AIR);
				}
			};
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, drop, 20);

			Bukkit.broadcastMessage(ColorUtil.formatString(
					"%s: %s",
					plugin.getPrefix(),
					plugin.getAWConfig().getString(
							"AutoWalls Messages.wallsFall")));
		}
	}

	public void startGame() {
		if (gameInProgress)
			return;
		if (!plugin.getTeamHandler().getPlayersOnTeam(TeamList.RED).isEmpty())
			plugin.getTeamHandler().teleportPlayers(mapNumber, TeamList.RED);
		if (!plugin.getTeamHandler().getPlayersOnTeam(TeamList.BLUE).isEmpty())
			plugin.getTeamHandler().teleportPlayers(mapNumber, TeamList.BLUE);
		if (!plugin.getTeamHandler().getPlayersOnTeam(TeamList.GREEN).isEmpty())
			plugin.getTeamHandler().teleportPlayers(mapNumber, TeamList.GREEN);
		if (!plugin.getTeamHandler().getPlayersOnTeam(TeamList.YELLOW)
				.isEmpty())
			plugin.getTeamHandler().teleportPlayers(mapNumber, TeamList.YELLOW);
		for (Player p : playing) {
			p.sendMessage(ColorUtil.formatColors("<gold>Good Luck!"));
			p.getInventory().clear();
			p.getInventory().setArmorContents(
					new ItemStack[] { null, null, null, null });
			if (plugin.getKitHandler().getKit(p) != null) {
				p.getInventory().addItem(
						plugin.getKitHandler().getKit(p).getItemStack());
			}
		}
		gameInProgress = true;
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!playing.contains(p)) {
				spectate(p);
			}
		}
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
	}

	public void spectate(Player p) {
		spectators.add(p);
		p.setAllowFlight(true);
		p.setFlying(true);
		p.setGameMode(GameMode.ADVENTURE);
		p.sendMessage(ColorUtil
				.formatColors("<gold>You are now spectating! Use your compass to navigate."));
		for (Player player : playing) {
			player.hidePlayer(p);
		}
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
				10000, 1, true));
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
	}

	public void endGame(TeamList team, String players) {
		if (!gameInProgress)
			return;
		gameInProgress = false;
		gameOver = true;

		for (Player p : playing) {
			p.sendMessage(ColorUtil.formatColors("<gold>Congrats on winning!"));
		}
		Bukkit.broadcastMessage(ColorUtil
				.formatColors("<white>================="));
		Bukkit.broadcastMessage(ColorUtil.formatString(
				"<gold>The Winning Team Is %s%s!", team.getTeam().getColor(),
				team.getTeam().getName()));
		Bukkit.broadcastMessage(ColorUtil.formatString(
				"<aqua>The Winning Players are %s!", players));
		Bukkit.broadcastMessage(ColorUtil
				.formatColors("<white>================="));

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				Bukkit.broadcastMessage(ColorUtil
						.formatColors("<gold>Voting is now open for 30 seconds! Please vote for the next map!"));

				voting = true;
			}

		}, 20);

		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				plugin.getVoteHandler().start();
			}

		}, 31 * 20);

	}

	public void joinTeam(Player p, TeamList team) {
		if (playing.contains(p)) {
			p.sendMessage(ChatColor.RED + "You are already on a team!");
		} else {
			if (team == TeamList.RED) {
				if (TeamList.RED.getTeam().getPlayers().size() == plugin
						.getTeamHandler().maxTeamSize) {
					p.sendMessage(ColorUtil.formatString(
							"%s: <red>Team %s%s <red>is full!", plugin
									.getPrefix(), team.getTeam().getColor(),
							team.getTeam().getName()));
					return;
				}
				plugin.getTeamHandler().addPlayerToTeam(p, TeamList.RED);
			} else if (team == TeamList.BLUE) {
				if (TeamList.BLUE.getTeam().getPlayers().size() == plugin
						.getTeamHandler().maxTeamSize) {
					p.sendMessage(ColorUtil.formatString(
							"%s: <red>Team %s%s <red>is full!", plugin
									.getPrefix(), team.getTeam().getColor(),
							team.getTeam().getName()));
					return;
				}
				plugin.getTeamHandler().addPlayerToTeam(p, TeamList.BLUE);
			} else if (team == TeamList.GREEN) {
				if (TeamList.GREEN.getTeam().getPlayers().size() == plugin
						.getTeamHandler().maxTeamSize) {
					p.sendMessage(ColorUtil.formatString(
							"%s: <red>Team %s%s <red>is full!", plugin
									.getPrefix(), team.getTeam().getColor(),
							team.getTeam().getName()));
					return;
				}
				plugin.getTeamHandler().addPlayerToTeam(p, TeamList.GREEN);
			} else if (team == TeamList.YELLOW) {
				if (TeamList.YELLOW.getTeam().getPlayers().size() == plugin
						.getTeamHandler().maxTeamSize) {
					p.sendMessage(ColorUtil.formatString(
							"%s: <red>Team %s%s <red>is full!", plugin
									.getPrefix(), team.getTeam().getColor(),
							team.getTeam().getName()));
					return;
				}
				plugin.getTeamHandler().addPlayerToTeam(p, TeamList.YELLOW);
			} else {
				throw new IllegalArgumentException("Invalid Team!");
			}
			playing.add(p);
			p.setAllowFlight(false);
			p.setGameMode(GameMode.SURVIVAL);
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (p != pl && !playing.contains(p))
					p.hidePlayer(pl);
			}
			removeDeadPlayer(p);
			spectators.remove(p);
			Bukkit.broadcastMessage(ColorUtil.formatString(
					"<gold>%s <gold>has joined %s%s <gold>team! %s",
					p.getDisplayName(),
					team.getTeam().getColor(),
					team.getTeam().getName(),
					plugin.getAWConfig()
							.getString("AutoWalls Message.count")
							.replaceAll("%online%", "" + playing.size())
							.replaceAll(
									"%max%",
									"" + plugin.getTeamHandler().maxTeamSize
											* 4)));
			int remaining = (plugin.getTeamHandler().maxTeamSize * 4)
					- playing.size();
			if (remaining == 0 && !gameInProgress) {
				Bukkit.broadcastMessage(ChatColor.GREEN
						+ "It is time for the game to start! " + ChatColor.RED
						+ "Go be the best you can be now!");
				startGame();
			}
			if (gameInProgress) {
				p.sendMessage(ColorUtil.formatString(
						"%s: <red>It is too late to join!", plugin.getPrefix()));
			}
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setExp(0);
			p.setLevel(0);
			p.setNoDamageTicks(60);

			if (plugin.getHandler().tabAPI)
				plugin.getTabHandler().updateTabAll();
		}
	}

	public void leaveTeam(Player p) {
		if (playing.contains(p))
			playing.remove(p);
		plugin.getTeamHandler().removePlayerFromTeam(p);
		if (plugin.getChatHandler().teamChatting.contains(p))
			plugin.getChatHandler().teamChatting.remove(p);
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (pl != p) {
				if (!p.canSee(pl))
					p.showPlayer(pl);
			}
		}
		Bukkit.broadcastMessage(ColorUtil.formatString(
				"<aqua>%s <gold>has left the game!", p.getDisplayName()));
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
		checkStats();
	}

	public void checkStats() {
		if (!gameInProgress)
			return;

		if (plugin.getTeamHandler().getTeam(TeamList.RED).getPlayers().size() == playing
				.size()) {
			String s = "";
			for (Player p : plugin.getTeamHandler().getTeam(TeamList.RED)
					.getPlayers()) {
				s += (ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + ", ");
			}
			s = s.substring(0, s.length() - 4);
			endGame(TeamList.RED, s);
		} else if (plugin.getTeamHandler().getTeam(TeamList.BLUE).getPlayers()
				.size() == playing.size()) {
			String s = "";
			for (Player p : plugin.getTeamHandler().getTeam(TeamList.BLUE)
					.getPlayers()) {
				s += (ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + ", ");
			}
			s = s.substring(0, s.length() - 4);
			endGame(TeamList.BLUE, s);
		} else if (plugin.getTeamHandler().getTeam(TeamList.GREEN).getPlayers()
				.size() == playing.size()) {
			String s = "";
			for (Player p : plugin.getTeamHandler().getTeam(TeamList.GREEN)
					.getPlayers()) {
				s += (ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + ", ");
			}
			s = s.substring(0, s.length() - 4);
			endGame(TeamList.GREEN, s);
		} else if (plugin.getTeamHandler().getTeam(TeamList.YELLOW)
				.getPlayers().size() == playing.size()) {
			String s = "";
			for (Player p : plugin.getTeamHandler().getTeam(TeamList.YELLOW)
					.getPlayers()) {
				s += (ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + ", ");
			}
			s = s.substring(0, s.length() - 4);
			endGame(TeamList.YELLOW, s);
		}
		if (plugin.getHandler().tabAPI)
			plugin.getTabHandler().updateTabAll();
	}
}
