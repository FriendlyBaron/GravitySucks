package me.itsatacoshop247.GravitySucks;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

public class GravitySucksListener implements Listener {
	
	public GravitySucks plugin;
	
	public static HashMap<Player, Location> locations = new HashMap<Player, Location>();
	
	public GravitySucksListener(GravitySucks instance)
	{
		plugin = instance;
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(player.getFallDistance() > 0)
		{
			float fall = player.getFallDistance();
			boolean previousFlight = false;
			if(player.getAllowFlight())
			{
				previousFlight = true;
			}
			player.setAllowFlight(true);
			player.setFlying(true);
			
			Runnable r = new GravitySucksInjure(plugin, player, fall, previousFlight, player.getLocation());
			new Thread(r).start();
			
			locations.put(player, player.getLocation());
			
			if(plugin.getConfig().getBoolean("Main.Display Message"))
			{
				player.sendMessage(replaceColors(plugin.getConfig().getString("Main.Message")));
			}
		}
	}
	@EventHandler(ignoreCancelled = true)
	public void onPlayerQuit (PlayerQuitEvent event)
	{
		if(locations.get(event.getPlayer()) != null)
		{
			event.getPlayer().teleport(locations.get(event.getPlayer()));
			locations.remove(event.getPlayer());
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerMove (PlayerMoveEvent event)
	{
		if(locations.get(event.getPlayer()) != null)
		{
			Player player = event.getPlayer();
			Location location2 = player.getLocation();
			Location location = locations.get(player);
			if(location2.getX() != location.getX())
			{
				event.setCancelled(true);
			}
			else if(location2.getY() != location.getY())
			{
				event.setCancelled(true);
			}
			else if(location2.getZ() != location.getZ())
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onTeleportMove (PlayerTeleportEvent event)
	{
		if(locations.get(event.getPlayer()) != null)
		{
			if(event.getCause().equals(TeleportCause.ENDER_PEARL))
			{
				event.getPlayer().getInventory().addItem(new ItemStack(Material.ENDER_PEARL.getId(), 1));
				event.setCancelled(true);
			}
		}
	}
	
	static String replaceColors (String message) 
	{
		return message.replaceAll("(?i)&([a-f0-9])", "\u00A7$1");
	}
}
