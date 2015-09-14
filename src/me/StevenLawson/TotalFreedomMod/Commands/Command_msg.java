package me.StevenLawson.TotalFreedomMod.Commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Message a player. This is to fix a glitch in Essentials.", usage = "/<command> <player> <message>", aliases = "w,m,t,pm,emsg,epm,tell,etell,whisper,ewhisper,message")
public class Command_msg extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }
        else if (args.length == 1)
        {
            return false;
        }
        else if (args.length == 2)
        {
            final Player player = getPlayer(args[0]);
            if (player == null) {
                playerMsg("Player not found!");
                return true;
            }
            String message = StringUtils.join(args[1], " ");
            
            sender.sendMessage(ChatColor.RED + "Your message has been sent: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message));
            player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + sender.getName() + ChatColor.GREEN + " --> " + ChatColor.GOLD + "me" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message));
        }
        return true;
    }
}
