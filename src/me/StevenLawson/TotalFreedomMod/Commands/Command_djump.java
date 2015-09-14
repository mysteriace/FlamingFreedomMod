package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.FOPM_TFM_Util;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Changes double jump mode", usage = "/<command> [player]", aliases = "/doublejump")
public class Command_djump extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            FOPM_TFM_Util.setDoubleJumper(sender_p, !FOPM_TFM_Util.isDoubleJumper(sender_p));
            TFM_Util.playerMsg(sender_p, "Double Jump mode set to " + FOPM_TFM_Util.isDoubleJumper(sender_p));
        }
        if (args.length == 1 && TFM_AdminList.isSuperAdmin(sender))
        {
            Player player = getPlayer(args[0]);
            if (player == null)
            {
                TFM_Util.playerMsg(sender, TFM_Command.PLAYER_NOT_FOUND);
            }
            else
            {
                FOPM_TFM_Util.setDoubleJumper(player, !FOPM_TFM_Util.isDoubleJumper(player));
                TFM_Util.playerMsg(player, "Double Jump mode of " + player.getName() + " set to " + FOPM_TFM_Util.isDoubleJumper(player));
            }
        }
        return true;
    }
}
