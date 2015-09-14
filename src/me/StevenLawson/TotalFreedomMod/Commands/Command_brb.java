package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.OP, source = SourceType.BOTH)
@CommandParameters(description = "Busy atm.", usage = "/<command> [on | off]")
public class Command_brb extends TFM_Command
{

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }
        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("on"))
            {
                TFM_Util.adminAction(ChatColor.RED + sender.getName(), "I'll be right back.", false);
                server.dispatchCommand(sender, "afk");
                return true;
            }
            else if (args[0].equalsIgnoreCase("off"))
            {
                TFM_Util.adminAction(ChatColor.AQUA + sender.getName(), "I'm back now.", false);
                return true;
            }
        }
        return true;
    }
}
