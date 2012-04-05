package me.itsatacoshop247.GravitySucks;

import org.bukkit.plugin.java.JavaPlugin;

public class GravitySucks extends JavaPlugin 
{
	
	public void onDisable() 
	{
		this.getServer().getScheduler().cancelTasks(this);
	}
	
	public void onEnable() 
	{
		new GravitySucksListener(this);
		getServer().getPluginManager().registerEvents(new GravitySucksListener(this), this);
		loadConfiguration();
	}
	
	private void loadConfiguration() {
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
}