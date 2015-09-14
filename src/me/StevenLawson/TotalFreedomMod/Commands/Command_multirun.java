package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.OP, source = SourceType.BOTH)
@CommandParameters(description = "Run a command a configurable amount of times.", usage = "/<command> <times> <outcommand>")
public class Command_multirun extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!TFM_AdminList.isTelnetAdmin(sender))
        {
            sender.sendMessage(ChatColor.RED + TFM_Command.MSG_NO_PERMS);
            return true;
        }
        if (args.length < 2)
        {
            return false;
        }
        if (Integer.parseInt(args[0]) == 1 || Integer.parseInt(args[0]) == 0)
        {
            TFM_Util.playerMsg(sender, String.format("Why are you trying to run the command %s times?", Integer.parseInt(args[0])), ChatColor.RED);
            return true;
        }
        else if (Integer.parseInt(args[0]) > 150)
        {
            TFM_Util.playerMsg(sender, String.format("Why are you trying to run the command %s times?", Integer.parseInt(args[0])), ChatColor.RED);
            return true;
        }

        String baseCommand = StringUtils.join(args, " ", 1, args.length);

        if (baseCommand.contains("sys"))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, not possible.");
            return true;
        }
        else if (baseCommand.contains("optroll"))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, not possible.");
            return true;
        }
        else if (baseCommand.contains("black"))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, not possible.");
            return true;
        }
        else if (baseCommand.contains("purple"))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, not possible.");
            return true;
        }
        else if (baseCommand.contains("blowup"))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, not possible.");
            return true;
        }
        else if (baseCommand.contains("smite"))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, not possible.");
            return true;
        }
        else if (baseCommand.contains("gcmd"))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, not possible.");
            return true;
        }

        TFM_Util.playerMsg(sender, String.format("Running: %s %s times", baseCommand, Integer.parseInt(args[0])), ChatColor.DARK_BLUE);
        int i = 0;
        do
        {
            Bukkit.dispatchCommand(sender, baseCommand);
            i++;
        }
        while (i < Integer.parseInt(args[0]));
        return true;
    }
}
