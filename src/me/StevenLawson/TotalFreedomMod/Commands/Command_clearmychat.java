package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Clear your own chat", usage = "/<command>", aliases = "pcc")
public class Command_clearmychat extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        for (int i = 0; i <= 150; i++)
        {
            sender.sendMessage("");
        }
        sender.sendMessage(ChatColor.GOLD + "You cleared your own chat.");
        return true;
    }
}
