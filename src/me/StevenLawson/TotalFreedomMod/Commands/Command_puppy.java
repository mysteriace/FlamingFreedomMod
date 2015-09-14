package me.StevenLawson.TotalFreedomMod.Commands;

import static me.StevenLawson.TotalFreedomMod.Commands.Command_smite.smite;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Puppy!", aliases = "pup", usage = "/<command>")
public class Command_puppy extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        final Location targetPos = sender_p.getLocation();
        final World world = sender_p.getWorld();
        Bukkit.broadcastMessage(ChatColor.RED + "WARNING: " + sender.getName() + " is now a puppy. Woof Woof Mother F**ker");
        sender_p.chat("Woof Woof Woof");
        sender_p.chat(ChatColor.AQUA + "OH NO I SPAMMED 3 TIMES");
        // Emulate smite instead of using it
        TFM_Util.bcastMsg(sender.getName() + " has been a naughty, naughty puppy.\nThey have thus been smitten!", ChatColor.RED);
        for (int x = -1; x <= 1; x++)
        {
            for (int z = -1; z <= 1; z++)
            {
                final Location strike_pos = new Location(world, targetPos.getBlockX() + x, targetPos.getBlockY(), targetPos.getBlockZ() + z);
                world.strikeLightning(strike_pos);
            }
        }
        sender_p.setHealth(0.0);
        sender_p.chat(ChatColor.AQUA + "Okay, I won't be a puppy. I'll be a cat.");
        sender_p.chat(ChatColor.AQUA + "NOOOOOOOOOO. NOT A CAT");
        TFM_Util.bcastMsg(sender.getName() + " has been a naughty, naughty cat.\nThey have thus been smitten!", ChatColor.RED);
        for (int x = -1; x <= 1; x++)
        {
            for (int z = -1; z <= 1; z++)
            {
                final Location strike_pos = new Location(world, targetPos.getBlockX() + x, targetPos.getBlockY(), targetPos.getBlockZ() + z);
                world.strikeLightning(strike_pos);
            }
        }
        sender_p.setHealth(0.0);
        return true;
    }
}
