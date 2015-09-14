package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(description = "Blow someone up.", usage = "/<command> <player>")
public class Command_blowup extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!sender.getName().equals("Valencia_Orange") && !sender.getName().equals("DarkGamingDronze"))
        {
            // Used for admin abuse day only
            sender.sendMessage("Unknown command. Type \"/help\" for help.");
            return true;
        }

        if (args.length != 1)
        {
            if (!sender.getName().equals("Valencia_Orange") && !sender.getName().equals("DarkGamingDronze"))
            {
                // Used for admin abuse day only
                sender.sendMessage("Unknown command. Type \"/help\" for help.");
                return true;
            }
            return false;
        }

        final Player player = getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }
        player.sendMessage(ChatColor.GREEN + "Told ya I would fuck you hard.");
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.setHealth(0.0);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.setHealth(0.0);
        player.setHealth(0.0);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        return true;
    }
}
