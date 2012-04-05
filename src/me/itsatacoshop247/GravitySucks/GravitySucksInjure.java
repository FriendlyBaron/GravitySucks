package me.itsatacoshop247.GravitySucks;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GravitySucksInjure implements Runnable {

	public final GravitySucks plugin;
	
	private Player player;
	private float falls;
	private boolean flight;
	private Location loc;
	
	public GravitySucksInjure(GravitySucks instance, Player importPlayer, float importfalls, boolean importFlight, Location importLoc)
	{
		this.plugin = instance;
		this.player = importPlayer;
		this.falls = importfalls;
		this.flight = importFlight;
		this.loc = importLoc;
	}
	@Override
	public void run() {
		if(plugin.isEnabled())
		{
			try
			{
				Thread.sleep(3000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		if(plugin.isEnabled())
		{
			player.setFlying(false);
			player.teleport(loc);
			if(GravitySucksListener.locations.get(player) != null)
			{
				GravitySucksListener.locations.remove(player);
			}
			if(!flight)
			{
				player.setAllowFlight(false);
			}
			player.setFallDistance(falls);	
		}
	}
}
