package net.oldschoolminecraft.scmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements CommandExecutor
{
    private StaffCMD plugin;

    protected CommandHandler(StaffCMD staffCMD)
    {
        this.plugin = staffCMD;
    }

    public File getPlayerFile(String username)
    {
        return new File(plugin.getDataFolder(), username + ".yml");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return true;
        }

        Player player = (Player) sender;
        File playerFile = getPlayerFile(player.getName());

        if (label.equalsIgnoreCase("staff"))
        {
            if (!player.hasPermission("scmd.use"))
            {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }

            Yaml yaml = new Yaml();

            if (playerFile.exists())
            {
                // toggle off
                try (FileReader reader = new FileReader(playerFile))
                {
                    PlayerInventory savedInv = (PlayerInventory) yaml.load(reader);

                    player.getInventory().setContents(savedInv.getContents());
                    player.getInventory().setArmorContents(savedInv.getArmorContents());
                    player.getInventory().setItemInHand(savedInv.getItemInHand());

                    if (!playerFile.delete()) playerFile.deleteOnExit();
                    ModeStatus.disableStaffMode(player.getName());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Staff mode toggled: &cOFF"));
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                    player.sendMessage(ChatColor.GREEN + "There was an error while switching your inventory: " + e.getMessage());
                    return true;
                }
            } else {
                // toggle on
                try (FileWriter writer = new FileWriter(getPlayerFile(player.getName())))
                {
                    yaml.dump(player.getInventory(), writer);
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                    player.sendMessage(ChatColor.GREEN + "There was an error while switching your inventory: " + e.getMessage());
                    return true;
                }

                player.getInventory().clear();
                List<Integer> staffItems = plugin.getConfig().getIntList("staffInventoryIDs", Collections.singletonList(0));
                for (Integer staffItem : staffItems) player.getInventory().addItem(new ItemStack(staffItem, 1));
                ModeStatus.enableStaffMode(player.getName());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Staff mode toggled: &aON"));
            }
        }

        return true;
    }
}
