/*
 * Author: 598Johnn897
 * 
 * Date: Aug 3, 2014
 * Package: com.jkush321.autowalls.util
 *
 */
package com.jkush321.autowalls.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/**
 * Created: Aug 3, 2014 <br>
 * Time: 8:42:35 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: TagUtil.java <br>
 * Package: com.jkush321.autowalls.util
 * <p>
 * 
 * @author 598Johnn897
 */
public class TagUtil {

	/**
	 * This sets a player's prefix on his/her nameplate. This creates a team for
	 * the player using the player's username for the team name then adds the player
	 * to the team and sets the defined prefix. The prefix supports color codes
	 * but can also use a string defined color.
	 * <p>
	 * <b>Note: Prefix can only be 16 characters long! (Color codes count as
	 * two!)</b>
	 * <p>
	 * If the String given is over 16 characters in length, it will be shortened
	 * to 16 character. Again, color codes count as two characters. If prefix
	 * given is equal to null or is left blank it will not change the prefix.
	 * <p>
	 * Example:
	 * <code>
	 * <ul>   
	 *        Player player = event.getPlayer();<br>
	 *        TagUtil.setTagPrefix(player, "Swagmaster");<br>
	 * </ul>
	 * </code>
	 * 
	 * @since AutoWalls 2.0.0-BETA
	 * 
	 * @see ColorUtil#formatColors(String)
	 * @see ChatColor#translateAlternateColorCodes(char, String)
	 * @see Team#setPrefix(String)
	 * 
	 * @author md_5
	 * 
	 * @param player
	 *            The player to add the tag prefix to
	 * @param tag
	 *            The prefix to set to player's nameplate
	 */
	public static void setTagPrefix(Player player, String prefix) {
		Team team = player.getScoreboard().getTeam(
				player.getName().toString());
		if (team == null)
			team = player.getScoreboard().registerNewTeam(
					player.getName().toString());

		if (prefix != null)
			team.setPrefix(ChatColor.translateAlternateColorCodes(
					'&',
					ColorUtil.formatColors(prefix.substring(0,
							Math.min(prefix.length(), 16)))));
		team.addPlayer(player);
	}

	/**
	 * This removes the prefix on a player's nameplate if they have a prefix. To
	 * remove the prefix, this tests if the player has a team registered with
	 * his/her UUID using {@link #setTagPrefix(Player, String)} then sets the
	 * prefix to null. If the team is null it does nothing.
	 * <p>
	 * Example:<br>
	 * <code>
	 * <ul>   
	 *        Player player = event.getPlayer();<br>
	 *        TagUtil.removeTagPrefix(player);<br>
	 * </ul>
	 * </code>
	 * 
	 * @since AutoWalls 2.0.0-BETA
	 * 
	 * @see Team#setPrefix(String)
	 * 
	 * @author md_5
	 * 
	 * @param player
	 *            The player to remove the nameplate prefix from
	 */
	public static void removeTagPrefix(Player player) {
		Team team = player.getScoreboard().getTeam(
				player.getName().toString());
		if (team != null) {
			team.setPrefix("");
		}
	}

	/**
	 * This sets a player's suffix on his/her nameplate. This creates a team for
	 * the player using the player's UUID for the team name then adds the player
	 * to the team and sets the defined suffix. The suffix supports color codes
	 * but can also use a string defined color. If the player already has a team
	 * defined with his/her UUID then it will just change the suffix.
	 * <p>
	 * <b>Note: suffix can only be 16 characters long! (Color codes count as
	 * two!)</b>
	 * <p>
	 * If the String given is over 16 characters in length, it will be shortened
	 * to 16 character. Again, color codes count as two characters. If suffix
	 * given is equal to null or is left blank it will not change the suffix.
	 * <p>
	 * Example:<br>
	 * <code>
	 * <ul>   
	 *        Player player = event.getPlayer();<br>
	 *        TagUtil.setTagSuffix(player, "is Awesome");<br>
	 * </ul>
	 * </code>
	 * 
	 * @since AutoWalls 2.0.0-BETA
	 * 
	 * @see ColorUtil#formatColors(String)
	 * @see ChatColor#translateAlternateColorCodes(char, String)
	 * @see Team#setSuffix(String)
	 * 
	 * @author md_5
	 * 
	 * @param player
	 *            The player to add the tag suffix to
	 * @param tag
	 *            The suffix to set to player's nameplate
	 */
	public static void setTagSuffix(Player player, String suffix) {
		Team team = player.getScoreboard().getTeam(
				player.getName().toString());
		if (team == null)
			team = player.getScoreboard().registerNewTeam(
					player.getName().toString());

		if (suffix != null)
			team.setSuffix(ChatColor.translateAlternateColorCodes(
					'&',
					ColorUtil.formatColors(suffix.substring(0,
							Math.min(suffix.length(), 16)))));
		team.addPlayer(player);
	}

	/**
	 * This removes the suffix on a player's nameplate if they have a suffix. To
	 * remove the suffix, this tests if the player has a team registered with
	 * his/her UUID using {@link #setTagSuffix(Player, String)} then sets the
	 * suffix to null. If the team is null it does nothing.
	 * <p>
	 * Example:
	 * <ul>
	 * <code>
	 * Player player = event.getPlayer();<br>
	 * TagUtil.removeTagSuffix(player);<br>
	 * </ul>
	 * </code>
	 * 
	 * @since AutoWalls 2.0.0-BETA
	 * 
	 * @see Team#setSuffix(String)
	 * 
	 * @author md_5
	 * 
	 * @param player
	 *            The player to remove the nameplate prefix from
	 */
	public static void removeTagSuffix(Player player) {
		Team team = player.getScoreboard().getTeam(
				player.getName().toString());
		if (team != null) {
			team.setSuffix("");
		}
	}

	/**
	 * To remove all tags (prefix and suffix) from the defined player, this
	 * removes player from their unique team then unregisters the team for later
	 * use and to prevent conflict. If the player's team is null nothing will
	 * happen.
	 * <p>
	 * Example:<ul><code>
	 * Player player = event.getPlayer();<br>
	 * TagUtil.removeAllTags(player)<br>
	 * </ul></code>
	 * 
	 * @since AutoWalls 2.0.0-BETA
	 * 
	 * @see Team#removePlayer(org.bukkit.OfflinePlayer)
	 * @see Team#unregister()
	 * 
	 * @author 598Johnn897
	 * 
	 * @param player
	 *            Player to remove all prefixes and suffixes from
	 */
	public static void removeAllTags(Player player) {
		Team team = player.getScoreboard().getTeam(
				player.getName().toString());
		if (team != null) {
			team.removePlayer(player);
			team.unregister();
		}
	}

}
