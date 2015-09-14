package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(description = "Cleans the server", usage = "/<command>")
public class Command_cleanup extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        TFM_Util.bcastMsg(ChatColor.RED + "Atempting to start server cleanup, expect lag for few seconds!");
        server.dispatchCommand(sender, "opall -c");
        server.dispatchCommand(sender, "setl");
        server.dispatchCommand(sender, "purgeall");
        server.dispatchCommand(sender, "tfbanlist purge");
        server.dispatchCommand(sender, "tfipbanlist purge");
        server.dispatchCommand(sender, "glist purge");
        server.dispatchCommand(sender, "tfm reload");
        server.dispatchCommand(sender, "saconfig clean");
        server.dispatchCommand(sender, "cc");
        return true;
    }
}
