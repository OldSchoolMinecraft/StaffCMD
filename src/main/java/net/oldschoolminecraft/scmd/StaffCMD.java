package net.oldschoolminecraft.scmd;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class StaffCMD extends JavaPlugin
{
    private PluginConfig config;

    public void onEnable()
    {
        config = new PluginConfig(new File(getDataFolder(), "config.yml"));
        getCommand("staff").setExecutor(new CommandHandler(this));
        getServer().getPluginManager().registerEvents(new PlayerHandler(this), this);
        System.out.println("StaffCMD enabled");
    }

    public PluginConfig getConfig()
    {
        return config;
    }

    public void onDisable()
    {
        System.out.println("StaffCMD disabled");
    }
}
