package net.oldschoolminecraft.scmd;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerHandler implements Listener
{
    private StaffCMD plugin;

    public PlayerHandler(StaffCMD plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
        boolean usingStaffMode = ModeStatus.usingStaffMode(event.getPlayer().getName());
        boolean allowDrops = (boolean) plugin.getConfig().getConfigOption("itemDrops.allow");
        boolean sendAMessage = (boolean) plugin.getConfig().getConfigOption("itemDrops.sendAMessage");
        if (usingStaffMode && !allowDrops && sendAMessage) event.getPlayer().sendMessage(ChatColor.RED + "The administrator has disabled item drops while using staff mode!");
        event.setCancelled((usingStaffMode && !allowDrops));
    }
}
