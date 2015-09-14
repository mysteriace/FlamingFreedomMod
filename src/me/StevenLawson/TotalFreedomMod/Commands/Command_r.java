package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Reply to the message you just saw", usage = "/<command> <message>", aliases = "reply")
public class Command_r extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        // TODO: Make a better method than this for me
        
        // Fake a no-one to reply to to make sure that it doesn't go insane
        sender.sendMessage(ChatColor.DARK_RED + "You have no one whom you can reply to.");
//        if (args.length == 0) {
//            return false;
//        }
//        // TODO: Make a better method than this for me
//        
//        if (sendme == null) {
//            sender.sendMessage(ChatColor.DARK_RED + "You have no one whom you can reply to.");
//            return true;
//        }
//        String message = StringUtils.join(args[0], " ");
//            
//            sender.sendMessage(ChatColor.RED + "Your message has been sent: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message));
//            sendme.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + sender.getName() + ChatColor.GREEN + " --> " + ChatColor.GOLD + "me" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message));
        return true;
    }
}
