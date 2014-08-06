/*
 * Author: 598Johnn897
 * 
 * Date: Aug 5, 2014
 * Package: com.jkush321.autowalls.handlers
 *
 */
package com.jkush321.autowalls.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.jkush321.autowalls.AutoWalls;
import com.jkush321.autowalls.util.ColorUtil;
import com.jkush321.autowalls.util.FileUtil;

/**
 * Created: Aug 5, 2014 <br>
 * Time: 3:02:32 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: AutoWalls <br>
 * File: VoteHandler.java <br>
 * Package: com.jkush321.autowalls.handlers
 * <p>
 * 
 * @author 598Johnn897
 */
public class VoteHandler implements Runnable {

	private AutoWalls plugin = AutoWalls.get();
	private GameHandler handler = plugin.getHandler();

	public static List<String> votesForWalls1 = new CopyOnWriteArrayList<String>();
	public static List<String> votesForWalls2 = new CopyOnWriteArrayList<String>();

	@SuppressWarnings("deprecation")
	public void run() {
		try {
			handler.voting = false;
			Bukkit.broadcastMessage(ColorUtil
					.formatColors("<gold>The votes are in..."));
			Bukkit.broadcastMessage(ColorUtil.formatString(
					"<aqua>   Walls 1 - Votes: %02d", votesForWalls1.size()));
			Bukkit.broadcastMessage(ColorUtil.formatString(
					"<aqua>   Walls 2 - Votes: %02d", votesForWalls2.size()));
			Thread.sleep(4000);
			if (votesForWalls1.size() != votesForWalls2.size()) {
				if (votesForWalls1.size() > votesForWalls2.size()) {
					this.setNextMap(1);
				} else {
					this.setNextMap(2);
				}
			} else {
				Random r = new Random();
				this.setNextMap(r.nextInt(2) + 1);
			}
			emptyFolder(new File("walls"));
			new File("walls").delete();

			copyFolder(new File("walls" + handler.getNextMap()), new File(
					"walls"));

			for (Player p : Bukkit.getOnlinePlayers()) {
				p.kickPlayer(ColorUtil
						.formatString("<white>Next Map: <aqua>The Walls %d!\n<red>Relog to play in the next game!"));
				handler.playersOnline.clear();
				handler.playing.clear();
				handler.dead.clear();
				handler.gameInProgress = false;
			}
			plugin.getVoteHandler().stop();
			Bukkit.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copyFolder(File src, File dest) throws IOException {
		if (src.isDirectory()) {

			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
		}
	}

	public static void emptyFolder(File dest) throws IOException {
		if (dest.isDirectory()) {
			if (dest.exists()) {
				String files[] = dest.list();

				for (String file : files) {
					File destFile = new File(dest, file);
					emptyFolder(destFile);
				}
			}
		} else {
			dest.delete();
		}
	}

	public void setNextMap(Integer map) throws IOException {
		Files.delete(new File(plugin.getDataFolder(), "next_map").toPath());
		File mapFile = new File(plugin.getDataFolder(), "next_map");
		if (!mapFile.exists()) {
			mapFile.createNewFile();
			FileUtil.writeTo(mapFile, "1");
		}
		FileUtil.writeTo(mapFile, map.toString());
	}

}
