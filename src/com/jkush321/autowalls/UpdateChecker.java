package com.jkush321.autowalls;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.jkush321.autowalls.lib.References;

public class UpdateChecker {
	
	@Deprecated
	public static void checkAndSendMessage(Player p)
	{
		try{
			URL url = new URL("http://infacraft.net/autowalls/version.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
			String s = "";
			String response = "";
			while ((s=br.readLine()) != null)
			{
				response+=s;
			}
			
			response=response.trim();
			
			if (!response.equals(References.VERSION)){
				url = new URL("http://infacraft.net/autowalls/majorchanges.txt");
				br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
				s = "";
				response = "";
				while ((s=br.readLine()) != null)
				{
					response+=s;
				}
				
				response=response.trim();
				
				p.sendMessage(ChatColor.DARK_RED + "AutoWalls (" + ChatColor.RED + "v:" + References.VERSION + ChatColor.DARK_RED + ") is outdated! It is recomended that you update to (" + ChatColor.RED + "v:" +response + ChatColor.DARK_RED + ")");
				p.sendMessage(ChatColor.DARK_RED + "Major Changes: " + ChatColor.RED + response);
		}
		}catch (Exception e)
		{
			
		}
	}
}
